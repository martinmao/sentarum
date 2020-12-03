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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品，将SPU关联到商家维度的信息化结构. <br>
 * 在商家上架一个商品时，需先选择一个标准的SPU进行挂靠, 商品为SPU的具象化。
 * 在生成策略上，参考 {@link io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType#ITEM_PROPERTY}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Item extends Serializable {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 商品类型
     *
     * @return
     */
    ItemType itemType();

    /**
     * 商家类型
     *
     * @return
     */
    SellerType sellerType();

    /**
     * 商家唯一标识（商业综合体标识）
     *
     * @return
     */
    Long sellerUnionId();

    /**
     * 商家唯一标识 (商业综合体内具体销售场所，例如店铺)
     *
     * @return
     */
    Long sellerId();

    /**
     * 商家编码
     *
     * @return
     */
    String sellerCode();

    /**
     * 外部编码（商家自己的货号）,存储为商家商业综合体标识_商家标识_商家货号
     *
     * @return
     */
    String outerId();

    /**
     * 标题
     *
     * @return
     */
    String tag();

    /**
     * 描述
     *
     * @return
     */
    String description();


    /**
     * 商品状态
     *
     * @return
     */
    Status status();


    /**
     * 挂靠的SPU
     *
     * @return
     */
    Spu spu();

    /**
     * 挂靠的标准类目
     *
     * @return
     */
    StandardCategory category();


    /**
     * 售价(多个sku应选择最低价)
     *
     * @return
     */
    BigDecimal salesPrice();

    /**
     * 数量（多个sku库存总和）
     *
     * @return
     */
    Integer num();


    /**
     * item属性值集（商品属性）
     *
     * @return
     */
    List<PropertyValue> properties();


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

    ////////////////// 可定义为商品属性，暂时列举如下 /////////////////////////

//    /**
//     * 运费模板id
//     *
//     * @return
//     */
//    Long postageId();
//
//    /**
//     * 平邮费用
//     *
//     * @return
//     */
//    BigDecimal postFee();
//
//
//    /**
//     * 快递费用
//     *
//     * @return
//     */
//    BigDecimal expressFee();
//
//    /**
//     * ems费用
//     *
//     * @return
//     */
//    BigDecimal emsFee();
//
//
//    /**
//     * 运费承担人，卖家，卖家，平台....
//     *
//     * @return
//     */
//    Integer freightPayer();


    enum ItemType {

        ITEM(1, "单品", "单SKU商品，从商品下SKU列表中选取一个SKU进行买卖,其价格在sku上. 关联关系"),

        COMBINE_SKU_ITEM(2, "组合SKU商品", "购买的商品包含其内所有的sku. 聚合关系");


        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        ItemType(int ordinal, String tag, String desc) {
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


        private static final Map<String, ItemType> nameMappings = new HashMap<>();
        private static final Map<Integer, ItemType> ordinalMappings = new HashMap<>();

        static {
            for (ItemType itemType : ItemType.values()) {
                nameMappings.put(itemType.name(), itemType);
                ordinalMappings.put(itemType.getOrdinal(), itemType);
            }
        }


        public static ItemType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ItemType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }


    /**
     * 商品状态机定义
     * <pre>
     *     SAVED->SUBMIT->APPROVING->READY->ONLINE->TERMINATE
     *              ^         |               |
     *              |         |->REJECT       |->OFFLINE->ONLINE
     *              |               |
     *              ----------------|
     *
     * </pre>
     */
    enum Status {

        SAVED(0, "已保存", "商品已保存"),
        SUBMIT(1, "提交", "商品提交审核"),
        APPROVING(2, "审核中", "活动正在审核中..."),
        READY(3, "就绪", "审核通过，商品就绪，等待卖家上架"),
        REJECT(4, "拒绝", "审核不通过，商品被驳回"),
        ONLINE(5, "上架", "商品上架，可进行售卖"),
        OFFLINE(6, "下架", "商品下架，暂时不可售卖"),
        TERMINATE(7, "不可用", "商品终止售卖");


        Status(int ordinal, String tag, String desc) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
        }

        private final int ordinal;
        private final String tag;
        private final String desc;

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public String getDesc() {
            return desc;
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

    enum SellerType {

        SUPPLIER(1, "供应商", "位于供应链上游"),
        VENDOR(2, "厂商(品牌商)", "售卖产品或服务"),
        RETAIL(3, "零售商", "其往往是一个商业综合体，包含多家门店"),
        STORE(4, "门店", "终端场所销售"),
        PLATFORM(5, "平台", "美柠自营");

        private final int ordinal;
        private final String tag;
        private final String desc;

        SellerType(int ordinal, String tag, String desc) {
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


        private static final Map<String, SellerType> nameMappings = new HashMap<>();
        private static final Map<Integer, SellerType> ordinalMappings = new HashMap<>();

        static {
            for (SellerType sellerType : SellerType.values()) {
                nameMappings.put(sellerType.name(), sellerType);
                ordinalMappings.put(sellerType.getOrdinal(), sellerType);
            }
        }


        public static SellerType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static SellerType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}
