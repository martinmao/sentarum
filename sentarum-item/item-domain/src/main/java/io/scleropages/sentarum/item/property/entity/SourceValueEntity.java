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

import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 参考模型： {@link SourceValueModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prop_source_value")
@SequenceGenerator(name = "prop_source_value_id", sequenceName = "seq_prop_source_value", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class SourceValueEntity extends IdEntity {

    private Long value;
    private String valueTag;
    private String bizPayload;

    private SourceValueEntity referencedSourceValue;
    private PropertyMetadataEntity propertyMetadata;


    @Column(name = "value_", nullable = false)
    public Long getValue() {
        return value;
    }

    @Column(name = "value_tag", nullable = false)
    public String getValueTag() {
        return valueTag;
    }

    @Column(name = "biz_payload")
    public String getBizPayload() {
        return bizPayload;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_source_id", nullable = false)
    public SourceValueEntity getReferencedSourceValue() {
        return referencedSourceValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_meta_id", nullable = false)
    public PropertyMetadataEntity getPropertyMetadata() {
        return propertyMetadata;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setValueTag(String valueTag) {
        this.valueTag = valueTag;
    }

    public void setBizPayload(String bizPayload) {
        this.bizPayload = bizPayload;
    }

    public void setReferencedSourceValue(SourceValueEntity referencedSourceValue) {
        this.referencedSourceValue = referencedSourceValue;
    }

    public void setPropertyMetadata(PropertyMetadataEntity propertyMetadata) {
        this.propertyMetadata = propertyMetadata;
    }
}
