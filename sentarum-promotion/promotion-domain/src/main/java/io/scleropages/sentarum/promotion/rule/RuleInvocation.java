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
 * 规则调用，将所有规则调用统一抽象.使其基于{@link Rule} 执行规则.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface RuleInvocation {


    /**
     * 促销参与渠道规则id {@link io.scleropages.sentarum.promotion.rule.participation.ChannelCondition}
     */
    Integer CHANNEL_CONDITION_RULE_ID = 10;

    /**
     * 促销参与用户规则id {@link io.scleropages.sentarum.promotion.rule.participation.UserCondition}
     */
    Integer USER_CONDITION_RULE_ID = 20;

    /**
     * 促销规则
     */
    Integer PROMOTION_RULE_ID_START = 30;


    /**
     * 返回与此规则调用对应的规则类型.
     *
     * @return
     */
    Class<? extends Rule> ruleClass();


    /**
     * 执行调用
     *
     * @param rule              当前调用的规则.
     * @param invocationContext 当前调用上下文
     * @param chain             当前的调用链.
     */
    void execute(Rule rule, InvocationContext invocationContext, InvocationChain chain);


    /**
     * 实现类必须确保其唯一性
     *
     * @return
     */
    Integer id();

    /**
     * 名称
     *
     * @return
     */
    String name();

    /**
     * 描述
     *
     * @return
     */
    String description();
}
