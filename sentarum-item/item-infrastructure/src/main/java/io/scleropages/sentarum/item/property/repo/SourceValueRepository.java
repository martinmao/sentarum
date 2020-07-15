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

import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.property.entity.SourceValueEntity;
import io.scleropages.sentarum.item.property.entity.mapper.SourceValueEntityMapper;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.vs.NativeValuesSource;
import io.scleropages.sentarum.jooq.tables.PtSourceValue;
import io.scleropages.sentarum.jooq.tables.records.PtSourceValueRecord;
import org.scleropages.crud.ModelMapperRepository;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface SourceValueRepository extends NativeValuesSource.NativeSourceValueLoader, GenericRepository<SourceValueEntity, Long>,
        JooqRepository<PtSourceValue, PtSourceValueRecord, SourceValueEntity> {

    @Override
    default Page<? extends ValuesSource.SourceValue> readValues(ValuesSource.SourceValue search, Pageable pageable) {
        Specification<SourceValueEntity> spec = (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            if (null != search.id()) {
                predicates.add(builder.equal(root.get("id"), search.id()));
            }
            if (null != search.valuesSourceId()) {
                predicates.add(builder.equal(root.get("valuesSource").get("id"), search.valuesSourceId()));
            }
            if (null != search.refId()) {
                predicates.add(builder.equal(root.get("refId"), search.refId()));
            }
            if (null != search.value()) {
                predicates.add(builder.equal(root.get("value"), search.value()));
            }
            if (null != search.valueTag()) {
                predicates.add(builder.like(root.get("valueTag"), search.valueTag() + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return findPage(spec, pageable).map(entity -> {
            SourceValueEntityMapper entityMapper = (SourceValueEntityMapper) ModelMapperRepository.getRequiredModelMapper(SourceValueEntityMapper.class);
            return entityMapper.mapForRead(entity);
        });
    }
}
