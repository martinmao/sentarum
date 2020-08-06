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
import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.entity.SpuEntity;
import io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.jooq.tables.ItemSpu;
import io.scleropages.sentarum.jooq.tables.PtKeyPropertyValue;
import io.scleropages.sentarum.jooq.tables.records.ItemSpuRecord;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.Table;
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
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.KEY_PROPERTY;
import static io.scleropages.sentarum.jooq.Tables.PT_KEY_PROPERTY_VALUE;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface SpuRepository extends GenericRepository<SpuEntity, Long>, JooqRepository<ItemSpu, ItemSpuRecord, SpuEntity> {


    @EntityGraph(attributePaths = "category")
    @Cacheable
    Optional<SpuEntity> getById(Long id);


    default Page<SpuEntity> findSpuPage(Map<String, SearchFilter> spuSearchFilters, Map<PropertyMetadata, SearchFilter> keyPropertySearchFilters, Pageable pageable, Sort propertySort) {
        ItemSpu spu = dslTable();
        JpaContexts.ManagedTypeModel<SpuEntity> managedTypeModel = JpaContexts.getManagedTypeModel(SpuEntity.class);
        List<Field> queryFields = Lists.newArrayList(spu.fields());

        SelectQuery<Record> query = dslContext().select(queryFields).from(spu).getQuery();

        Condition spuCondition = JpaSupportJooqConditions.bySearchFilters(query, managedTypeModel, spuSearchFilters.values());

        query.addConditions(spuCondition);

        if (!CollectionUtils.isEmpty(keyPropertySearchFilters)) {
            applyPropertyConditions(KEY_PROPERTY.getOrdinal(), Optional.of(query), propertySort, keyPropertySearchFilters, managedTypeModel);
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


    /**
     * apply property conditions to base query.
     *
     * @param bizType               business type id
     * @param requiredBaseQuery     base query to apply
     * @param propertySort          property sort
     * @param propertySearchFilters property search filters
     * @param bizEntityModel        entity model of business object.
     */
    default void applyPropertyConditions(Integer bizType, Optional<SelectQuery<Record>> requiredBaseQuery, Sort propertySort, Map<PropertyMetadata, SearchFilter> propertySearchFilters, JpaContexts.ManagedTypeModel<?> bizEntityModel) {

        SelectQuery<Record> baseQuery = requiredBaseQuery.get();

        Table bizTable = dslNameToTable(bizEntityModel.table().toUpperCase());

        Field bizIdField = dslNameToField(bizTable.getName(), bizEntityModel.getColumnOfId().toUpperCase());

        List<Condition> propertyConditions = Lists.newArrayList();//use this to keep property conditions.

        Map<String, Field> sortFields = Maps.newHashMap();// use this to process property sorting query.


        propertySearchFilters.forEach((metadata, searchFilter) -> {// apply join to query. and build propertyConditions, sortFields
            Assert.isTrue(searchFilter.fieldNames.length == 1, "not support multiple field names.");
            String propertyName = searchFilter.fieldNames[0];

            PtKeyPropertyValue joinAlias = PT_KEY_PROPERTY_VALUE.as(propertyName + "_" + searchFilter.hashCode());//alias of current PT_KEY_PROPERTY_VALUE to join

            baseQuery.addJoin(joinAlias, joinAlias.BIZ_TYPE.eq(bizType).and(bizIdField.eq(joinAlias.BIZ_ID)));

            Field valueField = dslNameToField(joinAlias.getName(), AbstractPropertyValueEntity.getColumnByMetaValueType(metadata.valueType()));

            Condition eachPropertyCondition = joinAlias.NAME_.eq(propertyName).and(JpaSupportJooqConditions.bySearchFilter(valueField, searchFilter));
            propertyConditions.add(eachPropertyCondition);
            sortFields.put(propertyName, valueField);
        });

        baseQuery.addConditions(propertyConditions);

        if (null != propertySort || propertySort.isSorted())
            propertySort.forEach(order -> {// check sort variable must containing in variable condition.
                String property = order.getProperty();
                Field field = sortFields.get(property);
                Assert.notNull(field, "sorted property must as a query condition: " + property);
                baseQuery.addOrderBy(order.isAscending() ? field.asc() : field.desc());
            });
    }
}
