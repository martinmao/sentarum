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
package io.scleropages.sentarum.core.fsm.entity.mapper;

import io.scleropages.sentarum.core.fsm.entity.EventDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.InvocationConfigEntity;
import io.scleropages.sentarum.core.fsm.entity.StateEntity;
import io.scleropages.sentarum.core.fsm.entity.StateTransitionEntity;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.impl.StateTransitionModel;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateTransitionEntityMapper extends ModelMapper<StateTransitionEntity, StateTransitionModel> {


    default State toState(StateEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (State) ModelMapperRepository.getRequiredModelMapper(StateEntityMapper.class).mapForRead(entity);
    }

    default StateEntity toStateEntity(State state) {
        return null;
    }


    default EventDefinition toEventDefinition(EventDefinitionEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (EventDefinition) ModelMapperRepository.getRequiredModelMapper(EventDefinitionEntityMapper.class).mapForRead(entity);
    }

    default EventDefinitionEntity toEventDefinitionEntity(EventDefinition eventDefinition) {
        return null;
    }

    default InvocationConfig toInvocationConfig(InvocationConfigEntity entity) {
        return (InvocationConfig) ModelMapperRepository.getRequiredModelMapper(InvocationConfigEntityMapper.class).mapForRead(entity);
    }

    default InvocationConfigEntity toInvocationConfigEntity(InvocationConfig invocationConfig) {
        return null;
    }
}
