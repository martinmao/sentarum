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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 属性值来源，当属性值非人工输入，而来源于其他关联实体（或枚举项).
 * {@link PropertyMetadata} 作为工厂接口，解析并生成 {@link SourceValue}列表.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ValuesSource {


    /**
     * 描述属性来源加载并解析生成统一的结构.
     */
    interface SourceValue {


        /**
         * 当前值的唯一标识.
         *
         * @return
         */
        Long id();

        /**
         * 返回属性值，其值类型必须与关联的 {@link PropertyMetadata#valueType()}中的java类型兼容.
         *
         * @return
         */
        Serializable value();

        /**
         * 返回属性值显示名.
         *
         * @return
         */
        String valueTag();

        /**
         * 当属性类型为 {@link PropertyValueType#PROPERTY_REF}.必须指定当前值关联的目标值唯一标识.
         * 例如 MacBookPro(型号值)->Mac(系列值)->Apple(品牌值)层层ref关系定义.
         *
         * @return
         */
        Long refId();

        /**
         * 值的其他描述项，例如属性值的来源是其他表(主键)，可将其他需要显示字段统一解析为 map结构.
         *
         * @return
         */
        Map<String, Object> additionalAttributes();
    }


    /**
     * get values.
     *
     * @return
     */
    List<SourceValue> getValues();
}
