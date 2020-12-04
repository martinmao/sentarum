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
package io.scleropages.sentarum.promotion.goods.repo;

import io.scleropages.sentarum.promotion.goods.entity.ClassifiedGoodsSourceEntity;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import static io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity.COLUMN_GOODS_SOURCE_TYPE;
import static io.scleropages.sentarum.promotion.goods.entity.ClassifiedGoodsSourceEntity.COLUMN_GOODS_SOURCE_ID;
import static io.scleropages.sentarum.promotion.goods.entity.ClassifiedGoodsSourceEntity.COLUMN_SECONDARY_GOODS_SOURCE_ID;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface ClassifiedGoodsSourceRepository<E extends ClassifiedGoodsSourceEntity, T extends Table, R extends Record> extends AbstractGoodsSourceRepository<E, T, R> {



    default Condition conditionByGoodsSourceTypeAndGoodsSourceIdAndSecondaryGoodsSourceId(Integer goodsSourceType, Long goodsSourceId, Long secondaryGoodsSourceId) {
        Assert.notNull(goodsSourceType, "goodsSourceType must not be null.");
        T t = dslTable();
        Condition goodsSourceTypeCondition = t.field(COLUMN_GOODS_SOURCE_TYPE.toUpperCase()).eq(goodsSourceType);
        Condition goodsSourceIdCondition = t.field(COLUMN_GOODS_SOURCE_ID.toUpperCase()).eq(goodsSourceId);
        Condition secondaryGoodsSourceIdCondition = t.field(COLUMN_SECONDARY_GOODS_SOURCE_ID.toUpperCase()).eq(secondaryGoodsSourceId);
        return goodsSourceTypeCondition
                .and(null != goodsSourceId ? goodsSourceIdCondition : dslConditionNo())
                .and(null != secondaryGoodsSourceId ? secondaryGoodsSourceIdCondition : dslConditionNo());
    }
}
