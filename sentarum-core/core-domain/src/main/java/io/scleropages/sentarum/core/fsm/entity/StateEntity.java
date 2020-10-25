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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.core.fsm.model.impl.StateModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "fsm_state")
@SequenceGenerator(name = "state_id", sequenceName = "seq_state", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class StateEntity extends IdEntity {

    private Integer value;
    private String name;
    private String tag;
    private String desc;

    private InvocationConfigEntity enteredActionConfig;
    private InvocationConfigEntity exitActionConfig;

    @Column(name = "value_", nullable = false, unique = true)
    public Integer getValue() {
        return value;
    }

    @Column(name = "name_", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    @Column(name = "tag_", nullable = false)
    public String getTag() {
        return tag;
    }

    @Column(name = "desc_", nullable = false)
    public String getDesc() {
        return desc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entered_invocation_conf_id")
    public InvocationConfigEntity getEnteredActionConfig() {
        return enteredActionConfig;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exit_invocation_conf_id")
    public InvocationConfigEntity getExitActionConfig() {
        return exitActionConfig;
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

    public void setEnteredActionConfig(InvocationConfigEntity enteredActionConfig) {
        this.enteredActionConfig = enteredActionConfig;
    }

    public void setExitActionConfig(InvocationConfigEntity exitActionConfig) {
        this.exitActionConfig = exitActionConfig;
    }
}
