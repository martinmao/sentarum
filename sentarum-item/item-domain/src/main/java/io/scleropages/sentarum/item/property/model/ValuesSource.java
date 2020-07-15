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
package io.scleropages.sentarum.item.property.model;

import io.scleropages.sentarum.item.property.model.vs.HttpGetValuesSource;
import io.scleropages.sentarum.item.property.model.vs.NativeValuesSource;
import io.scleropages.sentarum.item.property.model.vs.SqlQueryValuesSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 * 属性值来源，当属性值非人工输入，而来源于枚举列表或其他关联实体...
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ValuesSource {


    enum ValuesSourceType {


        NATIVE_VALUES_SOURCE(1, "本地值集", "数据维护在本地，分批返回. 适用于从列表中选择.", NativeValuesSource.class),
        SQL_QUERY_VALUES_SOURCE(2, "SQL检索", "大量候选值，数据维护在目标表，分批返回. 适用于从列表中选择.", SqlQueryValuesSource.class),
        HTTP_GET_VALUES_SOURCE(3, "HTTP-GET检索", "大量候选值，数据维护提供端，分批返回. 适用于从列表中选择.", HttpGetValuesSource.class);

        /**
         * 显式指定 ordinal,避免定义顺序被意外变更.
         */
        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 说明.
         */
        private final String description;

        /**
         * 实现类.
         */
        private final Class implementationClass;

        ValuesSourceType(int ordinal, String tag, String description, Class implementationClass) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.description = description;
            this.implementationClass = implementationClass;
        }


        private static final Map<String, ValuesSourceType> nameMappings = new HashMap<>();
        private static final Map<Integer, ValuesSourceType> ordinalMappings = new HashMap<>();


        static {
            for (ValuesSourceType valuesSourceType : ValuesSourceType.values()) {
                nameMappings.put(valuesSourceType.name(), valuesSourceType);
                ordinalMappings.put(valuesSourceType.ordinal, valuesSourceType);
            }
        }

        public static ValuesSourceType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ValuesSourceType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }

        /**
         * return ordinal of this enum.
         *
         * @return
         */
        public int getOrdinal() {
            return ordinal;
        }

        /**
         * return display tag of this enum.
         *
         * @return
         */
        public String getTag() {
            return tag;
        }

        /**
         * return description of this enum.
         *
         * @return
         */
        public String getDescription() {
            return description;
        }

        /**
         * return implementation class of {@link ValuesSource}
         *
         * @return
         */
        public Class getImplementationClass() {
            return implementationClass;
        }
    }


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
        Long value();

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
         * 归属的 {@link ValuesSource#id()}
         *
         * @return
         */
        Long valuesSourceId();

        /**
         * 值的其他描述项，例如属性值的来源是其他表(主键)，可将其他需要显示字段统一解析为 map结构.
         *
         * @return
         */
        Map<String, Object> additionalAttributes();
    }


    /**
     * return id of this values source.
     *
     * @return
     */
    Long id();

    /**
     * return values-source type.
     *
     * @return
     */
    ValuesSourceType valuesSourceType();


    /**
     * return source values.
     *
     * @param search
     * @param pageable
     * @return
     */
    Page<? extends SourceValue> readValues(SourceValue search, Pageable pageable);
}
