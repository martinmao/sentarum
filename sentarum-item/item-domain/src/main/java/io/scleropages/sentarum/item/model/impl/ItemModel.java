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
package io.scleropages.sentarum.item.model.impl;

import io.scleropages.sentarum.item.ge.model.Media;
import io.scleropages.sentarum.item.model.Item;
import io.scleropages.sentarum.item.model.Spu;
import io.scleropages.sentarum.item.property.model.PropertyValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ItemModel implements Item {


    private Long id;
    private ItemType itemType;
    private SellerType sellerType;
    private Long sellerUnionId;
    private Long sellerId;
    private String sellerCode;
    private String outerId;
    private String tag;
    private String description;
    private Status status;
    private Spu spu;
    private BigDecimal salesPrice;
    private Integer num;
    private List<PropertyValue> properties;
    private List<Media> mediaList;
    private Map<String, Object> additionalAttributes;

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return id;
    }

    @NotNull(groups = Create.class)
    public ItemType getItemType() {
        return itemType;
    }

    @NotNull(groups = Create.class)
    public SellerType getSellerType() {
        return sellerType;
    }

    @NotNull(groups = Create.class)
    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    @NotNull(groups = Create.class)
    public Long getSellerId() {
        return sellerId;
    }

    @NotNull(groups = Create.class)
    public String getSellerCode() {
        return sellerCode;
    }

    @NotNull(groups = Create.class)
    public String getOuterId() {
        return outerId;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Spu getSpu() {
        return spu;
    }

    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    public Integer getNum() {
        return num;
    }

    public List<PropertyValue> getProperties() {
        return properties;
    }

    public List<Media> getMediaList() {
        return mediaList;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setSellerType(SellerType sellerType) {
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setProperties(List<PropertyValue> properties) {
        this.properties = properties;
    }

    public void setMediaList(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public ItemType itemType() {
        return getItemType();
    }

    @Override
    public SellerType sellerType() {
        return getSellerType();
    }

    @Override
    public Long sellerUnionId() {
        return getSellerUnionId();
    }

    @Override
    public Long sellerId() {
        return getSellerId();
    }

    @Override
    public String sellerCode() {
        return getSellerCode();
    }

    @Override
    public String outerId() {
        return getOuterId();
    }

    @Override
    public String tag() {
        return getTag();
    }

    @Override
    public String description() {
        return getDescription();
    }

    @Override
    public Status status() {
        return getStatus();
    }

    @Override
    public Spu spu() {
        return getSpu();
    }

    @Override
    public BigDecimal salesPrice() {
        return getSalesPrice();
    }

    @Override
    public Integer num() {
        return getNum();
    }

    @Override
    public List<PropertyValue> properties() {
        return getProperties();
    }

    @Override
    public List<Media> mediaList() {
        return getMediaList();
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return getAdditionalAttributes();
    }

    public interface Create {
    }

    public interface Update {
    }
}
