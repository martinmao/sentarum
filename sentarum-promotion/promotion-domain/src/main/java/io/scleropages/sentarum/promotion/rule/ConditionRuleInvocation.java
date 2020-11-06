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
package io.scleropages.sentarum.promotion.rule;

import io.scleropages.sentarum.promotion.rule.model.Rule;

/**
 * 条件规则：特殊的规则调用，其直接返回一个布尔值来确定是否执行后续规则.
 * 可用于促销参与规则判定
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ConditionRuleInvocation extends RuleInvocation {


    @Override
    default void execute(Rule rule, InvocationContext invocationContext, InvocationChain chain) {
        if (match(rule, invocationContext, chain)) {
            chain.next(invocationContext);
        }
    }

    /**
     * 返回TRUE则调用链继续执行
     *
     * @param rule
     * @param invocationContext
     * @param chain
     * @return
     */
    boolean match(Rule rule, InvocationContext invocationContext, InvocationChain chain);
}
