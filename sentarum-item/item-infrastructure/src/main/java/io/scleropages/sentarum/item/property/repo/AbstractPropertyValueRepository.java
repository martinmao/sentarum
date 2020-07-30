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
import io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import static io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity.*;
import static io.scleropages.sentarum.jooq.Tables.PT_PROPERTY_META;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractPropertyValueRepository<E extends AbstractPropertyValueEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E> {


    default void batchSave(Iterable<E> entities) {
        List<R> recordsToSave = Lists.newArrayList();
        entities.forEach(entity -> {
            R record = dslNewRecord();
            record.set(dslField(BIZ_TYPE_COLUMN), entity.getBizType());
            record.set(dslField(BIZ_ID_COLUMN), entity.getBizId());
            record.set(dslField(NAME_COLUMN), entity.getName());
            record.set(dslField(PROPERTY_META_ID_COLUMN), entity.getPropertyMetaId());
            applyValueToFieldColumn(entity, record);
            if (null != entity.getDateValue()) {
                record.set(dslField(DATE_VALUE_COLUMN), new Timestamp(entity.getDateValue().getTime()));
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
        record.set(dslField(BOOL_VALUE_COLUMN), entity.getBooleanValue());
        record.set(dslField(DECIMAL_VALUE_COLUMN), entity.getDecimalValue());
        record.set(dslField(INT_VALUE_COLUMN), entity.getIntValue());
        record.set(dslField(LONG_VALUE_COLUMN), entity.getLongValue());
        record.set(dslField(TEXT_VALUE_COLUMN), entity.getTextValue());
    }

    /**
     * 子类必须实现，返回明确的entity类型
     *
     * @return
     */
    abstract E newPropertyValueEntity();

}
