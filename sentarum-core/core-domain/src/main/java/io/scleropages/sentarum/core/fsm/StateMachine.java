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
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;

import java.util.Map;

/**
 * represent a fsm (Finite-state machine)
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachine {


    /**
     * send event to this state machine.
     *
     * @param event             event to sent
     * @param contextAttributes attributes of context.
     */
    void sendEvent(Event event, Map<String, Object> contextAttributes);


    /**
     * start this state machine.
     */
    void start();


    /**
     * terminate this state machine.
     *
     * @param note
     */
    void terminate(String note);

    /**
     * suspend this state machine.
     *
     * @param note
     */
    void suspend(String note);

    /**
     * resume this state machine.
     */
    void resume();

    /**
     * return execution of this state machine.
     *
     * @return
     */
    StateMachineExecution stateMachineExecution();
}
