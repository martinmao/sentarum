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

import io.scleropages.sentarum.item.category.entity.CategoryPropertyEntity;
import io.scleropages.sentarum.item.category.entity.MarketingCategoryEntity;
import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.category.entity.StandardCategoryLinkEntity;
import io.scleropages.sentarum.item.category.entity.mapper.CategoryPropertyEntityMapper;
import io.scleropages.sentarum.item.category.entity.mapper.MarketingCategoryEntityMapper;
import io.scleropages.sentarum.item.category.entity.mapper.StandardCategoryEntityMapper;
import io.scleropages.sentarum.item.category.entity.mapper.StandardCategoryLinkEntityMapper;
import io.scleropages.sentarum.item.category.model.CategoryProperty;
import io.scleropages.sentarum.item.category.model.MarketingCategory;
import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.category.model.StandardCategoryLink;
import io.scleropages.sentarum.item.category.model.impl.CategoryPropertyModel;
import io.scleropages.sentarum.item.category.model.impl.MarketingCategoryModel;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryLinkModel;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryModel;
import io.scleropages.sentarum.item.category.repo.CategoryPropertyRepository;
import io.scleropages.sentarum.item.category.repo.MarketingCategoryRepository;
import io.scleropages.sentarum.item.category.repo.StandardCategoryLinkRepository;
import io.scleropages.sentarum.item.category.repo.StandardCategoryRepository;
import io.scleropages.sentarum.item.property.Inputs;
import io.scleropages.sentarum.item.property.PropertyValidators;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.scleropages.crud.exception.BizError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 类目通用（原子）功能管理器.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("20")
public class CategoryManager implements GenericManager<StandardCategoryModel, Long, StandardCategoryEntityMapper> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private CategoryPropertyRepository categoryPropertyRepository;
    private StandardCategoryRepository standardCategoryRepository;
    private MarketingCategoryRepository marketingCategoryRepository;
    private StandardCategoryLinkRepository standardCategoryLinkRepository;

    private PropertyManager propertyManager;


    @Validated({StandardCategoryModel.Create.class})
    @Transactional
    @BizError("01")
    public void createStandardCategory(@Valid StandardCategoryModel model, Long parentStdCategoryId) {
        StandardCategoryEntity standardCategoryEntity = getModelMapper().mapForSave(model);
        if (null != parentStdCategoryId) {
            StandardCategoryEntity parentEntity = standardCategoryRepository.get(parentStdCategoryId).orElseThrow(() -> new IllegalArgumentException("no parent category found: " + parentStdCategoryId));
            standardCategoryEntity.setParent(parentEntity);
        }
        standardCategoryRepository.save(standardCategoryEntity);
    }

    @Validated({StandardCategoryModel.Update.class})
    @Transactional
    @BizError("02")
    public void saveStandardCategory(@Valid StandardCategoryModel model) {
        StandardCategoryEntity toSave = standardCategoryRepository.get(model.id()).orElseThrow(() -> new IllegalArgumentException("no category found: " + model.id()));
        getModelMapper().mapForUpdate(model, toSave);
    }

    @Transactional
    @BizError("03")
    public void bindStandardCategoryToTargetParent(Long id, Long targetStdCategoryId) {
        Assert.notNull(id, "id must not be null.");
        Assert.notNull(targetStdCategoryId, "target parent id must not be null.");
        StandardCategoryEntity toSave = standardCategoryRepository.getById(id);
        Assert.notNull(toSave, "no category found: " + id);
        StandardCategoryEntity associated = toSave.getParent();
        Assert.state(Objects.equals(id, targetStdCategoryId), "not allowed bind category to self.");
        if (null == associated || (null != associated && !Objects.equals(associated.getId(), targetStdCategoryId))) {
            StandardCategoryEntity targetParent = standardCategoryRepository.get(targetStdCategoryId).orElseThrow(() -> new IllegalArgumentException("no parent category found: " + targetStdCategoryId));
            toSave.setParent(targetParent);
        }
    }


    @Validated({CategoryPropertyModel.Create.class})
    @Transactional
    @BizError("04")
    public void createCategoryProperty(@Valid CategoryPropertyModel model, Long stdCategoryId, Long propertyMetaId) {
        Assert.notNull(stdCategoryId, "stdCategoryId must not be null.");
        Assert.notNull(propertyMetaId, "propertyMetaId must not be null.");

        CategoryPropertyEntity toSave = getModelMapper(CategoryPropertyEntityMapper.class).mapForSave(model);
        propertyManager.awarePropertyMetaEntity(propertyMetaId, toSave);
        StandardCategoryEntity standardCategoryEntity = standardCategoryRepository.get(stdCategoryId).orElseThrow(() -> new IllegalArgumentException("no category found: " + stdCategoryId));

        CategoryProperty.DefaultValues defaultValues = model.getDefaultValues();
        if (null != defaultValues) {
            PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadata(propertyMetaId);
            Inputs.addValues(propertyMetadata.input(), defaultValues.getValues());
            PropertyValidators.assertInputValid(propertyMetadata);
        }
        toSave.setCategory(standardCategoryEntity);
        categoryPropertyRepository.save(toSave);
    }

    @Transactional
    @BizError("05")
    public void deleteCategoryProperty(Long id) {
        Assert.notNull(id, "delete category property must not be null.");
        categoryPropertyRepository.deleteById(id);
    }


    @Validated({MarketingCategoryModel.Create.class})
    @Transactional
    @BizError("20")
    public void createMarketingCategory(@Valid MarketingCategoryModel model, Long parentMktCategoryId) {
        MarketingCategoryEntity marketingCategoryEntity = getModelMapper(MarketingCategoryEntityMapper.class).mapForSave(model);
        if (null != parentMktCategoryId) {
            MarketingCategoryEntity parentEntity = marketingCategoryRepository.get(parentMktCategoryId).orElseThrow(() -> new IllegalArgumentException("no parent category found: " + parentMktCategoryId));
            marketingCategoryEntity.setParent(parentEntity);
        }
        marketingCategoryRepository.save(marketingCategoryEntity);
    }

    @Validated({MarketingCategoryModel.Update.class})
    @Transactional
    @BizError("21")
    public void saveMarketingCategory(@Valid MarketingCategoryModel model) {
        MarketingCategoryEntity toSave = marketingCategoryRepository.get(model.id()).orElseThrow(() -> new IllegalArgumentException("no category found: " + model.id()));
        getModelMapper(MarketingCategoryEntityMapper.class).mapForUpdate(model, toSave);
    }

    @Transactional
    @BizError("22")
    public void bindMarketingCategoryToTargetParent(Long id, Long targetMktCategoryId) {
        Assert.notNull(id, "id must not be null.");
        Assert.notNull(targetMktCategoryId, "target parent id must not be null.");
        MarketingCategoryEntity toSave = marketingCategoryRepository.getById(id);
        Assert.notNull(toSave, "no category found: " + id);
        MarketingCategoryEntity associated = toSave.getParent();
        Assert.state(Objects.equals(id, targetMktCategoryId), "not allowed bind category to self.");

        if (null == associated || (null != associated && !Objects.equals(associated.getId(), targetMktCategoryId))) {
            MarketingCategoryEntity targetParent = marketingCategoryRepository.get(targetMktCategoryId).orElseThrow(() -> new IllegalArgumentException("no parent category found: " + targetMktCategoryId));
            toSave.setParent(targetParent);
        }
    }

    @Validated({StandardCategoryLinkModel.Create.class})
    @Transactional
    @BizError("23")
    public void createStandardCategoryLink(@Valid StandardCategoryLinkModel model, Long marketingCategoryId, Long standardCategoryId) {
        Assert.notNull(marketingCategoryId, "marketingCategoryId must not be null.");
        StandardCategoryLinkEntity toSave = getModelMapper(StandardCategoryLinkEntityMapper.class).mapForSave(model);
        MarketingCategoryEntity marketingCategoryEntity = marketingCategoryRepository.get(marketingCategoryId).orElseThrow(() -> new IllegalArgumentException("no category found: " + marketingCategoryId));
        toSave.setMarketingCategory(marketingCategoryEntity);
        if (model.getLinkType() == StandardCategoryLink.LinkType.DIRECT) {
            Assert.isNull(model.getSearchFilter(), "search filter not support for direct link.");
            Assert.notNull(standardCategoryId, "standardCategoryId must not be null.");
            StandardCategoryEntity standardCategoryEntity = standardCategoryRepository.get(standardCategoryId).orElseThrow(() -> new IllegalArgumentException("no category found: " + standardCategoryId));
            toSave.setStandardCategory(standardCategoryEntity);
        }
        if (model.getLinkType() == StandardCategoryLink.LinkType.SOFT) {
            Assert.isTrue(null != model.getSearchFilter(), "search filter is required for soft link.");
        }
        standardCategoryLinkRepository.save(toSave);
    }

    @Validated({StandardCategoryLinkModel.Update.class})
    @Transactional
    @BizError("24")
    public void saveStandardCategoryLink(@Valid StandardCategoryLinkModel model) {
        StandardCategoryLinkEntity toSave = standardCategoryLinkRepository.get(model.getId()).orElseThrow(() -> new IllegalArgumentException("no standard category link found: " + model.id()));
        getModelMapper(StandardCategoryLinkEntityMapper.class).mapForUpdate(model, toSave);
    }

    @Transactional
    @BizError("25")
    public void deleteStandardCategoryLink(Long stdCategoryId) {
        standardCategoryLinkRepository.deleteById(stdCategoryId);
    }


    @Transactional(readOnly = true)
    @BizError("50")
    public Page<StandardCategory> findStandardCategoryPage(Map<String, SearchFilter> searchFilters, Pageable pageable) {
        return standardCategoryRepository.findPage(searchFilters, pageable).map(entity -> getModelMapper().mapForRead(entity));
    }

    @Transactional(readOnly = true)
    @BizError("55")
    public List<StandardCategory> findAllStandardCategory(Integer status) {
        return standardCategoryRepository.findAllByStatusEquals(status).stream().map(entity -> getModelMapper().mapForRead(entity)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @BizError("60")
    public StandardCategory getStandardCategory(Long id) {
        return getModelMapper().mapForRead(standardCategoryRepository.getByIdWithCategoryProperties(id).orElseThrow(() -> new IllegalArgumentException("no category found: " + id)));
    }


    @Transactional(readOnly = true)
    @BizError("65")
    public Page<MarketingCategory> findMarketingCategoryPage(Map<String, SearchFilter> searchFilters, Pageable pageable) {
        return marketingCategoryRepository.findPage(searchFilters, pageable).map(entity -> getModelMapper(MarketingCategoryEntityMapper.class).mapForRead(entity));
    }

    @Transactional(readOnly = true)
    @BizError("70")
    public List<MarketingCategory> findAllMarketingCategory(Integer status) {
        return marketingCategoryRepository.findAllByStatusEquals(status).stream().map(entity -> getModelMapper(MarketingCategoryEntityMapper.class).mapForRead(entity)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @BizError("75")
    public MarketingCategory getMarketingCategory(Long id) {
        return getModelMapper(MarketingCategoryEntityMapper.class).mapForRead(marketingCategoryRepository.getByIdWithStandardCategoryLinks(id).orElseThrow(() -> new IllegalArgumentException("no category found: " + id)));
    }


    @Autowired
    public void setCategoryPropertyRepository(CategoryPropertyRepository categoryPropertyRepository) {
        this.categoryPropertyRepository = categoryPropertyRepository;
    }

    @Autowired
    public void setStandardCategoryRepository(StandardCategoryRepository standardCategoryRepository) {
        this.standardCategoryRepository = standardCategoryRepository;
    }

    @Autowired
    public void setMarketingCategoryRepository(MarketingCategoryRepository marketingCategoryRepository) {
        this.marketingCategoryRepository = marketingCategoryRepository;
    }

    @Autowired
    public void setStandardCategoryLinkRepository(StandardCategoryLinkRepository standardCategoryLinkRepository) {
        this.standardCategoryLinkRepository = standardCategoryLinkRepository;
    }

    @Autowired
    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }
}
