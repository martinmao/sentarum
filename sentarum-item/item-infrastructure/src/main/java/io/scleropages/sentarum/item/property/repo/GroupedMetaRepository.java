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

import io.scleropages.sentarum.item.property.entity.GroupedMetaEntity;
import io.scleropages.sentarum.item.property.entity.GroupedMetaEntryEntity;
import io.scleropages.sentarum.jooq.tables.PtGrpPropMeta;
import io.scleropages.sentarum.jooq.tables.records.PtGrpPropMetaRecord;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.JoinType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface GroupedMetaRepository extends GenericRepository<GroupedMetaEntity, Long>, JooqRepository<PtGrpPropMeta, PtGrpPropMetaRecord, GroupedMetaEntity> {


    default List<GroupedMetaEntryEntity> findAllEntriesById(Long id) {
        Specification spec = (Specification) (root, query, builder) -> {
            root.fetch("entries", JoinType.LEFT).fetch("propertyMetadata");
            return builder.equal(root.get("id"), id);
        };
        Optional<GroupedMetaEntity> optional = get(spec);
        Assert.isTrue(optional.isPresent(), "no grouped property meta found.");
        List<GroupedMetaEntryEntity> entries = optional.get().getEntries();
        return null != entries ? entries : Collections.emptyList();
    }
}
