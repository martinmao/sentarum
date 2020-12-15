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

import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.PromotionCondition;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule.ConditionConjunction;

/**
 * conjunction condition is a condition container. and use {@link ConditionConjunction} as operator to process combinatorial logic operations
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ConjunctionCondition extends PromotionCondition<ConjunctionConditionRule, InvocationContext> {


    Integer ID = CONJUNCTION_CONDITION_ID;

    @Override
    default Integer id() {
        return ID;
    }

    @Override
    default String name() {
        return "条件组合";
    }

    @Override
    default String description() {
        return "用于将一组条件以逻辑运算符（与，或，非）进行组合.";
    }
}
