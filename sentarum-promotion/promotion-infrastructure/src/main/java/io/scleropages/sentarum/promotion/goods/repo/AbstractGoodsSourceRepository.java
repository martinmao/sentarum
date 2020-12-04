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

import io.scleropages.sentarum.core.entity.mapper.MapAttributesMapper;
import io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity;
import io.scleropages.sentarum.promotion.goods.model.GoodsSource;
import io.scleropages.sentarum.promotion.goods.repo.AdditionalAttributesInitializer.AdditionalAttributesSavingCallback;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity.COLUMN_ATTRIBUTE_PAYLOAD;
import static io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity.COLUMN_ID;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractGoodsSourceRepository<E extends AbstractGoodsSourceEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E>, AdditionalAttributesSavingCallback<GoodsSource, E>, MapAttributesMapper {


    default E createEntity() {
        throw new IllegalStateException("not implement.");
    }


    List<E> findByBizTypeAndBizId(Integer bizType, Long bizId);

    Optional<E> getTop1ByBizTypeAndBizId(Integer bizType, Long bizId);

    Boolean existsByGoodsSourceType(Integer goodsSourceType);

    @Cacheable(key = "#root.target+'-'+#bizType+'-'+#bizId")
    default Result<R> readByBizTypeAndBizId(Integer bizType, Long bizId) {
        T t = dslTable();
        return dslContext().selectFrom(t)
                .where(t.field(AbstractGoodsSourceEntity.COLUMN_BIZ_TYPE.toUpperCase()).eq(bizType))
                .and(t.field(AbstractGoodsSourceEntity.COLUMN_BIZ_ID.toUpperCase()).eq(bizId))
                .fetch();
    }

    default void consumeEntitiesByRecord(Result<R> rs, Consumer<E> consumer) {
        rs.forEach(r -> {
            E entity = createEntity();
            dslRecordInto(r, entity);
            consumer.accept(entity);
        });
    }


    @Override
    default void save(GoodsSource provider, Map<String, Object> attributes) {
        Assert.notNull(provider, "goods source must not be null.");
        Long id = provider.id();
        Assert.notNull(id, "goods source id must not be null.");
        T t = dslTable();
        dslContext().update(t)
                .set(t.field(COLUMN_ATTRIBUTE_PAYLOAD.toUpperCase()), attributesToPayload(attributes))
                .where(t.field(COLUMN_ID.toUpperCase()).eq(id)).execute();
    }

    @Override
    default Map<String, Object> additionalAttributesMap(E entity) {
        Assert.notNull(entity, "goods source entity must not be null.");
        return payloadToAttributes(entity.getAttributePayLoad());
    }

    class GoodsSourceConditionsAssembler {

        public static void applyGoodsSourceCondition(SelectQuery<? extends Record> baseQuery, GoodsSourceJoin goodsSourceJoin) {
            if (null == goodsSourceJoin.joinType) {
                baseQuery.addFrom(goodsSourceJoin.goodsSourceTable);
                baseQuery.addConditions(goodsSourceJoin.goodsSourceTable.field(AbstractGoodsSourceEntity.COLUMN_BIZ_TYPE.toUpperCase()).eq(goodsSourceJoin.bizType)
                                .and(goodsSourceJoin.baseJoinField.eq(goodsSourceJoin.goodsSourceTable.field(AbstractGoodsSourceEntity.COLUMN_BIZ_ID.toUpperCase()))),
                        goodsSourceJoin.otherCondition);
            } else {
                baseQuery.addJoin(
                        goodsSourceJoin.goodsSourceTable,
                        goodsSourceJoin.joinType,
                        goodsSourceJoin.goodsSourceTable.field(AbstractGoodsSourceEntity.COLUMN_BIZ_TYPE.toUpperCase()).eq(goodsSourceJoin.bizType)
                                .and(goodsSourceJoin.baseJoinField.eq(goodsSourceJoin.goodsSourceTable.field(AbstractGoodsSourceEntity.COLUMN_BIZ_ID.toUpperCase()))),
                        goodsSourceJoin.otherCondition);
            }
        }
    }


    class GoodsSourceJoin {
        private final Table goodsSourceTable;
        private final JoinType joinType;
        private final Field baseJoinField;
        private final Condition otherCondition;
        private final Integer bizType;

        public GoodsSourceJoin(Table goodsSourceTable, JoinType joinType, Field baseJoinField, Condition otherCondition, Integer bizType) {
            this.goodsSourceTable = goodsSourceTable;
            this.joinType = joinType;
            this.baseJoinField = baseJoinField;
            this.otherCondition = null != otherCondition ? otherCondition : DSL.noCondition();
            this.bizType = bizType;
        }
    }


}
