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
package io.scleropages.sentarum.promotion.rule.condition.repo;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.jooq.tables.PromConditionBase;
import io.scleropages.sentarum.jooq.tables.records.PromConditionBaseRecord;
import io.scleropages.sentarum.promotion.rule.entity.condition.BaseConditionRuleEntity;
import io.scleropages.sentarum.promotion.rule.repo.AbstractConditionRuleRepository;
import org.jooq.Field;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface BaseConditionRuleRepository extends AbstractConditionRuleRepository<BaseConditionRuleEntity, PromConditionBase, PromConditionBaseRecord> {


    @Override
    @Cacheable
    default List<BaseConditionRuleEntity> findAllByActivity_Id(Long activityId) {
        PromConditionBase promConditionBase = dslTable();
        List<BaseConditionRuleEntity> entities = Lists.newArrayList();
        dslContext().selectFrom(promConditionBase).where(promConditionBase.ACTIVITY_ID.eq(activityId)).fetch().forEach(r -> {
            BaseConditionRuleEntity baseConditionRuleEntity = new BaseConditionRuleEntity();
            dslRecordInto(r, baseConditionRuleEntity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {

                }
            });
            entities.add(baseConditionRuleEntity);
        });
        return entities;
    }

    default Optional<BaseConditionRuleEntity> getById(Long id) {
        Optional<PromConditionBaseRecord> promConditionBaseRecord = dslContext().selectFrom(dslTable()).where(dslTable().ID.eq(id)).fetchOptional();
        if (!promConditionBaseRecord.isPresent())
            return Optional.empty();
        BaseConditionRuleEntity entity = new BaseConditionRuleEntity();
        dslRecordInto(promConditionBaseRecord.get(), entity);
        return Optional.of(entity);
    }

    @Override
    default Integer countByParentCondition(Long parentCondition) {
        PromConditionBase promConditionBase = dslTable();
        return dslContext().selectCount().from(promConditionBase).where(promConditionBase.PARENT_CONDITION_ID.eq(parentCondition)).fetchOneInto(Integer.class);
    }

    @Override
    default Boolean existsByActivity_IdAndParentConditionIsNull(Long activityId) {
        PromConditionBase promConditionBase = dslTable();
        return null != dslContext().select(promConditionBase.ID)
                .from(promConditionBase)
                .where(promConditionBase.ACTIVITY_ID.eq(activityId).and(promConditionBase.PARENT_CONDITION_ID.isNull()))
                .fetchOne();
    }

}
