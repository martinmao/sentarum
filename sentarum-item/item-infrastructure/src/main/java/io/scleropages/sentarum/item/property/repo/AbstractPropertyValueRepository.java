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
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractPropertyValueRepository<E extends AbstractPropertyValueEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E> {


    default void batchSave(Iterable<E> entities) {
        List<R> recordsToSave = Lists.newArrayList();
        entities.forEach(entity -> {
            R record = dslNewRecord();
            record.set(dslField(AbstractPropertyValueEntity.BIZ_TYPE_COLUMN), entity.getBizType());
            record.set(dslField(AbstractPropertyValueEntity.BIZ_ID_COLUMN), entity.getBizId());
            record.set(dslField(AbstractPropertyValueEntity.NAME_COLUMN), entity.getName());
            record.set(dslField(AbstractPropertyValueEntity.PROPERTY_META_ID_COLUMN), entity.getPropertyMetaId());
            record.set(dslField(AbstractPropertyValueEntity.BOOL_VALUE_COLUMN), entity.getBooleanValue());
            record.set(dslField(AbstractPropertyValueEntity.DECIMAL_VALUE_COLUMN), entity.getDecimalValue());
            record.set(dslField(AbstractPropertyValueEntity.INT_VALUE_COLUMN), entity.getIntValue());
            record.set(dslField(AbstractPropertyValueEntity.LONG_VALUE_COLUMN), entity.getLongValue());
            record.set(dslField(AbstractPropertyValueEntity.TEXT_VALUE_COLUMN), entity.getTextValue());
            if (null != entity.getDateValue()) {
                record.set(dslField(AbstractPropertyValueEntity.DATE_VALUE_COLUMN), new Timestamp(entity.getDateValue().getTime()));
            }
            recordsToSave.add(record);
        });
        dslContext().batchInsert((Collection<? extends TableRecord<?>>) recordsToSave).execute();
    }
}
