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
package io.scleropages.sentarum.core.fsm.provider;

import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.State;

import java.util.Map;

/**
 * spi strategy interface state machine provider.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ProviderStateMachine {


    /**
     * send event to this state machine.
     *
     * @param event             event to sent
     * @param contextAttributes attributes of context. theses attribute will applying to {@link io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext}
     */
    boolean sendEvent(Event event, Map<String, Object> contextAttributes);


    /**
     * start this state machine.
     *
     * @param contextAttributes attributes of context. theses attribute will applying to {@link io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext}
     */
    void start(Map<String, Object> contextAttributes);


    /**
     * return state of now.
     *
     * @return
     */
    State currentState();
}
