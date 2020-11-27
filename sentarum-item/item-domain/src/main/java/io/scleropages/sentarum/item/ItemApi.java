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
package io.scleropages.sentarum.item;

import io.scleropages.sentarum.item.core.model.CombineSku;
import io.scleropages.sentarum.item.core.model.CombineSkuEntry;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.Sku;
import io.scleropages.sentarum.item.core.model.impl.CombineSkuEntryModel;
import io.scleropages.sentarum.item.core.model.impl.CombineSkuModel;
import io.scleropages.sentarum.item.core.model.impl.ItemModel;
import io.scleropages.sentarum.item.core.model.impl.SkuModel;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ItemApi {
    /**
     * 创建item
     *
     * @param model  item模型
     * @param spuId  所属spu
     * @param values 商品属性值集合(key为元数据id，value为属性值)
     */
    void createItem(@Valid ItemModel model, Long spuId, Map<Long, Object> values);

    /**
     * 更新保存item
     *
     * @param model  item模型
     * @param values 商品属性值集合(key为属性值id，value为属性值)
     */
    void saveItem(@Valid ItemModel model, Map<Long, Object> values);

    /**
     * 创建sku
     *
     * @param model  sku模型
     * @param values 销售属性值集合(key为元数据id，value为属性值)
     */
    void createSku(@Valid SkuModel model, Long itemId, Map<Long, Object> values);

    /**
     * 更新保存Sku
     *
     * @param model  sku模型
     * @param values 销售属性值集合(key为属性值id，value为属性值)
     */
    void saveSku(@Valid SkuModel model, Map<Long, Object> values);

    /**
     * 创建 combine sku
     *
     * @param model  combine sku模型
     * @param values 销售属性值集合(key为元数据id，value为属性值)
     */
    void createCombineSku(@Valid CombineSkuModel model, Long itemId, Map<Long, Object> values);

    /**
     * 更新保存 combine sku
     *
     * @param model  combine sku模型
     * @param values 销售属性值集合(key为属性值id，value为属性值)
     */
    void saveCombineSku(@Valid CombineSkuModel model, Map<Long, Object> values);

    /**
     * 创建组合sku条目(将目标sku加入到组合sku中)
     *
     * @param model        组合sku条目模型
     * @param combineSkuId 组合sku id
     * @param skuId        目标sku id
     */
    void createCombineSkuEntry(@Valid CombineSkuEntryModel model, Long combineSkuId, Long skuId);

    /**
     * 更新保存组合sku条目
     *
     * @param model 组合sku条目模型
     */
    void saveCombineSkuEntry(@Valid CombineSkuEntryModel model);

    /**
     * 删除组合sku条目
     *
     * @param id
     */
    void deleteCombineSkuEntry(Long id);

    /**
     * 查询item 页
     *
     * @param itemSearchFilters     item信息查询
     * @param propertySearchFilters 关键属性查询
     * @param pageable              分页
     * @param propertySort          关键属性排序
     * @return
     */
    Page<Item> findItemPage(Map<String, SearchFilter> itemSearchFilters, Map<String, SearchFilter> propertySearchFilters, Pageable pageable, Sort propertySort);

    /**
     * 查询 sku 页
     *
     * @param searchFilters
     * @param pageable
     * @return
     */
    Page<Sku> findSkuPage(Map<String, Object> searchFilters, Pageable pageable);

    /**
     * 查询 combine sku 页
     *
     * @param searchFilters
     * @param pageable
     * @return
     */
    Page<CombineSku> findCombineSkuPage(Map<String, Object> searchFilters, Pageable pageable);

    /**
     * 查询 combine sku entry 页
     *
     * @param searchFilters
     * @param pageable
     * @return
     */
    Page<CombineSkuEntry> findCombineSkuEntryPage(Map<String, Object> searchFilters, Pageable pageable);
}
