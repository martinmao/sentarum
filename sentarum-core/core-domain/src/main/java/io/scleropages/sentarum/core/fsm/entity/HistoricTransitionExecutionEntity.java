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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * referenced from: {@link io.scleropages.sentarum.core.fsm.model.impl.HistoricTransitionExecutionModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "fsm_execution_transition")
@SequenceGenerator(name = "fsm_execution_transition_id", sequenceName = "seq_fsm_execution_transition", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class HistoricTransitionExecutionEntity extends IdEntity {


    private StateMachineDefinitionEntity stateMachineDefinition;
    private StateMachineExecutionEntity stateMachineExecution;
    private StateEntity from;
    private StateEntity to;
    private EventEntity event;
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fsm_def_id", nullable = false)
    public StateMachineDefinitionEntity getStateMachineDefinition() {
        return stateMachineDefinition;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fsm_execution_id", nullable = false)
    public StateMachineExecutionEntity getStateMachineExecution() {
        return stateMachineExecution;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_from_id", nullable = false)
    public StateEntity getFrom() {
        return from;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_to_id", nullable = false)
    public StateEntity getTo() {
        return to;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    public EventEntity getEvent() {
        return event;
    }

    @Column(name = "time_", nullable = false)
    public Date getTime() {
        return time;
    }

    public void setStateMachineDefinition(StateMachineDefinitionEntity stateMachineDefinition) {
        this.stateMachineDefinition = stateMachineDefinition;
    }

    public void setStateMachineExecution(StateMachineExecutionEntity stateMachineExecution) {
        this.stateMachineExecution = stateMachineExecution;
    }

    public void setFrom(StateEntity from) {
        this.from = from;
    }

    public void setTo(StateEntity to) {
        this.to = to;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
