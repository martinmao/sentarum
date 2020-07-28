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
package io.scleropages.sentarum.item.entity;

import io.scleropages.sentarum.item.model.Spu;
import io.scleropages.sentarum.item.property.entity.AbstractPropertyValueEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced model： {@link Spu#keyProperties()}，将spu关键属性独立存储，与 {@link io.scleropages.sentarum.item.model.Item}属性进行区分。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "item_spu_property_value",
        // 允许 name重复，多选 uniqueConstraints = @UniqueConstraint(columnNames = {BIZ_TYPE_COLUMN, BIZ_ID_COLUMN, NAME_COLUMN}),
        indexes = {
                @Index(columnList = "biz_type,name_,int_,text_,bool_,date_,long_,decimal_")
        })
@SequenceGenerator(name = "item_spu_property_value_id", sequenceName = "seq_item_spu_property_value", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class SpuPropertyValueEntity extends AbstractPropertyValueEntity {

}
