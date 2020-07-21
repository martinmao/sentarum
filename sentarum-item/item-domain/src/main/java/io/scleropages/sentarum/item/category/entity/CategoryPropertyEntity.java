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
package io.scleropages.sentarum.item.category.entity;

import io.scleropages.sentarum.item.property.entity.PropertyMetaEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 参考模型： {@link io.scleropages.sentarum.item.category.model.impl.CategoryPropertyModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "cl_property", uniqueConstraints = @UniqueConstraint(columnNames = {"std_category_id", "property_meta_id"}))
@SequenceGenerator(name = "cl_property_id", sequenceName = "seq_cl_property", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class CategoryPropertyEntity extends IdEntity {

    private Boolean readOnly;
    private Boolean visible;
    private Boolean required;
    private Float order;
    private StandardCategoryEntity category;
    private PropertyMetaEntity propertyMetadata;
    private String defaultValues;
    private Integer categoryPropertyBizType;
    private String additionalAttributes;

    @Column(name = "read_only", nullable = false)
    public Boolean getReadOnly() {
        return readOnly;
    }

    @Column(name = "visible_", nullable = false)
    public Boolean getVisible() {
        return visible;
    }

    @Column(name = "required_", nullable = false)
    public Boolean getRequired() {
        return required;
    }

    @Column(name = "order_", nullable = false)
    public Float getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "std_category_id", nullable = false)
    public StandardCategoryEntity getCategory() {
        return category;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_meta_id", nullable = false)
    public PropertyMetaEntity getPropertyMetadata() {
        return propertyMetadata;
    }

    @Column(name = "default_values")
    public String getDefaultValues() {
        return defaultValues;
    }


    @Column(name = "biz_type", nullable = false)
    public Integer getCategoryPropertyBizType() {
        return categoryPropertyBizType;
    }

    @Column(name = "attrs_payload")
    public String getAdditionalAttributes() {
        return additionalAttributes;
    }


    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public void setCategory(StandardCategoryEntity category) {
        this.category = category;
    }

    public void setPropertyMetadata(PropertyMetaEntity propertyMetadata) {
        this.propertyMetadata = propertyMetadata;
    }

    public void setDefaultValues(String defaultValues) {
        this.defaultValues = defaultValues;
    }

    public void setCategoryPropertyBizType(Integer categoryPropertyBizType) {
        this.categoryPropertyBizType = categoryPropertyBizType;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }
}
