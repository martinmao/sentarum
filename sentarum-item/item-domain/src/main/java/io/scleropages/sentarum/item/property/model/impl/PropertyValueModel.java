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

import io.scleropages.sentarum.item.property.model.PropertyValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 默认的属性值模型，其他属性值模型必须基于此类进行扩展，否则存在类型兼容问题
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PropertyValueModel implements PropertyValue {

    private Long id;
    private String name;
    private Integer bizType;
    private Long bizId;
    private Long propertyMetaId;
    private Object value;

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return id;
    }

    @Null
    public String getName() {
        return name;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Integer getBizType() {
        return bizType;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Long getBizId() {
        return bizId;
    }

    @Null
    public Long getPropertyMetaId() {
        return propertyMetaId;
    }

    @NotNull(groups = Create.class)
    public Object getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public void setPropertyMetaId(Long propertyMetaId) {
        this.propertyMetaId = propertyMetaId;
    }

    public void setValue(Object value) {
        this.value = value;
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
    public Integer bizType() {
        return getBizType();
    }

    @Override
    public Long bizId() {
        return getBizId();
    }

    @Override
    public Long propertyMetaId() {
        return getPropertyMetaId();
    }


    @Override
    public Object value() {
        return getValue();
    }

    @Override
    public void changeValue(Object value) {
        setValue(value);
    }


    public interface Create {
    }

    public interface Update {
    }
}
