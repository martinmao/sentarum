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

import org.scleropages.crud.FrameworkContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 使用本地表存储值集
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class NativeValuesSource extends AbstractValuesSource {


    public interface NativeSourceValueLoader {
        Page<? extends SourceValue> readValues(SourceValue search, Pageable pageable);
    }


    @Override
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return super.getId();
    }


    @Override
    public ValuesSourceType valuesSourceType() {
        return ValuesSourceType.NATIVE_VALUES_SOURCE;
    }

    @Override
    public Page<? extends SourceValue> readValues(SourceValue search, Pageable pageable) {
        return FrameworkContext.getBean(NativeSourceValueLoader.class).readValues(search, pageable);
    }


    public interface Create {
    }

    public interface Update {
    }
}
