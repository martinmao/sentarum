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
package io.scleropages.sentarum.promotion.rule.condition;

import io.scleropages.sentarum.promotion.rule.Condition;
import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import io.scleropages.sentarum.promotion.rule.model.impl.UserTagRule;

/**
 * 促销参与用户规则
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class UserTagCondition implements Condition {


    @Override
    public boolean match(Rule rule, InvocationContext invocationContext, InvocationChain chain) {
        return false;
    }

    @Override
    public Class<? extends Rule> ruleClass() {
        return UserTagRule.class;
    }

    @Override
    public Integer id() {
        return USER_INVOCATION_ID + 2;
    }

    @Override
    public String name() {
        return "促销参与用户标签";
    }

    @Override
    public String description() {
        return "限定性规则：将促销活动限定为具备特定标签的用户人群.";
    }
}