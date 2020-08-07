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
import io.scleropages.sentarum.item.property.Inputs;
import io.scleropages.sentarum.item.property.PropertyValidators;
import io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity;
import io.scleropages.sentarum.item.property.entity.KeyPropertyValueEntity;
import io.scleropages.sentarum.item.property.entity.PropertyValueEntity;
import io.scleropages.sentarum.item.property.entity.mapper.AbstractPropertyValueEntityMapper;
import io.scleropages.sentarum.item.property.entity.mapper.KeyPropertyValueEntityMapper;
import io.scleropages.sentarum.item.property.entity.mapper.PropertyValueEntityMapper;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValue;
import io.scleropages.sentarum.item.property.model.impl.KeyPropertyValueModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyValueModel;
import io.scleropages.sentarum.item.property.repo.AbstractPropertyValueRepository;
import io.scleropages.sentarum.item.property.repo.KeyPropertyValueRepository;
import io.scleropages.sentarum.item.property.repo.PropertyValueRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 属性值管理器,提供通用的原子的功能
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("50")
public class PropertyValueManager implements GenericManager<PropertyValueModel, Long, PropertyValueEntityMapper> {

    private PropertyValueRepository propertyValueRepository;

    private KeyPropertyValueRepository keyPropertyValueRepository;

    private PropertyManager propertyManager;


    /**
     * 创建一个属性值
     *
     * @param model          属性模型
     * @param propertyMetaId 元数据id
     */
    @Transactional
    @BizError("10")
    @Validated({PropertyValueModel.Create.class})
    public void createPropertyValue(@Valid PropertyValueModel model, Long propertyMetaId) {
        AbstractPropertyValueEntity propertyValueEntity = (AbstractPropertyValueEntity) getMapper(model).mapForSave(model);
        PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadataDetail(propertyMetaId);
        validate(model, propertyMetadata);
        applyEntityBeforeSave(propertyValueEntity, model, propertyMetadata);
        getRepository(model).save(propertyValueEntity);
    }


    /**
     * 更新保存属性值
     *
     * @param model
     */
    @Transactional
    @BizError("11")
    @Validated({PropertyValueModel.Update.class})
    public void savePropertyValue(@Valid PropertyValueModel model) {
        Optional optional = getRepository(model).get(model.id());
        Assert.isTrue(optional.isPresent(), "no property value found: " + model.id());
        AbstractPropertyValueEntity entity = (AbstractPropertyValueEntity) optional.get();
        getMapper(model).mapForUpdate(model, entity);
        PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadataDetail(entity.getPropertyMetaId());
        validate(model, propertyMetadata);
        applyEntityBeforeSave(entity, model, propertyMetadata);
        getRepository(model).save(entity);
    }

    /**
     * 删除属性值
     *
     * @param propertyValueId
     */
    @Transactional
    @BizError("12")
    public void deletePropertyValue(Long propertyValueId) {
        Assert.notNull(propertyValueId, "property id must not be null.");
        propertyValueRepository.deleteById(propertyValueId);
    }

    /**
     * 删除关键属性
     *
     * @param propertyValueId
     */
    @Transactional
    @BizError("14")
    public void deleteKeyPropertyValue(Long propertyValueId) {
        Assert.notNull(propertyValueId, "property value id must not be null.");
        keyPropertyValueRepository.deleteById(propertyValueId);
    }

    /**
     * 批量新增属性值，其中key为属性元数据id，value为属性模型
     *
     * @param models key为属性元数据id，value为属性模型
     */
    @Transactional
    @BizError("13")
    @Validated({PropertyValueModel.Create.class})
    public void createPropertyValues(@Valid Map<Long, PropertyValueModel> models) {
        Assert.notEmpty(models, "property values must not be empty.");
        List<KeyPropertyValueEntity> keyPropertyValueEntities = Lists.newArrayList();//key属性预存列表
        List<PropertyValueEntity> propertyValueEntities = Lists.newArrayList();//普通属性预存列表
        List<PropertyMetadata> validates = Lists.newArrayList();
        models.forEach((propertyMetaId, model) -> {
            AbstractPropertyValueEntity propertyValueEntity = (AbstractPropertyValueEntity) getMapper(model).mapForSave(model);
            PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadataDetail(propertyMetaId);
            buildValidates(validates, propertyMetadata, model);
            applyEntityBeforeSave(propertyValueEntity, model, propertyMetadata);
            if (model instanceof KeyPropertyValueModel)
                keyPropertyValueEntities.add((KeyPropertyValueEntity) propertyValueEntity);
            else
                propertyValueEntities.add((PropertyValueEntity) propertyValueEntity);
        });
        assertValidates(validates);

        keyPropertyValueEntities.forEach(AbstractPropertyValueEntity::applyValue);
        propertyValueEntities.forEach(AbstractPropertyValueEntity::applyValue);

        keyPropertyValueRepository.batchSave(keyPropertyValueEntities);
        propertyValueRepository.batchSave(propertyValueEntities);
    }


