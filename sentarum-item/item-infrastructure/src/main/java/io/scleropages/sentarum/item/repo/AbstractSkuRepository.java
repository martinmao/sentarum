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
package io.scleropages.sentarum.item.repo;

import io.scleropages.sentarum.item.entity.AbstractSkuEntity;
import io.scleropages.sentarum.item.entity.SkuEntity;
import org.jooq.Record;
import org.jooq.Table;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractSkuRepository<E extends AbstractSkuEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E> {


    @EntityGraph(attributePaths = {"item", "category"})
    @Cacheable
    Optional<SkuEntity> getById(Long aLong);
}
