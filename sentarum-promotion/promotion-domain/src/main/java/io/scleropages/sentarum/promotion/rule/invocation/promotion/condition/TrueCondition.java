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
import io.scleropages.sentarum.promotion.rule.model.AbstractConditionRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;

/**
 * condition that matches always return true.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class TrueCondition implements PromotionCondition<TrueCondition.TrueConditionRule, InvocationContext> {


    /**
     * default instance of true condition.
     */
    public static final PromotionCondition TRUE_CONDITION = new TrueCondition();
    public static final ConditionRule TRUE_CONDITION_RULE = new TrueConditionRule();


    @Override
    public boolean match(TrueConditionRule rule, InvocationContext invocationContext) {
        return Boolean.TRUE;
    }

    @Override
    public Integer id() {
        return ALWAYS_TRUE_CONDITION_ID;
    }

    @Override
    public String name() {
        return "无条件，始终通过";
    }

    @Override
    public String description() {
        return "对于没有设置条件的活动，默认使用该条件，促销无任何限制.";
    }


    public static final class TrueConditionRule extends AbstractConditionRule {

        @Override
        public Long id() {
            return -1l;
        }

        @Override
        protected Integer defaultRuleInvocationImplementation() {
            return ALWAYS_TRUE_CONDITION_ID;
        }

        @Override
        public String description() {
            return "无条件，始终通过";
        }
    }

}
