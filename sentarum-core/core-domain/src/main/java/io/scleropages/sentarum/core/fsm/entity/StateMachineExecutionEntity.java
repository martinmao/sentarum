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
package io.scleropages.sentarum.core.fsm.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * referenced from: {@link io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "fsm_execution",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"biz_type", "biz_id"})},
        indexes = {
                @Index(columnList = "fsm_def_id,execution_state,current_state_id")
        })
@SequenceGenerator(name = "fsm_execution_id", sequenceName = "seq_fsm_execution", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class StateMachineExecutionEntity extends IdEntity {

    private StateMachineDefinitionEntity stateMachineDefinition;
    private StateEntity currentState;
    private Long bizId;
    private Integer bizType;
    private Integer executionState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fsm_def_id", nullable = false)
    public StateMachineDefinitionEntity getStateMachineDefinition() {
        return stateMachineDefinition;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_state_id", nullable = false)
    public StateEntity getCurrentState() {
        return currentState;
    }

    @Column(name = "biz_id", nullable = false)
    public Long getBizId() {
        return bizId;
    }

    @Column(name = "biz_type", nullable = false)
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = "execution_state", nullable = false)
    public Integer getExecutionState() {
        return executionState;
    }

    public void setStateMachineDefinition(StateMachineDefinitionEntity stateMachineDefinition) {
        this.stateMachineDefinition = stateMachineDefinition;
    }

    public void setCurrentState(StateEntity currentState) {
        this.currentState = currentState;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setExecutionState(Integer executionState) {
        this.executionState = executionState;
    }
}
