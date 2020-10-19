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
package io.scleropages.sentarum.core.fsm.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.core.fsm.model.impl.InvocationConfigModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "fsm_invocation_conf")
@SequenceGenerator(name = "invocation_conf_id", sequenceName = "seq_invocation_conf", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class InvocationConfigEntity extends IdEntity {

    private String configImplementation;
    private String invocationImplementation;
    private String configPayload;


    @Column(name = "conf_impl", nullable = false)
    public String getConfigImplementation() {
        return configImplementation;
    }

    @Column(name = "invocation_impl", nullable = false)
    public String getInvocationImplementation() {
        return invocationImplementation;
    }


    @Column(name = "conf_payload", nullable = false)
    public String getConfigPayload() {
        return configPayload;
    }

    public void setConfigImplementation(String configImplementation) {
        this.configImplementation = configImplementation;
    }

    public void setInvocationImplementation(String invocationImplementation) {
        this.invocationImplementation = invocationImplementation;
    }

    public void setConfigPayload(String configPayload) {
        this.configPayload = configPayload;
    }
}
