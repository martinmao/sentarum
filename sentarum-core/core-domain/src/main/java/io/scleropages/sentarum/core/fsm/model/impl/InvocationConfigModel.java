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

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class InvocationConfigModel implements InvocationConfig {

    private Long id;
    private String configImplementation;
    private String invocationImplementation;

    public Long getId() {
        return id;
    }

    public String getConfigImplementation() {
        return configImplementation;
    }

    public String getInvocationImplementation() {
        return invocationImplementation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConfigImplementation(String configImplementation) {
        this.configImplementation = configImplementation;
    }

    public void setInvocationImplementation(String invocationImplementation) {
        this.invocationImplementation = invocationImplementation;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public String configImplementation() {
        return getConfigImplementation();
    }

    @Override
    public String invocationImplementation() {
        return getInvocationImplementation();
    }
}