    /**
     * 批量更新保存属性值，需要注意的是 models中的元素，必须通过类型具体区分,例如： {@link PropertyValueModel} {@link KeyPropertyValueModel}，不同的模型对应不同的存储表
     *
     * @param models
     */
    @Transactional
    @BizError("14")
    @Validated({PropertyValueModel.Update.class})
    public void savePropertyValues(@Valid List<PropertyValueModel> models) {
        Assert.notEmpty(models, "property values must not be empty.");

        List<KeyPropertyValueEntity> keyPropertyValueEntities = Lists.newArrayList();//key属性预存列表
        List<PropertyValueEntity> propertyValueEntities = Lists.newArrayList();//普通属性预存列表
        List<PropertyMetadata> validates = Lists.newArrayList();
        models.forEach((model) -> {
            Optional<AbstractPropertyValueEntity> optional = getRepository(model).get(model.id());
            Assert.isTrue(optional.isPresent(), "no property value found: " + model.id());
            AbstractPropertyValueEntity propertyValueEntity = optional.get();

            PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadataDetail(propertyValueEntity.getPropertyMetaId());
            buildValidates(validates, propertyMetadata, model);
            propertyValueEntity.prepareValue(model.value(), propertyMetadata);
            if (model instanceof KeyPropertyValueModel)
                keyPropertyValueEntities.add((KeyPropertyValueEntity) propertyValueEntity);
            else
                propertyValueEntities.add((PropertyValueEntity) propertyValueEntity);
        });
        assertValidates(validates);

        keyPropertyValueEntities.forEach(AbstractPropertyValueEntity::applyValue);
        propertyValueEntities.forEach(AbstractPropertyValueEntity::applyValue);

        keyPropertyValueRepository.batchUpdate(keyPropertyValueEntities);
        propertyValueRepository.batchUpdate(propertyValueEntities);
    }


    /**
     * 基于业务类型+业务标识获取所有属性值
     *
     * @param bizType    业务类型标识
     * @param bizId      业务标识
     * @param modelClazz 匹配的属性模型类型,例如：({@link PropertyValueModel},{@link KeyPropertyValueModel})
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("30")
    public List<? extends PropertyValue> findAllPropertiesValue(Integer bizType, Long bizId, Class<? extends PropertyValueModel> modelClazz) {
        Assert.notNull(bizType, "bizType must not be null.");
        Assert.notNull(bizId, "bizId must not be null.");
        Assert.notNull(modelClazz, "modelClazz must not be null.");
        return (List<? extends PropertyValue>) getMapper(modelClazz).mapForReads(getRepository(modelClazz).findAllByBizTypeAndBizId(bizType, bizId));
    }


    protected void validate(PropertyValueModel model, PropertyMetadata propertyMetadata) {
        Inputs.addValues(propertyMetadata.input(), model.value());
        PropertyValidators.assertInputValid(propertyMetadata);
    }

    protected void buildValidates(List<PropertyMetadata> validates, PropertyMetadata propertyMetadata, PropertyValueModel model) {
        Inputs.addValues(propertyMetadata.input(), model.getValue());
        validates.add(propertyMetadata);
    }

    protected void applyEntityBeforeSave(AbstractPropertyValueEntity entity, PropertyValueModel model, PropertyMetadata metaDetail) {
        entity.prepareValue(model.value(), metaDetail);
        entity.setName(metaDetail.name());
        entity.setPropertyMetaId(metaDetail.id());
    }

    protected void assertValidates(List<PropertyMetadata> validates) {
        PropertyValidators.assertInputsValid(validates.toArray(new PropertyMetadata[validates.size()]));
    }


    protected AbstractPropertyValueEntityMapper getMapper(PropertyValueModel model) {
        if (model instanceof KeyPropertyValueModel)
            return getModelMapper(KeyPropertyValueEntityMapper.class);
        return getModelMapper();
    }

    protected AbstractPropertyValueRepository getRepository(PropertyValueModel model) {
        if (model instanceof KeyPropertyValueModel)
            return keyPropertyValueRepository;
        return propertyValueRepository;
    }


    protected AbstractPropertyValueEntityMapper getMapper(Class<? extends PropertyValueModel> modelClazz) {
        if (ClassUtils.isAssignable(KeyPropertyValueModel.class, modelClazz))
            return getModelMapper(KeyPropertyValueEntityMapper.class);
        return getModelMapper();
    }

    protected AbstractPropertyValueRepository getRepository(Class<? extends PropertyValueModel> modelClazz) {
        if (ClassUtils.isAssignable(KeyPropertyValueModel.class, modelClazz))
            return keyPropertyValueRepository;
        return propertyValueRepository;
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
    public void setkeyPropertyValueRepository(KeyPropertyValueRepository keyPropertyValueRepository) {
        this.keyPropertyValueRepository = keyPropertyValueRepository;
    }
}
