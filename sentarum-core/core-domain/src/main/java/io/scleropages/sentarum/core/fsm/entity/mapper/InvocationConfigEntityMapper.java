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
import io.scleropages.sentarum.core.fsm.model.impl.InvocationConfigModel;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.core.util.Reflections2;
import org.scleropages.crud.ModelMapper;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface InvocationConfigEntityMapper extends ModelMapper<InvocationConfigEntity, InvocationConfigModel> {


    @Override
    default InvocationConfigModel mapForRead(InvocationConfigEntity entity) {
        if (!isEntityInitialized(entity))
            return null;
        try {
            return JsonMapper2.fromJson(entity.getConfigPayload(), Reflections2.getClass(entity.getConfigImplementation()));
        } catch (Exception e) {
            throw new IllegalStateException("failure to create InvocationConfig by entity: " + entity.getId(), e);
        }
    }

    @Override
    default InvocationConfigEntity mapForSave(InvocationConfigModel model) {
        if (null == model)
            return null;
        InvocationConfigEntity configEntity = new InvocationConfigEntity();
        configEntity.setConfigImplementation(model.getConfigImplementation());
        configEntity.setInvocationImplementation(model.getInvocationImplementation());
        configEntity.setConfigPayload(JsonMapper2.toJson(model));
        return configEntity;
    }
}
