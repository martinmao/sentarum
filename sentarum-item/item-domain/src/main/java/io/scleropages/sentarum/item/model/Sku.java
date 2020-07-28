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
package io.scleropages.sentarum.item.model;

import io.scleropages.sentarum.item.ge.model.Media;
import io.scleropages.sentarum.item.model.impl.CombineSkuModel;
import io.scleropages.sentarum.item.model.impl.SkuModel;
import io.scleropages.sentarum.item.property.model.PropertyValue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SKU（最小库存单位），其与商品相关联（间接关联SPU），即具体可销售的SKU由商品确定（即卖家确定）。<br>
 * 当商品和具体的销售属性值（规格属性值）确定后，即成为一个最小的可销售实体（SKU）。<br>
 * 在生成策略上参考类目销售属性 {@link io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType#SALES_PROPERTY}。
 * <p>
 * SKU往往存在不同会员角色不同价格的体系, 当前设计中并不再增加一个维度用于定义不同会员不同价格，而是
 * 直接通过 销售属性进行区分，即针对不同会员级别定义多个价格属性，在品类定义时，直接将所有会员价格属性定义到
 * 销售属性集中。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Sku {


    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 关联商品
     *
     * @return
     */
    Item item();

    /**
     * 外部编码（商家自己的sku编码）
     *
     * @return
     */
    String outerId();

    /**
     * 状态
     *
     * @return
     */
    Status status();

    /**
     * 售价
     *
     * @return
     */
    BigDecimal salesPrice();

    /**
     * 库存量, 少于等于 {@link Item#num()}，如果是 {@link CombineSku}，该值需要统一库存量.
     *
     * @return
     */
    Integer quantity();


    /**
     * 销售属性
     *
     * @return
     */
    List<PropertyValue> salesProperties();

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


    enum SkuType {

        SKU(1, "SKU", "单独的sku", SkuModel.class),

        COMBINE_SKU(2, "Sku组合", "一组聚合的sku列表", CombineSkuModel.class);

        private final int ordinal;
        private final String tag;
        private final String desc;
        private final Class implementationClass;

        SkuType(int ordinal, String tag, String desc, Class implementationClass) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
            this.implementationClass = implementationClass;
        }


        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public String getDesc() {
            return desc;
        }

        public Class getImplementationClass() {
            return implementationClass;
        }

    }


    enum Status {
        VALID(1, "有效"), INVALID(2, "无效");


        Status(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        private final int ordinal;
        private final String tag;

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
