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

import io.scleropages.sentarum.core.fsm.entity.StateMachineDefinitionEntity;
import io.scleropages.sentarum.jooq.tables.FsmDef;
import io.scleropages.sentarum.jooq.tables.records.FsmDefRecord;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;

import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineDefinitionRepository extends GenericRepository<StateMachineDefinitionEntity, Long>, JooqRepository<FsmDef, FsmDefRecord, StateMachineDefinitionEntity> {

    default Optional<StateMachineDefinitionEntity> getById(Long id, boolean fetchInvocationConfig) {
        return get((root, query, builder) -> {
            if (fetchInvocationConfig) {
                Fetch<Object, Object> initialStateFetch = root.fetch("initialState", JoinType.LEFT);
                initialStateFetch.fetch("enteredActionConfig", JoinType.LEFT);
                initialStateFetch.fetch("exitActionConfig", JoinType.LEFT);
            }
            return builder.equal(root.get("id"), id);
        });
    }
}
