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
package io.scleropages.sentarum.core.fsm;

import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.State;

/**
 * a listener of state machine execution.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineExecutionListener {

    /**
     * called when state changed.
     *
     * @param from    changed state from
     * @param to      changed state to
     * @param event   fired event
     * @param context execution context.
     */
    default void stateChanged(State from, State to, Event event, StateMachineExecutionContext context) {
    }

    /**
     * called when state entered.
     *
     * @param state entered state.
     * @param event entered event.
     */
    default void stateEntered(State state, Event event) {
    }

    /**
     * called when state exited.
     *
     * @param state exited state
     * @param event exited event.
     */
    default void stateExited(State state, Event event) {
    }

    /**
     * called when fsm execution started.
     *
     * @param context execution context.
     */
    default void executionStarted(StateMachineExecutionContext context) {

    }

    /**
     * called when fsm execution stopped.
     *
     * @param context execution context
     */
    default void executionStopped(StateMachineExecutionContext context) {

    }

    /**
     * called when fsm execution terminated
     *
     * @param context
     * @param note
     */
    default void executionTerminated(StateMachineExecutionContext context, String note) {

    }

}
