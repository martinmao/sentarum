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
package io.scleropages.sentarum.item.property.repo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.jooq.tables.PtKeyPropertyValue;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.JpaContexts;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JpaSupportJooqConditions;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity.*;
import static io.scleropages.sentarum.jooq.Tables.PT_KEY_PROPERTY_VALUE;
import static io.scleropages.sentarum.jooq.Tables.PT_PROPERTY_META;
import static org.jooq.impl.DSL.*;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractPropertyValueRepository<E extends AbstractPropertyValueEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E> {

    default void batchSave(Iterable<E> entities) {
        List<R> recordsToSave = Lists.newArrayList();
        entities.forEach(entity -> {
            R record = dslNewRecord();
            record.set(dslField(BIZ_TYPE_COLUMN.toUpperCase()), entity.getBizType());
            record.set(dslField(BIZ_ID_COLUMN.toUpperCase()), entity.getBizId());
            record.set(dslField(NAME_COLUMN.toUpperCase()), entity.getName());
            record.set(dslField(PROPERTY_META_ID_COLUMN.toUpperCase()), entity.getPropertyMetaId());
            applyValueToFieldColumn(entity, record);
            if (null != entity.getDateValue()) {
                record.set(dslField(DATE_VALUE_COLUMN.toUpperCase()), new Timestamp(entity.getDateValue().getTime()));
            }
            recordsToSave.add(record);
        });
        dslContext().batchInsert((Collection<? extends TableRecord<?>>) recordsToSave).execute();
    }

    default void batchUpdate(Iterable<E> entities) {
        T actualTable = dslTable();
        List<R> recordsToUpdate = Lists.newArrayList();
        entities.forEach(entity -> {
            R record = (R) dslContext().selectFrom(actualTable).where(actualTable.field("ID").eq(entity.getId())).fetchOne();
            Assert.notNull(record, () -> "no property value found: " + entity.getId());
            applyValueToFieldColumn(entity, record);
            recordsToUpdate.add(record);
        });
        dslContext().batchUpdate((Collection<? extends UpdatableRecord<?>>) recordsToUpdate).execute();
    }


    default List<E> findAllByBizTypeAndBizId(Integer bizType, Long bizId) {
        T actualTable = dslTable();
        //由于此处没有外键约束，确保引用的元数据存在而进行一次join操作.
        return dslContext()
                .select(actualTable.fields())
                .from(actualTable)
                .join(PT_PROPERTY_META)
                .on(actualTable.field(PROPERTY_META_ID_COLUMN.toUpperCase()).eq(PT_PROPERTY_META.ID))
                .where(actualTable.field(BIZ_TYPE_COLUMN.toUpperCase()).eq(bizType))
                .and(actualTable.field(BIZ_ID_COLUMN.toUpperCase()).eq(bizId)).fetch().map(record -> {
                    E entity = newPropertyValueEntity();
                    dslRecordInto(record, entity);
                    return entity;
                });
    }


    default void applyValueToFieldColumn(E entity, R record) {
        record.set(dslField(BOOL_VALUE_COLUMN.toUpperCase()), entity.getBooleanValue());
        record.set(dslField(DECIMAL_VALUE_COLUMN.toUpperCase()), entity.getDecimalValue());
        record.set(dslField(INT_VALUE_COLUMN.toUpperCase()), entity.getIntValue());
        record.set(dslField(LONG_VALUE_COLUMN.toUpperCase()), entity.getLongValue());
        record.set(dslField(TEXT_VALUE_COLUMN.toUpperCase()), entity.getTextValue());
        record.set(dslField(NULL_VALUE_COLUMN.toUpperCase()), entity.getNullValue());
    }


    /**
     * 子类必须实现，返回明确的entity类型
     *
     * @return
     */
    abstract E newPropertyValueEntity();


    abstract class PropertyConditionsAssembler {
        /**
         * apply property conditions to base query.
         *
         * @param bizType               business type id
         * @param baseQuery             base query to apply
         * @param propertySort          property sort
         * @param propertySearchFilters property search filters
         * @param bizEntityModel        entity model of business object.
         */
        public static void applyPropertyConditions(Integer bizType, SelectQuery<Record> baseQuery, Sort propertySort, Map<PropertyMetadata, SearchFilter> propertySearchFilters, JpaContexts.ManagedTypeModel<?> bizEntityModel) {

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

            if (null != propertySort && propertySort.isSorted())
                propertySort.forEach(order -> {// check sort variable must containing in variable condition.
                    String property = order.getProperty();
                    Field field = sortFields.get(property);
                    Assert.notNull(field, "sorted property must as a query condition: " + property);
                    baseQuery.addOrderBy(order.isAscending() ? field.asc() : field.desc());
                });
        }

        private static Field dslNameToField(String... qualifiedNames) {
            return field(name(qualifiedNames));
        }

        private static Table dslNameToTable(String... qualifiedNames) {
            return table(name(qualifiedNames));
        }
    }

}
