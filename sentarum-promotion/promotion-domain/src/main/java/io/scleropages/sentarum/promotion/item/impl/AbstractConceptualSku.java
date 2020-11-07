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
package io.scleropages.sentarum.promotion.item.impl;

import io.scleropages.sentarum.promotion.item.ConceptualItem;
import io.scleropages.sentarum.promotion.item.ConceptualSku;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractConceptualSku implements ConceptualSku {

    private Long id;
    private Long skuId;
    private String outerSkuId;
    private String name;
    private ConceptualItem item;


    public Long getId() {
        return id;
    }

    public Long getSkuId() {
        return skuId;
    }

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public String getName() {
        return name;
    }

    public ConceptualItem getItem() {
        return item;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItem(ConceptualItem item) {
        this.item = item;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long skuId() {
        return getSkuId();
    }

    @Override
    public String outerSkuId() {
        return getOuterSkuId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public ConceptualItem item() {
        return getItem();
    }
}
