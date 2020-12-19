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
import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.TrueCondition;
import io.scleropages.sentarum.promotion.rule.model.AbstractRule;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 活动促销规则计算，包括参与条件判定 {@link ConditionRule} 及其促销计算 {@link CalculatorRule}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ActivityPromotionInvocation implements RuleInvocation<ConditionRule, PromotionContext> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ActivityPromotionInvocation.class);

    private final Activity activity;
    private final RuleContainer ruleContainer;

    public ActivityPromotionInvocation(Activity activity, RuleContainer ruleContainer) {
        Assert.notNull(activity, "activity must not be null.");
        Assert.notNull(ruleContainer, "ruleContainer must not be null.");
        this.activity = activity;
        this.ruleContainer = ruleContainer;
    }

    @Override
    public void execute(ConditionRule rule, PromotionContext invocationContext, InvocationChain chain) {
        ConditionRule conditionRule = getConditionRule(activity);
        PromotionCondition condition = ruleContainer.getCondition(conditionRule);
        Assert.notNull(condition, "no condition found by given condition-rule: " + conditionRule.id());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("evaluating condition: {}[{}] using: {}", conditionRule.getClass().getSimpleName(), conditionRule.id(), condition.information());
        }
        boolean match = condition.match(conditionRule, invocationContext);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("result: {}", match);
        }
        if (match) {
            if (activity.promotionalRule() == null) {
                LOGGER.warn("detected activity[{}] no calculator rule set. ignoring to calculating.", activity.id());
                return;
            }
            CalculatorRule calculatorRule = getCalculatorRule(activity);
            PromotionCalculator calculator = ruleContainer.getPromotionCalculator(calculatorRule);
            Assert.notNull(calculator, "no calculator found by given calculator-rule: " + calculatorRule.id());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("calculating: {}[{}] using: {}", ProxyUtils.getUserClass(calculatorRule).getSimpleName(), calculatorRule.id(), calculator.information());
            }
            calculator.calculate(calculatorRule, invocationContext);
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("result: {}", invocationContext.promotionResults());
            }
        }
        chain.next(invocationContext);
    }

    private final CalculatorRule getCalculatorRule(Activity activity) {
        CalculatorRule calculatorRule = (CalculatorRule) activity.promotionalRule();
        if (calculatorRule instanceof AbstractRule)
            ((AbstractRule) calculatorRule).setActivity(activity);
        return calculatorRule;
    }

    private final ConditionRule getConditionRule(Activity activity) {
        List<Rule> rules = activity.conditionRules();
        ConditionRule conditionRule = CollectionUtils.isEmpty(rules) ? TrueCondition.TRUE_CONDITION_RULE : (ConditionRule) rules.get(0);
        if (conditionRule instanceof AbstractRule) {
            ((AbstractRule) conditionRule).setActivity(activity);
        }
        return conditionRule;
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
}
