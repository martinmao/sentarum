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
package io.scleropages.sentarum.item.rpc.server.dubbo;

import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.item.core.model.CombineSku;
import io.scleropages.sentarum.item.core.model.CombineSkuEntry;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.Sku;
import io.scleropages.sentarum.item.core.model.impl.CombineSkuEntryModel;
import io.scleropages.sentarum.item.core.model.impl.CombineSkuModel;
import io.scleropages.sentarum.item.core.model.impl.ItemModel;
import io.scleropages.sentarum.item.core.model.impl.SkuModel;
import io.scleropages.sentarum.item.mgmt.ItemManager;
import org.apache.dubbo.config.annotation.Service;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
public class ItemApiDubboService implements ItemApi {


    private ItemManager itemManager;

    @Override
    public void createItem(@Valid ItemModel model, Long spuId, Map<Long, Object> values) {
        itemManager.createItem(model, spuId, values);
    }

    @Override
    public void saveItem(@Valid ItemModel model, Map<Long, Object> values) {
        itemManager.saveItem(model, values);
    }

    @Override
    public void createSku(@Valid SkuModel model, Long itemId, Map<Long, Object> values) {
        itemManager.createSku(model, itemId, values);
    }

    @Override
    public void saveSku(@Valid SkuModel model, Map<Long, Object> values) {
        itemManager.saveSku(model, values);
    }

    @Override
    public void createCombineSku(@Valid CombineSkuModel model, Long itemId, Map<Long, Object> values) {
        itemManager.createCombineSku(model, itemId, values);
    }

    @Override
    public void saveCombineSku(@Valid CombineSkuModel model, Map<Long, Object> values) {
        itemManager.saveCombineSku(model, values);
    }

    @Override
    public void createCombineSkuEntry(@Valid CombineSkuEntryModel model, Long combineSkuId, Long skuId) {
        itemManager.createCombineSkuEntry(model, combineSkuId, skuId);
    }

    @Override
    public void saveCombineSkuEntry(@Valid CombineSkuEntryModel model) {
        itemManager.saveCombineSkuEntry(model);
    }

    @Override
    public void deleteCombineSkuEntry(Long id) {
        itemManager.deleteCombineSkuEntry(id);
    }

    @Override
    public Page<Item> findItemPage(Map<String, SearchFilter> itemSearchFilters, Map<String, SearchFilter> propertySearchFilters, Pageable pageable, Sort propertySort) {
        return itemManager.findItemPage(itemSearchFilters, propertySearchFilters, pageable, propertySort);
    }

    @Override
    public Page<Sku> findSkuPage(Map<String, Object> searchFilters, Pageable pageable) {
        return itemManager.findSkuPage(searchFilters, pageable);
    }

    @Override
    public Page<CombineSku> findCombineSkuPage(Map<String, Object> searchFilters, Pageable pageable) {
        return itemManager.findCombineSkuPage(searchFilters, pageable);
    }

    @Override
    public Page<CombineSkuEntry> findCombineSkuEntryPage(Map<String, Object> searchFilters, Pageable pageable) {
        return itemManager.findCombineSkuEntryPage(searchFilters, pageable);
    }

    @Autowired
    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }
}
