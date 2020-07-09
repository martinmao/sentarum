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
package io.scleropages.sentarum.item.model.property.impl;

import io.scleropages.sentarum.item.model.property.ValuesSource;

import java.io.Serializable;
import java.util.Map;

/**
 * Generic {@link io.scleropages.sentarum.item.model.property.ValuesSource.SourceValue} implementation.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SourceValueBean implements ValuesSource.SourceValue {

    private Long id;
    private Serializable value;
    private String valueTag;
    private Long refId;
    private Map<String, Object> additionalAttributes;


    public Long getId() {
        return id;
    }

    public Serializable getValue() {
        return value;
    }

    public String getValueTag() {
        return valueTag;
    }

    public Long getRefId() {
        return refId;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(Serializable value) {
        this.value = value;
    }

    public void setValueTag(String valueTag) {
        this.valueTag = valueTag;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public Serializable value() {
        return value;
    }

    @Override
    public String valueTag() {
        return valueTag;
    }

    @Override
    public Long refId() {
        return refId;
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return additionalAttributes;
    }
}
