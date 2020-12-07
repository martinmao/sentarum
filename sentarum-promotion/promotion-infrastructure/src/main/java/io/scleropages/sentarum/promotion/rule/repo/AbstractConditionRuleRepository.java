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
package io.scleropages.sentarum.promotion.rule.repo;

import io.scleropages.sentarum.promotion.rule.entity.AbstractConditionRuleEntity;
import org.jooq.Record;
import org.jooq.Table;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractConditionRuleRepository<E extends AbstractConditionRuleEntity, T extends Table, R extends Record> extends AbstractRuleRepository<E, T, R> {

    @Cacheable(key = "#root.target+'-'+#activityId")
    List<E> findAllByActivity_Id(Long activityId);

    Integer countByParentCondition(Long parentCondition);

    Boolean existsByActivity_IdAndParentConditionIsNull(Long activityId);
}
