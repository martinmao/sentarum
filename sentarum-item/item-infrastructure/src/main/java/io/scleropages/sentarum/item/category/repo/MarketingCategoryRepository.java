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
package io.scleropages.sentarum.item.category.repo;

import io.scleropages.sentarum.item.category.entity.MarketingCategoryEntity;
import io.scleropages.sentarum.jooq.tables.ClMktCategory;
import io.scleropages.sentarum.jooq.tables.records.ClMktCategoryRecord;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface MarketingCategoryRepository extends AbstractCategoryRepository<MarketingCategoryEntity, ClMktCategory, ClMktCategoryRecord> {


    /**
     * get by id with standard category links.
     */
    default Optional<MarketingCategoryEntity> getByIdWithStandardCategoryLinks(Long id) {
        Specification specification = (Specification) (root, query, builder) -> {
            root.fetch("standardCategoryLinks").fetch("standardCategory");
            return builder.equal(root.get("id"), id);
        };
        return get(specification);
    }

    @Override
    default MarketingCategoryEntity createEntity() {
        return new MarketingCategoryEntity();
    }
}
