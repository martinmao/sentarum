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

import io.scleropages.sentarum.item.core.entity.SpuEntity;
import io.scleropages.sentarum.item.core.entity.mapper.SpuEntityMapper;
import io.scleropages.sentarum.item.core.model.Spu;
import io.scleropages.sentarum.item.core.model.impl.SpuModel;
import io.scleropages.sentarum.item.property.model.PropertyValue;
import io.scleropages.sentarum.item.property.model.impl.KeyPropertyValueModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyValueModel;
import io.scleropages.sentarum.item.repo.SpuRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.scleropages.crud.dao.orm.jpa.entity.EntityAware;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
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
@BizError("02")
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
        Map<Long, PropertyValueModel> propertiesValues = categoryManager.buildCategoryPropertyValues(stdCategoryId, values, KEY_PROPERTY, SPU_PROPERTY);
        spuRepository.save(spuEntity);
        if (CollectionUtils.isEmpty(propertiesValues))
            return;
        for (PropertyValueModel value : propertiesValues.values()) {
            value.setBizId(spuEntity.getId());
        }
        propertyValueManager.createPropertyValues(propertiesValues);
    }

    /**
     * 更新保存spu<br>
     * <p>
     * NOTE：关键属性与产品属性必须从参数中区分出来，由于在存储结构上，关键属性与产品属性源于不同的表，存在主键相同的可能。
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

        //apply property changes.
        List<PropertyValueModel> propertyValueModels = categoryManager.applyCategoryPropertyValuesChanges(spuEntity.getCategory().getId(), spuId
                , new CategoryManager.PropertyValueChange(KEY_PROPERTY, keyValues, KeyPropertyValueModel.class)
                , new CategoryManager.PropertyValueChange(SPU_PROPERTY, spuValues, PropertyValueModel.class));

        if (CollectionUtils.isEmpty(propertyValueModels))
            return;
        propertyValueManager.savePropertyValues(propertyValueModels);
    }


    /**
     * 获取spu
     *
     * @param spuId
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("20")
    public Spu getSpu(Long spuId) {
        Assert.notNull(spuId, "spuId must not be null.");
        return getModelMapper().mapForRead(spuRepository.get(spuId).orElseThrow(() -> new IllegalArgumentException("no spu found: " + spuId)));
    }

    /**
     * 获取spu详情
     *
     * @param spuId
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("20")
    public Spu getSpuDetail(Long spuId) {
        Assert.notNull(spuId, "spuId must not be null.");
        return getModelMapper().mapForRead(spuRepository.getById(spuId).orElseThrow(() -> new IllegalArgumentException("no spu found: " + spuId)));
    }

    /**
     * 查询spu 页
     *
     * @param spuSearchFilters      spu基本查询
     * @param propertySearchFilters 关键属性查询
     * @param pageable              分页
     * @param propertySort          关键属性排序
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("21")
    public Page<Spu> findSpuPage(Map<String, SearchFilter> spuSearchFilters, Map<String, SearchFilter> propertySearchFilters, Pageable pageable, Sort propertySort) {
        return spuRepository.findSpuPage(spuSearchFilters, propertyValueManager.buildPropertyValueSearchFilters(propertySearchFilters), pageable, propertySort).map(entity -> getModelMapper().mapForRead(entity));
    }

    @Transactional(readOnly = true)
    @BizError("30")
    public List<? extends PropertyValue> findAllKeyPropertyValues(Long spuId) {
        return propertyValueManager.findAllPropertiesValue(KEY_PROPERTY.getOrdinal(), spuId, KeyPropertyValueModel.class);
    }

    @Transactional(readOnly = true)
    @BizError("31")
    public List<? extends PropertyValue> findAllSpuPropertyValues(Long spuId) {
        return propertyValueManager.findAllPropertiesValue(SPU_PROPERTY.getOrdinal(), spuId, PropertyValueModel.class);
    }


    protected void awareSpuEntity(Long id, EntityAware entityAware) {
        entityAware.setEntity(spuRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no spu found: " + id)));
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
