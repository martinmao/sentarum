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
package io.scleropages.sentarum.item.property.model.vs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 基于Sql查询产生的结果集，本地不进行缓存，数据源与本地数据源同源。该接口不应该跨数据源集成其他存储系统的数据.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SqlQueryValuesSource extends AbstractValuesSource {

    private String query;

    @Override
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return super.getId();
    }


    @Override
    public ValuesSourceType valuesSourceType() {
        return ValuesSourceType.SQL_QUERY_VALUES_SOURCE;
    }


    @Override
    public Page<? extends SourceValue> readValues(SourceValue search, Pageable pageable) {
        return null;
    }

    /**
     * return query sql.
     *
     * @return
     */
    @NotEmpty(groups = Create.class)
    public String getQuery() {
        return query;
    }

    /**
     * set query sql.
     *
     * @param query
     */
    public void setQuery(String query) {
        this.query = query;
    }


    public interface Create {
    }

    public interface Update {
    }
}
