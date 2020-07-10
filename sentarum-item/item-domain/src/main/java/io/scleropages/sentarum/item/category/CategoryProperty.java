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
package io.scleropages.sentarum.item.category;

import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValue;

/**
 * 关联(挂靠)到品类的属性定义
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CategoryProperty {

    /**
     * 品类属性业务类型
     */
    enum CategoryPropertyBizType {
        /**
         * 关键属性(用于定位SPU)
         */
        KEY_PROPERTY,
        /**
         * SPU属性(非关键属性)
         */
        SPU_PROPERTY,
        /**
         * SKU属性(用于定位SPU,SKU. 销售属性,规格属性)
         */
        SKU_KEY_PROPERTY,
        /**
         * SKU属性(无法用于定位SPU,SKU. 销售属性，规格属性)
         */
        SKU_PROPERTY
    }

    /**
     * 属性业务类型，对于品类来说，属性包括（关键属性，SPU属性，销售属性等）
     *
     * @return
     */
    CategoryPropertyBizType categoryPropertyBizType();

    /**
     * 只读属性，不可变，品类上设置过值的属性，子品类以及叶子品类（包括，SPU，商品，SKU都不允许覆盖该值）.
     *
     * @return
     */
    boolean readOnly();

    /**
     * 控制下级(下级品类，SPU....)是否可见，例如某些统一设置，只读且不可见，这样下级类目SPU都不会感知到该属性
     *
     * @return
     */
    boolean visible();


    /**
     * 目标属性定义,可能的类型为：{@link PropertyMetadata},{@link GroupedPropertyMetadata}
     *
     * @return
     */
    PropertyMetadata propertyMetadata();

    /**
     * 属性值
     *
     * @return
     */
    PropertyValue defaultValue();
}
