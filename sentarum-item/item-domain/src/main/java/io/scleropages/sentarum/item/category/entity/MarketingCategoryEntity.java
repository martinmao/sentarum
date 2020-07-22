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
 * 参考模型： {@link io.scleropages.sentarum.item.category.model.impl.MarketingCategoryModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "cl_mkt_category")
@SequenceGenerator(name = "cl_mkt_category_id", sequenceName = "seq_cl_mkt_category", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class MarketingCategoryEntity extends AbstractCategoryEntity {

    private List<StandardCategoryLinkEntity> standardCategoryLinks;


    @OneToMany(mappedBy = "marketingCategory")
    public List<StandardCategoryLinkEntity> getStandardCategoryLinks() {
        return standardCategoryLinks;
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MarketingCategoryEntity.class)
    @JoinColumn(name = "parent_mkt_category_id")
    public MarketingCategoryEntity getParent() {
        return (MarketingCategoryEntity) super.getParent();
    }

    public void setStandardCategoryLinks(List<StandardCategoryLinkEntity> standardCategoryLinks) {
        this.standardCategoryLinks = standardCategoryLinks;
    }
}
