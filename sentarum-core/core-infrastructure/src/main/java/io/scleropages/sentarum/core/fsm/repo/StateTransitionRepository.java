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
package io.scleropages.sentarum.core.fsm.repo;

import io.scleropages.sentarum.core.fsm.entity.StateTransitionEntity;
import io.scleropages.sentarum.jooq.tables.FsmTransition;
import io.scleropages.sentarum.jooq.tables.records.FsmTransitionRecord;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateTransitionRepository extends GenericRepository<StateTransitionEntity, Long>, JooqRepository<FsmTransition, FsmTransitionRecord, StateTransitionEntity> {


    @Cacheable
    default List<StateTransitionEntity> findAllByStateMachineDefinition_Id(Long id, boolean fetchInvocationConfig) {
        List<StateTransitionEntity> result = findAll((root, query, builder) -> {
            Fetch<Object, Object> fromFetch = root.fetch("from", JoinType.LEFT);
            Fetch<Object, Object> toFetch = root.fetch("to", JoinType.LEFT);
            root.fetch("event", JoinType.LEFT);
            if (fetchInvocationConfig) {
                fromFetch.fetch("enteredActionConfig", JoinType.LEFT);
                fromFetch.fetch("exitActionConfig", JoinType.LEFT);
                toFetch.fetch("enteredActionConfig", JoinType.LEFT);
                toFetch.fetch("exitActionConfig", JoinType.LEFT);
                root.fetch("evaluatorConfig", JoinType.LEFT);
                root.fetch("actionConfig", JoinType.LEFT);
            }
            return builder.equal(root.get("stateMachineDefinition").get("id"), id);
        });
        return result;
    }
}
