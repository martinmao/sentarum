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
package io.scleropages.sentarum.promotion.rule.participation;

import io.scleropages.sentarum.promotion.rule.ConditionRule;
import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.model.Rule;

/**
 * 促销参与用户规则
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class UserCondition implements ConditionRule {


    @Override
    public boolean match(Rule rule, InvocationContext invocationContext, InvocationChain chain) {
        return false;
    }

    @Override
    public Class<? extends Rule> ruleClass() {
        return null;
    }

    @Override
    public Integer id() {
        return USER_CONDITION_RULE_ID;
    }

    @Override
    public String name() {
        return "促销参与用户";
    }

    @Override
    public String description() {
        return "限定性规则：将促销活动中关联的商品限定为某一用户类型.（例如，会员等级，用户标签等）";
    }
}
