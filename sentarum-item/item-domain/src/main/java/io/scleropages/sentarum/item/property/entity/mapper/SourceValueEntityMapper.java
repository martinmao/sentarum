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
package io.scleropages.sentarum.item.property.entity.mapper;

import io.scleropages.sentarum.item.property.entity.SourceValueEntity;
import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;

import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface SourceValueEntityMapper extends ModelMapper<SourceValueEntity, SourceValueModel> {

    default String attributesToPayload(Map<String, Object> attributes) {
        return JsonMapper2.toJson(attributes);

    }

    default Map<String, Object> payloadToAttributes(String payload) {
        return JsonMapper2.fromJson(payload);
    }
}
