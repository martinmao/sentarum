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
package io.scleropages.sentarum.promotion.goods.repo;

import io.scleropages.sentarum.core.entity.mapper.MapAttributesMapper;
import io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity;
import io.scleropages.sentarum.promotion.goods.model.GoodsSpecs;
import io.scleropages.sentarum.promotion.goods.repo.AdditionalAttributesInitializer.AdditionalAttributesSavingCallback;
import org.jooq.Record;
import org.jooq.Table;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import java.util.Map;

import static io.scleropages.sentarum.promotion.goods.entity.GoodsEntity.COLUMN_ATTRS_PAYLOAD;
import static io.scleropages.sentarum.promotion.goods.entity.GoodsEntity.COLUMN_ID;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface GoodsSpecsRepository<E extends GoodsSpecsEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E>, AdditionalAttributesSavingCallback<GoodsSpecs, E>, MapAttributesMapper {





    default E createEntity() {
        throw new IllegalStateException("not implement.");
    }

    @Override
    default void save(GoodsSpecs provider, Map<String, Object> additionalAttributesMap) {
        Assert.notNull(provider, "GoodsSpecs must not be null.");
        Long id = provider.id();
        Assert.notNull(id, "GoodsSpecs id must not be null.");
        T t = dslTable();
        dslContext().update(t)
                .set(t.field(COLUMN_ATTRS_PAYLOAD.toUpperCase()), attributesToPayload(additionalAttributesMap))
                .where(t.field(COLUMN_ID.toUpperCase()).eq(id)).execute();
    }

    @Override
    default Map<String, Object> additionalAttributesMap(E entity) {
        Assert.notNull(entity, "goods entity must not be null.");
        return payloadToAttributes(entity.getAdditionalAttributes());
    }

}
