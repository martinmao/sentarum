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
package io.scleropages.sentarum.item.model.property;

import io.scleropages.sentarum.item.model.property.input.MultiInput;
import io.scleropages.sentarum.item.model.property.input.SingleInput;

/**
 * 与输入相关，定义了一组输入类型名称.具体的输入类型需实现该接口.input不能独立存在，其必须绑定到特定的属性定义上 {@link io.scleropages.sentarum.item.model.property.PropertyMetadata}.
 * 属性定义作为一个工厂接口，结合属性定义上下文创建合适的input 实例.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Input {

    /**
     * 单值文本输入型. {@link SingleInput#setValue(String)}
     */
    public static final String TEXT = "text";

    /**
     * 多值文本输入型. {@link MultiInput#addValue(String)}
     */
    public static final String MULTI_TEXT = "multiText";

    /**
     * 单选（单值). {@link SingleInput#setValue(String)}
     */
    public static final String SINGLE_CHECK = "singleCheck";

    /**
     * 多选（多值). {@link MultiInput#addValue(String)}
     */
    public static final String MULTI_CHECK = "multiCheck";

    /**
     * 复合输入类型. 其中，元素类型只能是以上定义的基本输入类型（如: text,multiText,singleCheck,multiCheck.）
     * 举例：country，province，city三个 singleCheck合并定义为一个名为address的complex类型.
     */
    public static final String COMPLEX = "complex";

    /**
     * 多复合输入类型.其中.元素类型只能是 complex（复合类型）.
     */
    public static final String MULTI_COMPLEX = "multiComplex";

    /**
     * 输入类型名称，实现类参考接口的名称进行返回.
     *
     * @return
     */
    String name();
}
