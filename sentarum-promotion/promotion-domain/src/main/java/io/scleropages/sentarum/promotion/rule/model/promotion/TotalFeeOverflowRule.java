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
import io.scleropages.sentarum.promotion.goods.model.DetailedGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.AbstractEvaluatorRule;

import java.util.HashMap;
import java.util.Map;

/**
 * 满减促销规则定义，其主要目的通过满减让用户购买更多的商品，常见的包括"每满减，每满多少固定减"，"阶梯满减，满多少减多少等"
 * <pre>
 *     支持以下规则定义：
 *     金额维度：
 *     固定满减规则：满金额时触发，例如：满100元减10元，当满200时，则减20元, {@link #getOverCycleLimit()}} 控制循环上限
 *     阶梯满减规则：满100减10，满300减50，满500减80，不支持 {@link #getOverCycleLimit()}
 *     固定满赠规则：满100送牙膏,当满200时，送两只牙膏 {@link #getOverCycleLimit()} 参数可控制循环上限
 *     阶梯满赠规则：满100送牙膏，满200送牙刷，满300+50送电动牙刷, 不支持 {@link #getOverCycleLimit()}
 *
 *     商品数量维度：
 *     固定满件减规则：满件数时触发，例如：满10件减10元，当满20件时，则减20元, {@link #getOverCycleLimit()} 控制循环上限
 *     阶梯满件减规则：满10件减10，满30件减50，满50件减80，不支持 {@link #getOverCycleLimit()}
 *     固定满件赠规则：满10件送牙膏,当满20件时，送两只牙膏 {@link #getOverCycleLimit()} 参数可以控制是否支持满元上不封顶
 *     阶梯满件赠规则：满10件送牙膏，满20件送牙刷，满30件+50送电动牙刷,不支持 {@link #getOverCycleLimit()}
 *
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class TotalFeeOverflowRule extends AbstractEvaluatorRule {


    /**
     * 满减条件
     *
     * @return
     */
    private OverflowCondition overflowCondition;


    /**
     * 循环满减上限, {@link #getOverflowCondition()}=={@link OverflowCondition#FIXED_FEE_OVERFLOW} || {@link OverflowCondition#GOODS_NUM_OVERFLOW} 时有效，-1为上不封顶 <br>
     *
     * @return
     */
    private Integer overCycleLimit;


    public OverflowCondition getOverflowCondition() {
        return overflowCondition;
    }

    public Integer getOverCycleLimit() {
        return overCycleLimit;
    }

    public void setOverflowCondition(OverflowCondition overflowCondition) {
        this.overflowCondition = overflowCondition;
    }

    public void setOverCycleLimit(Integer overCycleLimit) {
        this.overCycleLimit = overCycleLimit;
    }

    /**
     * 满减优惠
     */
    public class OverFlowDetail {

        /**
         * 满减折扣，{@link OverflowCondition#FIXED_FEE_OVERFLOW} || {@link OverflowCondition#STEPPED_FEE_OVERFLOW} 时有效.<br>
         * 折扣类型仅支持 {@link DiscountType#DISCOUNT_WITHOUT_ORIGINAL_PRICE} || {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE}
         */
        private Discount overflowDiscount;

        /**
         * 满金额 {@link OverflowCondition#FIXED_FEE_OVERFLOW} || {@link OverflowCondition#STEPPED_FEE_OVERFLOW} 时有效.
         */
        private Amount overflowFee;

        /**
         * 满件数 {@link OverflowCondition#GOODS_NUM_OVERFLOW} 时有效.
         */
        private Integer overflowNum;

        /**
         * 满赠商品，
         */
        private DetailedGoodsSource presentGoodsSource;


        public Discount getOverflowDiscount() {
            return overflowDiscount;
        }

        public Amount getOverflowFee() {
            return overflowFee;
        }

        public Integer getOverflowNum() {
            return overflowNum;
        }

        public DetailedGoodsSource getPresentGoodsSource() {
            return presentGoodsSource;
        }

        public void setOverflowDiscount(Discount overflowDiscount) {
            this.overflowDiscount = overflowDiscount;
        }

        public void setOverflowFee(Amount overflowFee) {
            this.overflowFee = overflowFee;
        }

        public void setOverflowNum(Integer overflowNum) {
            this.overflowNum = overflowNum;
        }

        public void setPresentGoodsSource(DetailedGoodsSource presentGoodsSource) {
            this.presentGoodsSource = presentGoodsSource;
        }
    }

    /**
     * 规则触发条件
     */
    enum OverflowCondition {

        /**
         * 满固定金额.
         */
        FIXED_FEE_OVERFLOW(1, "满固定金额", "固定金额满减，可多次触发。例如：100元减10元，当满200时，则减20元"),
        /**
         * 满阶梯金额.
         */
        STEPPED_FEE_OVERFLOW(2, "阶梯式满金额", "阶梯金额满减。例如：满100减10，满300减50，满500减80"),
        /**
         * 满商品件数
         */
        GOODS_NUM_OVERFLOW(3, "满件数", "满指定件数商品时触发");

        private final int ordinal;

        private final String tag;

        private final String desc;

        OverflowCondition(int ordinal, String tag, String desc) {
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


        private static final Map<String, OverflowCondition> nameMappings = new HashMap<>();
        private static final Map<Integer, OverflowCondition> ordinalMappings = new HashMap<>();

        static {
            for (OverflowCondition overCondition : OverflowCondition.values()) {
                nameMappings.put(overCondition.name(), overCondition);
                ordinalMappings.put(overCondition.getOrdinal(), overCondition);
            }
        }


        public static OverflowCondition getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static OverflowCondition getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}

