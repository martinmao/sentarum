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

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * referenced model:
 * <pre>
 * {@link io.scleropages.sentarum.item.model.impl.CombineSkuModel}
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "item_combine_sku")
@SequenceGenerator(name = "item_combine_sku_id", sequenceName = "seq_item_combine_sku", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class CombineSkuEntity extends AbstractSkuEntity {

    private List<CombineSkuEntryEntity> entries;

    @OneToMany(mappedBy = "combineSku")
    public List<CombineSkuEntryEntity> getEntries() {
        return entries;
    }

    public void setEntries(List<CombineSkuEntryEntity> entries) {
        this.entries = entries;
    }
}
