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
package io.scleropages.sentarum.cloud.item.rpc.server.mvc;

import io.scleropages.sentarum.cloud.item.mapper.ItemCreateRequestMapper;
import io.scleropages.sentarum.cloud.v_1_0_0.api.ItemApi;
import io.scleropages.sentarum.cloud.v_1_0_0.model.ItemCreateRequest;
import io.scleropages.sentarum.cloud.v_1_0_0.model.ItemCreateResponse;
import io.scleropages.sentarum.item.core.model.impl.ItemModel;
import io.scleropages.sentarum.item.mgmt.ItemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RestController
@RequestMapping("/")
public class ItemApiHttpService implements ItemApi {

    private ItemManager itemManager;
    private ItemCreateRequestMapper itemCreateRequestMapper;

    @Override
    @PostMapping("create/v1.0.0")
    public ItemCreateResponse createItem(@RequestBody ItemCreateRequest request) {
        ItemModel itemModel = itemCreateRequestMapper.mapForBizInvoke(request);
        Map<Long, Object> propertyValuesMap = itemCreateRequestMapper.asPropertyValuesMap(request.getPropertyValues());
        Long id = itemManager.createItem(itemModel, request.getSpuId(), propertyValuesMap);
        ItemCreateResponse itemCreateResponse = new ItemCreateResponse();
        itemCreateResponse.setId(id);
        return itemCreateResponse;
    }

    @Autowired
    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @Autowired
    public void setItemCreateRequestMapper(ItemCreateRequestMapper itemCreateRequestMapper) {
        this.itemCreateRequestMapper = itemCreateRequestMapper;
    }
}
