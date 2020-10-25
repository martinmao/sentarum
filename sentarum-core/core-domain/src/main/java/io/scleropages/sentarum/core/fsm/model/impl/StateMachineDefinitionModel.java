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
package io.scleropages.sentarum.core.fsm.model.impl;

import io.scleropages.sentarum.core.fsm.StateMachineExecutionListener;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineDefinition;
import io.scleropages.sentarum.core.fsm.model.StateTransition;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StateMachineDefinitionModel implements StateMachineDefinition {
    
    private Long id;
    private String name;
    private String tag;
    private String desc;
    private State initialState;
    private List<StateTransition> transitions;
    private Boolean autoStartup;
    private StateMachineExecutionListener executionListener;


    @Null(groups = CreateModel.class)
    @NotNull(groups = UpdateModel.class)
    public Long getId() {
        return id;
    }

    @NotEmpty(groups = CreateModel.class)
    public String getName() {
        return name;
    }

    @NotEmpty(groups = CreateModel.class)
    public String getTag() {
        return tag;
    }

    @NotEmpty(groups = CreateModel.class)
    public String getDesc() {
        return desc;
    }

    @Null
    public State getInitialState() {
        return initialState;
    }

    @Null
    public List<StateTransition> getTransitions() {
        return transitions;
    }

    @NotNull(groups = CreateModel.class)
    public Boolean getAutoStartup() {
        return autoStartup;
    }

    public StateMachineExecutionListener getExecutionListener() {
        return executionListener;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }

    public void setTransitions(List<StateTransition> transitions) {
        this.transitions = transitions;
    }

    public void setAutoStartup(Boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public void setExecutionListener(StateMachineExecutionListener executionListener) {
        this.executionListener = executionListener;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public String tag() {
        return getTag();
    }

    @Override
    public String desc() {
        return getDesc();
    }

    @Override
    public State initialState() {
        return getInitialState();
    }

    @Override
    public List<StateTransition> transitions() {
        return getTransitions();
    }

    @Override
    public Boolean autoStartup() {
        return getAutoStartup();
    }

    @Override
    public StateMachineExecutionListener executionListener() {
        return getExecutionListener();
    }


    public interface CreateModel {
    }

    public interface UpdateModel {
    }
}
