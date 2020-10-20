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
import io.scleropages.sentarum.core.fsm.entity.EventEntity;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.impl.EventModel;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface EventEntityMapper extends ModelMapper<EventEntity, EventModel> {

    default EventDefinition toEventDefinition(EventDefinitionEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (EventDefinition) ModelMapperRepository.getRequiredModelMapper(EventDefinitionEntityMapper.class).mapForRead(entity);
    }

    default EventDefinitionEntity toEventDefinitionEntity(EventDefinition eventDefinition) {
        return null;
    }

    default String bodyToPayload(Map<String, Object> body) {
        if (null == body)
            return null;
        return JsonMapper2.toJson(body);
    }

    default Map<String, Object> payloadToBody(String payload) {
        if (null == payload)
            return null;
        return JsonMapper2.fromJson(payload);
    }
}
