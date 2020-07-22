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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 属性定义，用于描述一个属性元数据定义.
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
        FLAT_PROPERTY(1, "扁平属性"),

        /**
         * 存在层级的属性,root节点,其属性值会关联一组其他属性（ {@link #HIERARCHY_NODE_PROPERTY} 或 {@link #HIERARCHY_LEAF_PROPERTY} ）
         * 这种属性结构的划分是为了更好的进行属性分类，导航。避免属性过多而无法穷举筛选的情况.
         * 例如品牌，系列，型号这三种属性就存在层次关系(品牌确定系列，系列确定型号)，类似还有款式-尺码-颜色同样存在类似约束
         */
        HIERARCHY_ROOT_PROPERTY(2, "层级属性-根"),
        /**
         * 存在层级的属性，node节点，其属性值会关联一组其他属性 （ {@link #HIERARCHY_NODE_PROPERTY} 或 {@link #HIERARCHY_LEAF_PROPERTY} ）.
         */
        HIERARCHY_NODE_PROPERTY(3, "层级属性-节点"),
        /**
         * 存在层级的属性，leaf节点，其属性值作为整个层次的最终叶子节点.
         */
        HIERARCHY_LEAF_PROPERTY(4, "层级属性-叶");

        private final int ordinal;
        private final String tag;

        PropertyStructureType(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, PropertyStructureType> nameMappings = new HashMap<>();
        private static final Map<Integer, PropertyStructureType> ordinalMappings = new HashMap<>();

        static {
            for (PropertyStructureType structureType : PropertyStructureType.values()) {
                nameMappings.put(structureType.name(), structureType);
                ordinalMappings.put(structureType.getOrdinal(), structureType);
            }
        }

        public static PropertyStructureType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static PropertyStructureType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

    /**
     * 标识
     *
     * @return
     */
    Long id();

    /**
     * 属性名称格式规范：
     * 采用snack_case命名写法： 所有字母小写，单词之间用'_'分隔。
     * 在实际应用过程中，建议通过"."作为属性名字分隔符，例如：
     * <pre>
     *
     *     food_security.product_date_start:开始生产日期
     *     food_security.product_date_end：结束生产日期
     *     food_security.stock_date_start：进货开始日期
     *     food_security.stock_date_end：进货结束日期
     *     food_security.health_product_no：卫食健字号
     *     将food_security定义为属性组 食品安全，该组下所有属性命名都带上组前缀，
     *     系统不会做这步操作，这些约定均由用户定义.
     * </pre>
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
     * 属性业务类型:以及业务分类，在属性挂靠之前设置，例如类目属性，SPU属性，SKU属性,商品属性，其他业务属性....
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
     * 当 {@link #structureType()}为层级型属性(node,leaf)时，返回他的上级属性id. root除外.
     *
     * @return
     */
    Long refId();
}
