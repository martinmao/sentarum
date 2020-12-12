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

import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.ChannelCondition;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.ConjunctionCondition;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.UserLevelCondition;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import org.scleropages.core.util.GenericTypes;
import org.springframework.util.Assert;

/**
 * 规则调用，将所有规则调用统一抽象.使其基于{@link Rule} 执行规则.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface RuleInvocation<R extends Rule, C extends InvocationContext> {


    Integer PROMOTION_INVOCATION = 0;
    /**
     * 条件连接 {@link ConjunctionCondition}
     */
    Integer CONJUNCTION_CONDITION_ID = 1;
    Integer ALWAYS_TRUE_CONDITION_ID = 2;
    /**
     * 促销参与渠道规则id {@link ChannelCondition}
     */
    Integer CHANNEL_INVOCATION_ID = 10;

    /**
     * 促销参与用户规则id {@link UserLevelCondition}
     */
    Integer USER_INVOCATION_ID = 30;

    /**
     * 促销规则 id
     */
    Integer PROMOTION_INVOCATION_ID = 60;


    /**
     * 返回与此规则调用对应的规则类型.
     *
     * @return
     */
    default Class<R> ruleClass() {
        Class<R> ruleClass = GenericTypes.getClassGenericType(getClass(), RuleInvocation.class, 0);
        Assert.notNull(ruleClass, () -> "no generic type found from: " + getClass() + ". you must implementation you own #ruleClass.");
        return ruleClass;
    }


    /**
     * 执行调用
     *
     * @param rule              当前调用的规则.
     * @param invocationContext 当前调用上下文
     * @param chain             当前的调用链.
     */
    void execute(R rule, C invocationContext, InvocationChain chain);


    /**
     * 实现类必须确保其唯一性
     *
     * @return
     */
    Integer id();

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
     * detailed information about this invocation.
     *
     * @return
     */
    default String information() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + "{");
        sb.append("id: ").append(id());
        sb.append("name: ").append(name());
        sb.append("rule: ").append(ruleClass());
        sb.append("}");
        return sb.toString();
    }
}
