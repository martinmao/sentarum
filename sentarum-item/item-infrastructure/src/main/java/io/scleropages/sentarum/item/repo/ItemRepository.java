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

import io.scleropages.sentarum.item.entity.ItemEntity;
import io.scleropages.sentarum.jooq.tables.Item;
import io.scleropages.sentarum.jooq.tables.records.ItemRecord;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ItemRepository extends GenericRepository<ItemEntity, Long>, JooqRepository<Item, ItemRecord, ItemEntity> {


    @EntityGraph(attributePaths = {"spu", "category"})
    @Cacheable
    Optional<ItemEntity> getById(Long aLong);
}
