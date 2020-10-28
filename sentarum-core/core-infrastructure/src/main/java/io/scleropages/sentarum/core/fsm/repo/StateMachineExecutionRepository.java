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

import io.scleropages.sentarum.core.fsm.entity.StateMachineExecutionEntity;
import io.scleropages.sentarum.jooq.tables.FsmExecution;
import io.scleropages.sentarum.jooq.tables.records.FsmExecutionRecord;
import org.jooq.Field;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.metamodel.Attribute;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineExecutionRepository extends GenericRepository<StateMachineExecutionEntity, Long>, JooqRepository<FsmExecution, FsmExecutionRecord, StateMachineExecutionEntity> {


    @Cacheable
    default Long getIdByBizTypeAndBizId(Integer bizType, Long bizId) {
        FsmExecution fsmExecution = dslTable();
        return dslContext().select(fsmExecution.ID).from(fsmExecution)
                .where(fsmExecution.BIZ_TYPE.eq(bizType))
                .and(fsmExecution.BIZ_ID.eq(bizId))
                .fetchOptional(fsmExecution.ID)
                .orElseThrow(() ->
                        new IllegalArgumentException("no state machine execution found by biz type: " + bizType + " and biz id: " + bizId));
    }

    default void saveContextPayload(Long id, String contextPayload) {
        FsmExecution fsmExecution = dslTable();
        dslContext().update(fsmExecution).set(fsmExecution.CONTEXT_PAYLOAD, contextPayload).where(fsmExecution.ID.eq(id)).execute();
    }

    default StateMachineExecutionEntity getById(Long id, StateMachineDefinitionRepository definitionRepository, StateRepository stateRepository) {
        FsmExecution fsmExecution = dslTable();
        FsmExecutionRecord fsmExecutionRecord = dslContext().selectFrom(fsmExecution).where(fsmExecution.ID.eq(id)).fetchOptional().orElseThrow(() -> new IllegalArgumentException("no state machine execution found: " + id));
        StateMachineExecutionEntity executionEntity = new StateMachineExecutionEntity();
        dslRecordInto(fsmExecutionRecord, executionEntity, new ReferenceEntityAssembler() {
            @Override
            public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {
                //any referenced entity ignored.
            }
        });
        Long fsmDefId = fsmExecutionRecord.getFsmDefId();
        executionEntity.setStateMachineDefinition(definitionRepository.getById(fsmDefId).orElseThrow(() -> new IllegalArgumentException("no state machine definition found: " + fsmDefId)));
        Long currentStateId = fsmExecutionRecord.getCurrentStateId();
        executionEntity.setCurrentState(stateRepository.getById(currentStateId));
        return executionEntity;
    }

    default void updateStateMachineState(Long id, Long stateId) {
        FsmExecution fsmExecution = dslTable();
        dslContext().update(fsmExecution).set(fsmExecution.CURRENT_STATE_ID, stateId).where(fsmExecution.ID.eq(id)).execute();
    }

    default void updateStateMachineExecutionState(Long id, Integer executionState) {
        FsmExecution fsmExecution = dslTable();
        dslContext().update(fsmExecution).set(fsmExecution.EXECUTION_STATE, executionState).where(fsmExecution.ID.eq(id)).execute();
    }
}
