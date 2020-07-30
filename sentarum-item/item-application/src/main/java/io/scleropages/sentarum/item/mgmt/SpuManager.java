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

import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.category.model.CategoryProperty;
import io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType;
import io.scleropages.sentarum.item.entity.SpuEntity;
import io.scleropages.sentarum.item.entity.mapper.SpuEntityMapper;
import io.scleropages.sentarum.item.model.impl.KeyPropertyValueModel;
import io.scleropages.sentarum.item.model.impl.SpuModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyValueModel;
import io.scleropages.sentarum.item.repo.SpuRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Spu管理器
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("30")
public class SpuManager implements GenericManager<SpuModel, Long, SpuEntityMapper> {

    private SpuRepository spuRepository;
    private CategoryManager categoryManager;
    private PropertyValueManager propertyValueManager;


    @Validated({SpuModel.Create.class})
    @Transactional
    @BizError("10")
    public void createSpu(@Valid SpuModel model, Long stdCategoryId, Map<Long, Object> values) {
        Assert.notNull(stdCategoryId, "stdCategoryId must not be null.");
        Assert.notEmpty(values, "values must not be empty.");
        SpuEntity spuEntity = getModelMapper().mapForSave(model);
        categoryManager.awareStandardCategoryEntity(stdCategoryId, spuEntity);
        List<CategoryProperty> categoryProperties = categoryManager.getAllCategoryProperties(stdCategoryId, CategoryPropertyBizType.KEY_PROPERTY, CategoryPropertyBizType.SPU_PROPERTY);

        Map<Long, PropertyValueModel> propertiesValues = Maps.newHashMap();

        for (int i = 0; i < categoryProperties.size(); i++) {
            CategoryProperty categoryProperty = categoryProperties.get(i);
            Long metaId = categoryProperty.propertyMetadata().id();
            Object value = values.get(metaId);
            categoryProperty.assertsValueRule(value);
            CategoryPropertyBizType bizType = categoryProperty.categoryPropertyBizType();
            PropertyValueModel propertyValueModel = createPropertyValueModel(bizType, value);
            propertiesValues.put(metaId, propertyValueModel);
        }

        values.keySet().forEach(valueKey -> {
            if (!propertiesValues.containsKey(valueKey)) {
                throw new IllegalArgumentException("unknown spu property metadata id: " + valueKey);
            }
        });
        Long spuId = spuRepository.save(spuEntity).getId();
        updateBizId(propertiesValues, spuId);
        propertyValueManager.createPropertyValues(propertiesValues);
    }

    @Validated({SpuModel.Update.class})
    @Transactional
    @BizError("11")
    public void saveSpu(@Valid SpuModel model, Map<Long, Object> values) {

    }


    protected PropertyValueModel createPropertyValueModel(CategoryPropertyBizType bizType, Object value) {
        PropertyValueModel valueModel;
        if (bizType.isKeyProperty())
            valueModel = new KeyPropertyValueModel();
        else
            valueModel = new PropertyValueModel();
        valueModel.setBizType(bizType.getOrdinal());
        valueModel.setValue(value);
        return valueModel;
    }

    protected void updateBizId(Map<Long, PropertyValueModel> propertiesValues, Long bizId) {
        for (PropertyValueModel value : propertiesValues.values()) {
            value.setBizId(bizId);
        }
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
