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
import io.scleropages.sentarum.item.core.entity.ItemEntity;
import io.scleropages.sentarum.item.core.entity.SpuEntity;
import io.scleropages.sentarum.item.ge.entity.MediaEntity;
import io.scleropages.sentarum.item.ge.entity.StructureTextEntity;
import io.scleropages.sentarum.item.ge.model.Media;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.Spu;
import io.scleropages.sentarum.item.core.model.impl.ItemModel;
import io.scleropages.sentarum.item.property.model.PropertyValue;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface ItemEntityMapper extends ModelMapper<ItemEntity, ItemModel> {

    default Integer toOrdinal(Item.ItemType itemType) {
        return itemType.getOrdinal();
    }

    default Item.ItemType toItemType(Integer ordinal) {
        return Item.ItemType.getByOrdinal(ordinal);
    }

    default Integer toOrdinal(Item.SellerType sellerType) {
        return sellerType.getOrdinal();
    }

    default Item.SellerType toSellerType(Integer ordinal) {
        return Item.SellerType.getByOrdinal(ordinal);
    }

    default Integer toOrdinal(Item.Status status) {
        return status.getOrdinal();
    }

    default Item.Status toStatus(Integer ordinal) {
        return Item.Status.getByOrdinal(ordinal);
    }

    default Spu toSpu(SpuEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (Spu) ModelMapperRepository.getRequiredModelMapper(SpuEntityMapper.class).mapForRead(entity);
    }

    default SpuEntity toSpuEntity(Spu spu) {
        return null;
    }


    default StandardCategory toCategory(StandardCategoryEntity entity){
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (StandardCategory) ModelMapperRepository.getRequiredModelMapper(StandardCategoryEntityMapper.class).mapForRead(entity);
    }

    default StandardCategoryEntity toStandardCategoryEntity(StandardCategory standardCategory){return null;}

    default List<PropertyValue> toProperties(StructureTextEntity entity) {
        SpuEntityMapper mapper = (SpuEntityMapper) ModelMapperRepository.getRequiredModelMapper(SpuEntityMapper.class);
        return mapper.toProperties(entity);
    }

    default StructureTextEntity toStructureTextEntity(List<PropertyValue> propertyValues) {
        SpuEntityMapper mapper = (SpuEntityMapper) ModelMapperRepository.getRequiredModelMapper(SpuEntityMapper.class);
        return mapper.toStructureTextEntity(propertyValues);
    }


    default MediaEntity toMediaEntity(Media media) {
        SpuEntityMapper mapper = (SpuEntityMapper) ModelMapperRepository.getRequiredModelMapper(SpuEntityMapper.class);
        return mapper.toMediaEntity(media);
    }

    default Media toMedia(MediaEntity mediaEntity) {
        SpuEntityMapper mapper = (SpuEntityMapper) ModelMapperRepository.getRequiredModelMapper(SpuEntityMapper.class);
        return mapper.toMedia(mediaEntity);
    }


    default List<Media> mediaEntityListToMediaList(List<MediaEntity> list) {
        SpuEntityMapper mapper = (SpuEntityMapper) ModelMapperRepository.getRequiredModelMapper(SpuEntityMapper.class);
        return mapper.mediaEntityListToMediaList(list);
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
