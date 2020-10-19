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
package io.scleropages.sentarum.core.mgmt;

import io.scleropages.sentarum.core.fsm.StateMachine;
import io.scleropages.sentarum.core.fsm.model.impl.ActionModel;
import io.scleropages.sentarum.core.fsm.model.impl.EventDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateModel;
import io.scleropages.sentarum.core.fsm.model.HistoricTransitionExecution;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;

import java.util.List;

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
     * @param stateMachineDefinition
     */
    void createStateMachineDefinition(StateMachineDefinitionModel stateMachineDefinition);

    /**
     * create new state transition
     *
     * @param stateMachineDefinitionId id of state machine definition.
     * @param fromStateId              id of from state
     * @param toStateId                id of target to state
     * @param eventDefinitionId        id of event definition
     * @param action                   performed action.
     */
    void createStateTransition(Long stateMachineDefinitionId, Long fromStateId, Long toStateId, Long eventDefinitionId, ActionModel action);

    /**
     * create new state machine.
     *
     * @param stateMachineDefinitionId
     */
    void createStateMachine(Long stateMachineDefinitionId);

    /**
     * return state machine
     *
     * @param id id of state machine.
     * @return
     */
    StateMachine getStateMachine(Long id);

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
}
