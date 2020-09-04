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

import io.scleropages.sentarum.promotion.activity.model.Promotion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 满减促销，其主要目的通过满减让用户购买更多的商品，常见的包括"每满减，每满多少固定减多少"，"阶梯满减，满多少减多少"
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OverflowPromotion extends Promotion {


    /**
     * 触发条件
     *
     * @return
     */
    ConditionType conditionType();

    /**
     * 触发规则的结果
     *
     * @return
     */
    ResultType resultType();

    /**
     * 满金额 {@link #conditionType()}=={@link ConditionType#AMOUNT_OVER_ONCE}||{@link ConditionType#AMOUNT_OVER_MULTIPLE}||{@link ConditionType#AMOUNT_OVER_STEP}时有效
     *
     * @return
     */
    BigDecimal fireAmount();

    /**
     * 满件数量, {@link #conditionType()}=={@link ConditionType#ITEM_COUNT_OVER}时有效
     *
     * @return
     */
    Integer fireItemCount();


    /**
     * 规则触发条件
     */
    enum ConditionType {

        AMOUNT_OVER_ONCE(0, "满金额(一次)", "满指定金额时触发，只能触发一次"),
        AMOUNT_OVER_MULTIPLE(1, "满金额（多次，不封顶）", "满金额时触发，可多次触发上不封顶，例如：100元减10元，当满200时，则减20元"),
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

        ConditionType(int ordinal, String tag, String desc) {
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


        private static final Map<String, ConditionType> nameMappings = new HashMap<>();
        private static final Map<Integer, ConditionType> ordinalMappings = new HashMap<>();

        static {
            for (ConditionType conditionType : ConditionType.values()) {
                nameMappings.put(conditionType.name(), conditionType);
                ordinalMappings.put(conditionType.getOrdinal(), conditionType);
            }
        }


        public static ConditionType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ConditionType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }


    /**
     * 规则触发后的结果
     */
    enum ResultType {

        DECREASE_MONEY(0, "减金额", "满足条件后减少金额，例如满100减5元"),
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

        ResultType(int ordinal, String tag, String desc) {
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


        private static final Map<String, ResultType> nameMappings = new HashMap<>();
        private static final Map<Integer, ResultType> ordinalMappings = new HashMap<>();

        static {
            for (ResultType resultType : ResultType.values()) {
                nameMappings.put(resultType.name(), resultType);
                ordinalMappings.put(resultType.getOrdinal(), resultType);
            }
        }


        public static ResultType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ResultType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}

