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

import io.scleropages.sentarum.core.fsm.entity.InvocationConfigEntity;
import io.scleropages.sentarum.core.fsm.entity.StateEntity;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.impl.StateModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface StateEntityMapper extends ModelMapper<StateEntity, StateModel> {

    default InvocationConfig toInvocationConfig(InvocationConfigEntity entity) {
        return (InvocationConfig) ModelMapperRepository.getRequiredModelMapper(InvocationConfigEntityMapper.class).mapForRead(entity);
    }

    default InvocationConfigEntity toInvocationConfigEntity(InvocationConfig invocationConfig) {
        return (InvocationConfigEntity) ModelMapperRepository.getRequiredModelMapper(InvocationConfigEntityMapper.class).mapForSave(invocationConfig);
    }
}
