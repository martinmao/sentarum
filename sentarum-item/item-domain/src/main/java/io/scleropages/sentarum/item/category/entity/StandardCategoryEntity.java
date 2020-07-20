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
package io.scleropages.sentarum.item.category.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

/**
 * 参考模型： {@link io.scleropages.sentarum.item.category.model.impl.StandardCategoryModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "cl_std_category")
@SequenceGenerator(name = "cl_std_category_id", sequenceName = "seq_cl_std_category", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class StandardCategoryEntity extends AbstractCategoryEntity {

    private List<CategoryPropertyEntity> categoryProperties;


    @OneToMany(mappedBy = "category")
    public List<CategoryPropertyEntity> getCategoryProperties() {
        return categoryProperties;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = StandardCategoryEntity.class)
    @JoinColumn(name = "parent_std_category_id", nullable = false)
    public StandardCategoryEntity getParent() {
        return (StandardCategoryEntity) super.getParent();
    }

    public void setCategoryProperties(List<CategoryPropertyEntity> categoryProperties) {
        this.categoryProperties = categoryProperties;
    }
}
