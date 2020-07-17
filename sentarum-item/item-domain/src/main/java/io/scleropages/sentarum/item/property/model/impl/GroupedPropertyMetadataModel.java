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
package io.scleropages.sentarum.item.property.model.impl;

import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class GroupedPropertyMetadataModel implements GroupedPropertyMetadata {

    private Long id;
    private String name;
    private String tag;
    private String description;
    private List<OrderedPropertyMetadata> entries;
    private AtomicBoolean sorted = new AtomicBoolean(false);

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return id;
    }

    @NotEmpty(groups = Create.class)
    public String getName() {
        return name;
    }

    @NotEmpty(groups = Create.class)
    public String getTag() {
        return tag;
    }

    @NotEmpty(groups = Create.class)
    public String getDescription() {
        return description;
    }

    @Null
    public List<OrderedPropertyMetadata> getEntries() {
        return entries;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEntries(List<OrderedPropertyMetadata> entries) {
        this.entries = entries;
    }

    @Override
    public Long id() {
        return getId();
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
    public String description() {
        return getDescription();
    }

    @Override
    public List<OrderedPropertyMetadata> orderedPropertiesMetadata() {
        if (sorted.compareAndSet(false, true)) {
            List<OrderedPropertyMetadata> propertiesMetadata = getEntries();
            if (!CollectionUtils.isEmpty(propertiesMetadata))
                Collections.sort(propertiesMetadata);
        }
        return getEntries();
    }

    public interface Create {
    }

    public interface Update {
    }

}
