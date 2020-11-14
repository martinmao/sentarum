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
package io.scleropages.sentarum.promotion.goods.model.impl;

import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.goods.model.GoodsSpecs;

import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractGoodsSpecs implements GoodsSpecs {

    private Long id;
    private Long specsId;
    private String outerSpecsId;
    private String name;
    private Goods goods;
    private Map<String, Object> additionalAttributes;

    public Long getId() {
        return id;
    }

    public Long getSpecsId() {
        return specsId;
    }

    public String getOuterSpecsId() {
        return outerSpecsId;
    }

    public String getName() {
        return name;
    }

    public Goods getGoods() {
        return goods;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSpecsId(Long specsId) {
        this.specsId = specsId;
    }

    public void setOuterSpecsId(String outerSpecsId) {
        this.outerSpecsId = outerSpecsId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long specsId() {
        return getSpecsId();
    }

    @Override
    public String outerSpecsId() {
        return getOuterSpecsId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public Goods goods() {
        return getGoods();
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return getAdditionalAttributes();
    }
}
