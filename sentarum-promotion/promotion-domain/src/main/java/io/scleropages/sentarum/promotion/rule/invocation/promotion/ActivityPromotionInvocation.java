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
package io.scleropages.sentarum.promotion.rule.invocation.promotion;

import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.RuleInvocation;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 活动促销规则计算，包括参与条件判定 {@link ConditionRule} 及其促销计算 {@link CalculatorRule}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ActivityPromotionInvocation implements RuleInvocation<ConditionRule, PromotionContext> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ActivityPromotionInvocation.class);

    private final CalculatorRule calculatorRule;

    private final PromotionCondition rootCondition;
    private final PromotionCalculator calculator;


    public ActivityPromotionInvocation(Activity activity, PromotionCondition rootCondition, RuleContainer ruleContainer) {
        Assert.notNull(activity, "activity must not be null.");
        Assert.notNull(rootCondition, "rootCondition must not be null.");
        Assert.notNull(ruleContainer, "ruleContainer must not be null.");
        this.rootCondition = rootCondition;
        calculatorRule = (CalculatorRule) activity.promotionalRule();
        calculator = ruleContainer.getPromotionCalculator(calculatorRule);
    }

    @Override
    public void execute(ConditionRule rule, PromotionContext invocationContext, InvocationChain chain) {
        boolean match = rootCondition.match(rule, invocationContext);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("evaluated condition by: {}[{}] and result is: {}", rule.getClass().getSimpleName(), rule.description(), match);
        }
        if (match) {
            calculator.calculate(calculatorRule, invocationContext);
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("evaluated calculating by: {}[{}]. result is: {}", calculatorRule.getClass().getSimpleName(), calculatorRule.description(), invocationContext.promotionResults());
            }
        }
        chain.next(invocationContext);
    }

    @Override
    public Integer id() {
        return PROMOTION_INVOCATION;
    }

    @Override
    public String name() {
        return "促销计算单元";
    }

    @Override
    public String description() {
        return "将促销条件与促销计算合并到一个执行单元.";
    }


    @Override
    public String information() {
        StringBuilder sb = new StringBuilder();
        sb.append("condition-> ").append(rootCondition.information()).append(", ");
        sb.append("calculating[" + calculatorRule.id() + "]-> ").append(calculator.information()).append(" ");
        sb.append("}");
        return sb.toString();
    }
}
