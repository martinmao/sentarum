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
package io.scleropages.sentarum.promotion.rule.calculator.repo;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.jooq.tables.PromCalcBase;
import io.scleropages.sentarum.jooq.tables.records.PromCalcBaseRecord;
import io.scleropages.sentarum.promotion.rule.entity.calculator.BaseCalculatorRuleEntity;
import io.scleropages.sentarum.promotion.rule.repo.AbstractCalculatorRuleRepository;
import org.jooq.Field;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.metamodel.Attribute;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface BaseCalculatorRuleRepository extends AbstractCalculatorRuleRepository<BaseCalculatorRuleEntity, PromCalcBase, PromCalcBaseRecord> {

    /**
     * 覆写该方法避免全子表join.默认情况下所有规则设置在基表扩展属性字段中保存.
     *
     * @param activityId
     * @return
     */
//    @Override
    @Cacheable
    default List<BaseCalculatorRuleEntity> readAllByActivity_Id(Long activityId) {
        PromCalcBase table = dslTable();
        List<BaseCalculatorRuleEntity> entities = Lists.newArrayList();
        dslContext().selectFrom(table).where(table.ACTIVITY_ID.eq(activityId)).fetch().forEach(r -> {
            BaseCalculatorRuleEntity entity = new BaseCalculatorRuleEntity();
            dslRecordInto(r, entity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {

                }
            });
            entities.add(entity);
        });
        return entities;
    }
}
