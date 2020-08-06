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
package io.scleropages.sentarum.item.category.repo;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.category.entity.CategoryPropertyEntity;
import io.scleropages.sentarum.jooq.tables.ClProperty;
import io.scleropages.sentarum.jooq.tables.records.ClPropertyRecord;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.jooq.Condition;
import org.jooq.Field;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;

import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.Objects;

import static io.scleropages.sentarum.jooq.Tables.PT_PROPERTY_META;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CategoryPropertyRepository extends GenericRepository<CategoryPropertyEntity, Long>, JooqRepository<ClProperty, ClPropertyRecord, CategoryPropertyEntity> {

    /**
     * return true if given category already bind to property metadata.
     *
     * @param categoryId
     * @param propertyMetaId
     * @return
     */
    boolean existsByCategory_IdAndPropertyMetadata_Id(Long categoryId, Long propertyMetaId);


    /**
     * find all category properties by category id and biz type.
     *
     * @param categoryId
     * @param bizTypes
     * @return
     */
    default List<CategoryPropertyEntity> findAllByCategory_IdAndCategoryPropertyBizTypeIn(Long categoryId, Integer... bizTypes) {
        ClProperty clProperty = dslTable();
        List<Condition> conditions = Lists.newArrayList();
        conditions.add(clProperty.STD_CATEGORY_ID.eq(categoryId));
        if (ArrayUtils.isNotEmpty(bizTypes))
            conditions.add(clProperty.BIZ_TYPE.in(bizTypes));

        List<Field> selectFields = Lists.newArrayList(clProperty.fields());
        CollectionUtils.addAll(selectFields, PT_PROPERTY_META.fields());
        conditions.add(clProperty.PROPERTY_META_ID.eq(PT_PROPERTY_META.ID));

        List<CategoryPropertyEntity> entities = Lists.newArrayList();
        dslContext().select(selectFields).from(clProperty, PT_PROPERTY_META).where(conditions).forEach(record -> {
            CategoryPropertyEntity entity = new CategoryPropertyEntity();

            dslRecordInto(record, entity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {
                    if (Objects.equals(field, PT_PROPERTY_META.VALUES_SOURCE_ID))
                        return;
                    applyReferenceIdToTargetEntityInternal(targetEntity, refAttribute, field, fieldValue);
                }
            });

            entities.add(entity);
        });
        return entities;
    }

    @Override
    default Object dslGetEntityBasicAttributeValue(Field field, Object jooqFieldValue) {
        if (Objects.equals(field, dslTable().ORDER_)) {
            return Float.valueOf(jooqFieldValue.toString());
        }
        return jooqFieldValue;
    }
}
