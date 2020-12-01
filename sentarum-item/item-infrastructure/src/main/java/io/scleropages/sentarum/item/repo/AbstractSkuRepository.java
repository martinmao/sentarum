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

import io.scleropages.sentarum.item.category.repo.StandardCategoryRepository;
import io.scleropages.sentarum.item.core.entity.AbstractSkuEntity;
import org.jooq.Record;
import org.jooq.Table;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

import static io.scleropages.sentarum.item.core.entity.AbstractSkuEntity.COLUMN_ID;
import static io.scleropages.sentarum.item.core.entity.AbstractSkuEntity.COLUMN_ITEM_ID;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractSkuRepository<E extends AbstractSkuEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E> {


    @EntityGraph(attributePaths = {"item", "category"})
    @Cacheable
    Optional<E> getById(Long id);

    @Cacheable
    default Optional<R> readById(Long id) {
        T t = dslTable();
        return dslContext().selectFrom(t).where(t.field(COLUMN_ID.toUpperCase()).eq(id)).fetchOptional();
    }


    /**
     * get sku entity by id.
     *
     * @param id                 required.
     * @param itemRepository     (optional) required if want to fetch item info.
     * @param spuRepository      (optional) required if want to fetch spu info.
     * @param categoryRepository (optional) required if want to fetch category info.
     * @return
     */
    default Optional<E> readById(Long id, ItemRepository itemRepository, SpuRepository spuRepository, StandardCategoryRepository categoryRepository) {

        Optional<R> optionalR = readById(id);
        if (!optionalR.isPresent())
            return Optional.empty();
        R r = optionalR.get();
        E entity = createEntity();
        if (null != itemRepository) {
            itemRepository.readById(r.getValue(COLUMN_ITEM_ID.toUpperCase(), Long.class), spuRepository, categoryRepository).ifPresent(itemEntity -> entity.setItem(itemEntity));
        }
        return Optional.of(entity);
    }

    default E createEntity() {
        throw new IllegalStateException("not implemented.");
    }
}
