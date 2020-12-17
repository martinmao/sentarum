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

import io.scleropages.sentarum.promotion.rule.invocation.promotion.calculator.OverflowDiscountCalculator;
import io.scleropages.sentarum.promotion.rule.model.BaseCalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorInitializableRule;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource.GOODS_SOURCE_TYPE_OVERFLOW_DISCOUNT;

/**
 * 满减促销规则，其主要目的通过满减让用户购买更多的商品，常见的包括"每满减，每满多少固定减"，"阶梯满减，满多少减多少等"
 * <pre>
 *     支持以下规则定义：
 *
 *     固定满减：只允许设置一个 {@link #detailedConfigures}。{@link #getOverflowCycleLimit()} 可设置上限，-1为上不封顶
 *      固定金额满减 {@link OverflowDiscount#setOverflowFee}：满100元触发，可并持续叠加直至上限
 *      固定商品数满减 {@link OverflowDiscount#setOverflowNum}：满10件触发，可并持续叠加直至上限
 *     阶梯满减：可设置多个 {@link #detailedConfigures}。不支持 {@link #getOverflowCycleLimit()}
 *      阶梯金额满减 {@link OverflowDiscount#setOverflowFee}：满100元触发，满300元触发，限定在 {@link #detailedConfigures} 范围内
 *      阶梯商品数满减 {@link OverflowDiscount#setOverflowNum}：满10件触发，满30件触发，限定在 {@link #detailedConfigures} 范围内
 *     满赠说明：
 *      减金额：设置 {@link OverflowDiscount#getOverflowDiscount}
 *      折扣：设置 {@link OverflowDiscount#getOverflowDiscount}
 *      发赠品：匹配到{@link OverflowDiscount} 挂靠的 {@link io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource}
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OverflowDiscountRule extends BaseCalculatorRule implements CalculatorInitializableRule<OverflowDiscount> {

    /**
     * 满减类型
     */
    private OverflowDiscountType overflowDiscountType;
    /**
     * 循环满减上限,仅限固定满减 {@link #getOverflowDiscountType()}=={@link OverflowDiscountType#FIXED_FEE_OVERFLOW} || {@link OverflowDiscountType#FIXED_GOODS_NUM_OVERFLOW} 时有效，-1为上不封顶
     */
    private Integer overflowCycleLimit;

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public OverflowDiscountType getOverflowDiscountType() {
        return overflowDiscountType;
    }

    public Integer getOverflowCycleLimit() {
        return overflowCycleLimit;
    }

    public void setOverflowDiscountType(OverflowDiscountType overflowDiscountType) {
        this.overflowDiscountType = overflowDiscountType;
    }

    public void setOverflowCycleLimit(Integer overflowCycleLimit) {
        this.overflowCycleLimit = overflowCycleLimit;
    }

    @Override
    protected Integer defaultRuleInvocationImplementation() {
        return OverflowDiscountCalculator.ID;
    }


    @Override
    public Integer goodsSourceType() {
        return GOODS_SOURCE_TYPE_OVERFLOW_DISCOUNT;
    }

    @Override
    public List<OverflowDiscount> detailedConfigures() {
        throw new IllegalStateException("not initialized.");
    }

    /**
     * 满减类型
     */
    public enum OverflowDiscountType {

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

