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
package io.scleropages.sentarum.item.category.model.impl;

import io.scleropages.sentarum.item.category.model.MarketingCategory;
import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.category.model.StandardCategoryLink;
import org.scleropages.crud.dao.orm.SearchFilter;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class StandardCategoryLinkModel implements StandardCategoryLink {

    private Long id;
    private LinkType linkType;
    private LinkStatus linkStatus;
    private MarketingCategory marketingCategory;
    private StandardCategory standardCategory;
    private SearchFilter searchFilter;

    public Long getId() {
        return id;
    }

    public LinkType getLinkType() {
        return linkType;
    }

    public LinkStatus getLinkStatus() {
        return linkStatus;
    }

    public MarketingCategory getMarketingCategory() {
        return marketingCategory;
    }

    public StandardCategory getStandardCategory() {
        return standardCategory;
    }

    public SearchFilter getSearchFilter() {
        return searchFilter;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLinkType(LinkType linkType) {
        this.linkType = linkType;
    }

    public void setLinkStatus(LinkStatus linkStatus) {
        this.linkStatus = linkStatus;
    }

    public void setMarketingCategory(MarketingCategory marketingCategory) {
        this.marketingCategory = marketingCategory;
    }

    public void setStandardCategory(StandardCategory standardCategory) {
        this.standardCategory = standardCategory;
    }

    public void setSearchFilter(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public LinkType linkType() {
        return getLinkType();
    }

    @Override
    public LinkStatus status() {
        return getLinkStatus();
    }

    @Override
    public MarketingCategory marketingCategory() {
        return getMarketingCategory();
    }

    @Override
    public StandardCategory standardCategory() {
        return getStandardCategory();
    }

    @Override
    public SearchFilter searchFilter() {
        return getSearchFilter();
    }
}
