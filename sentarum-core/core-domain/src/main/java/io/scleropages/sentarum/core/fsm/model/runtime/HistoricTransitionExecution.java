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
package io.scleropages.sentarum.core.fsm.model.runtime;

import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;

import java.util.Date;

/**
 * Represent a during of transition
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface HistoricTransitionExecution {

    /**
     * id of this transition execution.
     *
     * @return
     */
    Long id();

    /**
     * associated statemachine definition.
     *
     * @return
     */
    StateMachineDefinition stateMachineDefinition();

    /**
     * associated statemachine execution.
     *
     * @return
     */
    StateMachineExecution stateMachineExecution();


    /**
     * source state of this transition execution.
     *
     * @return
     */
    State from();

    /**
     * target state of this transition execution.
     *
     * @return
     */
    State to();

    /**
     * event fired of this transition execution.
     *
     * @return
     */
    Event event();


    /**
     * time of this transition execution nearly to {@link Event#firedTime()}.
     *
     * @return
     */
    Date time();

}
