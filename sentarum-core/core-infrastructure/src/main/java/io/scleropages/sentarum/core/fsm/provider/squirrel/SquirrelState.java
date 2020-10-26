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
package io.scleropages.sentarum.core.fsm.provider.squirrel;

import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * proxy and override {@link State}'s {@link #hashCode()} and {@link #equals(Object)}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SquirrelState implements State {

    private final State state;

    public SquirrelState(State state) {
        Assert.notNull(state, "state must not be null.");
        Assert.hasText(state.name(), "state name must not be empty.");
        this.state = state;
    }

    @Override
    public Long id() {
        return state.id();
    }

    @Override
    public Integer value() {
        return state.value();
    }

    @Override
    public String name() {
        return state.name();
    }

    @Override
    public String tag() {
        return state.tag();
    }

    @Override
    public String desc() {
        return state.desc();
    }

    @Override
    public InvocationConfig enteredActionConfig() {
        return state.enteredActionConfig();
    }

    @Override
    public InvocationConfig exitActionConfig() {
        return state.exitActionConfig();
    }

    @Override
    public int hashCode() {
        return state.name().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof SquirrelState))
            return false;
        return Objects.equals(state.name(), ((SquirrelState) obj).name());
    }
}
