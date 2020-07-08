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

import io.scleropages.sentarum.item.entity.ByteArrayValueEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class AbstractPropertyEntity extends IdEntity {

    private Integer bizTypeId;
    private Long bizId;
    private String name;
    private Long longValue;
    private Double doubleValue;
    private String textValue;
    private Date dateValue;
    private BigDecimal decimalValue;
    private Boolean booleanValue;
    private ByteArrayValueEntity byteArrayValue;
    private PropertyMetadataEntity definition;

    @Column(name = "biz_type_id", nullable = false)
    public Integer getBizTypeId() {
        return bizTypeId;
    }

    @Column(name = "biz_id", nullable = false)
    public Long getBizId() {
        return bizId;
    }

    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "long_")
    public Long getLongValue() {
        return longValue;
    }

    @Column(name = "double_")
    public Double getDoubleValue() {
        return doubleValue;
    }

    @Column(name = "text_")
    public String getTextValue() {
        return textValue;
    }

    @Column(name = "date_")
    public Date getDateValue() {
        return dateValue;
    }

    @Column(name = "decimal_")
    public BigDecimal getDecimalValue() {
        return decimalValue;
    }

    @Column(name = "bool_")
    public Boolean getBooleanValue() {
        return booleanValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "byte_array_id", nullable = false)
    public ByteArrayValueEntity getByteArrayValue() {
        return byteArrayValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prop_def_id", nullable = false)
    public PropertyMetadataEntity getDefinition() {
        return definition;
    }

    public void setBizTypeId(Integer bizTypeId) {
        this.bizTypeId = bizTypeId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
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

    public void setByteArrayValue(ByteArrayValueEntity byteArrayValue) {
        this.byteArrayValue = byteArrayValue;
    }

    public void setDefinition(PropertyMetadataEntity definition) {
        this.definition = definition;
    }
}