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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 参考模型 {@link io.scleropages.sentarum.item.category.model.impl.StandardCategoryLinkModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "cl_std_category_link")
@SequenceGenerator(name = "cl_std_category_link_id", sequenceName = "seq_cl_std_category_link", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class StandardCategoryLinkEntity extends IdEntity {


    private Integer linkType;
    private Integer linkStatus;
    private MarketingCategoryEntity marketingCategory;
    private StandardCategoryEntity standardCategory;
    private String searchFilter;

    @Column(name = "link_type", nullable = false)
    public Integer getLinkType() {
        return linkType;
    }

    @Column(name = "link_status", nullable = false)
    public Integer getLinkStatus() {
        return linkStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mkt_category_id")
    public MarketingCategoryEntity getMarketingCategory() {
        return marketingCategory;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "std_category_id")
    public StandardCategoryEntity getStandardCategory() {
        return standardCategory;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public void setLinkStatus(Integer linkStatus) {
        this.linkStatus = linkStatus;
    }

    public void setMarketingCategory(MarketingCategoryEntity marketingCategory) {
        this.marketingCategory = marketingCategory;
    }

    public void setStandardCategory(StandardCategoryEntity standardCategory) {
        this.standardCategory = standardCategory;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }
}
