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
package io.scleropages.sentarum.item.property.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * 参考模型: {@link io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "pt_property_meta",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name_"}))
@SequenceGenerator(name = "pt_property_meta_id", sequenceName = "seq_pt_property_meta", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class PropertyMetaEntity extends IdEntity {

    private String name;
    private String tag;
    private String description;
    private Boolean keyed;
    private Integer bizType;
    /**
     * {@link io.scleropages.sentarum.item.property.model.PropertyValueType}
     */
    private Integer valueType;
    /**
     * {@link io.scleropages.sentarum.item.property.model.PropertyMetadata.PropertyStructureType}
     */
    private Integer structureType;
    /**
     * {@link io.scleropages.sentarum.item.property.model.Input.InputType}
     */
    private Integer input;

    private ValuesSourceEntity valuesSource;
    private List<ConstraintEntity> constraints;
    private PropertyMetaEntity referencedPropertyMetadata;


    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "tag_", nullable = false)
    public String getTag() {
        return tag;
    }

    @Column(name = "desc_", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "is_key", nullable = false)
    public Boolean getKeyed() {
        return keyed;
    }

    @Column(name = "biz_type", nullable = false)
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = "value_type", nullable = false)
    public Integer getValueType() {
        return valueType;
    }

    @Column(name = "structure_type", nullable = false)
    public Integer getStructureType() {
        return structureType;
    }

    @Column(name = "input_", nullable = false)
    public Integer getInput() {
        return input;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "values_source_id", nullable = false)
    public ValuesSourceEntity getValuesSource() {
        return valuesSource;
    }

    @OneToMany(mappedBy = "propertyMeta")
    public List<ConstraintEntity> getConstraints() {
        return constraints;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_property_meta_id", nullable = false)
    public PropertyMetaEntity getReferencedPropertyMetadata() {
        return referencedPropertyMetadata;
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

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public void setStructureType(Integer structureType) {
        this.structureType = structureType;
    }

    public void setInput(Integer input) {
        this.input = input;
    }

    public void setValuesSource(ValuesSourceEntity valuesSource) {
        this.valuesSource = valuesSource;
    }

    public void setConstraints(List<ConstraintEntity> constraints) {
        this.constraints = constraints;
    }

    public void setReferencedPropertyMetadata(PropertyMetaEntity referencedPropertyMetadata) {
        this.referencedPropertyMetadata = referencedPropertyMetadata;
    }
}
