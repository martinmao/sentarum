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
package io.scleropages.sentarum.trading.fsm.model.impl;

import io.scleropages.sentarum.trading.fsm.StateMachineExecutionListener;
import io.scleropages.sentarum.trading.fsm.model.Event;
import io.scleropages.sentarum.trading.fsm.model.State;
import io.scleropages.sentarum.trading.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.trading.fsm.model.StateMachineExecution;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StateMachineExecutionModel implements StateMachineExecution {


    private Long id;
    private StateMachineDefinition stateMachineDefinition;
    private State currentState;
    private List<Event> historiesEvents;
    private StateMachineExecutionListener executionListener;


    public Long getId() {
        return id;
    }

    public StateMachineDefinition getStateMachineDefinition() {
        return stateMachineDefinition;
    }

    public State getCurrentState() {
        return currentState;
    }

    public List<Event> getHistoriesEvents() {
        return historiesEvents;
    }

    public StateMachineExecutionListener getExecutionListener() {
        return executionListener;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStateMachineDefinition(StateMachineDefinition stateMachineDefinition) {
        this.stateMachineDefinition = stateMachineDefinition;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void setHistoriesEvents(List<Event> historiesEvents) {
        this.historiesEvents = historiesEvents;
    }

    public void setExecutionListener(StateMachineExecutionListener executionListener) {
        this.executionListener = executionListener;
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
    public State currentState() {
        return getCurrentState();
    }

    @Override
    public List<Event> historiesEvents() {
        return getHistoriesEvents();
    }

    @Override
    public void terminate(String note) {

    }

    @Override
    public void suspend(String note) {

    }

    @Override
    public void resume() {

    }

    @Override
    public StateMachineExecutionListener executionListener() {
        return null;
    }
}
