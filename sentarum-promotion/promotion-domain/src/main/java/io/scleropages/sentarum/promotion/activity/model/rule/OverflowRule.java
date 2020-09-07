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
package io.scleropages.sentarum.promotion.activity.model.rule;

import io.scleropages.sentarum.promotion.activity.model.PromotionRule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 满减促销规则定义，其主要目的通过满减让用户购买更多的商品，常见的包括"每满减，每满多少固定减"，"阶梯满减，满多少减多少"
 * <pre>
 *     支持以下规则定义：
 *     金额维度：
 *     固定满减规则：满金额时触发，例如：满100元减10元，当满200时，则减20元, {@link #overCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满减规则：满100减10，满300减50，满500减80，暂不支持 {@link #overCycle()}
 *     固定满赠规则：满100送牙膏,当满200时，送两只牙膏 {@link #overCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满赠规则：满100送牙膏，满200送牙刷，满300+50送电动牙刷, 暂不支持 {@link #overCycle()}
 *
 *     商品数量维度：
 *     固定满件减规则：满件数时触发，例如：满10件减10元，当满20件时，则减20元, {@link #overCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满件减规则：满10件减10，满30件减50，满50件减80，暂不支持 {@link #overCycle()}
 *     固定满件赠规则：满10件送牙膏,当满20件时，送两只牙膏 {@link #overCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满件赠规则：满10件送牙膏，满20件送牙刷，满30件+50送电动牙刷,不支持 {@link #overCycle()}
 *
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OverflowRule extends PromotionRule {

    /**
     * 触发条件
     *
     * @return
     */
    OverCondition overCondition();

    /**
     * 触发规则的结果
     *
     * @return
     */
    OverResult overResult();

    /**
     * 满金额 {@link #overCondition()}=={@link OverCondition#AMOUNT_OVER_FIX}||{@link OverCondition#AMOUNT_OVER_STEP}时有效
     *
     * @return
     */
    BigDecimal overAmount();

    /**
     * 满件数量, {@link #overCondition()}=={@link OverCondition#ITEM_COUNT_OVER}时有效
     *
     * @return
     */
    Integer itemOverCount();


    /**
     * 是否支持循环满减, {@link #overCondition()}=={@link OverCondition#AMOUNT_OVER_FIX} || {@link OverCondition#ITEM_COUNT_OVER} 时有效，true为上不封顶，false为仅一次<br>
     * true=满100元减10元，当满200时，则减20元,false=满100元减10元，当满200时，还是减10元
     *
     * @return
     */
    Boolean overCycle();


    /**
     * 规则触发条件
     */
    enum OverCondition {

        AMOUNT_OVER_FIX(0, "满固定金额", "满金额时触发，可多次触发上不封顶，例如：100元减10元，当满200时，则减20元"),
        AMOUNT_OVER_STEP(2, "满金额（阶梯式）", "例如：满100减10，满300减50，满500减80"),
        ITEM_COUNT_OVER(3, "满件数", "满指定件数商品时触发");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        OverCondition(int ordinal, String tag, String desc) {
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


        private static final Map<String, OverCondition> nameMappings = new HashMap<>();
        private static final Map<Integer, OverCondition> ordinalMappings = new HashMap<>();

        static {
            for (OverCondition overCondition : OverCondition.values()) {
                nameMappings.put(overCondition.name(), overCondition);
                ordinalMappings.put(overCondition.getOrdinal(), overCondition);
            }
        }


        public static OverCondition getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static OverCondition getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }


    /**
     * 规则触发后的结果
     */
    enum OverResult {

        DECREASE_AMOUNT(0, "减金额", "满足条件后减少金额，例如满100减5元"),
        DISCOUNT(1, "打折扣", "满足条件后打折,例如满300打8折"),
        SEND_GIFT(2, "发赠品", "满足条件后发放赠品，例如满200送牙刷");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        OverResult(int ordinal, String tag, String desc) {
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


        private static final Map<String, OverResult> nameMappings = new HashMap<>();
        private static final Map<Integer, OverResult> ordinalMappings = new HashMap<>();

        static {
            for (OverResult overResult : OverResult.values()) {
                nameMappings.put(overResult.name(), overResult);
                ordinalMappings.put(overResult.getOrdinal(), overResult);
            }
        }


        public static OverResult getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static OverResult getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }


    /**
     * 满减金额设置
     */
    class DecreaseAmount {

        /**
         * 满额
         */
        private BigDecimal overAmount;
        /**
         * 减额
         */
        private BigDecimal decreaseAmount;

        public BigDecimal getOverAmount() {
            return overAmount;
        }

        public void setOverAmount(BigDecimal overAmount) {
            this.overAmount = overAmount;
        }

        public BigDecimal getDecreaseAmount() {
            return decreaseAmount;
        }

        public void setDecreaseAmount(BigDecimal decreaseAmount) {
            this.decreaseAmount = decreaseAmount;
        }
    }


    /**
     * 满打折设置
     */
    class DecreaseDiscount {
        /**
         * 满额
         */
        private BigDecimal overAmount;
        /**
         * 折扣
         */
        private BigDecimal discount;

        public BigDecimal getOverAmount() {
            return overAmount;
        }

        public void setOverAmount(BigDecimal overAmount) {
            this.overAmount = overAmount;
        }

        public BigDecimal getDiscount() {
            return discount;
        }

        public void setDiscount(BigDecimal discount) {
            this.discount = discount;
        }
    }


}

