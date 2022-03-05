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
package io.scleropages.sentarum.cloud.item.mapper;

import com.google.common.collect.Maps;
import io.scleropages.sentarum.cloud.v_1_0_0.model.ItemCreateRequest;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.impl.ItemModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.DataObjectMapper;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = DataObjectMapper.DefaultConfig.class)
public interface ItemCreateRequestMapper extends DataObjectMapper<ItemCreateRequest, ItemModel> {

    default Map<Long, Object> asPropertyValuesMap(List<ItemCreateRequest.PropertyValue> propertyValues) {
        if (CollectionUtils.isEmpty(propertyValues)) {
            return Collections.emptyMap();
        }
        Map<Long, Object> propertyValuesMap = Maps.newHashMap();
        propertyValues.forEach(propertyValue -> {
            propertyValuesMap.put(propertyValue.getPropMetaId(), propertyValue.getPropertyValue());
        });
        return propertyValuesMap;
    }

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
}
