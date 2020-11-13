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

import io.scleropages.sentarum.promotion.rule.Condition;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class SpringRuleContainer implements RuleContainer, InitializingBean, ApplicationContextAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, Condition> conditions;
    private ApplicationContext applicationContext;

    @Override
    public Condition getCondition(ConditionRule conditionRule) {
        return conditions.get(conditionRule.ruleInvocationImplementation());
    }

    @Override
    public String[] conditionImplementations() {
        return conditions.keySet().toArray(new String[conditions.size()]);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        conditions = Collections.unmodifiableMap(applicationContext.getBeansOfType(Condition.class));
        logger.debug("successfully load conditions: {}", conditions);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
