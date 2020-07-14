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

import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractValuesSource implements ValuesSource {

    private Long id;

    private List<SourceValueModel> sourceValues;

    public AbstractValuesSource() {
    }

    public AbstractValuesSource(Long id, List<SourceValueModel> sourceValues) {
        this.id = id;
        this.sourceValues = sourceValues;
    }

    public Long getId() {
        return id;
    }

    public List<SourceValueModel> getSourceValues() {
        return sourceValues;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSourceValues(List<SourceValueModel> sourceValues) {
        this.sourceValues = sourceValues;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public List<? extends SourceValue> values() {
        return getSourceValues();
    }
}
