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
package io.scleropages.sentarum.item.ge.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced model: {@link io.scleropages.sentarum.item.ge.model.Media}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "ge_media")
@SequenceGenerator(name = "ge_media_id", sequenceName = "seq_ge_media", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class MediaEntity extends IdEntity {

    private String outerId;
    private Integer contentType;
    private Integer bizType;
    private Float order;
    private Integer status;
    private String additionalAttributes;


    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @Column(name = "content_type", nullable = false)
    public Integer getContentType() {
        return contentType;
    }

    @Column(name = "biz_type", nullable = false)
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = "order_", nullable = false)
    public Float getOrder() {
        return order;
    }

    @Column(name = "status_", nullable = false)
    public Integer getStatus() {
        return status;
    }

    @Column(name = "attrs_payload")
    public String getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }
}
