/**
 * Copyright 2001-2005 The Apache Software Foundation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.scleropages.sentarum.promotion.rule.model.calculator;

import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.core.model.primitive.Discount.DiscountType;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.goods.DetailedGoodsSourceReader.AllOfGoods;
import io.scleropages.sentarum.promotion.goods.DetailedGoodsSourceReader.GoodsHolder;
import io.scleropages.sentarum.promotion.goods.model.GoodsSpecs;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.calculator.GoodsDiscountCalculator;
import io.scleropages.sentarum.promotion.rule.model.AbstractCalculatorRule;
import org.scleropages.core.util.Namings;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * 商品折扣规则，可对活动关联的一组商品来源{@link Activity#goodsSource()}设置折扣. 按级别划分为：
 * <pre>
 *  {@link ActivityClassifiedGoodsSource} 支持 {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE},  {@link DiscountType#DISCOUNT_WITHOUT_ORIGINAL_PRICE}.
 *      当商品来源类型为 {@link ActivityGoodsSource#CLASSIFIED_GOODS_SOURCE_TYPE_BRAND}, 可对所有关联品牌商品设置统一折扣({@link #discount}).
 *      当商品来源类型为 {@link ActivityGoodsSource#CLASSIFIED_GOODS_SOURCE_TYPE_CATEGORY}, 可对所有关联品类下商品设置统一折扣({@link #discount}).
 *      当商品来源类型为 {@link ActivityGoodsSource#CLASSIFIED_GOODS_SOURCE_TYPE_SELLER}, 可对所有关联商家下商品设置统一折扣({@link #discount}).
 *
 *  多品牌、多品类、多店联合促销场景下均摊规则为：品牌A卖出商品原价总和/(品牌A卖出商品原价总和+品牌B卖出商品原价总和)*总折扣金额=品牌A均摊金额.
 *  单品牌，单品类，单店促销场景下：抵扣金额直接从目标主体扣除.
 *
 *  {@link ActivityDetailedGoodsSource} 支持 {@link DiscountType#DISCOUNT},{@link DiscountType#OVERRIDE_AMOUNT},{@link DiscountType#DECREASE_AMOUNT}.
 *      可将规则设置到各个商品或更进一步设置到各个规格.
 *
 *  两种商品源均支持基于会员级别的折扣设置,通过设置一组 {@link UserLevelDiscount} to {@link #userLevelDiscounts},优先级顺序为 discount>userLevelDiscounts.
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class GoodsDiscountRule extends AbstractCalculatorRule {


    public static final String ATTRIBUTE_PREFIX = Namings.snakeCaseName(GoodsDiscountRule.class.getSimpleName()) + ".";

    public static final String ATTRIBUTE_DISCOUNT = ATTRIBUTE_PREFIX + Namings.snakeCaseName("discount");

    public static final String ATTRIBUTE_USER_LEVEL_DISCOUNT = ATTRIBUTE_PREFIX + Namings.snakeCaseName("userLevelDiscounts");

    /**
     * 商品统一折扣.
     */
    private Discount discount;

    /**
     * 商品维度折扣
     */
    private List<GoodsDiscount> goodsDiscounts;

    /**
     * 会员商品统一折扣.
     */
    private List<UserLevelDiscount> userLevelDiscounts;


    public void applyActivityDetailedGoodsSourceConfigure(ActivityDetailedGoodsSource goodsSource) {
        Assert.notEmpty(goodsDiscounts, "goods discounts must not empty while current activity associated a detailed goods source.");
        AllOfGoods allOfGoods = goodsSource.detailedGoodsSourceReader().allOfGoods();
        goodsDiscounts.forEach(goodsDiscount -> {
            GoodsHolder goodsHolder = allOfGoods.goods(goodsDiscount.getNativeGoodsId());
            Assert.notNull(goodsHolder, () -> "no goods configured by id: " + goodsDiscount.getNativeGoodsId());
            if (goodsHolder.emptySpecs()) {
                Assert.isTrue(CollectionUtils.isEmpty(goodsDiscount.getGoodsSpecsDiscounts()), () -> "given goods no specs configured. but provide specs discounts: " + goodsDiscount.getNativeGoodsId());
                Discount discount = goodsDiscount.getDiscount();
                List<UserLevelDiscount> userLevelDiscounts = goodsDiscount.getUserLevelDiscounts();
                if (null != discount) {
                    assertDetailedGoodsSourceDiscount(discount);
                    goodsHolder.get().additionalAttributes().setAttribute(GoodsDiscountRule.ATTRIBUTE_DISCOUNT, discount, true).save();
                } else if (!CollectionUtils.isEmpty(userLevelDiscounts)) {
                    for (UserLevelDiscount userLevelDiscount : userLevelDiscounts) {
                        assertDetailedGoodsSourceDiscount(userLevelDiscount.getDiscount());
                    }
                    goodsHolder.get().additionalAttributes().setAttribute(GoodsDiscountRule.ATTRIBUTE_USER_LEVEL_DISCOUNT, userLevelDiscounts, true).save();
                } else
                    throw new IllegalArgumentException("discount must not be null or user-level discounts must not empty for goods:" + goodsDiscount.getNativeGoodsId());
            } else {
                List<GoodsSpecsDiscount> goodsSpecsDiscounts = goodsDiscount.getGoodsSpecsDiscounts();
                Assert.isTrue(!CollectionUtils.isEmpty(goodsSpecsDiscounts), () -> "given goods has specs configured. but no specs discounts: " + goodsDiscount.getNativeGoodsId());
                goodsSpecsDiscounts.forEach(goodsSpecsDiscount -> {
                    GoodsSpecs goodsSpecs = goodsHolder.goodsSpecs(goodsSpecsDiscount.getNativeGoodsSpecsId());
                    Assert.notNull(goodsSpecs, () -> "no goods specs configured by id: " + goodsSpecsDiscount.getNativeGoodsSpecsId());
                    Discount discount = goodsSpecsDiscount.getDiscount();
                    List<UserLevelDiscount> userLevelDiscounts = goodsSpecsDiscount.getUserLevelDiscounts();
                    if (null != discount) {
                        assertDetailedGoodsSourceDiscount(discount);
                        goodsSpecs.additionalAttributes().setAttribute(GoodsDiscountRule.ATTRIBUTE_DISCOUNT, discount, true).save();
                    } else if (!CollectionUtils.isEmpty(userLevelDiscounts)) {
                        for (UserLevelDiscount userLevelDiscount : userLevelDiscounts) {
                            assertDetailedGoodsSourceDiscount(userLevelDiscount.getDiscount());
                        }
                        goodsSpecs.additionalAttributes().setAttribute(GoodsDiscountRule.ATTRIBUTE_USER_LEVEL_DISCOUNT, userLevelDiscounts, true).save();
                    } else
                        throw new IllegalArgumentException("discount must not be null or user-level discounts must not empty for goods specs:" + goodsSpecsDiscount.getNativeGoodsSpecsId());
                });
            }
        });
    }


    public void applyActivityClassifiedGoodsSourceConfigure(ActivityClassifiedGoodsSource goodsSource) {
        Assert.isTrue(CollectionUtils.isEmpty(getGoodsDiscounts()), "goodsDiscounts must empty while activity associates classified goods source");
        Discount discount = getDiscount();
        Assert.notNull(discount, "discount must not be null for goods source: " + goodsSource.id());
        assertClassifiedGoodsSourceDiscount(discount);
        goodsSource.additionalAttributes().setAttribute(GoodsDiscountRule.ATTRIBUTE_DISCOUNT, discount, true).save();
    }

    private void assertDetailedGoodsSourceDiscount(Discount discount) {
        discount.assertDiscount();
        DiscountType discountType = discount.getDiscountType();
        Assert.isTrue(
                Objects.equals(discountType, DiscountType.DISCOUNT)
                        || Objects.equals(discountType, DiscountType.DECREASE_AMOUNT)
                        || Objects.equals(discountType, DiscountType.OVERRIDE_AMOUNT)
                , "not supported discount type for detailed goods source");
    }

    private void assertClassifiedGoodsSourceDiscount(Discount discount) {
        discount.assertDiscount();
        DiscountType discountType = discount.getDiscountType();
        Assert.isTrue(
                Objects.equals(discountType, DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE)
                        || Objects.equals(discountType, DiscountType.DISCOUNT_WITHOUT_ORIGINAL_PRICE)
                , "not supported discount type for classified goods source.");
    }


    public GoodsDiscountRule() {
    }

    public GoodsDiscountRule(Discount discount) {
        this.discount = discount;
    }

    public GoodsDiscountRule(List<UserLevelDiscount> userLevelDiscounts) {
        this.userLevelDiscounts = userLevelDiscounts;
    }

    public GoodsDiscountRule(Discount discount, List<GoodsDiscount> goodsDiscounts) {
        this.discount = discount;
        this.goodsDiscounts = goodsDiscounts;
    }


    public Discount getDiscount() {
        return discount;
    }

    public List<GoodsDiscount> getGoodsDiscounts() {
        return goodsDiscounts;
    }

    public List<UserLevelDiscount> getUserLevelDiscounts() {
        return userLevelDiscounts;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setGoodsDiscounts(List<GoodsDiscount> goodsDiscounts) {
        this.goodsDiscounts = goodsDiscounts;
    }

    public void setUserLevelDiscounts(List<UserLevelDiscount> userLevelDiscounts) {
        this.userLevelDiscounts = userLevelDiscounts;
    }

    @Override
    protected Integer defaultRuleInvocationImplementation() {
        return GoodsDiscountCalculator.ID;
    }

    /**
     * 商品折扣
     */
    public static class GoodsDiscount {

        /**
         * 本地商品id.
         */
        private Long nativeGoodsId;

        /**
         * 规格统一折扣.
         */
        private Discount discount;

        /**
         * 规格维度折扣.
         */
        private List<GoodsSpecsDiscount> goodsSpecsDiscounts;

        /**
         * 会员规格统一折扣.
         */
        private List<UserLevelDiscount> userLevelDiscounts;

        public GoodsDiscount() {
        }

        public GoodsDiscount(Long nativeGoodsId, Discount discount, List<GoodsSpecsDiscount> goodsSpecsDiscounts) {
            this.nativeGoodsId = nativeGoodsId;
            this.discount = discount;
            this.goodsSpecsDiscounts = goodsSpecsDiscounts;
        }

        public GoodsDiscount(Long nativeGoodsId, List<UserLevelDiscount> userLevelDiscounts) {
            this.nativeGoodsId = nativeGoodsId;
            this.userLevelDiscounts = userLevelDiscounts;
        }

        public GoodsDiscount(Long nativeGoodsId, Discount discount) {
            this.nativeGoodsId = nativeGoodsId;
            this.discount = discount;
        }

        @NotNull(groups = Create.class)
        public Long getNativeGoodsId() {
            return nativeGoodsId;
        }

        public Discount getDiscount() {
            return discount;
        }

        public List<GoodsSpecsDiscount> getGoodsSpecsDiscounts() {
            return goodsSpecsDiscounts;
        }

        public List<UserLevelDiscount> getUserLevelDiscounts() {
            return userLevelDiscounts;
        }

        public void setNativeGoodsId(Long nativeGoodsId) {
            this.nativeGoodsId = nativeGoodsId;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        public void setGoodsSpecsDiscounts(List<GoodsSpecsDiscount> goodsSpecsDiscounts) {
            this.goodsSpecsDiscounts = goodsSpecsDiscounts;
        }

        public void setUserLevelDiscounts(List<UserLevelDiscount> userLevelDiscounts) {
            this.userLevelDiscounts = userLevelDiscounts;
        }
    }

    /**
     * 商品规格折扣
     */
    public static class GoodsSpecsDiscount {

        /**
         * 本地商品规格id.
         */
        private Long nativeGoodsSpecsId;
        /**
         * 规格折扣.
         */
        private Discount discount;

        /**
         * 会员规格折扣.
         */
        private List<UserLevelDiscount> userLevelDiscounts;

        public GoodsSpecsDiscount() {

        }

        public GoodsSpecsDiscount(Long nativeGoodsSpecsId, Discount discount) {
            this.nativeGoodsSpecsId = nativeGoodsSpecsId;
            this.discount = discount;
        }

        public GoodsSpecsDiscount(Long nativeGoodsSpecsId, List<UserLevelDiscount> userLevelDiscounts) {
            this.nativeGoodsSpecsId = nativeGoodsSpecsId;
            this.userLevelDiscounts = userLevelDiscounts;
        }

        @NotNull(groups = Create.class)
        public Long getNativeGoodsSpecsId() {
            return nativeGoodsSpecsId;
        }

        public Discount getDiscount() {
            return discount;
        }

        public List<UserLevelDiscount> getUserLevelDiscounts() {
            return userLevelDiscounts;
        }

        public void setNativeGoodsSpecsId(Long nativeGoodsSpecsId) {
            this.nativeGoodsSpecsId = nativeGoodsSpecsId;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        public void setUserLevelDiscounts(List<UserLevelDiscount> userLevelDiscounts) {
            this.userLevelDiscounts = userLevelDiscounts;
        }
    }


    public static class UserLevelDiscount {
        /**
         * 折扣
         */
        private Discount discount;
        /**
         * 用户类型id
         */
        private Long typeId;
        /**
         * 用户等级id
         */
        private Long levelId;
        /**
         * 用户等级名称
         */
        private String levelName;

        public UserLevelDiscount() {
        }

        public UserLevelDiscount(Discount discount, Long typeId, Long levelId, String levelName) {
            this.discount = discount;
            this.typeId = typeId;
            this.levelId = levelId;
            this.levelName = levelName;
        }

        @NotNull(groups = Create.class)
        public Discount getDiscount() {
            return discount;
        }

        @NotNull(groups = Create.class)
        public Long getTypeId() {
            return typeId;
        }

        @NotNull(groups = Create.class)
        public Long getLevelId() {
            return levelId;
        }

        @NotEmpty(groups = Create.class)
        public String getLevelName() {
            return levelName;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        public void setTypeId(Long typeId) {
            this.typeId = typeId;
        }

        public void setLevelId(Long levelId) {
            this.levelId = levelId;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }
    }

}
