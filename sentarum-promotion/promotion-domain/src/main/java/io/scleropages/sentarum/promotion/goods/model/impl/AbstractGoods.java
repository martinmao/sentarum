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

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractGoods implements Goods {


    private Long id;
    private Long goodsId;
    private String outerGoodsId;
    private String name;
    private List<GoodsSpecs> specs;


    public Long getId() {
        return id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public String getOuterGoodsId() {
        return outerGoodsId;
    }

    public String getName() {
        return name;
    }

    public List<GoodsSpecs> getSpecs() {
        return specs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public void setOuterGoodsId(String outerGoodsId) {
        this.outerGoodsId = outerGoodsId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecs(List<GoodsSpecs> specs) {
        this.specs = specs;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long goodsId() {
        return getGoodsId();
    }

    @Override
    public String outerGoodsId() {
        return getOuterGoodsId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public List<GoodsSpecs> specs() {
        return getSpecs();
    }
}
