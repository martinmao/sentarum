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

import io.scleropages.sentarum.item.category.model.Category;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CategoryModel implements Category {

    private Long id;
    private String name;
    private String tag;
    private String description;
    private Status status;
    private Category parentCategory;
    private List<? extends Category> childCategories;
    private Map<String, Object> additionalAttributes;

    @NotNull(groups = Update.class)
    @Null(groups = Create.class)
    public Long getId() {
        return id;
    }

    @NotEmpty(groups = Create.class)
    public String getName() {
        return name;
    }

    @NotEmpty(groups = Create.class)
    public String getTag() {
        return tag;
    }

    @NotEmpty(groups = Create.class)
    public String getDescription() {
        return description;
    }

    @NotNull(groups = Create.class)
    public Status getStatus() {
        return status;
    }

    @Null
    public Category getParentCategory() {
        return parentCategory;
    }

    @Null
    public List<? extends Category> getChildCategories() {
        return childCategories;
    }


    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void setChildCategories(List<? extends Category> childCategories) {
        this.childCategories = childCategories;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
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
    public String tag() {
        return getTag();
    }

    @Override
    public String description() {
        return getDescription();
    }

    @Override
    public Status status() {
        return getStatus();
    }

    @Override
    public Category parentCategory() {
        return getParentCategory();
    }

    @Override
    public List<? extends Category> childCategories() {
        return getChildCategories();
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return getAdditionalAttributes();
    }

}
