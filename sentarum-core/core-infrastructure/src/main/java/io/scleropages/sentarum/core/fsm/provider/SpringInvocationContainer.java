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
package io.scleropages.sentarum.core.fsm.provider;

import io.scleropages.sentarum.core.fsm.Action;
import io.scleropages.sentarum.core.fsm.TransitionEvaluator;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
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
 * lookup invocation( {@link Action},{@link TransitionEvaluator}...) components from spring context.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class SpringInvocationContainer implements InvocationContainer, InitializingBean, ApplicationContextAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private ApplicationContext applicationContext;
    private Map<String, Action> actions;
    private Map<String, TransitionEvaluator> evaluators;


    @Override
    public Action getAction(InvocationConfig actionConfig) {
        assertInvocationConfig(actionConfig);
        return actions.get(actionConfig.invocationImplementation());
    }

    @Override
    public String[] actionImplementations() {
        return actions.keySet().toArray(new String[actions.size()]);
    }

    @Override
    public TransitionEvaluator getTransitionEvaluator(InvocationConfig evaluatorConfig) {
        assertInvocationConfig(evaluatorConfig);
        return evaluators.get(evaluatorConfig.invocationImplementation());
    }

    @Override
    public String[] transitionEvaluatorImplementations() {
        return evaluators.keySet().toArray(new String[evaluators.size()]);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        actions = Collections.unmodifiableMap(applicationContext.getBeansOfType(Action.class));
        logger.debug("successfully load state machine actions: {}", actions);
        evaluators = Collections.unmodifiableMap(applicationContext.getBeansOfType(TransitionEvaluator.class));
        logger.debug("successfully load state machine evaluators: {}", evaluators);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    protected void assertInvocationConfig(InvocationConfig invocationConfig) {
        Assert.notNull(invocationConfig, "invocation config must not be null.");
        Assert.hasText(invocationConfig.invocationImplementation(), "invocation implementation must not be empty text.");

    }
}
