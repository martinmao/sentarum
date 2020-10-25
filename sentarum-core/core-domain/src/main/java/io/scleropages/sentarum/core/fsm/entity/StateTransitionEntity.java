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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from : {@link io.scleropages.sentarum.core.fsm.model.impl.StateTransitionModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "fsm_transition")
@SequenceGenerator(name = "transition_id", sequenceName = "seq_transition", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class StateTransitionEntity extends IdEntity {

    private StateMachineDefinitionEntity stateMachineDefinition;
    private StateEntity from;
    private StateEntity to;
    private EventDefinitionEntity event;
    private InvocationConfigEntity evaluatorConfig;
    private InvocationConfigEntity actionConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fsm_def_id", nullable = false)
    public StateMachineDefinitionEntity getStateMachineDefinition() {
        return stateMachineDefinition;
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
    @JoinColumn(name = "event_def_id", nullable = false)
    public EventDefinitionEntity getEvent() {
        return event;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eval_invocation_conf_id")
    public InvocationConfigEntity getEvaluatorConfig() {
        return evaluatorConfig;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_invocation_conf_id")
    public InvocationConfigEntity getActionConfig() {
        return actionConfig;
    }


    public void setStateMachineDefinition(StateMachineDefinitionEntity stateMachineDefinition) {
        this.stateMachineDefinition = stateMachineDefinition;
    }

    public void setFrom(StateEntity from) {
        this.from = from;
    }

    public void setTo(StateEntity to) {
        this.to = to;
    }

    public void setEvent(EventDefinitionEntity event) {
        this.event = event;
    }

    public void setEvaluatorConfig(InvocationConfigEntity evaluatorConfig) {
        this.evaluatorConfig = evaluatorConfig;
    }

    public void setActionConfig(InvocationConfigEntity actionConfig) {
        this.actionConfig = actionConfig;
    }
}
