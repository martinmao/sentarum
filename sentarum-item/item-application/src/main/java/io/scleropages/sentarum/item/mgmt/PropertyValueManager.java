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
import io.scleropages.sentarum.item.entity.SpuPropertyValueEntity;
import io.scleropages.sentarum.item.entity.mapper.SpuPropertyValueEntityMapper;
import io.scleropages.sentarum.item.property.Inputs;
import io.scleropages.sentarum.item.property.PropertyValidators;
import io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity;
import io.scleropages.sentarum.item.property.entity.PropertyValueEntity;
import io.scleropages.sentarum.item.property.entity.mapper.PropertyValueEntityMapper;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.impl.PropertyValueModel;
import io.scleropages.sentarum.item.property.repo.PropertyValueRepository;
import io.scleropages.sentarum.item.repo.SpuPropertyValueRepository;
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
 * 属性值管理器
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("50")
public class PropertyValueManager implements GenericManager<PropertyValueModel, Long, PropertyValueEntityMapper> {

    private PropertyValueRepository propertyValueRepository;

    private SpuPropertyValueRepository spuPropertyValueRepository;

    private PropertyManager propertyManager;

    @Transactional
    @BizError("10")
    @Validated({PropertyValueModel.Create.class})
    public void createPropertyValue(@Valid PropertyValueModel model, Long propertyMetaId) {
        PropertyValueEntity propertyValueEntity = getModelMapper().mapForSave(model);
        beforeSave(propertyValueEntity, model, propertyMetaId);
        propertyValueRepository.save(propertyValueEntity);
    }


    @Transactional
    @BizError("11")
    @Validated({PropertyValueModel.Update.class})
    public void savePropertyValue(@Valid PropertyValueModel model) {
        PropertyValueEntity entity = propertyValueRepository.get(model.id()).orElseThrow(() -> new IllegalArgumentException("no property value found: " + model.id()));
        getModelMapper().mapForUpdate(model, entity);
        beforeSave(entity, model, entity.getPropertyMetaId());
        propertyValueRepository.save(entity);
    }

    @Transactional
    @BizError("12")
    public void deletePropertyValue(Long propertyId) {
        Assert.notNull(propertyId, "property id must not be null.");
        propertyValueRepository.deleteById(propertyId);
    }

    @Transactional
    @BizError("13")
    @Validated({PropertyValueModel.Create.class})
    public void createPropertyValues(@Valid Map<Long, PropertyValueModel> models) {
        Assert.notEmpty(models, "property values must not be empty.");
        List<PropertyValueEntity> entities = Lists.newArrayList();
        models.forEach((propertyMetaId, model) -> {
            PropertyValueEntity propertyValueEntity = getModelMapper().mapForSave(model);
            beforeSave(propertyValueEntity, model, propertyMetaId);
            entities.add(propertyValueEntity);
        });
        propertyValueRepository.batchSave(entities);
    }


    @Transactional
    @BizError("20")
    @Validated({PropertyValueModel.Create.class})
    public void createSpuPropertyValue(@Valid PropertyValueModel model, Long propertyMetaId) {
        SpuPropertyValueEntity propertyValueEntity = getModelMapper(SpuPropertyValueEntityMapper.class).mapForSave(model);
        beforeSave(propertyValueEntity, model, propertyMetaId);
        spuPropertyValueRepository.save(propertyValueEntity);
    }


    @Transactional
    @BizError("21")
    @Validated({PropertyValueModel.Update.class})
    public void saveSpuPropertyValue(@Valid PropertyValueModel model) {
        SpuPropertyValueEntity entity = spuPropertyValueRepository.get(model.id()).orElseThrow(() -> new IllegalArgumentException("no property value found: " + model.id()));
        getModelMapper(SpuPropertyValueEntityMapper.class).mapForUpdate(model, entity);
        beforeSave(entity, model, entity.getPropertyMetaId());
        spuPropertyValueRepository.save(entity);
    }

    @Transactional
    @BizError("23")
    public void deleteSpuPropertyValue(Long propertyId) {
        Assert.notNull(propertyId, "property id must not be null.");
        spuPropertyValueRepository.deleteById(propertyId);
    }

    @Transactional
    @BizError("24")
    @Validated({PropertyValueModel.Create.class})
    public void createSpuPropertyValues(@Valid Map<Long, PropertyValueModel> models) {
        Assert.notEmpty(models, "property values must not be null.");
        List<SpuPropertyValueEntity> entities = Lists.newArrayList();
        models.forEach((propertyMetaId, model) -> {
            SpuPropertyValueEntity propertyValueEntity = getModelMapper(SpuPropertyValueEntityMapper.class).mapForSave(model);
            beforeSave(propertyValueEntity, model, propertyMetaId);
            entities.add(propertyValueEntity);
        });
        spuPropertyValueRepository.batchSave(entities);
    }


    protected void beforeSave(AbstractPropertyValueEntity entity, PropertyValueModel model, Long propertyMetaId) {
        PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadataDetail(propertyMetaId);
        Inputs.addValues(propertyMetadata.input(), model.value());
        PropertyValidators.assertInputValid(propertyMetadata);
        entity.setValue(model.value(), propertyMetadata);
        entity.setPropertyMetaId(propertyMetadata.id());
    }


    @Autowired
    public void setPropertyValueRepository(PropertyValueRepository propertyValueRepository) {
        this.propertyValueRepository = propertyValueRepository;
    }

    @Autowired
    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    @Autowired
    public void setSpuPropertyValueRepository(SpuPropertyValueRepository spuPropertyValueRepository) {
        this.spuPropertyValueRepository = spuPropertyValueRepository;
    }
}
