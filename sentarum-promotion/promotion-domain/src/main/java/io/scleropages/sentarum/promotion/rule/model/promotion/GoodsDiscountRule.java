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
package io.scleropages.sentarum.promotion.rule.model.promotion;

import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.core.model.primitive.Discount.DiscountType;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.AbstractEvaluatorRule;
import org.scleropages.core.util.Namings;

import java.util.List;

/**
 * 商品折扣规则，可对活动关联的一组商品来源{@link Activity#goodsSource()}设置折扣. 按级别划分为：
 * <pre>
 *  {@link ActivityClassifiedGoodsSource} 仅支持 {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE},  {@link DiscountType#DISCOUNT_WITHOUT_ORIGINAL_PRICE}.
 *      当商品来源类型为 {@link ActivityGoodsSource#CLASSIFIED_GOODS_SOURCE_TYPE_BRAND}, 可对所有关联品牌商品设置统一折扣({@link #discount}).
 *      当商品来源类型为 {@link ActivityGoodsSource#CLASSIFIED_GOODS_SOURCE_TYPE_CATEGORY}, 可对所有关联品类下商品设置统一折扣({@link #discount}).
 *      当商品来源类型为 {@link ActivityGoodsSource#CLASSIFIED_GOODS_SOURCE_TYPE_SELLER}, 可对所有关联商家下商品设置统一折扣({@link #discount}).
 *
 *  多品牌、多品类、多店联合促销场景下均摊规则为：品牌A卖出商品原价总和/(品牌A卖出商品原价总和+品牌B卖出商品原价总和)*总折扣金额=品牌A均摊金额.
 *  单品牌，单品类，单店促销场景下：抵扣金额直接从目标主体扣除.
 *
 *  {@link ActivityDetailedGoodsSource} 仅支持 {@link DiscountType#DISCOUNT},{@link DiscountType#OVERRIDE_AMOUNT},{@link DiscountType#DECREASE_AMOUNT}.
 *      可将规则设置到各个商品或更进一步设置到各个规格.
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class GoodsDiscountRule extends AbstractEvaluatorRule {


    public static final String ATTRIBUTE_PREFIX = Namings.snakeCaseName(GoodsDiscountRule.class.getSimpleName()) + ".";

    public static final String ATTRIBUTE_DISCOUNT = ATTRIBUTE_PREFIX + "discount";


    /**
     * 统一折扣.
     */
    private Discount discount;

    /**
     * 商品维度折扣
     */
    private List<GoodsDiscount> goodsDiscounts;


    public GoodsDiscountRule() {
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

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setGoodsDiscounts(List<GoodsDiscount> goodsDiscounts) {
        this.goodsDiscounts = goodsDiscounts;
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
         * 商品折扣.
         */
        private Discount discount;

        /**
         * 规格维度折扣.
         */
        private List<GoodsSpecsDiscount> goodsSpecsDiscounts;

        public GoodsDiscount() {
        }

        public GoodsDiscount(Long nativeGoodsId, Discount discount, List<GoodsSpecsDiscount> goodsSpecsDiscounts) {
            this.nativeGoodsId = nativeGoodsId;
            this.discount = discount;
            this.goodsSpecsDiscounts = goodsSpecsDiscounts;
        }

        public Long getNativeGoodsId() {
            return nativeGoodsId;
        }

        public Discount getDiscount() {
            return discount;
        }

        public List<GoodsSpecsDiscount> getGoodsSpecsDiscounts() {
            return goodsSpecsDiscounts;
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

        public GoodsSpecsDiscount() {

        }

        public GoodsSpecsDiscount(Long nativeGoodsSpecsId, Discount discount) {
            this.nativeGoodsSpecsId = nativeGoodsSpecsId;
            this.discount = discount;
        }

        public Long getNativeGoodsSpecsId() {
            return nativeGoodsSpecsId;
        }

        public Discount getDiscount() {
            return discount;
        }

        public void setNativeGoodsSpecsId(Long nativeGoodsSpecsId) {
            this.nativeGoodsSpecsId = nativeGoodsSpecsId;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }
    }

}
