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
package io.scleropages.sentarum.core.fsm.provider;

import io.scleropages.sentarum.core.fsm.Action;
import io.scleropages.sentarum.core.fsm.StateMachine;
import io.scleropages.sentarum.core.fsm.TransitionEvaluator;
import io.scleropages.sentarum.core.fsm.entity.StateMachineDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateTransitionEntity;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateMachineDefinitionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateTransitionEntityMapper;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateTransition;
import io.scleropages.sentarum.core.fsm.repo.EventDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateMachineDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateRepository;
import io.scleropages.sentarum.core.fsm.repo.StateTransitionRepository;
import org.scleropages.crud.ModelMapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * abstract implementation for state machine factory.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractStateMachineFactory implements StateMachineFactory {


    private StateMachineDefinitionRepository definitionRepository;
    private StateTransitionRepository transitionRepository;
    private EventDefinitionRepository eventRepository;
    private StateRepository stateRepository;

    private InvocationContainer invocationContainer;


    @Override
    @Transactional(readOnly = true)
    public StateMachine createStateMachine(Long definitionId) {
        StateMachineDefinitionEntity stateMachineDefinitionEntity = definitionRepository.get(definitionId).orElseThrow(() -> new IllegalArgumentException("no state machine definition id found: " + definitionId));
        List<StateTransitionEntity> stateTransitionEntities = transitionRepository.findAllByStateMachineDefinition_Id(definitionId, true);
        return createStateMachineInternal(map(stateMachineDefinitionEntity), map(stateTransitionEntities));
    }

    protected abstract StateMachine createStateMachineInternal(StateMachineDefinition stateMachineDefinition, List<StateTransition> stateTransitions);


    protected StateMachineDefinition map(StateMachineDefinitionEntity stateMachineDefinitionEntity) {
        return (StateMachineDefinition) ModelMapperRepository.getRequiredModelMapper(StateMachineDefinitionEntityMapper.class).mapForRead(stateMachineDefinitionEntity);
    }

    protected List<StateTransition> map(List<StateTransitionEntity> stateTransitionEntities) {
        return (List<StateTransition>) ModelMapperRepository.getRequiredModelMapper(StateTransitionEntityMapper.class).mapForReads(stateTransitionEntities);
    }

    protected Action getAction(InvocationConfig invocationConfig) {
        return invocationContainer.getAction(invocationConfig);
    }

    protected TransitionEvaluator getTransitionEvaluator(InvocationConfig invocationConfig) {
        return invocationContainer.getTransitionEvaluator(invocationConfig);
    }

    @Override
    public StateMachine getStateMachine(Long machineId) {
        return null;
    }

    @Autowired
    public void setDefinitionRepository(StateMachineDefinitionRepository definitionRepository) {
        this.definitionRepository = definitionRepository;
    }

    @Autowired
    public void setTransitionRepository(StateTransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    @Autowired
    public void setEventRepository(EventDefinitionRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Autowired
    public void setInvocationContainer(InvocationContainer invocationContainer) {
        this.invocationContainer = invocationContainer;
    }
}
