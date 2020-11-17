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

import io.scleropages.sentarum.promotion.goods.model.impl.AbstractClassifiedGoodsSource;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link AbstractClassifiedGoodsSource}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class ClassifiedGoodsSourceEntity extends AbstractGoodsSourceEntity {

    public static final String COLUMN_GOODS_SOURCE_ID = "goods_source_id";
    public static final String COLUMN_SECONDARY_GOODS_SOURCE_ID = "secondary_goods_source_id";

    private Long goodsSourceId;

    private Long secondaryGoodsSourceId;

    private String goodsSourceName;

    private String query;

    @Column(name = COLUMN_GOODS_SOURCE_ID, nullable = false)
    public Long getGoodsSourceId() {
        return goodsSourceId;
    }

    @Column(name = COLUMN_SECONDARY_GOODS_SOURCE_ID)
    public Long getSecondaryGoodsSourceId() {
        return secondaryGoodsSourceId;
    }

    @Column(name = "goods_source_name", nullable = false)
    public String getGoodsSourceName() {
        return goodsSourceName;
    }

    @Column(name = "query_", nullable = false)
    public String getQuery() {
        return query;
    }

    public void setGoodsSourceId(Long goodsSourceId) {
        this.goodsSourceId = goodsSourceId;
    }

    public void setSecondaryGoodsSourceId(Long secondaryGoodsSourceId) {
        this.secondaryGoodsSourceId = secondaryGoodsSourceId;
    }

    public void setGoodsSourceName(String goodsSourceName) {
        this.goodsSourceName = goodsSourceName;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
