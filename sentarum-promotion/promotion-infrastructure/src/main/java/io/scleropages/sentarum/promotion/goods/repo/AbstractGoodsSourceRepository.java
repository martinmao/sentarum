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

import io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.JpaContexts;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.data.repository.NoRepositoryBean;

import static org.jooq.impl.DSL.*;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractGoodsSourceRepository<E extends AbstractGoodsSourceEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E> {


    abstract class QueryJoinAssembler {

        public static void applyJoin(Table actualGoodsSourceTable, Integer bizType, SelectQuery<Record> baseQuery, JpaContexts.ManagedTypeModel<?> bizEntityModel) {

            Table bizTable = dslNameToTable(bizEntityModel.table().toUpperCase());
            Field bizTableId = dslNameToField(bizTable.getName(), bizEntityModel.getColumnOfId().toUpperCase());
            Field actualGoodsSourceBizTypeField = dslNameToField(actualGoodsSourceTable.getName(), "biz_type".toUpperCase());
            Field actualGoodsSourceBizIdField = dslNameToField(actualGoodsSourceTable.getName(), "biz_id".toUpperCase());
            baseQuery.addJoin(actualGoodsSourceTable, actualGoodsSourceBizTypeField.eq(bizType).and(actualGoodsSourceBizIdField.eq(bizTableId)));

        }

        private static Field dslNameToField(String... qualifiedNames) {
            return field(name(qualifiedNames));
        }

        private static Table dslNameToTable(String... qualifiedNames) {
            return table(name(qualifiedNames));
        }
    }
}
