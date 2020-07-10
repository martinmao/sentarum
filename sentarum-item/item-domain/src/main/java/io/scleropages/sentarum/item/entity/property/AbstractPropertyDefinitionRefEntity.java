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
package io.scleropages.sentarum.item.entity.property;

import io.scleropages.sentarum.item.property.entity.PropertyMetadataEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class AbstractPropertyDefinitionRefEntity extends IdEntity {

    private Boolean readable;
    private Boolean writeable;
    private Boolean required;
    private Double order;
    private PropertyMetadataEntity definitionEntity;
    private PropertyEntity defaultValue;

    @Column(name = "readable_", nullable = false)
    public Boolean getReadable() {
        return readable;
    }

    @Column(name = "writeable_", nullable = false)
    public Boolean getWriteable() {
        return writeable;
    }

    @Column(name = "required_", nullable = false)
    public Boolean getRequired() {
        return required;
    }

    @Column(name = "order_", nullable = false)
    public Double getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_def_id", nullable = false)
    public PropertyMetadataEntity getDefinitionEntity() {
        return definitionEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_value_id", nullable = false)
    public PropertyEntity getDefaultValue() {
        return defaultValue;
    }

    public void setReadable(Boolean readable) {
        this.readable = readable;
    }

    public void setWriteable(Boolean writeable) {
        this.writeable = writeable;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public void setOrder(Double order) {
        this.order = order;
    }

    public void setDefinitionEntity(PropertyMetadataEntity definitionEntity) {
        this.definitionEntity = definitionEntity;
    }

    public void setDefaultValue(PropertyEntity defaultValue) {
        this.defaultValue = defaultValue;
    }
}
