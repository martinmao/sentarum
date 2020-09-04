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
package io.scleropages.sentarum.item.core.entity.mapper;

import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.category.entity.mapper.StandardCategoryEntityMapper;
import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.core.entity.SpuEntity;
import io.scleropages.sentarum.item.ge.entity.MediaEntity;
import io.scleropages.sentarum.item.ge.entity.StructureTextEntity;
import io.scleropages.sentarum.item.ge.entity.mapper.MediaEntityMapper;
import io.scleropages.sentarum.item.ge.model.Media;
import io.scleropages.sentarum.item.ge.model.StructureText;
import io.scleropages.sentarum.item.core.model.Spu;
import io.scleropages.sentarum.item.core.model.impl.SpuModel;
import io.scleropages.sentarum.item.property.model.PropertyValue;
import io.scleropages.sentarum.item.property.model.impl.PropertyValueModel;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface SpuEntityMapper extends ModelMapper<SpuEntity, SpuModel> {


    default Integer toOrdinal(Spu.Status status) {
        return status.getOrdinal();
    }

    default Spu.Status toStatus(Integer ordinal) {
        return Spu.Status.getByOrdinal(ordinal);
    }

    default List<PropertyValue> toProperties(StructureTextEntity entity) {
        if (!isEntityInitialized(entity))
            return null;
        StructureText.MediaType mediaType = StructureText.MediaType.getByOrdinal(entity.getMediaType());
        if (mediaType == StructureText.MediaType.JSON) {
            return JsonMapper2.fromJson(entity.getText(), List.class, PropertyValueModel.class);
        }
        throw new IllegalStateException("just support json format for structure text. current: " + mediaType);
    }

    default StructureTextEntity toStructureTextEntity(List<PropertyValue> propertyValues) {
        return null;
    }

    default StandardCategoryEntity toStandardCategoryEntity(StandardCategory standardCategory) {
        return null;
    }

    default StandardCategory toStandardCategory(StandardCategoryEntity entity) {
        if (!isEntityInitialized(entity))
            return null;
        return (StandardCategory) ModelMapperRepository.getRequiredModelMapper(StandardCategoryEntityMapper.class).mapForRead(entity);
    }


    default MediaEntity toMediaEntity(Media media) {
        return null;
    }

    default Media toMedia(MediaEntity mediaEntity) {
        if (!isEntityInitialized(mediaEntity))
            return null;
        return (Media) ModelMapperRepository.getRequiredModelMapper(MediaEntityMapper.class).mapForRead(mediaEntity);
    }


    default List<Media> mediaEntityListToMediaList(List<MediaEntity> list) {
        if (!isEntityInitialized(list))
            return null;
        List<Media> list1 = new ArrayList<Media>(list.size());
        for (MediaEntity mediaEntity : list) {
            list1.add(toMedia(mediaEntity));
        }
        return list1;
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
