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
 *     {@link io.scleropages.sentarum.item.property.model.vs.GenericValuesSource}
 *     {@link io.scleropages.sentarum.item.property.model.vs.DataValuesSource}
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

    private Integer valuesSourceType;

    private Integer fetchSize;

    private String command;

    private String parametersPayLoad;

    @Column(name = "values_source_type", nullable = false)
    public Integer getValuesSourceType() {
        return valuesSourceType;
    }

    @Column(name = "fetch_size")
    public Integer getFetchSize() {
        return fetchSize;
    }

    @Column(name = "cmd_")
    public String getCommand() {
        return command;
    }

    @Column(name = "parameters_payload")
    public String getParametersPayLoad() {
        return parametersPayLoad;
    }

    public void setValuesSourceType(Integer valuesSourceType) {
        this.valuesSourceType = valuesSourceType;
    }

    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setParametersPayLoad(String parametersPayLoad) {
        this.parametersPayLoad = parametersPayLoad;
    }
}
