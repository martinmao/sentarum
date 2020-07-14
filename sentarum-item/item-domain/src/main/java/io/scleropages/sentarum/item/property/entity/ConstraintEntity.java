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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 参考模型:
 * {@link io.scleropages.sentarum.item.property.model.Constraint}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "pt_constraint")
@SequenceGenerator(name = "pt_constraint_id", sequenceName = "seq_pt_constraint", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class ConstraintEntity extends IdEntity {

    private String name;

    private String rule;

    private PropertyMetaEntity propertyMeta;

    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "rule_", nullable = false)
    public String getRule() {
        return rule;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_meta_id", nullable = false)
    public PropertyMetaEntity getPropertyMeta() {
        return propertyMeta;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public void setPropertyMeta(PropertyMetaEntity propertyMeta) {
        this.propertyMeta = propertyMeta;
    }
}
