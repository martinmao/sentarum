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

import io.scleropages.sentarum.item.property.model.Constraint;
import io.scleropages.sentarum.item.property.model.Input;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValueType;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import org.springframework.core.OrderComparator;

import java.util.List;

/**
 * Generic {@link PropertyMetadata} implementation.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PropertyMetadataModel implements PropertyMetadata {

    private Long id;
    private String name;
    private String tag;
    private String description;
    private Boolean keyed;
    private Integer bizType;
    private PropertyValueType valueType;
    private PropertyStructureType structureType;
    private Input input;
    private ValuesSourceModel valuesSource;
    private List<Constraint> constraints;
    private Long refId;

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String tag() {
        return tag;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public Boolean keyed() {
        return keyed;
    }

    @Override
    public Integer bizType() {
        return bizType;
    }


    @Override
    public PropertyValueType valueType() {
        return valueType;
    }

    @Override
    public PropertyStructureType structureType() {
        return structureType;
    }

    @Override
    public Input input() {
        return input;
    }

    @Override
    public ValuesSource valuesSource() {
        return valuesSource;
    }

    @Override
    public List<Constraint> constraints() {
        return constraints;
    }

    @Override
    public Long refId() {
        return refId;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getKeyed() {
        return keyed;
    }

    public Integer getBizType() {
        return bizType;
    }

    public PropertyValueType getValueType() {
        return valueType;
    }

    public PropertyStructureType getStructureType() {
        return structureType;
    }

    public Input getInput() {
        return input;
    }

    public ValuesSourceModel getValuesSource() {
        return valuesSource;
    }

    public List<Constraint> getConstraints() {
        if (null != constraints && constraints.size() > 0)
            OrderComparator.sort(constraints);
        return constraints;
    }

    public Long getRefId() {
        return refId;
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

    public void setKeyed(Boolean keyed) {
        this.keyed = keyed;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setValueType(PropertyValueType valueType) {
        this.valueType = valueType;
    }

    public void setStructureType(PropertyStructureType structureType) {
        this.structureType = structureType;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void setValuesSource(ValuesSourceModel valuesSource) {
        this.valuesSource = valuesSource;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }
}
