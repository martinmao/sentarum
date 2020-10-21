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
package io.scleropages.sentarum.core.fsm.provider.squirrel;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.scleropages.sentarum.core.fsm.StateMachine;
import io.scleropages.sentarum.core.fsm.TransitionEvaluator;
import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;
import io.scleropages.sentarum.core.fsm.model.StateTransition;
import io.scleropages.sentarum.core.fsm.provider.AbstractStateMachineFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.Condition;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.builder.On;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * squirrel implementation for state machine factory.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SquirrelStateMachineFactory extends AbstractStateMachineFactory implements InitializingBean {


    @Value("#{ @environment['statemachine.squirrel.builder-cache-timeout-minutes'] ?: 30 }")
    private long cacheTimeoutInMinutes = 30;

    private Cache<Long, StateMachineBuilder> stateMachineBuilderCache;

    @Override
    protected StateMachine createStateMachineInternal(StateMachineDefinition stateMachineDefinition, List<StateTransition> stateTransitions) {
        StateMachineBuilder builder;
        try {
            builder = stateMachineBuilderCache.get(stateMachineDefinition.id(), () -> {
                StateMachineBuilder smb = StateMachineBuilderFactory.create(DefaultSquirrelStateMachine.class);
                stateTransitions.forEach(stateTransition -> {
                    On on = smb.externalTransition().from(stateTransition.from()).to(stateTransition.to()).on(stateTransition.event());
                    InvocationConfig evaluatorConfig = stateTransition.evaluatorConfig();
                    if (null != evaluatorConfig) {
                        TransitionEvaluator transitionEvaluator = getTransitionEvaluator(evaluatorConfig);
                        on.when(new Condition() {
                            @Override
                            public boolean isSatisfied(Object context) {
                                return transitionEvaluator.evaluate(evaluatorConfig, (StateMachineExecutionContext) context);
                            }

                            @Override
                            public String name() {
                                return evaluatorConfig.invocationImplementation();
                            }
                        });
                    }
                    InvocationConfig actionConfig = stateTransition.actionConfig();

                    if (null != actionConfig) {
                        io.scleropages.sentarum.core.fsm.Action action = getAction(actionConfig);
                        on.perform(new Action() {
                            @Override
                            public void execute(Object from, Object to, Object event, Object context, org.squirrelframework.foundation.fsm.StateMachine stateMachine) {
                                action.execute(actionConfig, (State) from, (State) to, (Event) event, (StateMachineExecutionContext) context);
                            }

                            @Override
                            public String name() {
                                return actionConfig.invocationImplementation();
                            }

                            @Override
                            public int weight() {
                                return 0;
                            }

                            @Override
                            public boolean isAsync() {
                                return false;
                            }

                            @Override
                            public long timeout() {
                                return 0;
                            }
                        });
                    }
                });
                return smb;
            });
        } catch (ExecutionException e) {
            throw new IllegalStateException("failure to create state machine builder. ", e);
        }
        org.squirrelframework.foundation.fsm.StateMachine stateMachine = builder.newStateMachine(stateMachineDefinition.initialState());

        return null;
    }

    @StateMachineParameters(stateType = State.class, eventType = Event.class, contextType = StateMachineExecutionContext.class)
    static class DefaultSquirrelStateMachine extends AbstractUntypedStateMachine {


        protected void afterTransitionCausedException(State fromState, State toState, Event event, StateMachineExecutionContext context) {
            //默认情况下：状态机异常将会终止状态机执行，此处进行重置
            setStatus(StateMachineStatus.IDLE);
            //super.afterTransitionCausedException(fromState, toState, event, context);
        }


        protected void beforeTransitionBegin(State fromState, Event event, StateMachineExecutionContext context) {
            super.beforeTransitionBegin(fromState, event, context);
        }

        protected void afterTransitionCompleted(State fromState, State toState, Event event, StateMachineExecutionContext context) {
            super.afterTransitionCompleted(fromState, toState, event, context);
        }


        protected void afterTransitionEnd(State fromState, State toState, Event event, StateMachineExecutionContext context) {
            super.afterTransitionEnd(fromState, toState, event, context);
        }


        protected void afterTransitionDeclined(State fromState, Event event, StateMachineExecutionContext context) {
            super.afterTransitionDeclined(fromState, event, context);
        }


        protected void beforeActionInvoked(State fromState, State toState, Event event, StateMachineExecutionContext context) {
            super.beforeActionInvoked(fromState, toState, event, context);
        }


        protected void afterActionInvoked(State fromState, State toState, Event event, StateMachineExecutionContext context) {
            super.afterActionInvoked(fromState, toState, event, context);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        stateMachineBuilderCache = CacheBuilder.newBuilder().expireAfterWrite(cacheTimeoutInMinutes, TimeUnit.MINUTES).build();
    }
}
