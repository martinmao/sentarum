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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 参考模型: {@link io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "pt_grp_prop_meta_entry", uniqueConstraints = @UniqueConstraint(columnNames = {"grp_prop_meta_id", "property_meta_id"}))
@SequenceGenerator(name = "pt_grp_prop_meta_entry_id", sequenceName = "seq_pt_grp_prop_meta_entry", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class GroupedMetaEntryEntity extends IdEntity {

    private Float order;
    private PropertyMetaEntity propertyMetadata;
    private GroupedMetaEntity groupedMeta;

    @Column(name = "order_", nullable = false)
    public Float getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_meta_id", nullable = false)
    public PropertyMetaEntity getPropertyMetadata() {
        return propertyMetadata;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grp_prop_meta_id", nullable = false)
    public GroupedMetaEntity getGroupedMeta() {
        return groupedMeta;
    }


    public void setOrder(Float order) {
        this.order = order;
    }

    public void setPropertyMetadata(PropertyMetaEntity propertyMetadata) {
        this.propertyMetadata = propertyMetadata;
    }

    public void setGroupedMeta(GroupedMetaEntity groupedMeta) {
        this.groupedMeta = groupedMeta;
    }
}
