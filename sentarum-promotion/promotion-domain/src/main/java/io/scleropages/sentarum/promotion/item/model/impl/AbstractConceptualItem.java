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
package io.scleropages.sentarum.promotion.item.model.impl;

import io.scleropages.sentarum.promotion.item.model.ConceptualItem;
import io.scleropages.sentarum.promotion.item.model.ConceptualSku;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractConceptualItem implements ConceptualItem {


    private Long id;
    private Long itemId;
    private String outerItemId;
    private String name;
    private List<ConceptualSku> skuList;


    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getOuterItemId() {
        return outerItemId;
    }

    public String getName() {
        return name;
    }

    public List<ConceptualSku> getSkuList() {
        return skuList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setOuterItemId(String outerItemId) {
        this.outerItemId = outerItemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSkuList(List<ConceptualSku> skuList) {
        this.skuList = skuList;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long itemId() {
        return getItemId();
    }

    @Override
    public String outerItemId() {
        return getOuterItemId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public List<ConceptualSku> skuList() {
        return getSkuList();
    }
}
