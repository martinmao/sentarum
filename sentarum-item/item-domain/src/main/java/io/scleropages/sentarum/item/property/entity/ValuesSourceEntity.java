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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 参考兼容模型:
 * <pre>
 *     {@link io.scleropages.sentarum.item.property.model.vs.AbstractValuesSource}
 *     {@link io.scleropages.sentarum.item.property.model.vs.NativeValuesSource}
 *     {@link io.scleropages.sentarum.item.property.model.vs.SqlQueryValuesSource}
 *     {@link io.scleropages.sentarum.item.property.model.vs.HttpGetValuesSource}
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "pt_values_source")
@SequenceGenerator(name = "pt_values_source_id", sequenceName = "seq_pt_values_source", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class ValuesSourceEntity extends IdEntity {


    private String name;
    private String tag;
    private Integer valuesSourceType;
    private String payload;

    @Column(name = "name_", unique = true)
    public String getName() {
        return name;
    }

    @Column(name = "tag_", nullable = false)
    public String getTag() {
        return tag;
    }

    @Column(name = "vs_type", nullable = false)
    public Integer getValuesSourceType() {
        return valuesSourceType;
    }

    @Column(name = "conf_")
    public String getPayload() {
        return payload;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setValuesSourceType(Integer valuesSourceType) {
        this.valuesSourceType = valuesSourceType;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
