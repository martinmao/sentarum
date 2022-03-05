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
package io.scleropages.sentarum.cloud.v_1_0_0.model;

import java.util.List;
import java.util.Map;

/**
 * 商品创建请求.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ItemCreateRequest {

    /**
     * spu id.
     */
    private Long spuId;
    /**
     * 单品类型.
     * 1=单品, 单SKU商品，从商品下SKU列表中选取一个SKU进行买卖,其价格在sku上. 关联关系
     * 2=组合SKU商品, 购买的商品包含其内所有的sku. 聚合关系
     */
    private Integer itemType;
    /**
     * 商家类型.
     * SUPPLIER(1, "供应商", "位于供应链上游"),
     * VENDOR(2, "厂商(品牌商)", "售卖产品或服务"),
     * RETAIL(3, "零售商", "其往往是一个商业综合体，包含多家门店"),
     * STORE(4, "门店", "终端场所销售"),
     * PLATFORM(5, "平台", "xxx电商平台");
     */
    private Integer sellerType;
    /**
     * 商家唯一标识（商业综合体）
     */
    private Long sellerUnionId;
    /**
     * 商家唯一标识 (商业综合体内具体销售场所，例如店铺)
     */
    private Long sellerId;
    /**
     * 商家编码
     */
    private String sellerCode;
    /**
     * 外部编码（商家自己的货号）,存储为商家商业综合体标识_商家标识_商家货号
     */
    private String outerId;
    /**
     * 商品标题.
     */
    private String tag;
    /**
     * 商品描述.
     */
    private String description;
    /**
     * 售价.
     */
    private String salesPrice;
    /**
     * 数量.
     */
    private Integer num;

    /**
     * 扩展/自定义属性.
     */
    private Map<String, Object> additionalAttributes;

    /**
     * 属性值集合
     */
    private List<PropertyValue> propertyValues;

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public static class PropertyValue {

        /**
         * 属性元数据id.
         */
        private Long propMetaId;
        /**
         * 属性值.
         */
        private Object propertyValue;


        public Long getPropMetaId() {
            return propMetaId;
        }

        public Object getPropertyValue() {
            return propertyValue;
        }

        public void setPropMetaId(Long propMetaId) {
            this.propMetaId = propMetaId;
        }

        public void setPropertyValue(Object propertyValue) {
            this.propertyValue = propertyValue;
        }
    }


    public Long getSpuId() {
        return spuId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public Integer getSellerType() {
        return sellerType;
    }

    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getSellerCode() {
        return sellerCode;
    }

    public String getOuterId() {
        return outerId;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public String getSalesPrice() {
        return salesPrice;
    }

    public Integer getNum() {
        return num;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public void setSellerUnionId(Long sellerUnionId) {
        this.sellerUnionId = sellerUnionId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public void setSellerCode(String sellerCode) {
        this.sellerCode = sellerCode;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSalesPrice(String salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
