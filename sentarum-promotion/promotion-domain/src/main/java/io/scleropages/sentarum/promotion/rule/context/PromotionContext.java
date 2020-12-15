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
package io.scleropages.sentarum.promotion.rule.context;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule.ChannelType;

import java.util.List;

/**
 * base interface of promotion context.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PromotionContext extends InvocationContext {

    /**
     * 渠道类型
     *
     * @return
     */
    ChannelType channelType();

    /**
     * 渠道标识
     *
     * @return
     */
    Integer channelId();


    /**
     * 买家唯一标识
     *
     * @return
     */
    Long buyerId();


    /**
     * 活动列表
     *
     * @return
     */
    List<Activity> activities();


    /**
     * return builder of {@link PromotionResult}
     *
     * @return
     */
    default PromotionResultBuilder promotionResultBuilder() {
        return PromotionResultBuilder.newBuilder();
    }

    /**
     * add {@link PromotionResult} to current context.
     *
     * @param promotionResult
     */
    void addPromotionResult(PromotionResult promotionResult);


    /**
     * return promotion results of this context.
     *
     * @return
     */
    List<PromotionResult> promotionResults();


    /**
     * builder class of promotion result.
     */
    class PromotionResultBuilder {

        private Long ruleId;
        private String name;
        private String description;
        private Amount adjustFee;


        private PromotionResultBuilder() {

        }

        public static PromotionResultBuilder newBuilder() {
            return new PromotionResultBuilder();
        }

        public PromotionResultBuilder withRuleId(Long ruleId) {
            this.ruleId = ruleId;
            return this;
        }

        public PromotionResultBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PromotionResultBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PromotionResultBuilder withAdjustFee(Amount adjustFee) {
            this.adjustFee = adjustFee;
            return this;
        }

        public PromotionResult build() {
            return new PromotionResult() {
                @Override
                public Long ruleId() {
                    return ruleId;
                }

                @Override
                public String name() {
                    return name;
                }

                @Override
                public String description() {
                    return description;
                }

                @Override
                public Amount adjustFee() {
                    return adjustFee;
                }

                @Override
                public String toString() {
                    StringBuilder sb = new StringBuilder("{ ");
                    sb.append("name: ").append(name).append(", ");
                    sb.append("adjustFee: ").append(adjustFee).append(" }");
                    return sb.toString();
                }
            };
        }
    }


    /**
     * 优惠计算结果
     */
    interface PromotionResult {

        /**
         * 规则id.
         *
         * @return
         */
        Long ruleId();

        /**
         * 规则名称
         *
         * @return
         */
        String name();


        /**
         * 规则描述
         *
         * @return
         */
        String description();


        /**
         * 计算后的价格调整，通常为减免金额(特殊情况下可能为负)
         *
         * @return
         */
        Amount adjustFee();
    }
}
