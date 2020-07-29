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
package io.scleropages.sentarum.item.property.repo;

import io.scleropages.sentarum.item.property.entity.PropertyMetaEntity;
import io.scleropages.sentarum.item.property.entity.mapper.PropertyMetaEntityMapper;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.jooq.tables.PtPropertyMeta;
import io.scleropages.sentarum.jooq.tables.records.PtPropertyMetaRecord;
import org.scleropages.crud.ModelMapperRepository;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.JoinType;
import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PropertyMetaRepository extends GenericRepository<PropertyMetaEntity, Long>,
        JooqRepository<PtPropertyMeta, PtPropertyMetaRecord, PropertyMetaEntity> {

    PropertyMetaEntity getByName(String name);

    @Cacheable
    default PropertyMetadata getByIdFromCache(Long id) {
        Specification specification = (Specification) (root, query, builder) -> {
            root.fetch("valuesSource", JoinType.LEFT);
            root.fetch("constraints", JoinType.LEFT);
            return builder.equal(root.get("id"), id);
        };
        Optional<PropertyMetaEntity> optional = get(specification);
        Assert.isTrue(optional.isPresent(), "no property meta found: " + id);
        return (PropertyMetadata) ModelMapperRepository.getRequiredModelMapper(PropertyMetaEntityMapper.class).mapForRead(optional.get());
    }
}
