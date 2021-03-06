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

import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class EventModel implements Event {

    private Long id;

    private EventDefinition eventDefinition;

    private String name;

    private String tag;

    private Date firedTime;

    private Map<String, Object> body;

    private Boolean accepted;

    private String note;

    public EventModel() {
    }

    public EventModel(String name) {
        Assert.hasText(name, "event name must not empty.");
        this.name = name;
    }

    public EventModel(EventDefinition eventDefinition, Map<String, Object> body) {
        Assert.notNull(eventDefinition, "event definition must not be null.");
        this.eventDefinition = eventDefinition;
        this.name = eventDefinition.name();
        this.tag = eventDefinition.tag();
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public EventDefinition getEventDefinition() {
        return eventDefinition;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public Date getFiredTime() {
        return firedTime;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public String getNote() {
        return note;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEventDefinition(EventDefinition eventDefinition) {
        this.eventDefinition = eventDefinition;
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

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public EventDefinition eventDefinition() {
        return getEventDefinition();
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
    public Date firedTime() {
        return getFiredTime();
    }

    @Override
    public Map<String, Object> body() {
        return getBody();
    }

    @Override
    public Boolean accepted() {
        return getAccepted();
    }

    @Override
    public String note() {
        return getNote();
    }
}
