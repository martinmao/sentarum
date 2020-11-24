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

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.core.model.primitive.Discount.DiscountType;
import io.scleropages.sentarum.promotion.rule.model.AbstractEvaluatorRule;
import io.scleropages.sentarum.promotion.rule.model.promotion.goods.EvaluatorGoodsSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 满减促销规则，其主要目的通过满减让用户购买更多的商品，常见的包括"每满减，每满多少固定减"，"阶梯满减，满多少减多少等"
 * <pre>
 *     支持以下规则定义：
 *
 *     固定满减：只允许设置一个 {@link #overflowDiscounts}。{@link #getOverflowCycleLimit()} 可设置上限，-1为上不封顶
 *      固定金额满减 {@link OverflowDiscount#overflowFee}：满100元触发，可并持续叠加直至上限
 *      固定商品数满减 {@link OverflowDiscount#overflowNum}：满10件触发，可并持续叠加直至上限
 *     阶梯满减：可设置多个 {@link #overflowDiscounts}。不支持 {@link #getOverflowCycleLimit()}
 *      阶梯金额满减 {@link OverflowDiscount#overflowFee}：满100元触发，满300元触发，限定在 {@link #overflowDiscounts} 范围内
 *      阶梯商品数满减 {@link OverflowDiscount#overflowNum}：满10件触发，满30件触发，限定在 {@link #overflowDiscounts} 范围内
 *     满赠规则：
 *      减金额：设置 {@link OverflowDiscount#overflowDiscount}
 *      折扣：设置 {@link OverflowDiscount#overflowDiscount}
 *      发赠品：设置 {@link OverflowDiscount#giftSource}
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OverflowDiscountRule extends AbstractEvaluatorRule {


    public static final Integer BIZ_TYPE_OVERFLOW_DISCOUNT = 23;


    /**
     * 满减条件
     */
    private OverflowDiscountType overflowDiscountType;
    /**
     * 循环满减上限, {@link #getOverflowDiscountType()}=={@link OverflowDiscountType#FIXED_FEE_OVERFLOW} || {@link OverflowDiscountType#FIXED_GOODS_NUM_OVERFLOW} 时有效，-1为上不封顶 <br>
     */
    private Integer overflowCycleLimit;
    /**
     * 满减折扣详情
     */
    private List<OverflowDiscount> overflowDiscounts;

    public OverflowDiscountType getOverflowDiscountType() {
        return overflowDiscountType;
    }

    public Integer getOverflowCycleLimit() {
        return overflowCycleLimit;
    }

    public List<OverflowDiscount> getOverflowDiscounts() {
        return overflowDiscounts;
    }

    public void setOverflowDiscountType(OverflowDiscountType overflowDiscountType) {
        this.overflowDiscountType = overflowDiscountType;
    }

    public void setOverflowCycleLimit(Integer overflowCycleLimit) {
        this.overflowCycleLimit = overflowCycleLimit;
    }

    public void setOverflowDiscounts(List<OverflowDiscount> overflowDiscounts) {
        this.overflowDiscounts = overflowDiscounts;
    }

    /**
     * 满减优惠详情
     */
    public class OverflowDiscount {
        /**
         * 满减折扣，{@link OverflowDiscountType#FIXED_FEE_OVERFLOW} || {@link OverflowDiscountType#STEPPED_FEE_OVERFLOW} 时有效.<br>
         * 折扣类型仅支持 {@link DiscountType#DISCOUNT_WITHOUT_ORIGINAL_PRICE} || {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE}
         */
        private Discount overflowDiscount;
        /**
         * 满件数 {@link OverflowDiscountType#FIXED_GOODS_NUM_OVERFLOW} || {@link OverflowDiscountType#STEPPED_GOODS_NUM_OVERFLOW} 时有效.
         */
        private Integer overflowNum;
        /**
         * 满金额 {@link OverflowDiscountType#FIXED_FEE_OVERFLOW} || {@link OverflowDiscountType#STEPPED_FEE_OVERFLOW} 时有效.
         */
        private Amount overflowFee;
        /**
         * 赠品来源.
         */
        private EvaluatorGoodsSource giftSource;
        /**
         * 满赠商品时有效：用户可选赠品总数，-1为全选
         */
        private Integer userSelectNum;

        public Discount getOverflowDiscount() {
            return overflowDiscount;
        }

        public Integer getOverflowNum() {
            return overflowNum;
        }

        public Amount getOverflowFee() {
            return overflowFee;
        }

        public EvaluatorGoodsSource getGiftSource() {
            return giftSource;
        }

        public Integer getUserSelectNum() {
            return userSelectNum;
        }

        public void setOverflowDiscount(Discount overflowDiscount) {
            this.overflowDiscount = overflowDiscount;
        }

        public void setOverflowNum(Integer overflowNum) {
            this.overflowNum = overflowNum;
        }

        public void setOverflowFee(Amount overflowFee) {
            this.overflowFee = overflowFee;
        }

        public void setGiftSource(EvaluatorGoodsSource giftSource) {
            this.giftSource = giftSource;
        }

        public void setUserSelectNum(Integer userSelectNum) {
            this.userSelectNum = userSelectNum;
        }
    }

    /**
     * 满减类型
     */
    enum OverflowDiscountType {

        /**
         * 满固定金额.
         */
        FIXED_FEE_OVERFLOW(1, "固定满金额", "固定金额满减。 例如：100元减10元，当满200时，则减20元"),
        /**
         * 满阶梯金额.
         */
        STEPPED_FEE_OVERFLOW(2, "阶梯满金额", "阶梯金额满减。 例如：满100减10，满300减50，满500减80"),
        /**
         * 满固定商品数
         */
        FIXED_GOODS_NUM_OVERFLOW(3, "固定满商品数", "固定商品数满减。例如:满5件减5元，当满10件时，则减20元"),
        /**
         * 满阶梯商品数
         */
        STEPPED_GOODS_NUM_OVERFLOW(4, "阶梯满商品数", "阶梯商品数满减。 例如:满5件减5元，满10件减13元，满15件减20");

        private final int ordinal;

        private final String tag;

        private final String desc;

        OverflowDiscountType(int ordinal, String tag, String desc) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public String getDesc() {
            return desc;
        }


        private static final Map<String, OverflowDiscountType> nameMappings = new HashMap<>();
        private static final Map<Integer, OverflowDiscountType> ordinalMappings = new HashMap<>();

        static {
            for (OverflowDiscountType overCondition : OverflowDiscountType.values()) {
                nameMappings.put(overCondition.name(), overCondition);
                ordinalMappings.put(overCondition.getOrdinal(), overCondition);
            }
        }


        public static OverflowDiscountType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static OverflowDiscountType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}

