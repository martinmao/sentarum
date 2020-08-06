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

import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.jooq.tables.ClStdCategory;
import io.scleropages.sentarum.jooq.tables.records.ClStdCategoryLinkRecord;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StandardCategoryRepository extends AbstractCategoryRepository<StandardCategoryEntity, ClStdCategory, ClStdCategoryLinkRecord> {

    /**
     * get by id with category properties.
     *
     * @param id
     * @param categoryPropertyBizType fetch by given bizType or null return all.
     * @return
     */
    default Optional<StandardCategoryEntity> getByIdWithCategoryProperties(Long id, Integer... categoryPropertyBizType) {
        Specification specification = (Specification) (root, query, builder) -> {
            root.fetch("categoryProperties", JoinType.LEFT).fetch("propertyMetadata", JoinType.LEFT);
            Predicate and = builder.equal(root.get("id"), id);
            if (ArrayUtils.isNotEmpty(categoryPropertyBizType)) {
                and = builder.and(and, root.join("categoryProperties", JoinType.LEFT).get("categoryPropertyBizType").in(categoryPropertyBizType));
            }
            query.distinct(true);
            query.where(and);
            return query.getRestriction();
        };
        return get(specification);
    }


    /**
     * get by id with parent entity and category properties.
     *
     * @param id
     * @param categoryPropertyBizType fetch by given bizType or null return all.
     * @return
     */
    default Optional<StandardCategoryEntity> getByIdWithParentAndCategoryProperties(Long id, Integer... categoryPropertyBizType) {
        Specification specification = (Specification) (root, query, builder) -> {
            root.fetch("categoryProperties", JoinType.LEFT).fetch("propertyMetadata", JoinType.LEFT);
            root.fetch("parent", JoinType.LEFT);
            Predicate and = builder.and(builder.equal(root.get("id"), id));
            if (null != categoryPropertyBizType) {
                and = builder.and(and, root.join("categoryProperties", JoinType.LEFT).get("categoryPropertyBizType").in(categoryPropertyBizType));
            }
            query.distinct(true);

            query.where(and);
            return query.getRestriction();
        };
        return get(specification);
    }


    /**
     * return full graph for all {@link StandardCategoryEntity}s
     *
     * @return
     */
    @Cacheable
    default CategoryEntityGraph<StandardCategoryEntity> getFullStandardCategoryGraph() {
        Specification specification = (Specification) (root, query, builder) -> {
            root.fetch("categoryProperties", JoinType.LEFT).fetch("propertyMetadata", JoinType.LEFT);
            root.fetch("parent", JoinType.LEFT);
            return builder.conjunction();
        };
        return createGraph(specification);
    }

}
