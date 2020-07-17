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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品，将SPU关联到商家维度的信息结构，淘宝称为item，京东称为product<br>
 * 在商家上架一个商品时，其需先选择一个标准的SPU进行挂靠。
 * 可以理解为商品为SPU的具象化。
 * 在生成策略上，参考 {@link io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType#ITEM_PROPERTY}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Item {


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
     * 外部编码（商家自己的货号）
     *
     * @return
     */
    String outerId();

    /**
     * 名称
     *
     * @return
     */
    String name();

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
     * 关联的SPU
     *
     * @return
     */
    Spu spu();


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
     * 媒体列表，图片，视频等.
     *
     * @return
     */
    List<Media> mediaList();

    ////////////////// 可定义为商品属性，暂时列举如下 /////////////////////////

    /**
     * 运费模板id
     *
     * @return
     */
    Long postageId();

    /**
     * 平邮费用
     *
     * @return
     */
    BigDecimal postFee();


    /**
     * 快递费用
     *
     * @return
     */
    BigDecimal expressFee();

    /**
     * ems费用
     *
     * @return
     */
    BigDecimal emsFee();


    /**
     * 运费承担人，卖家，卖家，平台....
     *
     * @return
     */
    Integer freightPayer();

    /**
     * 扩展属性
     *
     * @return
     */
    Map<String, Object> additionalAttributes();


    enum ItemType {


    }

    enum Status {
    }

    enum SellerType {

        SUPPLIER(1, "供应商", "位于供应链上游"),
        VENDOR(2, "厂商(品牌商)", "售卖产品或服务"),
        RETAIL(3, "零售商", "其往往是一个商业综合体，包含多家门店"),
        STORE(4, "门店", "终端场所销售");

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
    }
}
