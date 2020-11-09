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
package io.scleropages.sentarum.promotion.goods.model;

import io.scleropages.sentarum.promotion.goods.GoodsSourceReader;

import java.util.HashMap;
import java.util.Map;

/**
 * represent a goods source.
 * 商品来源是一个配置接口，其将商品来源的配置（某一品牌，某一类目，某一三方平台，某些选定商品等）与某一业务进行绑定（例如促销），
 * 具体读取工作由策略接口 {@link GoodsSourceReader} 来实现，即reader接口根据来源的配置进行商品的读取.
 * 这在很多场景下可以使用，例如某一业务实体与商品的关系建模往往不是固定的，而是通过配置后形成的。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface GoodsSource {

    /**
     * id of this goods source.
     *
     * @return
     */
    Long id();

    /**
     * 商品来源类型
     *
     * @return
     */
    GoodsSourceType goodsSourceType();

    /**
     * 与此商品来源关联的业务类型.
     *
     * @return
     */
    Integer bizType();

    /**
     * 与此商品来源关联的业务模型标识.
     *
     * @return
     */
    Long bizId();

//    /**
//     * 目标id
//     * <pre>
//     * {@link #goodsSourceType()}== {@link GoodsSourceType#ALL} 固定为-1
//     * {@link #goodsSourceType()}== {@link GoodsSourceType#GOODS} 固定为0
//     * {@link #goodsSourceType()}== {@link GoodsSourceType#BRAND} 为品牌id
//     * {@link #goodsSourceType()}== {@link GoodsSourceType#CATEGORY} 为类目id
//     * {@link #goodsSourceType()}== {@link GoodsSourceType#SELLER} 为卖家id
//     * </pre>
//     *
//     * @return
//     */
//    Long targetId();
//
//    /**
//     * 目标综合id
//     * 仅{@link #goodsSourceType()}== {@link GoodsSourceType#SELLER} 有效，为商家unionSellerId.
//     *
//     * @return
//     */
//    Long targetUnionId();

//    /**
//     * 目标名称
//     *
//     * @return
//     */
//    String targetName();

    /**
     * reader of this goods source.
     *
     * @return
     */
    GoodsSourceReader goodsSourceReader();

    /**
     * 商品来源类型
     */
    enum GoodsSourceType {

        ALL(0, "全部商品", "平台所有商品"),
        GOODS(1, "指定商品", "选定的商品"),
        BRAND(2, "指定品牌", "品牌内所有商品"),
        CATEGORY(3, "指定营销类目", "类目内所有商品"),
        SELLER(4, "指定商家", "商家所有商品");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        GoodsSourceType(int ordinal, String tag, String desc) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
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


        private static final Map<String, GoodsSourceType> nameMappings = new HashMap<>();
        private static final Map<Integer, GoodsSourceType> ordinalMappings = new HashMap<>();

        static {
            for (GoodsSourceType goodsSourceType : GoodsSourceType.values()) {
                nameMappings.put(goodsSourceType.name(), goodsSourceType);
                ordinalMappings.put(goodsSourceType.getOrdinal(), goodsSourceType);
            }
        }


        public static GoodsSourceType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static GoodsSourceType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

}
