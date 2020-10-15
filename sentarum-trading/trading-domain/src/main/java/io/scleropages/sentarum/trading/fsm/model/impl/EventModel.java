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
package io.scleropages.sentarum.trading.fsm.model.impl;

import io.scleropages.sentarum.trading.fsm.model.Event;
import io.scleropages.sentarum.trading.fsm.model.EventDefinition;

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

    private String desc;

    private Date firedTime;

    private Map<String, Object> headers;

    private Object body;


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

    public String getDesc() {
        return desc;
    }

    public Date getFiredTime() {
        return firedTime;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public Object getBody() {
        return body;
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

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFiredTime(Date firedTime) {
        this.firedTime = firedTime;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public void setBody(Object body) {
        this.body = body;
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
    public String desc() {
        return getDesc();
    }

    @Override
    public Date firedTime() {
        return getFiredTime();
    }

    @Override
    public Map<String, Object> headers() {
        return getHeaders();
    }

    @Override
    public Object body() {
        return getBody();
    }
}
