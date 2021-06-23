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
package io.scleropages.sentarum.core.tag.entity;

import io.scleropages.sentarum.core.tenant.entity.TenantAppEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import static io.scleropages.sentarum.core.entity.ColumnNames.*;

/**
 * referenced model: {@link io.scleropages.sentarum.core.tag.model.impl.TagModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "tag_def", uniqueConstraints = {@UniqueConstraint(columnNames = {COLUMN_APP_ID, COLUMN_BIZ_TYPE, COLUMN_NAME})})
@SequenceGenerator(name = "tag_def_id", sequenceName = "seq_tag_def", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class TagEntity extends TenantAppEntity {

    private Integer bizType;
    private String name;
    private Boolean enabled;
    private Integer seq;
    private Float order;

    private TagGroupEntity tagGroup;

    @Column(name = COLUMN_BIZ_TYPE, nullable = false)
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = COLUMN_NAME, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = COLUMN_ENABLED, nullable = false)
    public Boolean getEnabled() {
        return enabled;
    }

    @Column(name = "seq_", nullable = false)
    public Integer getSeq() {
        return seq;
    }

    @Column(name = COLUMN_ORDER, nullable = false)
    public Float getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_group_id", nullable = false)
    public TagGroupEntity getTagGroup() {
        return tagGroup;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public void setTagGroup(TagGroupEntity tagGroup) {
        this.tagGroup = tagGroup;
    }
}
