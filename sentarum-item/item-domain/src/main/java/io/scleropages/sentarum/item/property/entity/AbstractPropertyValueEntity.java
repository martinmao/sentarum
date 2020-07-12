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

import io.scleropages.sentarum.item.ge.entity.ByteArrayEntity;
import io.scleropages.sentarum.item.ge.entity.StructureTextEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 参考模型:
 * <pre>
 * {@link io.scleropages.sentarum.item.property.model.PropertyValue}.属性值
 * {@link io.scleropages.sentarum.item.property.model.PropertyValueType}.属性值类型
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class AbstractPropertyValueEntity extends IdEntity {


    public static final String BIZ_TYPE_COLUMN = "biz_type";
    public static final String BIZ_ID_COLUMN = "biz_id";
    public static final String PROPERTY_META_ID_COLUMN = "property_meta_id";
    public static final String NAME_COLUMN = "name_";
    public static final String INT_VALUE_COLUMN = "int_";
    public static final String LONG_VALUE_COLUMN = "long_";
    public static final String TEXT_VALUE_COLUMN = "text_";
    public static final String DATE_VALUE_COLUMN = "date_";
    public static final String DECIMAL_VALUE_COLUMN = "decimal_";
    public static final String BOOL_VALUE_COLUMN = "bool_";


    private Integer bizType;
    private Long bizId;
    private Long propertyMetaId;
    private String name;


    private Integer intValue;
    private Long longValue;//两种可能，本身为PropertyValueType.LONG值或PropertyValueType.PROPERTY_REF，通过property meta区分含义.
    private String textValue;
    private Date dateValue;
    private BigDecimal decimalValue;
    private Boolean booleanValue;
    private StructureTextEntity structureTextValue;
    private ByteArrayEntity byteArrayValue;

    @Column(name = BIZ_TYPE_COLUMN, nullable = false)
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = BIZ_ID_COLUMN, nullable = false)
    public Long getBizId() {
        return bizId;
    }

    @Column(name = PROPERTY_META_ID_COLUMN, nullable = false)
    public Long getPropertyMetaId() {
        return propertyMetaId;
    }

    @Column(name = NAME_COLUMN, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = INT_VALUE_COLUMN)
    public Integer getIntValue() {
        return intValue;
    }

    @Column(name = LONG_VALUE_COLUMN)
    public Long getLongValue() {
        return longValue;
    }

    @Column(name = TEXT_VALUE_COLUMN)
    public String getTextValue() {
        return textValue;
    }

    @Column(name = DATE_VALUE_COLUMN)
    public Date getDateValue() {
        return dateValue;
    }

    @Column(name = DECIMAL_VALUE_COLUMN)
    public BigDecimal getDecimalValue() {
        return decimalValue;
    }

    @Column(name = BOOL_VALUE_COLUMN)
    public Boolean getBooleanValue() {
        return booleanValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "struct_text_id", nullable = false)
    public StructureTextEntity getStructureTextValue() {
        return structureTextValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "byte_array_id", nullable = false)
    public ByteArrayEntity getByteArrayValue() {
        return byteArrayValue;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public void setDecimalValue(BigDecimal decimalValue) {
        this.decimalValue = decimalValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public void setStructureTextValue(StructureTextEntity structureTextValue) {
        this.structureTextValue = structureTextValue;
    }

    public void setByteArrayValue(ByteArrayEntity byteArrayValue) {
        this.byteArrayValue = byteArrayValue;
    }
}