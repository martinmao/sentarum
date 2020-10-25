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

import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StateModel implements State {

    private Long id;
    private Integer value;
    private String name;
    private String tag;
    private String desc;
    private InvocationConfig enteredActionConfig;
    private InvocationConfig exitActionConfig;

    @Null(groups = CreateModel.class)
    @NotNull(groups = UpdateModel.class)
    public Long getId() {
        return id;
    }

    @NotNull(groups = CreateModel.class)
    public Integer getValue() {
        return value;
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

    public InvocationConfig getEnteredActionConfig() {
        return enteredActionConfig;
    }

    public InvocationConfig getExitActionConfig() {
        return exitActionConfig;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(Integer value) {
        this.value = value;
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

    public void setEnteredActionConfig(InvocationConfig enteredActionConfig) {
        this.enteredActionConfig = enteredActionConfig;
    }

    public void setExitActionConfig(InvocationConfig exitActionConfig) {
        this.exitActionConfig = exitActionConfig;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Integer value() {
        return getValue();
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
    public InvocationConfig enteredActionConfig() {
        return getEnteredActionConfig();
    }

    @Override
    public InvocationConfig exitActionConfig() {
        return getExitActionConfig();
    }


    public interface CreateModel {
    }

    public interface UpdateModel {
    }
}
