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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 满减促销规则定义，其主要目的通过满减让用户购买更多的商品，常见的包括"每满减，每满多少固定减"，"阶梯满减，满多少减多少"
 * <pre>
 *     支持以下规则定义：
 *     金额维度：
 *     固定满减规则：满金额时触发，例如：满100元减10元，当满200时，则减20元, {@link #getOverCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满减规则：满100减10，满300减50，满500减80，暂不支持 {@link #getOverCycle()}
 *     固定满赠规则：满100送牙膏,当满200时，送两只牙膏 {@link #getOverCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满赠规则：满100送牙膏，满200送牙刷，满300+50送电动牙刷, 暂不支持 {@link #getOverCycle()}
 *
 *     商品数量维度：
 *     固定满件减规则：满件数时触发，例如：满10件减10元，当满20件时，则减20元, {@link #getOverCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满件减规则：满10件减10，满30件减50，满50件减80，暂不支持 {@link #getOverCycle()}
 *     固定满件赠规则：满10件送牙膏,当满20件时，送两只牙膏 {@link #getOverCycle()} 参数可以控制是否支持满元上不封顶
 *     阶梯满件赠规则：满10件送牙膏，满20件送牙刷，满30件+50送电动牙刷,不支持 {@link #getOverCycle()}
 *
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OverflowRule extends AbstractPromotionRule {


    /**
     * 触发条件
     *
     * @return
     */
    private OverCondition overCondition;

    /**
     * 触发规则的结果
     *
     * @return
     */
    private OverResult overResult;
    /**
     * 是否支持循环满减, {@link #getOverCondition()}=={@link OverCondition#AMOUNT_OVER_FIX} || {@link OverCondition#ITEM_COUNT_OVER} 时有效，true为上不封顶，false为仅一次<br>
     * true=满100元减10元，当满200时，则减20元,false=满100元减10元，当满200时，还是减10元
     *
     * @return
     */
    private Boolean overCycle;


    /**
     * 一组与当前规则绑定的促销设置
     */
    private List<? extends OverResultConfig> overResultConfigs;


    public OverCondition getOverCondition() {
        return overCondition;
    }

    public void setOverCondition(OverCondition overCondition) {
        this.overCondition = overCondition;
    }


    public OverResult getOverResult() {
        return overResult;
    }

    public void setOverResult(OverResult overResult) {
        this.overResult = overResult;
    }

    public Boolean getOverCycle() {
        return overCycle;
    }

    public void setOverCycle(Boolean overCycle) {
        this.overCycle = overCycle;
    }


    public List<? extends OverResultConfig> getOverResultConfigs() {
        return overResultConfigs;
    }

    public void setOverResultConfigs(List<? extends OverResultConfig> overResultConfigs) {
        this.overResultConfigs = overResultConfigs;
    }

    /**
     * 规则触发条件
     */
    enum OverCondition {

        AMOUNT_OVER_FIX(0, "满固定金额", "满金额时触发，可多次触发上不封顶，例如：100元减10元，当满200时，则减20元"),
        AMOUNT_OVER_STEP(2, "满金额（阶梯式）", "例如：满100减10，满300减50，满500减80"),
        ITEM_COUNT_OVER(3, "满件数", "满指定件数商品时触发");

        private final int ordinal;

        private final String tag;

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

        DECREASE_AMOUNT(0, "减金额", "满足条件后减少金额，例如满100减5元", DecreaseAmount.class),
        DISCOUNT(1, "折扣", "满足条件后打折,例如满300打8折", DecreaseDiscount.class),
        SEND_GIFT(2, "发赠品", "满足条件后发放赠品，例如满200送牙刷", SendGift.class);

        private final int ordinal;

        private final String tag;

        private final String desc;

        private final Class overResultConfigClass;

        OverResult(int ordinal, String tag, String desc, Class overResultConfigClass) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
            this.overResultConfigClass = overResultConfigClass;
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

        public Class getOverResultConfigClass() {
            return overResultConfigClass;
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
     * 标识接口，用于标识各种促销结果配置
     */
    public interface OverResultConfig {

        /**
         * map to {@link OverResult#ordinal}
         *
         * @return
         */
        Integer ordinal();
    }


    /**
     * 满减金额设置 for {@link OverResult}
     */
    class DecreaseAmount implements OverResultConfig {

        /**
         * 满金额 {@link #getOverCondition()}=={@link OverCondition#AMOUNT_OVER_FIX}||{@link OverCondition#AMOUNT_OVER_STEP}时有效
         */
        private BigDecimal overAmount;

        /**
         * 减额
         */
        private BigDecimal decreaseAmount;

        /**
         * 满件数量, {@link #getOverCondition()}=={@link OverCondition#ITEM_COUNT_OVER}时有效
         */
        private Integer itemOverCount;

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


        public Integer getItemOverCount() {
            return itemOverCount;
        }

        public void setItemOverCount(Integer itemOverCount) {
            this.itemOverCount = itemOverCount;
        }

        @Override
        public Integer ordinal() {
            return 0;
        }
    }


    /**
     * 满打折设置 for {@link OverResult}
     */
    public static class DecreaseDiscount implements OverResultConfig {

        /**
         * 满金额 {@link #getOverCondition()}=={@link OverCondition#AMOUNT_OVER_FIX}||{@link OverCondition#AMOUNT_OVER_STEP}时有效
         */
        private BigDecimal overAmount;

        /**
         * 折扣
         */
        private BigDecimal discount;

        /**
         * 满件数量, {@link #getOverCondition()}=={@link OverCondition#ITEM_COUNT_OVER}时有效
         */
        private Integer itemOverCount;

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

        public Integer getItemOverCount() {
            return itemOverCount;
        }

        public void setItemOverCount(Integer itemOverCount) {
            this.itemOverCount = itemOverCount;
        }

        @Override
        public Integer ordinal() {
            return 1;
        }
    }


    /**
     * 发赠品设置 for {@link OverResult}
     */
    public static class SendGift implements OverResultConfig {

        /**
         * 满金额 {@link #getOverCondition()}=={@link OverCondition#AMOUNT_OVER_FIX}||{@link OverCondition#AMOUNT_OVER_STEP}时有效
         */
        private BigDecimal overAmount;

        /**
         * 满件数量, {@link #getOverCondition()}=={@link OverCondition#ITEM_COUNT_OVER}时有效
         */
        private Integer itemOverCount;

        /**
         * 赠品详情
         */
        private List<SendGiftDetail> sendGiftDetails;

        public BigDecimal getOverAmount() {
            return overAmount;
        }

        public void setOverAmount(BigDecimal overAmount) {
            this.overAmount = overAmount;
        }

        public Integer getItemOverCount() {
            return itemOverCount;
        }

        public void setItemOverCount(Integer itemOverCount) {
            this.itemOverCount = itemOverCount;
        }

        public List<SendGiftDetail> getSendGiftDetails() {
            return sendGiftDetails;
        }

        public void setSendGiftDetails(List<SendGiftDetail> sendGiftDetails) {
            this.sendGiftDetails = sendGiftDetails;
        }

        @Override
        public Integer ordinal() {
            return 2;
        }
    }

    /**
     * 发赠品详情
     */
    public static class SendGiftDetail {

        /**
         * 赠品id
         */
        private Long giftId;
        /**
         * 赠品数量
         */
        private Integer num;


        public Long getGiftId() {
            return giftId;
        }

        public void setGiftId(Long giftId) {
            this.giftId = giftId;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }
}

