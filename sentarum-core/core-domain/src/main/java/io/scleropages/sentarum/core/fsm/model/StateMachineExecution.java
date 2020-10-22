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
package io.scleropages.sentarum.core.fsm.model;

import io.scleropages.sentarum.core.fsm.StateMachineExecutionListener;

/**
 * represent a fsm (Finite-state machine) execution.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineExecution {


    /**
     * id of this execution.
     *
     * @return
     */
    Long id();

    /**
     * id of business
     *
     * @return
     */
    Long bizId();

    /**
     * type of business
     *
     * @return
     */
    Integer bizType();

    /**
     * associated definition of this fsm.
     *
     * @return
     */
    StateMachineDefinition stateMachineDefinition();

    /**
     * current state of this execution.
     *
     * @return
     */
    State currentState();

    /**
     * state of this execution.
     *
     * @return
     */
    ExecutionState executionState();

    /**
     * the execution listener of this execution.
     *
     * @return
     */
    StateMachineExecutionListener executionListener();


    /**
     * enum state of execution.
     */
    enum ExecutionState {
        //! NOTE: never to change enum orders.
        RUNNING, SUSPEND, TERMINATE, FINISHED
    }
}
