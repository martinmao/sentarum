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

import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.EventDefinition;
import io.scleropages.sentarum.core.fsm.model.impl.EventModel;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Map;

/**
 * proxy and override {@link Event}'s {@link #hashCode()} and {@link #equals(Object)}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SquirrelEvent implements Event {

    private final Event event;

    public SquirrelEvent(Event event) {
        Assert.notNull(event, "event must not be null.");
        Assert.hasText(event.name(), "event name must not be empty.");
        this.event = event;
    }

    public SquirrelEvent(EventDefinition eventDefinition) {
        Assert.notNull(eventDefinition, "event definition must not be null.");
        this.event = new EventModel(eventDefinition.name());
    }

    @Override
    public Long id() {
        return event.id();
    }

    @Override
    public EventDefinition eventDefinition() {
        return event.eventDefinition();
    }

    @Override
    public String name() {
        return event.name();
    }

    @Override
    public String tag() {
        return event.tag();
    }

    @Override
    public Date firedTime() {
        return event.firedTime();
    }

    @Override
    public Map<String, Object> body() {
        return event.body();
    }

    @Override
    public Boolean accepted() {
        return event.accepted();
    }

    @Override
    public String note() {
        return event.note();
    }

    @Override
    public int hashCode() {
        return event.name().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return event.name().equals(obj);
    }
}
