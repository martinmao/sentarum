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

import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * 使用本地存储值集(大量的数据),持久层需要基于既定的 {@link io.scleropages.sentarum.item.property.model.ValuesSource.SourceValue} 确定统一存储结构.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class DataValuesSource extends AbstractValuesSource {

    private Integer fetchSize;

    @Override
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return super.getId();
    }

    @Override
    @Null
    public List<SourceValueModel> getSourceValues() {
        return super.getSourceValues();
    }


    @Override
    public ValuesSourceType valuesSourceType() {
        return ValuesSourceType.DATA_VALUES;
    }

    /**
     * return number of data's to fetch.
     *
     * @return
     */
    @NotNull(groups = Create.class)
    public Integer getFetchSize() {
        return fetchSize;
    }

    /**
     * set number of data's to fetch.
     *
     * @param fetchSize
     */
    public void setFetchSize(Integer fetchSize) {
        this.fetchSize = fetchSize;
    }


    public interface Create {
    }

    public interface Update {
    }
}
