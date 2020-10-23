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

import io.scleropages.sentarum.core.fsm.StateMachine;

import java.util.Map;

/**
 * spi strategy interface for building {@link StateMachine}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineFactory {

    /**
     * create new state machine with initial state.
     *
     * @param definitionId      id for {@link io.scleropages.sentarum.core.fsm.model.StateMachineDefinition}
     * @param bizType           type of business model.
     * @param bizId             id of business model.
     * @param contextAttributes optional context attributes.
     * @return
     */
    StateMachine createStateMachine(Long definitionId, Integer bizType, Long bizId, Map<String, Object> contextAttributes);

    /**
     * get exists state machine by id.
     *
     * @param machineId id for {@link StateMachine}
     * @return
     */
    StateMachine getStateMachine(Long machineId);
}
