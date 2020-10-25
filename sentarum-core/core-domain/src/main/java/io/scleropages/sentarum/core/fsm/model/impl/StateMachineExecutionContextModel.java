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
import org.apache.commons.collections.MapUtils;
import org.scleropages.core.mapper.JsonMapper2;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Map;

/**
 * not a thread-safety context implementation. if required a concurrency context implementation you must override {@link #createMap()}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StateMachineExecutionContextModel implements StateMachineExecutionContext {

    private static final String EMPTY_CONTEXT_PAYLOAD = "{}";

    private final Map<String, Object> context;

    private volatile boolean contextChanges = false;

    public StateMachineExecutionContextModel(Map<String, Object> context) {
        if (null == context)
            context = createMap();
        this.context = context;
    }

    public StateMachineExecutionContextModel() {
        this(null);
    }

    @Override
    public void setAttribute(String name, Object attribute) {
        context.put(name, attribute);
        contextChanges = true;
    }

    @Override
    public void removeAttribute(String name) {
        context.remove(name);
        contextChanges = true;
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


    @Override
    public String getContextPayload() {
        if (MapUtils.isNotEmpty(context)) {
            return JsonMapper2.toJson(context);
        } else {
            return EMPTY_CONTEXT_PAYLOAD;
        }
    }

    @Override
    public void addAttributes(Map<String, Object> contextAttributes, boolean force) {
        if (null == contextAttributes || contextAttributes.isEmpty())
            return;
        contextAttributes.forEach((k, v) -> {
            if (force) {
                context.put(k, v);
            } else {
                context.putIfAbsent(k, v);
            }
        });
        contextChanges = true;
    }

    public static final StateMachineExecutionContextModel newInstance(String contextPayload) {
        if (StringUtils.hasText(contextPayload)) {
            return new StateMachineExecutionContextModel(JsonMapper2.fromJson(contextPayload));
        }
        return new StateMachineExecutionContextModel();
    }

    @Override
    public boolean contextChanges() {
        return contextChanges;
    }

    protected Map<String, Object> createMap() {
        return Maps.newHashMap();
    }

    /**
     * reset contextChanges flag.
     */
    public void resetChanges() {
        contextChanges = false;
    }
}
