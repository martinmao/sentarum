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
package io.scleropages.sentarum.item.ge.entity.mapper;

import io.scleropages.sentarum.item.ge.entity.MediaEntity;
import io.scleropages.sentarum.item.ge.model.Media;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;

import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface MediaEntityMapper extends ModelMapper<MediaEntity, Media> {

    default Integer toOrdinal(Media.ContentType contentType) {
        return contentType.getOrdinal();
    }

    default Media.ContentType toContentType(Integer ordinal) {
        return Media.ContentType.getByOrdinal(ordinal);
    }


    default Integer toOrdinal(Media.BizType bizType) {
        return bizType.getOrdinal();
    }

    default Media.BizType toBizType(Integer ordinal) {
        return Media.BizType.getByOrdinal(ordinal);
    }


    default Integer toOrdinal(Media.Status status) {
        return status.getOrdinal();
    }

    default Media.Status toStatus(Integer ordinal) {
        return Media.Status.getByOrdinal(ordinal);
    }

    default String additionalAttributesToPayload(Map<String, Object> additionalAttributes) {
        if (null == additionalAttributes)
            return null;
        return JsonMapper2.toJson(additionalAttributes);
    }

    default Map<String, Object> payloadToAdditionalAttributes(String payload) {
        if (null == payload)
            return null;
        return JsonMapper2.fromJson(payload);
    }
}
