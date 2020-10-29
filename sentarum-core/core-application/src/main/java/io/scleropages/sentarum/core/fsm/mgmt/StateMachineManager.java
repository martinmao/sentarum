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
package io.scleropages.sentarum.core.fsm.mgmt;

import io.scleropages.sentarum.core.fsm.StateMachine;
import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.HistoricTransitionExecution;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;
import io.scleropages.sentarum.core.fsm.model.impl.EventDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateTransitionModel;

import java.util.List;
import java.util.Map;

/**
 * manager of state machine.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineManager {

    /**
     * create new state.
     *
     * @param state state to create
     */
    void createState(StateModel state);

    /**
     * create new event definition.
     *
     * @param eventDefinition
     */
    void createEventDefinition(EventDefinitionModel eventDefinition);

    /**
     * create new state machine definition.
     *
     * @param stateMachineDefinition definition of state machine.
     * @param initialState           required initial state
     * @param endState               optional end state. if not specified. the state machine will running forever
     */
    void createStateMachineDefinition(StateMachineDefinitionModel stateMachineDefinition, Long initialState, Long endState);

    /**
     * create new state transition
     *
     * @param stateTransition          model of transition
     * @param stateMachineDefinitionId id of state machine definition.
     * @param fromStateId              id of from state
     * @param toStateId                id of target to state
     * @param eventDefinitionId        id of event definition
     */
    void createStateTransition(StateTransitionModel stateTransition, Long stateMachineDefinitionId, Long fromStateId, Long toStateId, Long eventDefinitionId);

    /**
     * create new state machine.
     *
     * @param definitionId      id of state machine definition.
     * @param bizType           type business model.
     * @param bizId             id of business model.
     * @param contextAttributes attributes of context.
     */
    StateMachine createStateMachine(Long definitionId, Integer bizType, Long bizId, Map<String, Object> contextAttributes);

    /**
     * create new event for sending to statemachine.
     *
     * @param name name of event definition.
     * @param body event business body.
     * @return
     */
    Event createEvent(String name, Map<String, Object> body);

    /**
     * return exists state machine
     *
     * @param id id of state machine.
     * @return
     */
    StateMachine getStateMachine(Long id);

    /**
     * return exists state machine
     *
     * @param bizType type of business model.
     * @param bizId   id of business model.
     * @return
     */
    StateMachine getStateMachine(Integer bizType, Long bizId);

    /**
     * return state machine execution by id.
     *
     * @param id
     * @return
     */
    StateMachineExecution getStateMachineExecution(Long id);

    /**
     * return all historic transition executions by state machine execution id.
     *
     * @param stateMachineExecutionId
     * @return
     */
    List<HistoricTransitionExecution> getAllHistoricTransitionExecutions(Long stateMachineExecutionId);


    /**
     * return state definition by id.
     *
     * @param id
     * @return
     */
    StateMachineDefinition getStateMachineDefinition(Long id);

    /**
     * return state definition by name.
     *
     * @param name
     * @return
     */
    StateMachineDefinition getStateMachineDefinitionByName(String name);


    /**
     * return state by id.
     *
     * @param id
     * @return
     */
    State getState(Long id);

    /**
     * return state by value.
     *
     * @param value
     * @return
     */
    State getStateByValue(Integer value);

    /**
     * return state by name.
     *
     * @param name
     * @return
     */
    State getStateByName(String name);

    /**
     * return event definition by id.
     *
     * @param id
     * @return
     */
    EventDefinition getEventDefinition(Long id);

    /**
     * return event definition by name.
     *
     * @param name
     * @return
     */
    EventDefinition getEventDefinitionByName(String name);
}
