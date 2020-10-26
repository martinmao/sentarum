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
package io.scleropages.sentarum.core.fsm.mgmt;

import io.scleropages.sentarum.core.fsm.StateMachine;
import io.scleropages.sentarum.core.fsm.entity.EventDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateEntity;
import io.scleropages.sentarum.core.fsm.entity.StateMachineDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateTransitionEntity;
import io.scleropages.sentarum.core.fsm.entity.mapper.EventDefinitionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateMachineDefinitionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateTransitionEntityMapper;
import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.HistoricTransitionExecution;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;
import io.scleropages.sentarum.core.fsm.model.impl.EventDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.EventModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateTransitionModel;
import io.scleropages.sentarum.core.fsm.provider.StateMachineFactory;
import io.scleropages.sentarum.core.fsm.repo.EventDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.InvocationConfigRepository;
import io.scleropages.sentarum.core.fsm.repo.StateMachineDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateRepository;
import io.scleropages.sentarum.core.fsm.repo.StateTransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * generic state machine manager implementation.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class StateMachineManagerImpl implements StateMachineManager {

    private StateMachineDefinitionEntityMapper stateMachineDefinitionEntityMapper;
    private StateEntityMapper stateEntityMapper;
    private EventDefinitionEntityMapper eventDefinitionEntityMapper;
    private StateTransitionEntityMapper stateTransitionEntityMapper;

    private StateMachineDefinitionRepository stateMachineDefinitionRepository;
    private EventDefinitionRepository eventDefinitionRepository;
    private StateRepository stateRepository;
    private StateTransitionRepository transitionRepository;
    private InvocationConfigRepository invocationConfigRepository;

    private StateMachineFactory stateMachineFactory;

    @Override
    @Transactional
    public void createState(StateModel state) {
        StateEntity stateEntity = stateEntityMapper.mapForSave(state);
        if (null != stateEntity.getEnteredActionConfig())
            invocationConfigRepository.save(stateEntity.getEnteredActionConfig());
        if (null != stateEntity.getExitActionConfig())
            invocationConfigRepository.save(stateEntity.getExitActionConfig());
        stateRepository.save(stateEntity);
    }

    @Override
    @Transactional
    public void createEventDefinition(EventDefinitionModel eventDefinition) {
        EventDefinitionEntity eventDefinitionEntity = eventDefinitionEntityMapper.mapForSave(eventDefinition);
        eventDefinitionRepository.save(eventDefinitionEntity);
    }

    @Override
    @Transactional
    public void createStateMachineDefinition(StateMachineDefinitionModel stateMachineDefinition, Long initialState) {
        StateEntity stateEntity = stateRepository.get(initialState).orElseThrow(() -> new IllegalArgumentException("no initialState found: " + initialState));
        StateMachineDefinitionEntity stateMachineDefinitionEntity = stateMachineDefinitionEntityMapper.mapForSave(stateMachineDefinition);
        stateMachineDefinitionEntity.setInitialState(stateEntity);
        stateMachineDefinitionRepository.save(stateMachineDefinitionEntity);
    }

    @Override
    @Transactional
    public void createStateTransition(StateTransitionModel stateTransition, Long stateMachineDefinitionId, Long fromStateId, Long toStateId, Long eventDefinitionId) {
        StateMachineDefinitionEntity stateMachineDefinitionEntity = stateMachineDefinitionRepository.get(stateMachineDefinitionId).orElseThrow(() -> new IllegalArgumentException("no state machine definition found: " + stateMachineDefinitionId));
        StateEntity fromStateEntity = stateRepository.get(fromStateId).orElseThrow(() -> new IllegalArgumentException("no state found: " + fromStateId));
        StateEntity toStateEntity = stateRepository.get(toStateId).orElseThrow(() -> new IllegalArgumentException("no state found: " + toStateId));
        EventDefinitionEntity eventDefinitionEntity = eventDefinitionRepository.get(eventDefinitionId).orElseThrow(() -> new IllegalArgumentException("no event definition found: " + eventDefinitionId));
        StateTransitionEntity stateTransitionEntity = stateTransitionEntityMapper.mapForSave(stateTransition);
        if (null != stateTransitionEntity.getEvaluatorConfig()) {
            invocationConfigRepository.save(stateTransitionEntity.getEvaluatorConfig());
        }
        if (null != stateTransitionEntity.getActionConfig()) {
            invocationConfigRepository.save(stateTransitionEntity.getActionConfig());
        }
        stateTransitionEntity.setStateMachineDefinition(stateMachineDefinitionEntity);
        stateTransitionEntity.setFrom(fromStateEntity);
        stateTransitionEntity.setTo(toStateEntity);
        stateTransitionEntity.setEvent(eventDefinitionEntity);
        transitionRepository.save(stateTransitionEntity);
    }

    @Override
    @Transactional
    public StateMachine createStateMachine(Long definitionId, Integer bizType, Long bizId, Map<String, Object> contextAttributes) {
        return stateMachineFactory.createStateMachine(definitionId, bizType, bizId, contextAttributes);
    }

    @Override
    @Transactional(readOnly = true)
    public Event createEvent(String name, Map<String, Object> body) {
        Assert.hasText(name, "event name must not empty.");
        EventDefinition eventDefinition = getEventDefinitionByName(name);
        return new EventModel(eventDefinition, body);
    }

    @Override
    public StateMachine getStateMachine(Long id) {
        return null;
    }

    @Override
    public StateMachineExecution getStateMachineExecution(Long id) {
        return null;
    }

    @Override
    public List<HistoricTransitionExecution> getAllHistoricTransitionExecutions(Long stateMachineExecutionId) {
        return null;
    }

    @Override
    public StateMachineDefinition getStateMachineDefinition(Long id) {
        return stateMachineDefinitionEntityMapper.mapForRead(stateMachineDefinitionRepository.getById(id).orElseThrow(() -> new IllegalArgumentException("no state machine definition found by id: " + id)));
    }

    @Override
    public StateMachineDefinition getStateMachineDefinitionByName(String name) {
        return getStateMachineDefinition(stateMachineDefinitionRepository.getIdByName(name).orElseThrow(() -> new IllegalArgumentException("no state machine definition found by name: " + name)));
    }

    @Override
    public State getState(Long id) {
        return stateEntityMapper.mapForRead(stateRepository.getById(id));
    }

    @Override
    public State getStateByValue(Integer value) {
        return getState(stateRepository.getIdByValue(value).orElseThrow(() -> new IllegalArgumentException("no state found by value: " + value)));
    }

    @Override
    public State getStateByName(String name) {
        return getState(stateRepository.getIdByName(name).orElseThrow(() -> new IllegalArgumentException("no state found by name: " + name)));
    }

    @Override
    public EventDefinition getEventDefinition(Long id) {
        return eventDefinitionEntityMapper.mapForRead(eventDefinitionRepository.getById(id).orElseThrow(() -> new IllegalArgumentException("no event definition found by id: " + id)));
    }

    @Override
    public EventDefinition getEventDefinitionByName(String name) {
        return getEventDefinition(eventDefinitionRepository.getIdByName(name).orElseThrow(() -> new IllegalArgumentException("no event definition found by name: " + name)));
    }


    @Autowired
    public void setStateMachineDefinitionEntityMapper(StateMachineDefinitionEntityMapper stateMachineDefinitionEntityMapper) {
        this.stateMachineDefinitionEntityMapper = stateMachineDefinitionEntityMapper;
    }

    @Autowired
    public void setStateEntityMapper(StateEntityMapper stateEntityMapper) {
        this.stateEntityMapper = stateEntityMapper;
    }

    @Autowired
    public void setEventDefinitionEntityMapper(EventDefinitionEntityMapper eventDefinitionEntityMapper) {
        this.eventDefinitionEntityMapper = eventDefinitionEntityMapper;
    }


    @Autowired
    public void setStateTransitionEntityMapper(StateTransitionEntityMapper stateTransitionEntityMapper) {
        this.stateTransitionEntityMapper = stateTransitionEntityMapper;
    }

    @Autowired
    public void setStateMachineDefinitionRepository(StateMachineDefinitionRepository stateMachineDefinitionRepository) {
        this.stateMachineDefinitionRepository = stateMachineDefinitionRepository;
    }

    @Autowired
    public void setEventDefinitionRepository(EventDefinitionRepository eventDefinitionRepository) {
        this.eventDefinitionRepository = eventDefinitionRepository;
    }

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Autowired
    public void setTransitionRepository(StateTransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    @Autowired
    public void setInvocationConfigRepository(InvocationConfigRepository invocationConfigRepository) {
        this.invocationConfigRepository = invocationConfigRepository;
    }

    @Autowired
    public void setStateMachineFactory(StateMachineFactory stateMachineFactory) {
        this.stateMachineFactory = stateMachineFactory;
    }

}
