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
package io.scleropages.sentarum.item.property.model.input;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.property.model.Input;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class MultiComplexInput implements Input {

    private List<ComplexInput> complexInputs = Lists.newArrayList();

    /**
     * 添加一个复合类型
     *
     * @param complexInput
     */
    public void addComplexInput(ComplexInput complexInput) {
        complexInputs.add(complexInput);
    }

    /**
     * 获取所有复合类型
     *
     * @return
     */
    public List<ComplexInput> complexInputs() {
        return complexInputs;
    }

    @Override
    public InputType getType() {
        return InputType.MULTI_COMPLEX;
    }
}
