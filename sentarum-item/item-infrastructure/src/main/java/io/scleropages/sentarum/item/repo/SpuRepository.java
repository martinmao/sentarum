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
import io.scleropages.sentarum.item.core.entity.SpuEntity;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.repo.AbstractPropertyValueRepository.PropertyConditionsAssembler;
import io.scleropages.sentarum.jooq.tables.ItemSpu;
import io.scleropages.sentarum.jooq.tables.records.ItemSpuRecord;
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

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface SpuRepository extends GenericRepository<SpuEntity, Long>, JooqRepository<ItemSpu, ItemSpuRecord, SpuEntity> {


    @EntityGraph(attributePaths = "category")
    @Cacheable
    Optional<SpuEntity> getById(Long id);


    @Cacheable
    default Optional<ItemSpuRecord> readById(Long id) {
        ItemSpu spu = dslTable();
        return dslContext().selectFrom(spu).where(spu.ID.eq(id)).fetchOptional();
    }


    /**
     * read spu by given id
     *
     * @param id                 required.
     * @param categoryRepository (optional) required if want to fetch category.
     * @return
     */
    default Optional<SpuEntity> readById(Long id, StandardCategoryRepository categoryRepository) {
        Optional<ItemSpuRecord> optionalItemSpuRecord = readById(id);
        if (!optionalItemSpuRecord.isPresent())
            return Optional.empty();
        ItemSpuRecord itemSpuRecord = optionalItemSpuRecord.get();
        SpuEntity spuEntity = new SpuEntity();
        dslRecordInto(itemSpuRecord, spuEntity);
        if (null != categoryRepository) {
            categoryRepository.consumeById(itemSpuRecord.getStdCategoryId(), entity -> spuEntity.setCategory(entity));
        }
        return Optional.of(spuEntity);
    }


    default void consumeById(Long id, Consumer<SpuEntity> entityConsumer) {
        readById(id).ifPresent(r -> {
            SpuEntity entity = new SpuEntity();
            dslRecordInto(r, entity);
            entityConsumer.accept(entity);
        });
    }


    default Page<SpuEntity> findSpuPage(Map<String, SearchFilter> spuSearchFilters, Map<PropertyMetadata, SearchFilter> propertySearchFilters, Pageable pageable, Sort propertySort) {
        ItemSpu spu = dslTable();
        JpaContexts.ManagedTypeModel<SpuEntity> managedTypeModel = JpaContexts.getManagedTypeModel(SpuEntity.class);
        List<Field> queryFields = Lists.newArrayList(spu.fields());

        SelectQuery<Record> query = dslContext().select(queryFields).from(spu).getQuery();

        query.addConditions(JpaSupportJooqConditions.bySearchFilters(query, managedTypeModel, spuSearchFilters.values()));

        if (!CollectionUtils.isEmpty(propertySearchFilters)) {
            PropertyConditionsAssembler.applyPropertyConditions(KEY_PROPERTY.getOrdinal(), query, propertySort, propertySearchFilters, managedTypeModel);
        }

        return dslPage(() -> query, pageable, false, false).map(o -> {
            SpuEntity spuEntity = new SpuEntity();
            dslRecordInto(o, spuEntity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {

                }
            });
            return spuEntity;
        });
    }


}
