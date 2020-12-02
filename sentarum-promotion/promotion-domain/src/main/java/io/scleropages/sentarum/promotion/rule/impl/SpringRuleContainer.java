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
package io.scleropages.sentarum.promotion.rule.impl;

import com.google.common.collect.Maps;
import io.scleropages.sentarum.promotion.rule.Condition;
import io.scleropages.sentarum.promotion.rule.PromotionCalculator;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.RuleInvocation;
import io.scleropages.sentarum.promotion.rule.model.AbstractRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class SpringRuleContainer implements RuleContainer, InitializingBean, ApplicationContextAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<Integer, RuleInvocation> ruleInvocations;
    private Map<Integer, RuleInvocationDescriptor> conditionDescriptors;
    private Map<Integer, RuleInvocationDescriptor> calculatorDescriptors;
    private Map<Class, Integer> ruleClasses;

    @Override
    public Condition getCondition(ConditionRule conditionRule) {
        return (Condition) ruleInvocations.get(conditionRule.ruleInvocationImplementation());
    }

    @Override
    public PromotionCalculator getPromotionCalculator(CalculatorRule calculatorRule){
        return (PromotionCalculator) ruleInvocations.get(calculatorRule.ruleInvocationImplementation());
    }

    @Override
    public Map<Integer, RuleInvocationDescriptor> conditionImplementations() {
        return conditionDescriptors;
    }

    @Override
    public Map<Integer, RuleInvocationDescriptor> calculatorImplementations() {
        return calculatorDescriptors;
    }

    @Override
    public Integer ruleInvocationImplementation(Class<AbstractRule> ruleClass) {
        return ruleClasses.get(ruleClass);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Map<Integer, RuleInvocation> _ruleInvocations = Maps.newHashMap();
        Map<Integer, RuleInvocationDescriptor> _conditionDescriptors = Maps.newHashMap();
        Map<Integer, RuleInvocationDescriptor> _calculatorDescriptors = Maps.newHashMap();
        Map<Class, Integer> _ruleClasses = Maps.newHashMap();

        applicationContext.getBeansOfType(Condition.class).forEach((s, condition) -> {
            Integer conditionId = condition.id();
            Assert.isNull(_ruleInvocations.put(conditionId, condition), () -> "condition with id: " + conditionId + " already exists. please specify another one.");
            _conditionDescriptors.put(conditionId, new RuleInvocationDescriptor(condition));
            _ruleClasses.put(condition.ruleClass(), conditionId);
        });
        applicationContext.getBeansOfType(PromotionCalculator.class).forEach((s, calculator) -> {
            Integer calculatorId = calculator.id();
            Assert.isNull(_ruleInvocations.put(calculatorId, calculator), () -> "calculator with id: " + calculatorId + " already exists. please specify another one.");
            _calculatorDescriptors.put(calculatorId, new RuleInvocationDescriptor(calculator));
            _ruleClasses.put(calculator.ruleClass(), calculatorId);
        });
        ruleInvocations = Collections.unmodifiableMap(_ruleInvocations);
        conditionDescriptors = Collections.unmodifiableMap(_conditionDescriptors);
        calculatorDescriptors = Collections.unmodifiableMap(_calculatorDescriptors);
        ruleClasses = Collections.unmodifiableMap(_ruleClasses);
        logger.debug("successfully loaded rule invocations: {}", ruleInvocations);
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
