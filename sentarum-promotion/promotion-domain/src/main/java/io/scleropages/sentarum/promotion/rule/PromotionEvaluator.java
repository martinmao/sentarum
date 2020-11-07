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

import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.model.Rule;

/**
 * 促销规则调用
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PromotionEvaluator<R extends Rule, C extends PromotionContext> extends RuleInvocation<R, C> {

    @Override
    default void execute(R rule, C invocationContext, InvocationChain chain) {
        evaluate(rule, invocationContext, chain);
    }


    /**
     * 基于的规则进行促销金额计算.
     *
     * @param rule             促销规则
     * @param promotionContext 促销上下文
     * @param chain            规则调用链
     */
    void evaluate(R rule, C promotionContext, InvocationChain chain);
}
