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

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.scleropages.sentarum.core.fsm.Action;
import io.scleropages.sentarum.core.fsm.StateMachine;
import io.scleropages.sentarum.core.fsm.TransitionEvaluator;
import io.scleropages.sentarum.core.fsm.entity.EventDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.EventEntity;
import io.scleropages.sentarum.core.fsm.entity.HistoricTransitionExecutionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateMachineDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateMachineExecutionEntity;
import io.scleropages.sentarum.core.fsm.entity.StateTransitionEntity;
import io.scleropages.sentarum.core.fsm.entity.mapper.EventDefinitionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.EventEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateMachineDefinitionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateMachineExecutionEntityMapper;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateTransitionEntityMapper;
import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution.ExecutionState;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;
import io.scleropages.sentarum.core.fsm.model.StateTransition;
import io.scleropages.sentarum.core.fsm.model.impl.EventModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionContextModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateTransitionModel;
import io.scleropages.sentarum.core.fsm.repo.EventDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.EventRepository;
import io.scleropages.sentarum.core.fsm.repo.HistoricTransitionExecutionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateMachineDefinitionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateMachineExecutionRepository;
import io.scleropages.sentarum.core.fsm.repo.StateRepository;
import io.scleropages.sentarum.core.fsm.repo.StateTransitionRepository;
import org.apache.commons.collections.MapUtils;
import org.scleropages.core.mapper.JsonMapper2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * abstract implementation for state machine factory.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractStateMachineFactory implements StateMachineFactory, InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    ///~~repositories
    private StateMachineDefinitionRepository definitionRepository;
    private StateTransitionRepository transitionRepository;
    private EventDefinitionRepository eventDefinitionRepository;
    private EventRepository eventRepository;
    private StateMachineExecutionRepository stateMachineExecutionRepository;
    private StateRepository stateRepository;
    private HistoricTransitionExecutionRepository historicTransitionExecutionRepository;

    ///~~ mappers
    private StateMachineDefinitionEntityMapper definitionEntityMapper;
    private StateTransitionEntityMapper transitionEntityMapper;
    private StateMachineExecutionEntityMapper stateMachineExecutionEntityMapper;
    private EventEntityMapper eventEntityMapper;
    private EventDefinitionEntityMapper eventDefinitionEntityMapper;

    ///~~ components
    private InvocationContainer invocationContainer;
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;


    @Override
    public StateMachine createStateMachine(Long definitionId, Integer bizType, Long bizId, Map<String, Object> contextAttributes) {

        Assert.notNull(definitionId, "definitionId is required.");
        Assert.notNull(bizType, "bizType is required.");
        Assert.notNull(bizId, "bizId is required.");

        //read state machine definition and transitions for state machine building.
        StateMachineDefinitionEntity stateMachineDefinitionEntity = definitionRepository.getById(definitionId).orElseThrow(() -> new IllegalArgumentException("no state machine definition id found: " + definitionId));
        List<StateTransitionEntity> stateTransitionEntities = transitionRepository.findAllByStateMachineDefinition_Id(definitionId, definitionRepository, stateRepository, eventDefinitionRepository);
        if (logger.isDebugEnabled()) {
            logger.debug("building state machine from definition: {}", stateMachineDefinitionEntity.getName());
        }
        StateMachineDefinition stateMachineDefinition = map(stateMachineDefinitionEntity);
        ProviderStateMachine providerStateMachine = createStateMachineInternal(stateMachineDefinition, map(stateTransitionEntities));

        DelegatingStateMachine delegatingStateMachine = new DelegatingStateMachine(providerStateMachine, stateMachineDefinition, bizType, bizId);

        if (stateMachineDefinitionEntity.getAutoStartup()) {
            delegatingStateMachine.start(contextAttributes);
        }
        return delegatingStateMachine;
    }


    @Override
    public StateMachine getStateMachine(Long machineId) {
        Assert.notNull(machineId, "machine id must not be null.");
        StateMachineExecutionEntity stateMachineExecutionEntity = getStateMachineExecutionEntity(machineId);
        List<StateTransitionEntity> stateTransitionEntities = transitionRepository.findAllByStateMachineDefinition_Id(stateMachineExecutionEntity.getStateMachineDefinition().getId(), definitionRepository, stateRepository, eventDefinitionRepository);


        StateMachineExecution stateMachineExecution = stateMachineExecutionEntityMapper.mapForRead(stateMachineExecutionEntity);

        StateMachineDefinition stateMachineDefinition = definitionEntityMapper.mapForRead(stateMachineExecutionEntity.getStateMachineDefinition());

        if (logger.isDebugEnabled()) {
            logger.debug("building state machine {} from exists state: {}", stateMachineExecution.id(), stateMachineExecution.currentState().name());
        }
        ProviderStateMachine providerStateMachine = buildStateMachineInternal(stateMachineDefinition, map(stateTransitionEntities), stateMachineExecution);
        return new DelegatingStateMachine(providerStateMachine, (StateMachineExecutionModel) stateMachineExecution, stateMachineExecutionEntity);
    }


    private void sendEventInternal(DelegatingStateMachine stateMachine, Event event, Map<String, Object> contextAttributes) {
        final ExecutionState executionState = stateMachine.stateMachineExecution.executionState();
        if (!executionState.acceptingEvents()) {
            throw new IllegalStateException("not allowed accepting events. execution state is: " + executionState);
        }
        final ProviderStateMachine providerStateMachine = stateMachine.providerStateMachine;
        final StateMachineExecutionModel execution = stateMachine.stateMachineExecution;


        State stateFrom = providerStateMachine.currentState();
        ObservableStateMachineExecutionContext executionContext = (ObservableStateMachineExecutionContext) execution.getExecutionContext();
        executionContext.addAttributes(contextAttributes, true);
        boolean accepted = providerStateMachine.sendEvent(event, executionContext);
        String note = accepted ? null : String.format("statemachine execution %s[%s] with state [%s] reject event [%s]. ", stateMachine.stateMachineDefinition.name(), execution.id(), execution.currentState().name(), event.name());
        EventEntity eventEntity = createEventEntity(event, accepted, note);

        //if events was accepted write back execution state,context and create transition execution...
        if (accepted) {
            State stateTo = providerStateMachine.currentState();
            stateMachineExecutionRepository.updateStateMachineState(execution.id(), stateTo.id());
            saveContextPayloadInternal(execution.id(), executionContext);
            createHistoricTransitionExecution(stateMachine, stateFrom, stateTo, eventEntity);
            State endState = stateMachine.stateMachineDefinition.endState();
            if (null != endState && Objects.equals(endState.id(), stateTo.id())) {
                updateStateMachineExecutionState(execution.id(), ExecutionState.FINISHED, "finished.");
                logger.debug("state machine {}[{}] finished with state: {}", stateMachine.stateMachineDefinition.name(), execution.id(), endState.name());
            }
        } else {
            executionContext.resetChange();
            logger.warn(note);
        }
    }


    private final void createHistoricTransitionExecution(DelegatingStateMachine stateMachine, State
            stateFrom, State stateTo, EventEntity event) {
        HistoricTransitionExecutionEntity entity = new HistoricTransitionExecutionEntity();
        StateMachineDefinitionEntity stateMachineDefinitionEntity = definitionRepository.getById(stateMachine.stateMachineDefinition.id()).orElseThrow(() -> new IllegalArgumentException("no state machine definition found: " + stateMachine.stateMachineDefinition.id()));
        entity.setStateMachineDefinition(stateMachineDefinitionEntity);
        entity.setStateMachineExecution(stateMachine.stateMachineExecutionEntity);
        entity.setFrom(stateRepository.getById(stateFrom.id()));
        entity.setTo(stateRepository.getById(stateTo.id()));
        entity.setEvent(event);
        entity.setTime(new Date());
        historicTransitionExecutionRepository.save(entity);
    }

    private final EventEntity createEventEntity(Event event, boolean accepted, String note) {
        Assert.notNull(event, "event must not be null.");
        EventDefinition eventDefinition = event.eventDefinition();
        Assert.notNull(eventDefinition, "event definition must not be null.");
        Long eventDefinitionId = eventDefinition.id();
        Assert.notNull(eventDefinitionId, "event definition id must not be null.");
        EventDefinitionEntity eventDefinitionEntity = eventDefinitionRepository.getById(eventDefinitionId).orElseThrow(() -> new IllegalArgumentException("no event definition found: " + eventDefinitionId));
        EventEntity eventEntity = eventEntityMapper.mapForSave((EventModel) event);
        eventEntity.setEventDefinition(eventDefinitionEntity);
        eventEntity.populateEventDefinitionInformation();
        eventEntity.setAccepted(accepted);
        eventEntity.setNote(note);
        eventRepository.save(eventEntity);
        if (logger.isDebugEnabled())
            logger.debug("saved state machine event: {}. accepting result: {}.", eventEntity.getName(), eventEntity.getAccepted());
        return eventEntity;
    }


    /**
     * create new state machine execution.
     *
     * @param bizType                  client request business type.
     * @param bizId                    client request business id.
     * @param stateMachineDefinitionId id of state machine definition  to be created.
     * @param contextAttributes        init attributes of context for this execution.
     * @return
     */
    protected StateMachineExecutionEntity createStateMachineExecution(Integer bizType, Long
            bizId, Long stateMachineDefinitionId, Map<String, Object> contextAttributes) {
        StateMachineExecutionEntity executionEntity = new StateMachineExecutionEntity();
        executionEntity.setBizType(bizType);
        executionEntity.setBizId(bizId);
        StateMachineDefinitionEntity stateMachineDefinitionEntity = definitionRepository.getById(stateMachineDefinitionId).orElseThrow(() -> new IllegalArgumentException("no state machine definition found: " + stateMachineDefinitionId));
        executionEntity.setStateMachineDefinition(stateMachineDefinitionEntity);
        executionEntity.setCurrentState(stateMachineDefinitionEntity.getInitialState());
        executionEntity.setExecutionContext(new StateMachineExecutionContextModel(contextAttributes).getContextPayload());
        executionEntity.setExecutionState(ExecutionState.RUNNING.getOrdinal());
        stateMachineExecutionRepository.save(executionEntity);
        if (logger.isDebugEnabled())
            logger.debug("successfully create state machine execution: {} with state: {}.", executionEntity.getId(), executionEntity.getCurrentState().getName());
        return executionEntity;
    }

    /**
     * return a exists state machine execution.
     *
     * @param id id of state machine execution.
     * @return
     */
    protected StateMachineExecutionEntity getStateMachineExecutionEntity(Long id) {
        return stateMachineExecutionRepository.getById(id, definitionRepository, stateRepository);
    }


    /**
     * a default implementation of {@link StateMachine}
     */
    private final class DelegatingStateMachine implements StateMachine {

        private final ProviderStateMachine providerStateMachine;
        private final StateMachineDefinition stateMachineDefinition;
        private final Integer bizType;
        private final Long bizId;
        private StateMachineExecutionModel stateMachineExecution;
        private StateMachineExecutionEntity stateMachineExecutionEntity;
        private final AtomicBoolean started;


        /**
         * this constructor used for first state machine creating call.
         *
         * @param providerStateMachine
         * @param stateMachineDefinition
         * @param bizType
         * @param bizId
         */
        public DelegatingStateMachine(ProviderStateMachine providerStateMachine, StateMachineDefinition stateMachineDefinition, Integer bizType, Long bizId) {
            this.providerStateMachine = providerStateMachine;
            this.stateMachineDefinition = stateMachineDefinition;
            this.bizType = bizType;
            this.bizId = bizId;
            this.started = new AtomicBoolean(false);
        }

        /**
         * this constructor used for exists state machine building call.
         *
         * @param providerStateMachine
         * @param stateMachineExecution
         */
        public DelegatingStateMachine(ProviderStateMachine providerStateMachine, StateMachineExecutionModel stateMachineExecution, StateMachineExecutionEntity stateMachineExecutionEntity) {
            this.providerStateMachine = providerStateMachine;
            this.stateMachineExecution = stateMachineExecution;
            this.stateMachineExecutionEntity = stateMachineExecutionEntity;
            this.stateMachineDefinition = stateMachineExecution.stateMachineDefinition();
            postStateMachineExecutionCreation(stateMachineExecution);
            this.bizType = stateMachineExecution.bizType();
            this.bizId = stateMachineExecution.bizId();
            startInternal(null);
            this.started = new AtomicBoolean(true);
        }

        @Override
        public Long id() {
            assertStarted();
            return stateMachineExecution.id();
        }

        @Override
        public void sendEvent(Event event, Map<String, Object> contextAttributes) {
            assertStarted();
            transactionTemplate.execute(status -> {
                sendEventInternal(this, event, contextAttributes);
                return null;
            });
        }

        @Override
        public void sendEvent(String name, Map<String, Object> body, Map<String, Object> contextAttributes) {
            Assert.hasText(name, "event name must not empty.");
            EventDefinition eventDefinition = eventDefinitionEntityMapper.mapForRead(
                    eventDefinitionRepository.getById(
                            eventDefinitionRepository.getIdByName(name).orElseThrow(() -> new IllegalArgumentException("no event definition found by name: " + name))
                    ).orElseThrow(() -> new IllegalArgumentException("no event definition found by name: " + name)));
            sendEvent(new EventModel(eventDefinition, body), contextAttributes);
        }

        @Override
        public void sendEvent(String eventName, Map<String, Object> contextAttributes) {
            sendEvent(eventName, null, contextAttributes);
        }

        @Override
        public void sendEvent(String eventName) {
            sendEvent(eventName, null, null);
        }

        @Override
        public void start(Map<String, Object> contextAttributes) {
            if (started.compareAndSet(false, true)) {
                transactionTemplate.execute(status -> {
                    //create execution for this state machine.
                    startInternal(contextAttributes);
                    return null;
                });
            }
        }

        private void startInternal(Map<String, Object> contextAttributes) {
            //create execution for this state machine.
            if (null == this.stateMachineExecution) {
                this.stateMachineExecutionEntity = createStateMachineExecution(bizType, bizId, stateMachineDefinition.id(), contextAttributes);
                this.stateMachineExecution = stateMachineExecutionEntityMapper.mapForRead(stateMachineExecutionEntity);
                postStateMachineExecutionCreation(stateMachineExecution);
            }
            providerStateMachine.start(stateMachineExecution.getExecutionContext());
            logger.debug("state machine [{}--{}] started.", stateMachineDefinition.name(), stateMachineExecution.id());
        }

        @Override
        public void terminate(String note) {
            if (!stateMachineExecution.executionState().acceptingTerminate()) {
                throw new IllegalStateException("not allowed terminate a [" + stateMachineExecution.executionState() + "] state machine.");
            }
            transactionTemplate.execute(status -> {
                updateStateMachineExecutionState(stateMachineExecution.id(), ExecutionState.TERMINATE, note);
                return null;
            });
        }

        @Override
        public void suspend(String note) {
            if (!stateMachineExecution.executionState().acceptingSuspend()) {
                throw new IllegalStateException("not allowed suspend a [" + stateMachineExecution.executionState() + "] state machine.");
            }
            transactionTemplate.execute(status -> {
                updateStateMachineExecutionState(stateMachineExecution.id(), ExecutionState.SUSPEND, note);
                return null;
            });
        }

        @Override
        public void resume(String note) {
            if (!stateMachineExecution.executionState().acceptingResume()) {
                throw new IllegalStateException("not allowed resume a [" + stateMachineExecution.executionState() + "] state machine.");
            }
            transactionTemplate.execute(status -> {
                updateStateMachineExecutionState(stateMachineExecution.id(), ExecutionState.RUNNING, note);
                return null;
            });
        }

        @Override
        public StateMachineExecution stateMachineExecution() {
            assertStarted();
            return stateMachineExecution;
        }

        @Override
        public boolean isStarted() {
            return started.get();
        }

        private void assertStarted() {
            if (!isStarted())
                throw new IllegalStateException("state machine has not bean started. call start first.");
        }
    }

    /**
     * add features for given execution.
     * <pre>
     * 1.wrap execution context as a observable context.
     * </pre>
     *
     * @param execution
     */
    private void postStateMachineExecutionCreation(StateMachineExecutionModel execution) {
        execution.setExecutionContext(new ObservableStateMachineExecutionContext(execution));
    }


    /**
     * update statemachine execution state and saved immediately.
     *
     * @param executionId   id of execution.
     * @param updateToState updated to state.
     * @param note
     */
    private final void updateStateMachineExecutionState(Long executionId, ExecutionState updateToState, String note) {
        stateMachineExecutionRepository.updateStateMachineExecutionState(executionId, updateToState.getOrdinal());
    }


    /**
     * observable execution context holds the change status. after {@link #save()} the change status will applying to repository.
     */
    private final class ObservableStateMachineExecutionContext extends StateMachineExecutionContextModel implements StateMachineExecutionContext {

        private final StateMachineExecution execution;
        private final StateMachineExecutionContextModel nativeContext;
        private final Map<String, Object> writingBuffer;//keep writing attributes and flush to native context when saving.
        private final Set<String> removingBuffer;//keep removing attributes and flush to native context when saving.
        private volatile boolean changeFlag = false;

        private ObservableStateMachineExecutionContext(StateMachineExecution execution) {
            this.execution = execution;
            this.nativeContext = (StateMachineExecutionContextModel) execution.executionContext();
            this.writingBuffer = Maps.newHashMap();
            this.removingBuffer = Sets.newHashSet();
        }

        @Override
        public void setAttribute(String name, Object attribute) {
            writingBuffer.put(name, attribute);
            if (removingBuffer.contains(name))
                removingBuffer.remove(name);
            change();
        }

        @Override
        public void removeAttribute(String name) {
            removingBuffer.add(name);
            change();
        }

        @Override
        public Object getAttribute(String name) {
            if (removingBuffer.contains(name))
                return null;
            Object attribute = nativeContext.getAttribute(name);
            if (null != attribute)
                return attribute;
            return writingBuffer.get(name);
        }

        @Override
        public Map<String, Object> getAttributes() {
            Map<String, Object> map = Maps.newHashMap(nativeContext.getAttributes());
            map.putAll(writingBuffer);
            removingBuffer.forEach(key -> map.remove(key));
            return Collections.unmodifiableMap(map);
        }

        @Override
        public boolean hasAttribute(String name) {
            boolean exists = nativeContext.hasAttribute(name) && !removingBuffer.contains(name);
            if (exists)
                return true;
            return writingBuffer.containsKey(name) && !removingBuffer.contains(name);
        }

        @Override
        public void addAttributes(Map<String, Object> contextAttributes, boolean force) {
            writingBuffer.putAll(contextAttributes);
            contextAttributes.keySet().forEach(k -> {
                if (removingBuffer.contains(k))
                    removingBuffer.remove(k);
            });
            change();
        }

        @Override
        public String getContextPayload() {
            Map<String, Object> attributes = getAttributes();
            if (MapUtils.isNotEmpty(attributes)) {
                return JsonMapper2.toJson(attributes);
            } else {
                return EMPTY_CONTEXT_PAYLOAD;
            }
        }

        private void change() {
            changeFlag = true;
        }

        private boolean isChanged() {
            return changeFlag;
        }

        private void resetChange() {
            if (logger.isDebugEnabled()) {
                logger.debug("reset changes");
            }
            writingBuffer.clear();
            removingBuffer.clear();
            changeFlag = false;
        }

        @Override
        public void save() {
            transactionTemplate.execute(status -> {
                saveContextPayloadInternal(execution.id(), this);
                return null;
            });
        }

        /**
         * flushing buffered attributes to native context.
         */
        private void flush() {
            if (!writingBuffer.isEmpty()) {
                nativeContext.addAttributes(writingBuffer, true);
                if (logger.isDebugEnabled()) {
                    logger.debug("apply writing buffer [{}] to native context.", writingBuffer);
                }
            }
            for (String removingKey : removingBuffer) {
                nativeContext.removeAttribute(removingKey);
                if (logger.isDebugEnabled()) {
                    logger.debug("remove [] from native context.", removingKey);
                }
            }
        }
    }

    private void saveContextPayloadInternal(Long executionId, ObservableStateMachineExecutionContext stateMachineExecutionContext) {
        if (!stateMachineExecutionContext.isChanged()) {
            if (logger.isDebugEnabled()) {
                logger.debug("no context attributes changes found from state machine execution: ", stateMachineExecutionContext.execution.id());
            }
            return;
        }
        stateMachineExecutionContext.flush();
        stateMachineExecutionRepository.saveContextPayload(executionId, stateMachineExecutionContext.getContextPayload());
        stateMachineExecutionContext.resetChange();
    }

    /**
     * implements this method for provider to create a new statemachine.
     *
     * @param stateMachineDefinition definition of state machine for creating.
     * @param stateTransitions       state transitions of state machine for creating.
     * @return
     */
    protected abstract ProviderStateMachine createStateMachineInternal(StateMachineDefinition
                                                                               stateMachineDefinition, List<StateTransition> stateTransitions);

    /**
     * implements this method for provider to build a exists statemachine.
     *
     * @param stateMachineDefinition definition of state machine for building.
     * @param stateTransitions       state transitions of state machine for building.
     * @param stateMachineExecution  execution of state machine for building.
     * @return
     */
    protected abstract ProviderStateMachine buildStateMachineInternal(StateMachineDefinition
                                                                              stateMachineDefinition, List<StateTransition> stateTransitions, StateMachineExecution stateMachineExecution);

    /**
     * map given entities to model.
     *
     * @param stateMachineDefinitionEntity
     * @return
     */
    protected StateMachineDefinitionModel map(StateMachineDefinitionEntity stateMachineDefinitionEntity) {
        return definitionEntityMapper.mapForRead(stateMachineDefinitionEntity);
    }

    /**
     * map given entities to model.
     *
     * @param stateTransitionEntities
     * @return
     */
    protected List<StateTransition> map(List<StateTransitionEntity> stateTransitionEntities) {
        return Collections.unmodifiableList((List<StateTransitionModel>) transitionEntityMapper.mapForReads(stateTransitionEntities));
    }

    /**
     * return {@link Action} by invocation config from {@link InvocationContainer}
     *
     * @param invocationConfig
     * @return
     */
    protected Action getAction(InvocationConfig invocationConfig) {
        return invocationContainer.getAction(invocationConfig);
    }

    /**
     * return {@link TransitionEvaluator} by invocation config from {@link InvocationContainer}.
     *
     * @param invocationConfig
     * @return
     */
    protected TransitionEvaluator getTransitionEvaluator(InvocationConfig invocationConfig) {
        return invocationContainer.getTransitionEvaluator(invocationConfig);
    }

    @Override
    public final void afterPropertiesSet() throws Exception {
        Assert.notNull(transactionManager, "no transaction manager found from spring context.");
        transactionTemplate = buildTransactionTemplate();
        afterPropertiesSetInternal();
    }

    protected TransactionTemplate buildTransactionTemplate() {
        return new TransactionTemplate(transactionManager, new DefaultTransactionDefinition());
    }

    abstract protected void afterPropertiesSetInternal() throws Exception;

    @Autowired
    public void setDefinitionRepository(StateMachineDefinitionRepository definitionRepository) {
        this.definitionRepository = definitionRepository;
    }

    @Autowired
    public void setTransitionRepository(StateTransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    @Autowired
    public void setEventDefinitionRepository(EventDefinitionRepository eventDefinitionRepository) {
        this.eventDefinitionRepository = eventDefinitionRepository;
    }

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Autowired
    public void setStateMachineExecutionRepository(StateMachineExecutionRepository stateMachineExecutionRepository) {
        this.stateMachineExecutionRepository = stateMachineExecutionRepository;
    }

    @Autowired
    public void setStateRepository(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Autowired
    public void setHistoricTransitionExecutionRepository(HistoricTransitionExecutionRepository historicTransitionExecutionRepository) {
        this.historicTransitionExecutionRepository = historicTransitionExecutionRepository;
    }

    @Autowired
    public void setDefinitionEntityMapper(StateMachineDefinitionEntityMapper definitionEntityMapper) {
        this.definitionEntityMapper = definitionEntityMapper;
    }

    @Autowired
    public void setTransitionEntityMapper(StateTransitionEntityMapper transitionEntityMapper) {
        this.transitionEntityMapper = transitionEntityMapper;
    }

    @Autowired
    public void setStateMachineExecutionEntityMapper(StateMachineExecutionEntityMapper stateMachineExecutionEntityMapper) {
        this.stateMachineExecutionEntityMapper = stateMachineExecutionEntityMapper;
    }

    @Autowired
    public void setEventDefinitionEntityMapper(EventDefinitionEntityMapper eventDefinitionEntityMapper) {
        this.eventDefinitionEntityMapper = eventDefinitionEntityMapper;
    }

    @Autowired
    public void setEventEntityMapper(EventEntityMapper eventEntityMapper) {
        this.eventEntityMapper = eventEntityMapper;
    }

    @Autowired
    public void setInvocationContainer(InvocationContainer invocationContainer) {
        this.invocationContainer = invocationContainer;
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

}
