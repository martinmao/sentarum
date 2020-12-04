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

import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.category.repo.StandardCategoryRepository;
import io.scleropages.sentarum.item.core.entity.AbstractSkuEntity;
import io.scleropages.sentarum.item.core.entity.CombineSkuEntity;
import io.scleropages.sentarum.item.core.entity.CombineSkuEntryEntity;
import io.scleropages.sentarum.item.core.entity.ItemEntity;
import io.scleropages.sentarum.item.core.entity.SkuEntity;
import io.scleropages.sentarum.item.core.entity.SpuEntity;
import io.scleropages.sentarum.item.core.entity.mapper.AbstractSkuEntityMapper;
import io.scleropages.sentarum.item.core.entity.mapper.CombineSkuEntityMapper;
import io.scleropages.sentarum.item.core.entity.mapper.CombineSkuEntryEntityMapper;
import io.scleropages.sentarum.item.core.entity.mapper.ItemEntityMapper;
import io.scleropages.sentarum.item.core.entity.mapper.SkuEntityMapper;
import io.scleropages.sentarum.item.core.model.CombineSku;
import io.scleropages.sentarum.item.core.model.CombineSkuEntry;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.Sku;
import io.scleropages.sentarum.item.core.model.impl.CombineSkuEntryModel;
import io.scleropages.sentarum.item.core.model.impl.CombineSkuModel;
import io.scleropages.sentarum.item.core.model.impl.ItemModel;
import io.scleropages.sentarum.item.core.model.impl.SkuModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyValueModel;
import io.scleropages.sentarum.item.repo.AbstractSkuRepository;
import io.scleropages.sentarum.item.repo.CombineSkuEntryRepository;
import io.scleropages.sentarum.item.repo.CombineSkuRepository;
import io.scleropages.sentarum.item.repo.ItemRepository;
import io.scleropages.sentarum.item.repo.SkuRepository;
import io.scleropages.sentarum.item.repo.SpuRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.dao.orm.SearchFilter;
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
import java.util.Optional;

import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.ITEM_PROPERTY;
import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.SALES_PROPERTY;

