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
package io.scleropages.sentarum.core.fsm.model.impl;

import io.scleropages.sentarum.core.fsm.Action;
import io.scleropages.sentarum.core.fsm.TransitionEvaluator;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateTransition;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StateTransitionModel implements StateTransition {


    private Long id;
    private StateMachineDefinition stateMachineDefinition;
    private State from;
    private State to;
    private EventDefinition event;
    private TransitionEvaluator evaluator;
    private Action action;

    public Long getId() {
        return id;
    }

    public StateMachineDefinition getStateMachineDefinition() {
        return stateMachineDefinition;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public EventDefinition getEvent() {
        return event;
    }

    public TransitionEvaluator getEvaluator() {
        return evaluator;
    }

    public Action getAction() {
        return action;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStateMachineDefinition(StateMachineDefinition stateMachineDefinition) {
        this.stateMachineDefinition = stateMachineDefinition;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public void setTo(State to) {
        this.to = to;
    }

    public void setEvent(EventDefinition event) {
        this.event = event;
    }

    public void setEvaluator(TransitionEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public StateMachineDefinition stateMachineDefinition() {
        return getStateMachineDefinition();
    }

    @Override
    public State from() {
        return getFrom();
    }

    @Override
    public State to() {
        return getTo();
    }

    @Override
    public EventDefinition event() {
        return getEvent();
    }

    @Override
    public TransitionEvaluator evaluator() {
        return getEvaluator();
    }

    @Override
    public Action action() {
        return getAction();
    }
}
