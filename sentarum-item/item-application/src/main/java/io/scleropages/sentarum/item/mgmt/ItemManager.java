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
package io.scleropages.sentarum.item.mgmt;

import io.scleropages.sentarum.item.entity.ItemEntity;
import io.scleropages.sentarum.item.entity.SpuEntity;
import io.scleropages.sentarum.item.entity.mapper.ItemEntityMapper;
import io.scleropages.sentarum.item.model.impl.ItemModel;
import io.scleropages.sentarum.item.model.impl.SpuModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyValueModel;
import io.scleropages.sentarum.item.repo.ItemRepository;
import io.scleropages.sentarum.item.repo.SpuRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.ITEM_PROPERTY;

/**
 * Item管理器，提供Item相关通用原子管理功能
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("40")
public class ItemManager implements GenericManager<ItemModel, Long, ItemEntityMapper> {

    private ItemRepository itemRepository;
    private SpuRepository spuRepository;
    private CategoryManager categoryManager;
    private PropertyValueManager propertyValueManager;

    @Validated({ItemModel.Create.class})
    @Transactional
    @BizError("10")
    public void createItem(@Valid ItemModel model, Long spuId, Map<Long, Object> values) {
        Assert.notNull(spuId, "spuId is required.");
        ItemEntity itemEntity = getModelMapper().mapForSave(model);
        SpuEntity spuEntity = spuRepository.getById(spuId).orElseThrow(() -> new IllegalArgumentException("no spu found: " + spuId));
        itemEntity.setSpu(spuEntity);
        itemEntity.setCategory(spuEntity.getCategory());
        Map<Long, PropertyValueModel> propertiesValues = categoryManager.buildCategoryPropertyValues(spuEntity.getCategory().getId(), values, ITEM_PROPERTY);
        itemRepository.save(itemEntity);
        for (PropertyValueModel value : propertiesValues.values()) {
            value.setBizId(itemEntity.getId());
        }
        propertyValueManager.createPropertyValues(propertiesValues);
    }


    /**
     * 更新保存spu
     *
     * @param model      spu模型
     * @param itemValues 商品属性值集合(key为属性值id，value为属性值)
     */
    @Validated({SpuModel.Update.class})
    @Transactional
    @BizError("11")
    public void saveItem(@Valid ItemModel model, Map<Long, Object> itemValues) {
        Long itemId = model.id();
        ItemEntity itemEntity = itemRepository.getById(itemId).orElseThrow(() -> new IllegalArgumentException("no item found: " + itemId));
        getModelMapper().mapForUpdate(model, itemEntity);
        itemRepository.save(itemEntity);

        List<PropertyValueModel> propertyValueModels = categoryManager.applyCategoryPropertyValuesChanges(itemEntity.getCategory().getId(), itemId
                , new CategoryManager.PropertyValueChange(ITEM_PROPERTY, itemValues, PropertyValueModel.class));

        if (CollectionUtils.isEmpty(propertyValueModels))
            return;
        propertyValueManager.savePropertyValues(propertyValueModels);
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setSpuRepository(SpuRepository spuRepository) {
        this.spuRepository = spuRepository;
    }

    @Autowired
    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Autowired
    public void setPropertyValueManager(PropertyValueManager propertyValueManager) {
        this.propertyValueManager = propertyValueManager;
    }
}