/**
 * Item管理器，提供Item相关通用原子管理功能
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("03")
public class ItemManager implements GenericManager<ItemModel, Long, ItemEntityMapper>, ItemApi {

    private ItemRepository itemRepository;
    private SpuRepository spuRepository;
    private SkuRepository skuRepository;
    private StandardCategoryRepository standardCategoryRepository;
    private CombineSkuRepository combineSkuRepository;
    private CombineSkuEntryRepository combineSkuEntryRepository;
    private CategoryManager categoryManager;
    private PropertyValueManager propertyValueManager;

    /**
     * 创建item
     *
     * @param model  item模型
     * @param spuId  所属spu
     * @param values 商品属性值集合(key为元数据id，value为属性值)
     */
    @Override
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
        applyBizIdToPropertyValues(propertiesValues, itemEntity.getId());
    }

    /**
     * 更新保存item
     *
     * @param model  item模型
     * @param values 商品属性值集合(key为属性值id，value为属性值)
     */
    @Override
    @Validated({ItemModel.Update.class})
    @Transactional
    @BizError("11")
    public void saveItem(@Valid ItemModel model, Map<Long, Object> values) {
        Long itemId = model.id();
        ItemEntity itemEntity = itemRepository.getById(itemId).orElseThrow(() -> new IllegalArgumentException("no item found: " + itemId));
        getModelMapper().mapForUpdate(model, itemEntity);
        itemRepository.save(itemEntity);
        saveValues(itemEntity.getCategory().getId(), itemId, new CategoryManager.PropertyValueChange(ITEM_PROPERTY, values, PropertyValueModel.class));
    }

    /**
     * 创建sku
     *
     * @param model  sku模型
     * @param values 销售属性值集合(key为元数据id，value为属性值)
     */
    @Override
    @Validated({SkuModel.Create.class})
    @Transactional
    @BizError("12")
    public void createSku(@Valid SkuModel model, Long itemId, Map<Long, Object> values) {
        createSkuInternal(model, itemId, values);
    }


    /**
     * 更新保存Sku
     *
     * @param model  sku模型
     * @param values 销售属性值集合(key为属性值id，value为属性值)
     */
    @Override
    @Validated({SkuModel.Update.class})
    @Transactional
    @BizError("13")
    public void saveSku(@Valid SkuModel model, Map<Long, Object> values) {
        saveSkuInternal(model, values);
    }


    /**
     * 创建 combine sku
     *
     * @param model  combine sku模型
     * @param values 销售属性值集合(key为元数据id，value为属性值)
     */
    @Override
    @Validated({CombineSkuModel.Create.class})
    @Transactional
    @BizError("14")
    public void createCombineSku(@Valid CombineSkuModel model, Long itemId, Map<Long, Object> values) {
        createSkuInternal(model, itemId, values);
    }


    /**
     * 更新保存 combine sku
     *
     * @param model  combine sku模型
     * @param values 销售属性值集合(key为属性值id，value为属性值)
     */
    @Override
    @Validated({CombineSkuModel.Update.class})
    @Transactional
    @BizError("15")
    public void saveCombineSku(@Valid CombineSkuModel model, Map<Long, Object> values) {
        saveSkuInternal(model, values);
    }

    /**
     * 创建组合sku条目(将目标sku加入到组合sku中)
     *
     * @param model        组合sku条目模型
     * @param combineSkuId 组合sku id
     * @param skuId        目标sku id
     */
    @Override
    @Validated({CombineSkuEntryModel.Create.class})
    @Transactional
    @BizError("16")
    public void createCombineSkuEntry(@Valid CombineSkuEntryModel model, Long combineSkuId, Long skuId) {
        Assert.notNull(combineSkuId, "combineSkuId must not be null.");
        Assert.notNull(skuId, "skuId must not be null.");
        CombineSkuEntryEntity combineSkuEntryEntity = getModelMapper(CombineSkuEntryEntityMapper.class).mapForSave(model);
        CombineSkuEntity combineSkuEntity = combineSkuRepository.get(combineSkuId).orElseThrow(() -> new IllegalArgumentException("no combine sku found: " + combineSkuId));
        SkuEntity skuEntity = skuRepository.get(skuId).orElseThrow(() -> new IllegalArgumentException("no sku found: " + skuId));
        combineSkuEntryEntity.setCombineSku(combineSkuEntity);
        combineSkuEntryEntity.setSku(skuEntity);
        combineSkuEntryRepository.save(combineSkuEntryEntity);
    }

    /**
     * 更新保存组合sku条目
     *
     * @param model 组合sku条目模型
     */
    @Override
    @Validated({CombineSkuEntryModel.Update.class})
    @Transactional
    @BizError("17")
    public void saveCombineSkuEntry(@Valid CombineSkuEntryModel model) {
        CombineSkuEntryEntity entity = combineSkuEntryRepository.get(model.id()).orElseThrow(() -> new IllegalArgumentException("no combine sku entry found: " + model.id()));
        getModelMapper(CombineSkuEntryEntityMapper.class).mapForUpdate(model, entity);
        combineSkuEntryRepository.save(entity);
    }

    /**
     * 删除组合sku条目
     *
     * @param id
     */
    @Override
    @Transactional
    @BizError("18")
    public void deleteCombineSkuEntry(Long id) {
        Assert.notNull(id, "id must be null.");
        combineSkuEntryRepository.deleteById(id);
    }


    /**
     * 查询item 页
     *
     * @param itemSearchFilters     item信息查询
     * @param propertySearchFilters 关键属性查询
     * @param pageable              分页
     * @param propertySort          关键属性排序
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @BizError("50")
    public Page<Item> findItemPage(Map<String, SearchFilter> itemSearchFilters, Map<String, SearchFilter> propertySearchFilters, Pageable pageable, Sort propertySort) {
        return itemRepository.findItemPage(itemSearchFilters, propertyValueManager.buildPropertyValueSearchFilters(propertySearchFilters), pageable, propertySort).map(entity -> getModelMapper().mapForRead(entity));
    }


    /**
     * 查询 sku 页
     *
     * @param searchFilters
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @BizError("51")
    public Page<Sku> findSkuPage(Map<String, Object> searchFilters, Pageable pageable) {
        return skuRepository.findPage(SearchFilter.SearchFilterBuilder.build(searchFilters), pageable).map(skuEntity -> getModelMapper(SkuEntityMapper.class).mapForRead(skuEntity));
    }

    /**
     * 查询 combine sku 页
     *
     * @param searchFilters
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @BizError("52")
    public Page<CombineSku> findCombineSkuPage(Map<String, Object> searchFilters, Pageable pageable) {
        return combineSkuRepository.findPage(SearchFilter.SearchFilterBuilder.build(searchFilters), pageable).map(skuEntity -> getModelMapper(CombineSkuEntityMapper.class).mapForRead(skuEntity));
    }

    /**
     * 查询 combine sku entry 页
     *
     * @param searchFilters
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @BizError("53")
    public Page<CombineSkuEntry> findCombineSkuEntryPage(Map<String, Object> searchFilters, Pageable pageable) {
        return combineSkuEntryRepository.findPage(SearchFilter.SearchFilterBuilder.build(searchFilters), pageable).map(entity -> getModelMapper(CombineSkuEntryEntityMapper.class).mapForRead(entity));
    }


    /**
     * 获取商品
     *
     * @param itemId
     * @param fetchSpu
     * @param fetchCategory
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @BizError("54")
    public Item getItem(Long itemId, boolean fetchSpu, boolean fetchCategory) {
        Assert.notNull(itemId, "item id is required.");
        ItemEntity itemEntity = itemRepository.readByRecord(itemRepository.readById(itemId), fetchSpu ? spuRepository : null, fetchCategory ? standardCategoryRepository : null).orElseThrow(() -> new IllegalArgumentException("no item found: " + itemId));
        return getModelMapper().mapForRead(itemEntity);
    }

    /**
     * 获取sku
     *
     * @param skuId
     * @param fetchItem
     * @param fetchSpu
     * @param fetchCategory
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @BizError("55")
    public Sku getSku(Long skuId, boolean fetchItem, boolean fetchSpu, boolean fetchCategory) {
        Assert.notNull(skuId, "sku id is required.");
        SkuEntity skuEntity = skuRepository.readByRecord(skuRepository.readById(skuId), fetchItem ? itemRepository : null, fetchSpu ? spuRepository : null, fetchCategory ? standardCategoryRepository : null).orElseThrow(() -> new IllegalArgumentException("no sku found: " + skuId));
        return getModelMapper(SkuEntityMapper.class).mapForRead(skuEntity);
    }


    protected void createSkuInternal(SkuModel model, Long itemId, Map<Long, Object> values) {
        Assert.notNull(itemId, "itemId is required.");
        AbstractSkuEntity skuEntity = (AbstractSkuEntity) getSkuMapper(model).mapForSave(model);
        ItemEntity itemEntity = itemRepository.getById(itemId).orElseThrow(() -> new IllegalArgumentException("no item found: " + itemId));
        StandardCategoryEntity categoryEntity = itemEntity.getCategory();
        skuEntity.setItem(itemEntity);
        skuEntity.setCategory(categoryEntity);
        Map<Long, PropertyValueModel> propertiesValues = categoryManager.buildCategoryPropertyValues(categoryEntity.getId(), values, SALES_PROPERTY);
        getSkuRepository(model).save(skuEntity);
        applyBizIdToPropertyValues(propertiesValues, skuEntity.getId());
    }

    public void saveSkuInternal(SkuModel model, Map<Long, Object> values) {
        Long skuId = model.id();
        Optional optional = getSkuRepository(model).getById(skuId);
        Assert.isTrue(optional.isPresent(), "no sku found: " + skuId);
        AbstractSkuEntity skuEntity = (AbstractSkuEntity) optional.get();
        getSkuMapper(model).mapForUpdate(model, skuEntity);
        getSkuRepository(model).save(skuEntity);
        saveValues(skuEntity.getCategory().getId(), skuId, new CategoryManager.PropertyValueChange(SALES_PROPERTY, values, PropertyValueModel.class));
    }


    protected AbstractSkuEntityMapper getSkuMapper(SkuModel model) {
        if (model instanceof CombineSkuModel) {
            return getModelMapper(CombineSkuEntityMapper.class);
        }
        return getModelMapper(SkuEntityMapper.class);
    }

    protected AbstractSkuRepository getSkuRepository(SkuModel model) {
        if (model instanceof CombineSkuModel) {
            return combineSkuRepository;
        }
        return skuRepository;
    }


    /**
     * apply biz id to property value models.
     *
     * @param propertiesValues
     * @param bizId
     */
    protected void applyBizIdToPropertyValues(Map<Long, PropertyValueModel> propertiesValues, Long bizId) {
        if (!CollectionUtils.isEmpty(propertiesValues)) {
            for (PropertyValueModel value : propertiesValues.values()) {
                value.setBizId(bizId);
            }
            propertyValueManager.createPropertyValues(propertiesValues);
        }
    }


    protected void saveValues(Long categoryId, Long bizId, CategoryManager.PropertyValueChange valueChange) {
        List<PropertyValueModel> propertyValueModels = categoryManager.applyCategoryPropertyValuesChanges(categoryId, bizId
                , valueChange);
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
    public void setSkuRepository(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    @Autowired
    public void setCombineSkuRepository(CombineSkuRepository combineSkuRepository) {
        this.combineSkuRepository = combineSkuRepository;
    }

    @Autowired
    public void setCombineSkuEntryRepository(CombineSkuEntryRepository combineSkuEntryRepository) {
        this.combineSkuEntryRepository = combineSkuEntryRepository;
    }

    @Autowired
    public void setStandardCategoryRepository(StandardCategoryRepository standardCategoryRepository) {
        this.standardCategoryRepository = standardCategoryRepository;
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
