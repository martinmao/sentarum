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

import io.scleropages.sentarum.item.property.entity.GroupedMetaEntity;
import io.scleropages.sentarum.item.property.entity.GroupedMetaEntryEntity;
import io.scleropages.sentarum.item.property.entity.PropertyMetaEntity;
import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata.OrderedPropertyMetadata;
import io.scleropages.sentarum.item.property.model.impl.GroupedPropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface GroupedMetaEntityMapper extends ModelMapper<GroupedMetaEntity, GroupedPropertyMetadataModel> {


    default GroupedMetaEntryEntity toGroupedMetaEntryEntity(OrderedPropertyMetadata model) {
        PropertyMetaEntityMapper mapper = (PropertyMetaEntityMapper) ModelMapperRepository.getRequiredModelMapper(PropertyMetaEntityMapper.class);
        PropertyMetaEntity propertyMetaEntity = mapper.mapForSave((PropertyMetadataModel) model.getPropertyMetadata());
        GroupedMetaEntryEntity groupedMetaEntryEntity = new GroupedMetaEntryEntity();
        groupedMetaEntryEntity.setOrder(model.getOrder());
        groupedMetaEntryEntity.setPropertyMetadata(propertyMetaEntity);
        return groupedMetaEntryEntity;
    }


    default OrderedPropertyMetadata toOrderedPropertyMetadata(GroupedMetaEntryEntity entity) {
        if (!isEntityInitialized(entity))
            return null;
        PropertyMetaEntityMapper mapper = (PropertyMetaEntityMapper) ModelMapperRepository.getRequiredModelMapper(PropertyMetaEntityMapper.class);
        OrderedPropertyMetadata model = new OrderedPropertyMetadata(entity.getOrder(), mapper.mapForRead(entity.getPropertyMetadata()));
        return model;
    }


    default List<OrderedPropertyMetadata> groupedMetaEntryEntityListToOrderedPropertyMetadataList(List<GroupedMetaEntryEntity> list) {
        if (!isEntityInitialized(list))
            return null;

        List<OrderedPropertyMetadata> list1 = new ArrayList(list.size());
        for (GroupedMetaEntryEntity groupedMetaEntryEntity : list) {
            list1.add(toOrderedPropertyMetadata(groupedMetaEntryEntity));
        }

        return list1;
    }


}