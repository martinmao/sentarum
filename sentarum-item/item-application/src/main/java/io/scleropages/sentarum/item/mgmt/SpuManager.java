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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.category.model.CategoryProperty;
import io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType;
import io.scleropages.sentarum.item.entity.SpuEntity;
import io.scleropages.sentarum.item.entity.mapper.SpuEntityMapper;
import io.scleropages.sentarum.item.model.impl.SpuModel;
import io.scleropages.sentarum.item.property.model.PropertyValue;
import io.scleropages.sentarum.item.property.model.impl.KeyPropertyValueModel;
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

import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.KEY_PROPERTY;
import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.SPU_PROPERTY;

/**
 * Spu管理器，提供spu相关通用原子管理功能
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


    /**
     * 创建一个spu
     *
     * @param model         spu模型
     * @param stdCategoryId 挂靠的标准类目id
     * @param values        属性值集合（key为属性元数据id，value为属性值）
     */
    @Validated({SpuModel.Create.class})
    @Transactional
    @BizError("10")
    public void createSpu(@Valid SpuModel model, Long stdCategoryId, Map<Long, Object> values) {
        Assert.notNull(stdCategoryId, "stdCategoryId must not be null.");
        Assert.notEmpty(values, "values must not be empty.");
        SpuEntity spuEntity = getModelMapper().mapForSave(model);
        categoryManager.awareStandardCategoryEntity(stdCategoryId, spuEntity);
        List<CategoryProperty> categoryProperties = categoryManager.getAllCategoryProperties(stdCategoryId, KEY_PROPERTY, SPU_PROPERTY);

        Map<Long, PropertyValueModel> propertiesValues = Maps.newHashMap();

        for (CategoryProperty categoryProperty : categoryProperties) {
            Long metaId = categoryProperty.propertyMetadata().id();
            Object value = values.get(metaId);
            categoryProperty.assertsValueRule(value);
            if (null == value)
                continue;
            PropertyValueModel propertyValueModel = createPropertyValueModel(categoryProperty.categoryPropertyBizType(), value);
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

    /**
     * 更新保存spu
     *
     * @param model     spu模型
     * @param keyValues 关键属性值集合(key为属性值id，value为属性值)
     * @param spuValues spu属性值集合(key为属性值id，value为属性值)
     */
    @Validated({SpuModel.Update.class})
    @Transactional
    @BizError("11")
    public void saveSpu(@Valid SpuModel model, Map<Long, Object> keyValues, Map<Long, Object> spuValues) {
        Long spuId = model.id();
        SpuEntity spuEntity = spuRepository.getById(spuId).orElseThrow(() -> new IllegalArgumentException("no spu found: " + spuId));
        getModelMapper().mapForUpdate(model, spuEntity);
        spuRepository.save(spuEntity);

        Map<Long, PropertyValueModel> metaIdToPv = Maps.newHashMap();//mapping property meta id to property value.


        propertyValueManager.findAllPropertiesValue(KEY_PROPERTY.getOrdinal(), spuId, KeyPropertyValueModel.class).forEach(o -> {
            metaIdToPv.put(o.propertyMetaId(), (PropertyValueModel) o);
            if (null == keyValues)
                return;
            if (keyValues.containsKey(o.id())) {
                o.changeValue(keyValues.get(o.id()));
            }
        });
        propertyValueManager.findAllPropertiesValue(SPU_PROPERTY.getOrdinal(), spuId, PropertyValueModel.class).forEach(o -> {
            metaIdToPv.put(o.propertyMetaId(), (PropertyValueModel) o);
            if (null != spuValues && spuValues.containsKey(o.id())) {
                o.changeValue(spuValues.get(o.id()));
            }
        });

        List<CategoryProperty> categoryProperties = categoryManager.getAllCategoryProperties(spuEntity.getCategory().getId(), KEY_PROPERTY, SPU_PROPERTY);

        for (CategoryProperty categoryProperty : categoryProperties) {
            PropertyValue propertyValue = metaIdToPv.get(categoryProperty.propertyMetadata().id());
            categoryProperty.assertsValueRule(propertyValue.value());
        }
        List<PropertyValueModel> updates = Lists.newArrayList(metaIdToPv.values());
        propertyValueManager.savePropertyValues(updates);
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
