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

import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.HistoricTransitionExecution;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class HistoricTransitionExecutionModel implements HistoricTransitionExecution {


    private Long id;
    private StateMachineDefinition stateMachineDefinition;
    private StateMachineExecution stateMachineExecution;
    private State from;
    private State to;
    private Event event;
    private Date time;

    public Long getId() {
        return id;
    }

    public StateMachineDefinition getStateMachineDefinition() {
        return stateMachineDefinition;
    }

    public StateMachineExecution getStateMachineExecution() {
        return stateMachineExecution;
    }

    public State getFrom() {
        return from;
    }

    public State getTo() {
        return to;
    }

    public Event getEvent() {
        return event;
    }

    public Date getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStateMachineDefinition(StateMachineDefinition stateMachineDefinition) {
        this.stateMachineDefinition = stateMachineDefinition;
    }

    public void setStateMachineExecution(StateMachineExecution stateMachineExecution) {
        this.stateMachineExecution = stateMachineExecution;
    }

    public void setFrom(State from) {
        this.from = from;
    }

    public void setTo(State to) {
        this.to = to;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setTime(Date time) {
        this.time = time;
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
    public StateMachineExecution stateMachineExecution() {
        return getStateMachineExecution();
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
    public Event event() {
        return getEvent();
    }

    @Override
    public Date time() {
        return getTime();
    }
}
