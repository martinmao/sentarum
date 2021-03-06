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
package io.scleropages.sentarum.core.fsm.model;

import java.util.Map;

/**
 * the execution context of fsm (Finite-state machine)
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineExecutionContext {


    /**
     * set a attribute to this context.
     *
     * @param name
     * @param attribute
     */
    void setAttribute(String name, Object attribute);

    /**
     * remove attribute from this context.
     *
     * @param name
     */
    void removeAttribute(String name);

    /**
     * get attribute from this context.
     *
     * @param name
     * @return
     */
    Object getAttribute(String name);

    /**
     * get attributes from this context.
     *
     * @return
     */
    Map<String, Object> getAttributes();

    /**
     * return true if given attribute exists.
     *
     * @param name
     * @return
     */
    boolean hasAttribute(String name);

    /**
     * append given attributes to current context.
     *
     * @param contextAttributes appending attributes.
     * @param force             true if replaces when exists. or false do nothing.
     */
    void addAttributes(Map<String, Object> contextAttributes, boolean force);


    /**
     * save changes of this context.
     */
    default void save() {
    }
}
