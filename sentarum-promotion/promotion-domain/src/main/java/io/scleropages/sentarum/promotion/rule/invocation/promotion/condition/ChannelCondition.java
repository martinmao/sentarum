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
package io.scleropages.sentarum.promotion.rule.invocation.promotion.condition;

import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;

import java.util.Objects;

/**
 * 促销参与渠道规则
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ChannelCondition extends AbstractPromotionCondition<ChannelConditionRule> {

    Integer ID = CHANNEL_INVOCATION_ID;


    @Override
    default boolean match(ChannelConditionRule rule, PromotionContext invocationContext) {
        return Objects.equals(rule.getChannelId(), invocationContext.channelId());
    }

    @Override
    default Integer id() {
        return ID;
    }

    @Override
    default String name() {
        return "促销参与渠道";
    }

    @Override
    default String description() {
        return "限定性规则：将促销活动中关联的商品限定到某一（或多）个渠道进行.只有在限定渠道内购买才可以获得优惠.";
    }
}
