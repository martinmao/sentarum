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

import io.scleropages.sentarum.promotion.goods.model.ClassifiedGoodsSource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractClassifiedGoodsSource extends AbstractGoodsSource implements ClassifiedGoodsSource {

    private Long goodsSourceId;

    private Long secondaryGoodsSourceId;

    private String goodsSourceName;

    private String query;

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Long getGoodsSourceId() {
        return goodsSourceId;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Long getSecondaryGoodsSourceId() {
        return secondaryGoodsSourceId;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public String getGoodsSourceName() {
        return goodsSourceName;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
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

    @Override
    public Long goodsSourceId() {
        return getGoodsSourceId();
    }

    @Override
    public Long secondaryGoodsSourceId() {
        return getSecondaryGoodsSourceId();
    }

    @Override
    public String goodsSourceName() {
        return getGoodsSourceName();
    }

    @Override
    public String query() {
        return getQuery();
    }


}
