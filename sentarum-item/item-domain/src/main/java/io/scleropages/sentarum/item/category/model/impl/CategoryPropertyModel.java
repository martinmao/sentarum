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
package io.scleropages.sentarum.item.category.model.impl;

import io.scleropages.sentarum.item.category.model.CategoryProperty;
import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;

import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CategoryPropertyModel implements CategoryProperty {

    private Long id;
    private Boolean readOnly;
    private Boolean visible;
    private Boolean required;
    private Float order;
    private StandardCategory category;
    private PropertyMetadata propertyMetadata;
    private DefaultValues defaultValues;
    private CategoryPropertyBizType categoryPropertyBizType;
    private Map<String, Object> additionalAttributes;

    public Long getId() {
        return id;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public Boolean getVisible() {
        return visible;
    }

    public Boolean getRequired() {
        return required;
    }

    public Float getOrder() {
        return order;
    }

    public StandardCategory getCategory() {
        return category;
    }

    public PropertyMetadata getPropertyMetadata() {
        return propertyMetadata;
    }

    public DefaultValues getDefaultValues() {
        return defaultValues;
    }

    public CategoryPropertyBizType getCategoryPropertyBizType() {
        return categoryPropertyBizType;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCategory(StandardCategory category) {
        this.category = category;
    }

    public void setPropertyMetadata(PropertyMetadata propertyMetadata) {
        this.propertyMetadata = propertyMetadata;
    }

    public void setDefaultValues(DefaultValues defaultValues) {
        this.defaultValues = defaultValues;
    }

    public void setCategoryPropertyBizType(CategoryPropertyBizType categoryPropertyBizType) {
        this.categoryPropertyBizType = categoryPropertyBizType;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Boolean readOnly() {
        return getReadOnly();
    }

    @Override
    public Boolean visible() {
        return getVisible();
    }

    @Override
    public Boolean required() {
        return getRequired();
    }

    @Override
    public Float order() {
        return getOrder();
    }

    @Override
    public PropertyMetadata propertyMetadata() {
        return getPropertyMetadata();
    }

    @Override
    public DefaultValues defaultValues() {
        return getDefaultValues();
    }

    @Override
    public CategoryPropertyBizType categoryPropertyBizType() {
        return getCategoryPropertyBizType();
    }

    @Override
    public StandardCategory category() {
        return getCategory();
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return getAdditionalAttributes();
    }
}
