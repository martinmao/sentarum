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

import com.google.common.collect.Lists;
import io.scleropages.sentarum.core.entity.mapper.MapAttributesMapper;
import io.scleropages.sentarum.promotion.goods.entity.GoodsEntity;
import io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity;
import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.goods.repo.AdditionalAttributesInitializer.AdditionalAttributesSavingCallback;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.Map;

import static io.scleropages.sentarum.promotion.goods.entity.GoodsEntity.COLUMN_ATTRS_PAYLOAD;
import static io.scleropages.sentarum.promotion.goods.entity.GoodsEntity.COLUMN_ID;
import static io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity.COLUMN_GOODS_ID;


/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface GoodsRepository<E extends GoodsEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E>, AdditionalAttributesSavingCallback<Goods, E>, MapAttributesMapper {


    @Cacheable(key = "#goodsPk")
    default List<? extends GoodsSpecsEntity> findAllGoodsSpecsByGoodsId(GoodsSpecsRepository goodsSpecsRepository, Long goodsPk) {
        Table goodsSpecsTable = goodsSpecsRepository.dslTable();
        T goodsTable = dslTable();
        Field goodsTableId = goodsTable.field(COLUMN_ID.toUpperCase());
        List<GoodsSpecsEntity> entities = Lists.newArrayList();
        dslContext().select(goodsSpecsTable.fields())
                .from(goodsTable)
                .join(goodsSpecsTable)
                .on(goodsTableId.eq(goodsSpecsTable.field(COLUMN_GOODS_ID.toUpperCase())))
                .where(goodsTableId.eq(goodsPk))
                .fetch().forEach(record -> {
            GoodsSpecsEntity entity = goodsSpecsRepository.createEntity();
            dslRecordInto(record, entity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {

                }
            });
            entities.add(entity);
        });
        return entities;
    }

    @Override
    default void save(Goods provider, Map<String, Object> additionalAttributesMap) {
        Assert.notNull(provider, "goods must not be null.");
        Long id = provider.id();
        Assert.notNull(id, "goods id must not be null.");
        T t = dslTable();
        dslContext().update(t)
                .set(t.field(COLUMN_ATTRS_PAYLOAD.toUpperCase()), attributesToPayload(additionalAttributesMap))
                .where(t.field(COLUMN_ID.toUpperCase()).eq(id)).execute();
    }

    @Override
    default Map<String, Object> additionalAttributesMap(E entity) {
        Assert.notNull(entity, "goods entity must not be null.");
        return payloadToAttributes(entity.getAdditionalAttributes());
    }

    class GoodsConditionsAssembler {

        public static void applyGoodsCondition(SelectQuery<Record> baseQuery, GoodsJoin goodsJoin) {
            baseQuery.addJoin(
                    goodsJoin.goodsTable,
                    goodsJoin.joinType,
                    goodsJoin.goodsRefField.eq(goodsJoin.sourceIdField),
                    goodsJoin.otherCondition);
        }
    }


    class GoodsJoin {
        private final Table goodsTable;
        private final Field sourceIdField;
        private final Field goodsRefField;
        private final JoinType joinType;
        private final Condition otherCondition;

        public GoodsJoin(Table goodsTable, Field sourceIdField, Field goodsRefField, JoinType joinType, Condition otherCondition) {
            this.goodsTable = goodsTable;
            this.sourceIdField = sourceIdField;
            this.goodsRefField = goodsRefField;
            this.joinType = joinType;
            this.otherCondition = null != otherCondition ? otherCondition : DSL.noCondition();
        }
    }
}
