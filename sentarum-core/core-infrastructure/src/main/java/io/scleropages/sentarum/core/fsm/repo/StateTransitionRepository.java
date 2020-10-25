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

import com.google.common.collect.Lists;
import io.scleropages.sentarum.core.fsm.entity.InvocationConfigEntity;
import io.scleropages.sentarum.core.fsm.entity.StateTransitionEntity;
import io.scleropages.sentarum.jooq.tables.FsmInvocationConf;
import io.scleropages.sentarum.jooq.tables.FsmTransition;
import io.scleropages.sentarum.jooq.tables.records.FsmTransitionRecord;
import org.jooq.Field;
import org.jooq.Record;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

import static io.scleropages.sentarum.jooq.Tables.FSM_INVOCATION_CONF;
import static io.scleropages.sentarum.jooq.Tables.FSM_TRANSITION;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateTransitionRepository extends GenericRepository<StateTransitionEntity, Long>, JooqRepository<FsmTransition, FsmTransitionRecord, StateTransitionEntity> {


    @Cacheable(key = "#id")
    default List<StateTransitionEntity> findAllByStateMachineDefinition_Id(Long id, StateMachineDefinitionRepository stateMachineDefinitionRepository, StateRepository stateRepository, EventDefinitionRepository eventDefinitionRepository/*, boolean fetchInvocationConfig*/) {
        FsmTransition fsmTransition = dslTable();
        List<Field> fields = Lists.newArrayList(fsmTransition.fields());
        FsmInvocationConf evaluatorConfig = FSM_INVOCATION_CONF.as("FSM_INVOCATION_CONF_0");
        for (Field<?> field : evaluatorConfig.fields()) {
            fields.add(field);
        }
        FsmInvocationConf actionConfig = FSM_INVOCATION_CONF.as("FSM_INVOCATION_CONF_1");
        for (Field<?> field : actionConfig.fields()) {
            fields.add(field);
        }

        List<StateTransitionEntity> stateTransitions = Lists.newArrayList();
        dslContext().select(fields).from(FSM_TRANSITION)
                .leftJoin(evaluatorConfig).on(FSM_TRANSITION.EVAL_INVOCATION_CONF_ID.eq(evaluatorConfig.ID))
                .leftJoin(actionConfig).on(FSM_TRANSITION.EVAL_INVOCATION_CONF_ID.eq(actionConfig.ID))
                .where(FSM_TRANSITION.FSM_DEF_ID.eq(id)).fetch().forEach(record -> {

            StateTransitionEntity stateTransitionEntity = new StateTransitionEntity();
            stateTransitionEntity.setId(record.getValue(fsmTransition.ID));
            stateTransitionEntity.setStateMachineDefinition(stateMachineDefinitionRepository.getById(record.getValue(fsmTransition.FSM_DEF_ID)).orElseThrow(() -> new IllegalArgumentException("no state machine definition found: " + record.getValue(fsmTransition.FSM_DEF_ID))));
            stateTransitionEntity.setEvent(eventDefinitionRepository.getById(record.getValue(fsmTransition.EVENT_DEF_ID)).orElseThrow(() -> new IllegalArgumentException("no event definition found: " + record.getValue(fsmTransition.EVENT_DEF_ID))));
            stateTransitionEntity.setFrom(stateRepository.getById(record.getValue(fsmTransition.STATE_FROM_ID)));
            stateTransitionEntity.setTo(stateRepository.getById(record.getValue(fsmTransition.STATE_TO_ID)));
            stateTransitionEntity.setEvaluatorConfig(createInvocationConfigEntity(evaluatorConfig, record));
            stateTransitionEntity.setEvaluatorConfig(createInvocationConfigEntity(actionConfig, record));
            stateTransitions.add(stateTransitionEntity);
        });
        return stateTransitions;
    }

    default InvocationConfigEntity createInvocationConfigEntity(FsmInvocationConf table, Record record) {
        Long id = record.getValue(table.ID);
        if (null == id)
            return null;
        InvocationConfigEntity configEntity = new InvocationConfigEntity();
        configEntity.setId(id);
        configEntity.setConfigPayload(record.getValue(table.CONF_PAYLOAD));
        configEntity.setInvocationImplementation(record.getValue(table.INVOCATION_IMPL));
        configEntity.setConfigImplementation(record.getValue(table.CONF_IMPL));
        return configEntity;
    }
}
