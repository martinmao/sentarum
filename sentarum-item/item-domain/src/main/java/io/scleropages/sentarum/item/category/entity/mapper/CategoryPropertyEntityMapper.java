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
package io.scleropages.sentarum.item.category.entity.mapper;

import io.scleropages.sentarum.item.category.entity.CategoryPropertyEntity;
import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.category.model.CategoryProperty;
import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.category.model.impl.CategoryPropertyModel;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryModel;
import io.scleropages.sentarum.item.property.entity.PropertyMetaEntity;
import io.scleropages.sentarum.item.property.entity.PropertyValueEntity;
import io.scleropages.sentarum.item.property.entity.mapper.PropertyMetaEntityMapper;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValue;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface CategoryPropertyEntityMapper extends ModelMapper<CategoryPropertyEntity, CategoryPropertyModel> {

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


    default CategoryProperty.CategoryPropertyBizType toCategoryPropertyBizType(Integer ordinal) {
        return CategoryProperty.CategoryPropertyBizType.getByOrdinal(ordinal);
    }

    default Integer toOrdinal(CategoryProperty.CategoryPropertyBizType categoryPropertyBizType) {
        return categoryPropertyBizType.ordinal();
    }

    default PropertyValue toPropertyValue(PropertyValueEntity entity) {
        return null;
    }

    default PropertyValueEntity toPropertyValueEntity(PropertyValue model) {
        return null;
    }

    default PropertyMetadata toPropertyMetadata(PropertyMetaEntity entity) {
        PropertyMetaEntityMapper mapper = (PropertyMetaEntityMapper) ModelMapperRepository.getRequiredModelMapper(PropertyMetaEntityMapper.class);
        return mapper.mapForRead(entity);
    }

    default PropertyMetaEntity toPropertyMetaEntity(PropertyMetadata model) {
        PropertyMetaEntityMapper mapper = (PropertyMetaEntityMapper) ModelMapperRepository.getRequiredModelMapper(PropertyMetaEntityMapper.class);
        return mapper.mapForSave((PropertyMetadataModel) model);
    }

    default StandardCategoryEntity toStandardCategoryEntity(StandardCategory model) {
        StandardCategoryEntityMapper mapper = (StandardCategoryEntityMapper) ModelMapperRepository.getRequiredModelMapper(StandardCategoryEntityMapper.class);
        return mapper.mapForSave((StandardCategoryModel) model);
    }

    default StandardCategory toStandardCategory(StandardCategoryEntity entity) {
        StandardCategoryEntityMapper mapper = (StandardCategoryEntityMapper) ModelMapperRepository.getRequiredModelMapper(StandardCategoryEntityMapper.class);
        return mapper.mapForRead(entity);
    }
}
