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

import java.util.List;

/**
 * 属性定义，用于描述一个属性或属性元数据定义.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PropertyMetadata {

    /**
     * 属性结构的分类
     */
    enum PropertyStructureType {
        /**
         * 扁平的属性，每个属性各自作为一个维度与其他属性是平行的.
         */
        FLAT_PROPERTY,

        /**
         * 存在层级的属性,root节点,其属性值会关联一组其他属性（ {@link #HIERARCHY_NODE_PROPERTY} 或 {@link #HIERARCHY_LEAF_PROPERTY} ）
         * 这种属性结构的划分是为了更好的进行属性分类，导航。避免属性过多而无法穷举筛选的情况.
         * 例如品牌，系列，型号这三种属性就存在层次关系(品牌确定系列，系列确定型号)
         */
        HIERARCHY_ROOT_PROPERTY,
        /**
         * 存在层级的属性，node节点，其属性值会关联一组其他属性 （ {@link #HIERARCHY_NODE_PROPERTY} 或 {@link #HIERARCHY_LEAF_PROPERTY} ）.
         */
        HIERARCHY_NODE_PROPERTY,
        /**
         * 存在层级的属性，leaf节点，其属性值作为整个层次的最终叶子节点.
         */
        HIERARCHY_LEAF_PROPERTY
    }

    /**
     * 标识
     *
     * @return
     */
    Long id();

    /**
     * 属性名称
     *
     * @return
     */
    String name();

    /**
     * 属性标题
     *
     * @return
     */
    String tag();

    /**
     * 属性描述
     *
     * @return
     */
    String description();

    /**
     * 是否为key属性，key属性特指可以根据名称以及值进行检索的属性,存储方案选择上需要优化的属性.非key属性则没有检索定位需求.
     *
     * @return
     */
    Boolean keyed();

    /**
     * 属性业务类型:关键属性，销售属性，产品属性，商品属性....
     *
     * @return
     */
    Integer bizType();

    /**
     * 属性值类型
     *
     * @return
     */
    PropertyValueType valueType();

    /**
     * 属性结构类型
     *
     * @return
     */
    PropertyStructureType structureType();

    /**
     * 获取属性关联的输入类型
     *
     * @return
     */
    Input input();

    /**
     * 属性值来源
     *
     * @return
     */
    ValuesSource valuesSource();

    /**
     * 属性约束列表
     *
     * @return
     */
    List<Constraint> constraints();


    /**
     * 当 {@link #structureType()}为层级型属性(node,leaf)时，返回他的上级属性id.
     *
     * @return
     */
    Long refId();
}
