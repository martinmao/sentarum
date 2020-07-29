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
import io.scleropages.sentarum.item.entity.SpuPropertyValueEntity;
import io.scleropages.sentarum.item.entity.mapper.SpuEntityMapper;
import io.scleropages.sentarum.item.ge.repo.MediaRepository;
import io.scleropages.sentarum.item.model.impl.SpuModel;
import io.scleropages.sentarum.item.property.Inputs;
import io.scleropages.sentarum.item.property.PropertyValidators;
import io.scleropages.sentarum.item.property.model.Constraint;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.repo.SpuPropertyValueRepository;
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
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("30")
public class SpuManager implements GenericManager<SpuModel, Long, SpuEntityMapper> {

    private SpuRepository spuRepository;
    private MediaRepository mediaRepository;
    private SpuPropertyValueRepository propertyValueRepository;
    private CategoryManager categoryManager;
    private PropertyManager propertyManager;


    @Validated({SpuModel.Create.class})
    @Transactional
    @BizError("10")
    public void createSpu(@Valid SpuModel model, Long stdCategoryId, Map<Long, Object> values) {
        Assert.notNull(stdCategoryId, "stdCategoryId must not be null.");
        Assert.notEmpty(values, "values must not be empty.");
        SpuEntity spuEntity = getModelMapper().mapForSave(model);
        categoryManager.awareStandardCategoryEntity(stdCategoryId, spuEntity);
        List<CategoryProperty> categoryProperties = categoryManager.getAllCategoryProperties(stdCategoryId, CategoryPropertyBizType.KEY_PROPERTY, CategoryPropertyBizType.SPU_PROPERTY);
        Map<Long, CategoryProperty> categoryPropertiesMap = Maps.newHashMap();
        PropertyMetadata[] validates = new PropertyMetadata[values.size()];
        for (int i = 0; i < categoryProperties.size(); i++) {
            CategoryProperty categoryProperty = categoryProperties.get(i);
            Long metaId = categoryProperty.propertyMetadata().id();
            Object value = values.get(metaId);
            if (categoryProperty.required()) {
                Assert.notNull(value, () -> "missing required property value for: " + metaId);
            }
            categoryPropertiesMap.put(metaId, categoryProperty);
            validates[i] = propertyManager.getPropertyMetadataFromCache(metaId);
            Inputs.addValues(validates[i].input(), value);
        }
        Constraint violate = PropertyValidators.validate(validates);
        if (null != violate) {
            PropertyMetadata violateMeta = PropertyValidators.resetAndGetViolatePropertyMetadata();
            throw new IllegalArgumentException(violateMeta.name() + "(" + violateMeta.tag() + ")" + violate.getMessage());
        }

        Map<Long, Object> keyProperties = Maps.newHashMap();
        Map<Long, Object> spuProperties = Maps.newHashMap();

        values.forEach((metaId, value) -> {
            CategoryProperty categoryProperty = categoryPropertiesMap.get(metaId);
            Assert.notNull(categoryProperty, "no category property metadata found for: " + metaId);
            if (categoryProperty.readOnly() || categoryProperty.visible())
                throw new IllegalArgumentException("category property is read only or invisible for: " + metaId);
            if (categoryProperty.categoryPropertyBizType().isKeyProperty()) {
                keyProperties.put(metaId, value);
            } else {
                spuProperties.put(metaId, value);
            }
        });

        keyProperties.forEach((metaId, value) -> {
            SpuPropertyValueEntity propertyValueEntity = new SpuPropertyValueEntity();
        });

        spuRepository.save(spuEntity);
    }

    @Autowired
    public void setSpuRepository(SpuRepository spuRepository) {
        this.spuRepository = spuRepository;
    }

    @Autowired
    public void setMediaRepository(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    @Autowired
    public void setPropertyValueRepository(SpuPropertyValueRepository propertyValueRepository) {
        this.propertyValueRepository = propertyValueRepository;
    }

    @Autowired
    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }

    @Autowired
    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }
}
