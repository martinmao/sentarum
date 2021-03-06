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
package io.scleropages.sentarum.promotion.goods.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import static io.scleropages.sentarum.promotion.goods.entity.GoodsEntity.COLUMN_ATTRS_PAYLOAD;
import static io.scleropages.sentarum.promotion.goods.entity.GoodsEntity.COLUMN_GOODS_SOURCE_ID;

/**
 * referenced from: {@link io.scleropages.sentarum.promotion.goods.model.impl.AbstractGoodsSpecs}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public abstract class GoodsSpecsEntity extends IdEntity {

    public static final String COLUMN_GOODS_ID = "goods_id";

    private Long specsId;
    private String outerSpecsId;
    private String name;
    private GoodsEntity goods;
    private AbstractGoodsSourceEntity goodsSource;
    private String additionalAttributes;

    @Column(name = "specs_id", nullable = false)
    public Long getSpecsId() {
        return specsId;
    }

    @Column(name = "outer_specs_id", nullable = false)
    public String getOuterSpecsId() {
        return outerSpecsId;
    }

    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_GOODS_ID, nullable = false)
    @Transient
    public GoodsEntity getGoods() {
        return goods;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COLUMN_GOODS_SOURCE_ID, nullable = false)
    @Transient
    public AbstractGoodsSourceEntity getGoodsSource() {
        return goodsSource;
    }

    @Column(name = COLUMN_ATTRS_PAYLOAD, length = 2048)
    public String getAdditionalAttributes() {
        return additionalAttributes;
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

    public void setGoods(GoodsEntity goods) {
        this.goods = goods;
    }

    public void setGoodsSource(AbstractGoodsSourceEntity goodsSource) {
        this.goodsSource = goodsSource;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }
}
