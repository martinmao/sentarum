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

import io.scleropages.sentarum.core.tag.model.Tag;
import io.scleropages.sentarum.core.tag.model.TagGroup;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class TagModel implements Tag {

    private Long id;
    private String name;
    private Boolean enabled;
    private TagGroup tagGroup;
    private Integer bizType;
    private Integer seq;
    private Float order;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public TagGroup getTagGroup() {
        return tagGroup;
    }

    public Integer getBizType() {
        return bizType;
    }

    public Integer getSeq() {
        return seq;
    }

    public Float getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setTagGroup(TagGroup tagGroup) {
        this.tagGroup = tagGroup;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setOrder(Float order) {
        this.order = order;
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
    public Boolean enabled() {
        return getEnabled();
    }

    @Override
    public TagGroup tagGroup() {
        return getTagGroup();
    }

    @Override
    public Integer bizType() {
        return getBizType();
    }

    @Override
    public Integer seq() {
        return getSeq();
    }

    @Override
    public Float order() {
        return getOrder();
    }
}
