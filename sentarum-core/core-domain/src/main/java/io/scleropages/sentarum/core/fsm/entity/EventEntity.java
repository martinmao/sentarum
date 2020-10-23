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
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * referenced model: {@link io.scleropages.sentarum.core.fsm.model.impl.EventModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "fsm_event")
@SequenceGenerator(name = "event_id", sequenceName = "seq_event", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class EventEntity extends IdEntity {

    private String name;
    private String tag;
    private Date firedTime;
    private String body;
    private Boolean accepted;
    private String note;

    private EventDefinitionEntity eventDefinition;


    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "tag_", nullable = false)
    public String getTag() {
        return tag;
    }

    @Column(name = "fired_time", nullable = false)
    public Date getFiredTime() {
        return firedTime;
    }

    @Column(name = "body_")
    public String getBody() {
        return body;
    }

    @Column(name = "accepted_", nullable = false)
    public Boolean getAccepted() {
        return accepted;
    }

    @Column(name = "note_")
    public String getNote() {
        return note;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_def_id", nullable = false)
    public EventDefinitionEntity getEventDefinition() {
        return eventDefinition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setFiredTime(Date firedTime) {
        this.firedTime = firedTime;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setEventDefinition(EventDefinitionEntity eventDefinition) {
        this.eventDefinition = eventDefinition;
    }

    public void populateEventDefinitionInformation() {
        Assert.notNull(eventDefinition, "eventDefinition not yet associated.");
        setName(eventDefinition.getName());
        setTag(eventDefinition.getTag());
        if (null == firedTime)
            setFiredTime(new Date());
    }
}
