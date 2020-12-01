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
package io.scleropages.sentarum.item.repo;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.category.repo.StandardCategoryRepository;
import io.scleropages.sentarum.item.core.entity.ItemEntity;
import io.scleropages.sentarum.item.core.entity.SpuEntity;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.jooq.tables.Item;
import io.scleropages.sentarum.jooq.tables.ItemSpu;
import io.scleropages.sentarum.jooq.tables.records.ItemRecord;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.JpaContexts;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JpaSupportJooqConditions;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.util.CollectionUtils;

import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.KEY_PROPERTY;
import static io.scleropages.sentarum.item.property.repo.AbstractPropertyValueRepository.PropertyConditionsAssembler;
import static io.scleropages.sentarum.jooq.Tables.ITEM_SPU;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ItemRepository extends GenericRepository<ItemEntity, Long>, JooqRepository<Item, ItemRecord, ItemEntity> {


    @EntityGraph(attributePaths = {"spu", "category"})
    @Cacheable
    Optional<ItemEntity> getById(Long id);


    @Cacheable
    default Optional<ItemRecord> readById(Long id) {
        Item item = dslTable();
        return dslContext().selectFrom(item).where(item.ID.eq(id)).fetchOptional();
    }

    /**
     * read item by given id
     *
     * @param id                 required
     * @param spuRepository      (optional) required if want to fetch spu info.
     * @param categoryRepository (optional) required if want to fetch category info.
     * @return
     */
    default Optional<ItemEntity> readById(Long id, SpuRepository spuRepository, StandardCategoryRepository categoryRepository) {
        Optional<ItemRecord> optionalItemRecord = readById(id);
        if (!optionalItemRecord.isPresent())
            return Optional.empty();

        ItemRecord itemRecord = optionalItemRecord.get();
        ItemEntity itemEntity = new ItemEntity();

        dslRecordInto(itemRecord, itemEntity);
        if (null != spuRepository)
            spuRepository.readById(itemRecord.getSpuId(), categoryRepository).ifPresent(spuEntity -> itemEntity.setSpu(spuEntity));
        return Optional.of(itemEntity);
    }

    default void consumeById(Long id, Consumer<ItemEntity> entityConsumer) {
        readById(id).ifPresent(r -> {
            ItemEntity entity = new ItemEntity();
            dslRecordInto(r, entity);
            entityConsumer.accept(entity);
        });
    }


    default Page<ItemEntity> findItemPage(Map<String, SearchFilter> spuSearchFilters, Map<PropertyMetadata, SearchFilter> keyPropertySearchFilters, Pageable pageable, Sort propertySort) {

        Item item = dslTable();
        ItemSpu spu = ITEM_SPU;

        List<Field> queryFields = Lists.newArrayList(item.fields());

        SelectQuery<Record> query = dslContext().select(queryFields).from(spu).join(item).on(spu.ID.eq(item.SPU_ID)).getQuery();

        Condition itemCondition = JpaSupportJooqConditions.bySearchFilters(query, JpaContexts.getManagedTypeModel(ItemEntity.class), spuSearchFilters.values());

        query.addConditions(itemCondition);

        if (!CollectionUtils.isEmpty(keyPropertySearchFilters)) {
            PropertyConditionsAssembler.applyPropertyConditions(KEY_PROPERTY.getOrdinal(), query, propertySort, keyPropertySearchFilters, JpaContexts.getManagedTypeModel(SpuEntity.class));
        }

        return dslPage(() -> query, pageable, false, true).map(o -> {
            ItemEntity itemEntity = new ItemEntity();
            dslRecordInto(o, itemEntity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {

                }
            });
            return itemEntity;
        });

    }
}
