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
import io.scleropages.sentarum.core.fsm.TransitionEvaluator;
import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;
import io.scleropages.sentarum.core.fsm.model.StateTransition;
import io.scleropages.sentarum.core.fsm.provider.AbstractStateMachineFactory;
import io.scleropages.sentarum.core.fsm.provider.ProviderStateMachine;
import org.scleropages.core.mapper.JsonMapper2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
@Component
public class SquirrelStateMachineFactory extends AbstractStateMachineFactory implements InitializingBean {


    @Value("#{ @environment['statemachine.squirrel.builder-cache-timeout-minutes'] ?: 30 }")
    private long cacheTimeoutInMinutes = 30;

    private Cache<Long, StateMachineBuilder> stateMachineBuilderCache;

    @Override
    protected ProviderStateMachine createStateMachineInternal(StateMachineDefinition stateMachineDefinition, List<StateTransition> stateTransitions) {
        StateMachineBuilder builder;
        try {
            builder = stateMachineBuilderCache.get(stateMachineDefinition.id(), () -> {
                StateMachineBuilder smb = StateMachineBuilderFactory.create(DefaultSquirrelStateMachine.class);
                stateTransitions.forEach(stateTransition -> {
                    On on = smb.externalTransition().from(createSquirrelState(stateTransition.from()))
                            .to(createSquirrelState(stateTransition.to()))
                            .on(createSquirrelEvent(stateTransition.event()));
                    InvocationConfig evaluatorConfig = stateTransition.evaluatorConfig();
                    if (null != evaluatorConfig) {
                        TransitionEvaluator transitionEvaluator = getTransitionEvaluator(evaluatorConfig);
                        on.when(new Condition() {
                            @Override
                            public boolean isSatisfied(Object context) {
                                return transitionEvaluator.evaluate(evaluatorConfig, null != context ? (StateMachineExecutionContext) context : null);
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
                                action.execute(actionConfig, null != from ? (State) from : null, null != to ? (State) to : null, null != event ? (Event) event : null, null != context ? (StateMachineExecutionContext) context : null);
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
        org.squirrelframework.foundation.fsm.StateMachine stateMachine = builder.newStateMachine(createSquirrelState(stateMachineDefinition.initialState()));

        stateMachine.addStateMachineListener(event -> {
            System.out.println(event);
        });

        return new ProviderStateMachine() {
            @Override
            public boolean sendEvent(Event event, StateMachineExecutionContext executionContext) {
                SquirrelEvent squirrelEvent = createSquirrelEvent(event);
                if (stateMachine.canAccept(squirrelEvent)) {
                    stateMachine.fire(squirrelEvent, executionContext);
                    return true;
                }
                return false;
            }

            @Override
            public void start(StateMachineExecutionContext executionContext) {
                stateMachine.start(executionContext);
            }

            @Override
            public State currentState() {
                return (State) stateMachine.getCurrentState();
            }
        };

    }


    protected SquirrelState createSquirrelState(State state) {
        return new SquirrelState(state);
    }

    protected SquirrelEvent createSquirrelEvent(EventDefinition event) {
        return new SquirrelEvent(event);
    }

    protected SquirrelEvent createSquirrelEvent(Event event) {
        return new SquirrelEvent(event);
    }

    @StateMachineParameters(stateType = SquirrelState.class, eventType = SquirrelEvent.class, contextType = StateMachineExecutionContext.class)
    static class DefaultSquirrelStateMachine extends AbstractUntypedStateMachine {


        protected void afterTransitionCausedException(SquirrelState fromState, SquirrelState toState, SquirrelEvent event, StateMachineExecutionContext context) {
            //默认情况下：状态机异常将会终止状态机执行，此处进行重置
//            setStatus(StateMachineStatus.IDLE);
            //super.afterTransitionCausedException(fromState, toState, event, context);
            System.out.println("afterTransitionCausedException: "+event + "(" + fromState + "->" + toState + "): [" + context+"]");

        }


        protected void beforeTransitionBegin(SquirrelState fromState, SquirrelEvent event, StateMachineExecutionContext context) {
            System.out.println("beforeTransitionBegin: "+event + "(" + fromState + "->" + null + "): [" + context+"]");
        }

        protected void afterTransitionCompleted(SquirrelState fromState, SquirrelState toState, SquirrelEvent event, StateMachineExecutionContext context) {
            System.out.println("afterTransitionCompleted: "+event + "(" + fromState + "->" + toState + "): [" + context+"]");
        }


        protected void afterTransitionEnd(SquirrelState fromState, SquirrelState toState, SquirrelEvent event, StateMachineExecutionContext context) {
            System.out.println("afterTransitionEnd: "+event + "(" + fromState + "->" + toState + "): [" + context+"]");
        }


        protected void afterTransitionDeclined(SquirrelState fromState, SquirrelEvent event, StateMachineExecutionContext context) {
            System.out.println("afterTransitionDeclined: "+event + "(" + fromState + "->" + null + "): [" + context+"]");
        }


        protected void beforeActionInvoked(SquirrelState fromState, SquirrelState toState, SquirrelEvent event, StateMachineExecutionContext context) {
            System.out.println("beforeActionInvoked: "+event + "(" + fromState + "->" + null + "): [" + context+"]");
        }


        protected void afterActionInvoked(SquirrelState fromState, SquirrelState toState, SquirrelEvent event, StateMachineExecutionContext context) {
            System.out.println("afterActionInvoked: "+event + "(" + fromState + "->" + null + "): [" + context+"]");
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        stateMachineBuilderCache = CacheBuilder.newBuilder().expireAfterWrite(cacheTimeoutInMinutes, TimeUnit.MINUTES).build();
    }
}
