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
package io.scleropages.sentarum.item.category.model;

import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValue;

import java.util.Map;

/**
 * 关联(挂靠)到品类的属性定义
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CategoryProperty {

    /**
     * 只读属性(不可变)，品类上设置过值的属性，子品类以及叶子品类（包括，SPU，商品，SKU都不允许覆盖该值）.
     *
     * @return
     */
    Boolean readOnly();

    /**
     * 控制下级(下级品类，SPU....)是否可见，例如某些统一设置，只读且不可见，这样下级类目SPU都不会感知到该属性
     *
     * @return
     */
    Boolean visible();

    /**
     * 必填属性
     *
     * @return
     */
    Boolean required();

    /**
     * 顺序
     *
     * @return
     */
    Float order();


    /**
     * 目标属性定义,可能的类型为：{@link PropertyMetadata},{@link GroupedPropertyMetadata}
     *
     * @return
     */
    PropertyMetadata propertyMetadata();

    /**
     * 默认属性值
     *
     * @return
     */
    PropertyValue defaultValue();


    /**
     * 品类属性业务类型,实现时需基于属性业务类型，确定其 key类型 （{@link PropertyMetadata#keyed()}）。这里的设置应该
     * 覆盖元数据中的定义.
     */
    enum CategoryPropertyBizType {

        /**
         * 关键属性(用于定位SPU或商品)，此处存在一定的误区，部分书中将规格属性定义在SKU(销售属性)中，认为规格的差异化产生不同的SKU。
         * 而实际情况为某一产品（SPU）上架时，其支持的规格已经明确定义在SPU中（包含的尺码，颜色），而SKU是从SPU中定义的规格属性值列表中选择匹配的值。
         * 为满足检索需要，在SPU定义时，就需要将规格属性所有的值绑定到SPU上（全选），此时前端基于属性的检索，才能通过规格匹配到具体的SPU。
         * 而这些值会向下继承到商品，也能满足商品的导航检索。在进行SKU设置时，需要从中确定特定的（多选变单选）规格值。
         */
        KEY_PROPERTY,
        /**
         * SPU属性(非关键属性)
         */
        SPU_PROPERTY,

        /**
         * 商品属性，与商家相关（例如，运费模板，运费，是否全新，生产日期等。。）
         */
        ITEM_PROPERTY,

        /**
         * SKU销售属性(销售属性，规格属性)
         */
        SALES_PROPERTY
    }

    /**
     * 属性业务类型，对于品类来说，属性包括（关键属性，SPU属性，销售属性等）
     *
     * @return
     */
    CategoryPropertyBizType categoryPropertyBizType();

    /**
     * 扩展属性
     *
     * @return
     */
    Map<String, Object> additionalAttributes();
}
