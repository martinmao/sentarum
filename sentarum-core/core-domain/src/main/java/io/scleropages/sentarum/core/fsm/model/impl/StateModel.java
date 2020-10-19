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

import io.scleropages.sentarum.core.fsm.Action;
import io.scleropages.sentarum.core.fsm.model.State;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StateModel implements State {

    private Long id;
    private Integer value;
    private String name;
    private String desc;
    private Action enteredAction;
    private Action exitAction;

    public Long getId() {
        return id;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Action getEnteredAction() {
        return enteredAction;
    }

    public Action getExitAction() {
        return exitAction;
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

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEnteredAction(Action enteredAction) {
        this.enteredAction = enteredAction;
    }

    public void setExitAction(Action exitAction) {
        this.exitAction = exitAction;
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
    public String desc() {
        return getDesc();
    }

    @Override
    public Action enteredAction() {
        return getEnteredAction();
    }

    @Override
    public Action exitAction() {
        return getExitAction();
    }
}
