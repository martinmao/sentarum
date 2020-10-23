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

import io.scleropages.sentarum.core.fsm.entity.StateEntity;
import io.scleropages.sentarum.core.fsm.entity.StateMachineDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateMachineExecutionEntity;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionContextModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface StateMachineExecutionEntityMapper extends ModelMapper<StateMachineExecutionEntity, StateMachineExecutionModel> {

    default Integer toOrdinal(StateMachineExecution.ExecutionState executionState) {
        return executionState.getOrdinal();
    }

    default StateMachineExecution.ExecutionState toExecutionState(Integer ordinal) {
        return StateMachineExecution.ExecutionState.getByOrdinal(ordinal);
    }

    default State toState(StateEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (State) ModelMapperRepository.getRequiredModelMapper(StateEntityMapper.class).mapForRead(entity);
    }

    default StateEntity toStateEntity(State state) {
        return null;
    }


    default StateMachineDefinition toStateMachineDefinition(StateMachineDefinitionEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (StateMachineDefinition) ModelMapperRepository.getRequiredModelMapper(StateMachineDefinitionEntityMapper.class).mapForRead(entity);
    }

    default String executionContextToPayload(StateMachineExecutionContext executionContext) {
        if (null == executionContext)
            return null;
        return ((StateMachineExecutionContextModel) executionContext).getContextPayload();
    }

    default StateMachineExecutionContext payloadToExecutionContext(String contextPayload) {
        return StateMachineExecutionContextModel.newInstance(contextPayload);
    }
}
