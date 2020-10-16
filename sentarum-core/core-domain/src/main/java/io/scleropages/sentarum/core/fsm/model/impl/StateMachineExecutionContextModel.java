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

import com.google.common.collect.Maps;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;

import java.util.Collections;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StateMachineExecutionContextModel implements StateMachineExecutionContext {

    private final Map<String, Object> context = Maps.newHashMap();

    @Override
    public void setAttribute(String name, Object attribute) {
        context.put(name, attribute);
    }

    @Override
    public void removeAttribute(String name) {
        context.remove(name);
    }

    @Override
    public Object getAttribute(String name) {
        return context.get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(context);
    }

    @Override
    public boolean hasAttribute(String name) {
        return context.containsKey(name);
    }
}
