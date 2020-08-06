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
import io.scleropages.sentarum.item.category.repo.CategoryEntityGraph;
import io.scleropages.sentarum.item.category.repo.CategoryPropertyRepository;
import io.scleropages.sentarum.item.category.repo.MarketingCategoryRepository;
import io.scleropages.sentarum.item.category.repo.StandardCategoryLinkRepository;
import io.scleropages.sentarum.item.category.repo.StandardCategoryRepository;
import io.scleropages.sentarum.item.mgmt.impl.CategoryNavigatorImpl;
import io.scleropages.sentarum.item.property.Inputs;
import io.scleropages.sentarum.item.property.PropertyValidators;
import io.scleropages.sentarum.item.property.entity.PropertyMetaEntity;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import org.apache.commons.collections.CollectionUtils;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.scleropages.crud.dao.orm.jpa.entity.EntityAware;
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


    /**
     * 创建一个标准类目
     *
     * @param model               类目模型
     * @param parentStdCategoryId 挂靠标准类目id
     */
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

    /**
     * 保存更新标准类目
     *
     * @param model
     */
    @Validated({StandardCategoryModel.Update.class})
    @Transactional
    @BizError("02")
    public void saveStandardCategory(@Valid StandardCategoryModel model) {
        StandardCategoryEntity toSave = standardCategoryRepository.get(model.id()).orElseThrow(() -> new IllegalArgumentException("no category found: " + model.id()));
        getModelMapper().mapForUpdate(model, toSave);
    }

    /**
     * 将指定标准类目挂靠到目标标准类目下
     *
     * @param id
     * @param targetStdCategoryId
     */
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


    /**
     * 创建类目属性
     *
     * @param model          类目属性模型
     * @param stdCategoryId  所属类目id
     * @param propertyMetaId 属性元数据id
     */
    @Validated({CategoryPropertyModel.Create.class})
    @Transactional
    @BizError("04")
    public void createCategoryProperty(@Valid CategoryPropertyModel model, Long stdCategoryId, Long propertyMetaId) {
        Assert.notNull(stdCategoryId, "stdCategoryId must not be null.");
        Assert.notNull(propertyMetaId, "propertyMetaId must not be null.");

        CategoryPropertyEntity toSave = getModelMapper(CategoryPropertyEntityMapper.class).mapForSave(model);
        propertyManager.awarePropertyMetaEntity(propertyMetaId, toSave);

        StandardCategoryEntity standardCategoryEntity = assertPropertyMetaUniqueForCategoryHierarchy(stdCategoryId, propertyMetaId);

        CategoryProperty.DefaultValues defaultValues = model.getDefaultValues();
        if (null != defaultValues) {
            if (model.required()) {
                throw new IllegalStateException("required property must not has default value.");
            }
            Assert.notEmpty(defaultValues.getValues(), "values must not be null (or empty).");
            PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadataDetail(propertyMetaId);
            Inputs.addValues(propertyMetadata.input(), defaultValues.getValues());
            PropertyValidators.assertInputValid(propertyMetadata);
        }
        toSave.setCategory(standardCategoryEntity);
        categoryPropertyRepository.save(toSave);
    }


    /**
     * 删除类目属性
     *
     * @param id 类目属性id
     */
    @Transactional
    @BizError("05")
    public void deleteCategoryProperty(Long id) {
        Assert.notNull(id, "delete category property must not be null.");
        categoryPropertyRepository.deleteById(id);
    }


    /**
     * 创建一个营销类目
     *
     * @param model               营销类目模型
     * @param parentMktCategoryId 挂靠营销类目id
     */
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

    /**
     * 更新保存营销类目
     *
     * @param model
     */
    @Validated({MarketingCategoryModel.Update.class})
    @Transactional
    @BizError("21")
    public void saveMarketingCategory(@Valid MarketingCategoryModel model) {
        MarketingCategoryEntity toSave = marketingCategoryRepository.get(model.id()).orElseThrow(() -> new IllegalArgumentException("no category found: " + model.id()));
        getModelMapper(MarketingCategoryEntityMapper.class).mapForUpdate(model, toSave);
    }

    /**
     * 将制定营销类目挂靠到目标营销类目下
     *
     * @param id                  营销类目id
     * @param targetMktCategoryId 目标营销类目id
     */
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

    /**
     * 创建标准类目链接
     *
     * @param model               类目链接模型
     * @param marketingCategoryId 营销类目id
     * @param standardCategoryId  标准类目id
     */
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

    /**
     * 更新保存标准类目链接
     *
     * @param model
     */
    @Validated({StandardCategoryLinkModel.Update.class})
    @Transactional
    @BizError("24")
    public void saveStandardCategoryLink(@Valid StandardCategoryLinkModel model) {
        StandardCategoryLinkEntity toSave = standardCategoryLinkRepository.get(model.getId()).orElseThrow(() -> new IllegalArgumentException("no standard category link found: " + model.id()));
        getModelMapper(StandardCategoryLinkEntityMapper.class).mapForUpdate(model, toSave);
    }

    /**
     * 删除标准类目链接
     *
     * @param stdCategoryLinkId 类目链接id
     */
    @Transactional
    @BizError("25")
    public void deleteStandardCategoryLink(Long stdCategoryLinkId) {
        standardCategoryLinkRepository.deleteById(stdCategoryLinkId);
    }


    /**
     * 查询标准类目
     *
     * @param searchFilters 查询条件
     * @param pageable      分页
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("50")
    public Page<StandardCategory> findStandardCategoryPage(Map<String, SearchFilter> searchFilters, Pageable pageable) {
        return standardCategoryRepository.findPage(searchFilters, pageable).map(entity -> getModelMapper().mapForRead(entity));
    }

    /**
     * 基于状态查询所有标准类目
     *
     * @param status 状态标识
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("55")
    public List<StandardCategory> findAllStandardCategory(StandardCategory.Status status) {
        return standardCategoryRepository.findAllByStatusEquals(status.getOrdinal()).stream().map(entity -> getModelMapper().mapForRead(entity)).collect(Collectors.toList());
    }

    /**
     * 获取标准类目
     *
     * @param id 类目id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("60")
    public StandardCategory getStandardCategory(Long id) {
        return getModelMapper().mapForRead(standardCategoryRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no category found: " + id)));
    }

    /**
     * 获取标准类目，包含其上级类目
     *
     * @param id 类目id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("61")
    public StandardCategory getStandardCategoryWithParent(Long id) {
        return getModelMapper().mapForRead(standardCategoryRepository.getById(id));
    }

    /**
     * 获取标准类目，包含其所有类目属性
     *
     * @param id      类目id
     * @param bizType 属性业务标识
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("62")
    public StandardCategory getStandardCategoryWithProperties(Long id, CategoryProperty.CategoryPropertyBizType... bizType) {
        StandardCategoryEntity entity = standardCategoryRepository.getById(id);
        Assert.notNull(entity, "no category found: " + id);
        StandardCategoryModel standardCategoryModel = getModelMapper().mapForRead(entity);
        List<CategoryProperty> categoryProperties = getModelMapper().categoryPropertyEntityListToCategoryPropertyList(categoryPropertyRepository.findAllByCategory_IdAndCategoryPropertyBizTypeIn(id, CategoryProperty.CategoryPropertyBizType.toOrdinals(bizType)));
        standardCategoryModel.setCategoryProperties(categoryProperties);
        return standardCategoryModel;
//        return getModelMapper().mapForRead(standardCategoryRepository.getByIdWithCategoryProperties(id, CategoryProperty.CategoryPropertyBizType.toOrdinals(bizType)).orElseThrow(() -> new IllegalArgumentException("no category found: " + id)));
    }

    /**
     * 获取标准类目所有类目属性（包含其上级类目属性）
     *
     * @param stdCategoryId 类目id
     * @param bizType       属性业务标识
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("63")
    public List<CategoryProperty> getAllCategoryProperties(Long stdCategoryId, CategoryProperty.CategoryPropertyBizType... bizType) {
        List<CategoryProperty> properties = Lists.newArrayList();
        addCategoryPropertyFromParent(properties, stdCategoryId, bizType);
        return properties;
    }

    /**
     * 查询营销类目
     *
     * @param searchFilters 查询条件
     * @param pageable      分页
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("65")
    public Page<MarketingCategory> findMarketingCategoryPage(Map<String, SearchFilter> searchFilters, Pageable pageable) {
        return marketingCategoryRepository.findPage(searchFilters, pageable).map(entity -> getModelMapper(MarketingCategoryEntityMapper.class).mapForRead(entity));
    }

    /**
     * 基于状态查询所有营销类目
     *
     * @param status
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("70")
    public List<MarketingCategory> findAllMarketingCategory(MarketingCategory.Status status) {
        return marketingCategoryRepository.findAllByStatusEquals(status.getOrdinal()).stream().map(entity -> getModelMapper(MarketingCategoryEntityMapper.class).mapForRead(entity)).collect(Collectors.toList());
    }

    /**
     * 获取营销类目
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("75")
    public MarketingCategory getMarketingCategory(Long id) {
        return getModelMapper(MarketingCategoryEntityMapper.class).mapForRead(marketingCategoryRepository.getByIdWithStandardCategoryLinks(id).orElseThrow(() -> new IllegalArgumentException("no category found: " + id)));
    }

    /**
     * 获取营销类目导航图
     *
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("80")
    public CategoryNavigator getStandardCategoryNavigator() {
        CategoryEntityGraph<StandardCategoryEntity> graph = standardCategoryRepository.getFullStandardCategoryGraph();
        return new CategoryNavigatorImpl(graph);
    }


    /**
     * 断言 {@link PropertyMetadata} 在整个 {@link io.scleropages.sentarum.item.category.model.Category} 层次中是唯一的
     *
     * @param categoryId
     * @param propertyMetaId
     * @return categoryId匹配的entity（仅root call 有效）
     */
    protected StandardCategoryEntity assertPropertyMetaUniqueForCategoryHierarchy(Long categoryId, Long propertyMetaId) {
        StandardCategoryEntity standardCategoryEntity = standardCategoryRepository.getById(categoryId);
        Assert.notNull(standardCategoryEntity, "no category found: " + categoryId);
//                .getByIdWithParentAndCategoryProperties(categoryId, null)
//                .orElseThrow(() -> new IllegalArgumentException("no category found: " + categoryId));

//        List<CategoryPropertyEntity> categoryProperties = standardCategoryEntity.getCategoryProperties();
        List<CategoryPropertyEntity> categoryProperties = categoryPropertyRepository.findAllByCategory_IdAndCategoryPropertyBizTypeIn(categoryId, null);
        if (CollectionUtils.isNotEmpty(categoryProperties))
            for (CategoryPropertyEntity categoryProperty : categoryProperties) {
                PropertyMetaEntity propertyMetadata = categoryProperty.getPropertyMetadata();
                if (Objects.equals(propertyMetadata.getId(), propertyMetaId))
                    throw new IllegalStateException("category [" + standardCategoryEntity.getName() + "(" + standardCategoryEntity.getTag() + ")] already bind given property meta: " + propertyMetadata.getName() + "(" + propertyMetadata.getTag() + ")");
            }
        if (null != standardCategoryEntity.getParent())
            assertPropertyMetaUniqueForCategoryHierarchy(standardCategoryEntity.getParent().getId(), propertyMetaId);
        return standardCategoryEntity;

    }

    /**
     * 递归获取上级类目属性，并将其加入 参数 properties 中
     *
     * @param properties    属性容器
     * @param stdCategoryId 标准类目id
     * @param bizType       属性业务标识
     */
    protected void addCategoryPropertyFromParent(List<CategoryProperty> properties, Long stdCategoryId, CategoryProperty.CategoryPropertyBizType... bizType) {
        StandardCategoryEntity standardCategoryEntity = standardCategoryRepository.getById(stdCategoryId);
        Assert.notNull(standardCategoryEntity, "no category found: " + stdCategoryId);
//                .getByIdWithParentAndCategoryProperties(stdCategoryId, CategoryProperty.CategoryPropertyBizType.toOrdinals(bizType))
//                .orElseThrow(() -> new IllegalArgumentException("no category found: " + stdCategoryId));

        List<CategoryProperty> categoryProperties = getModelMapper().categoryPropertyEntityListToCategoryPropertyList(categoryPropertyRepository.findAllByCategory_IdAndCategoryPropertyBizTypeIn(stdCategoryId, CategoryProperty.CategoryPropertyBizType.toOrdinals(bizType)));
        properties.addAll(categoryProperties);
//        for (CategoryPropertyModel categoryPropertyModel : getModelMapper(CategoryPropertyEntityMapper.class).mapForReads(standardCategoryEntity.getCategoryProperties())) {
//            properties.add(categoryPropertyModel);
//        }
        if (null != standardCategoryEntity.getParent())
            addCategoryPropertyFromParent(properties, standardCategoryEntity.getParent().getId(), bizType);
    }


    protected void awareStandardCategoryEntity(Long id, EntityAware entityAware) {
        entityAware.setEntity(standardCategoryRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no std category found: " + id)));
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
