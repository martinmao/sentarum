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
package io.scleropages.sentarum.item.core.model;

import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.ge.model.Media;
import io.scleropages.sentarum.item.property.model.PropertyValue;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SPU(标准化产品单元)，创建SPU前须将其挂靠到指定类目。<br>
 * 挂靠后的SPU就继承类目中的属性 {@link io.scleropages.sentarum.item.category.model.CategoryProperty} ，包括：
 * <pre>
 * 关键属性 {@link io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType#KEY_PROPERTY}
 * SPU属性 {@link io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType#SPU_PROPERTY}
 * </pre>
 * 继承的含义为其具备这些属性项，以及属性值。这些属性值有的直接源于类目（只读，不可变），有的需要在创建过程中填写。
 * 如果为了检索需求，将规格属性（例如，淘宝京东可以直接对商品规格进行检索，尺码，颜色等）也定义为关键属性。
 * 所以在设计属性规则时，可以将 {@link io.scleropages.sentarum.item.property.model.Input}设定为多选类型支持检索, 即SPU支持的所有规格列表。
 * 而在SKU进行规则选择时，再新建一个同样的属性，但其为单选。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Spu extends Serializable {


    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 名称
     *
     * @return
     */
    String name();

    /**
     * 显示名
     *
     * @return
     */
    String tag();

    /**
     * 状态
     *
     * @return
     */
    Status status();

    /**
     * 关键属性值集 (支持检索，定位)
     *
     * @return
     */
    List<PropertyValue> keyProperties();

    /**
     * spu属性值集（普通属性）
     *
     * @return
     */
    List<PropertyValue> properties();

    /**
     * 市场价
     *
     * @return
     */
    BigDecimal marketPrice();


    /**
     * 所属类目
     *
     * @return
     */
    StandardCategory category();


    /**
     * 上市时间.
     *
     * @return
     */
    Date marketTime();


    /**
     * 媒体列表，图片，视频等.
     *
     * @return
     */
    List<Media> mediaList();


    /**
     * 扩展属性
     *
     * @return
     */
    Map<String, Object> additionalAttributes();


    enum Status {
        VALID(1, "有效"), INVALID(2, "无效");
        private final int ordinal;
        private final String tag;

        Status(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }


        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, Status> nameMappings = new HashMap<>();
        private static final Map<Integer, Status> ordinalMappings = new HashMap<>();

        static {
            for (Status status : Status.values()) {
                nameMappings.put(status.name(), status);
                ordinalMappings.put(status.getOrdinal(), status);
            }
        }


        public static Status getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static Status getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}
