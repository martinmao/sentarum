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
package io.scleropages.sentarum.core.tag.model.impl;

import io.scleropages.sentarum.core.tag.model.TagGroup;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class TagGroupModel implements TagGroup {


    private Long id;
    private Integer bizType;
    private String name;
    private Boolean enabled;
    private Boolean multipleSelect;


    public Long getId() {
        return id;
    }

    public Integer getBizType() {
        return bizType;
    }

    public String getName() {
        return name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean getMultipleSelect() {
        return multipleSelect;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setMultipleSelect(Boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Integer bizType() {
        return getBizType();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public Boolean enabled() {
        return getEnabled();
    }

    @Override
    public Boolean multipleSelect() {
        return getMultipleSelect();
    }
}
