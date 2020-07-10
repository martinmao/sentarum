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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 属性值类型定义
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public enum PropertyValueType {


    /**
     * INTEGER(1, Integer.class, "整数")
     */
    INTEGER(1, Integer.class, "整数"),
    /**
     * LONG(2, Long.class, "长整数")
     */
    LONG(2, Long.class, "长整数"),
    /**
     * TEXT(3, String.class, "文本")
     */
    TEXT(3, String.class, "文本"),
    /**
     * DATE(4, Date.class, "日期")
     */
    DATE(4, Date.class, "日期"),
    /**
     * DECIMAL(5, BigDecimal.class, "小数")
     */
    DECIMAL(5, BigDecimal.class, "小数"),
    /**
     * STRUCTURE_TEXT(6, String.class, "结构化文本")
     */
    //DEV tips: 存储到单独的clob表, 表结构需要包含media type（json,html,textarea....）
    STRUCTURE_TEXT(6, String.class, "结构化文本"),
    /**
     * BOOLEAN(7, Boolean.class, "是否")
     */
    BOOLEAN(7, Boolean.class, "是否"),
    /**
     * BYTE_ARRAY(8, Byte[].class, "字节序列")
     */
    //DEV tips: 存储到单独的blob byte array表,表结构需包含二进制序列化协议
    BYTE_ARRAY(8, Byte[].class, "字节序列"),
    /**
     * PROPERTY_REF(9, Long.class, "属性关联")
     */
    //属性值关联其他属性，型号->系列->品牌
    PROPERTY_REF(9, Long.class, "属性关联");


    private static final Map<String, PropertyValueType> nameMappings = new HashMap<>(18);
    private static final Map<Integer, PropertyValueType> ordinalMappings = new HashMap<>(18);


    static {
        for (PropertyValueType propertyValueType : PropertyValueType.values()) {
            nameMappings.put(propertyValueType.name(), propertyValueType);
            ordinalMappings.put(propertyValueType.getOrdinal(), propertyValueType);
        }
    }

    /**
     * 显示指定 ordinal,避免定义顺序被意外变更.
     */
    private final int ordinal;
    /**
     * 匹配的java类型
     */
    private final Class javaType;
    /**
     * 显示名.
     */
    private final String tag;

    PropertyValueType(int ordinal, Class javaType, String tag) {
        this.ordinal = ordinal;
        this.javaType = javaType;
        this.tag = tag;
    }


    public static PropertyValueType getByName(String name) {
        return (name != null ? nameMappings.get(name) : null);
    }

    public static PropertyValueType getByOrdinal(int ordinal) {
        return ordinalMappings.get(ordinal);
    }


    /**
     * 返回显示指定的 ordinal
     *
     * @return
     */
    public int getOrdinal() {
        return ordinal;
    }

    /**
     * 返回对应的java类型
     *
     * @return
     */
    public Class getJavaType() {
        return javaType;
    }


    public String getTag() {
        return tag;
    }}