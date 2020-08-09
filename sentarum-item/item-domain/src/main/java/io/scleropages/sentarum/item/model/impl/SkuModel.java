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

import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.ge.model.Media;
import io.scleropages.sentarum.item.model.Item;
import io.scleropages.sentarum.item.model.Sku;
import io.scleropages.sentarum.item.property.model.PropertyValue;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SkuModel implements Sku {

    private Long id;
    private Item item;
    private StandardCategory category;
    private String outerId;
    private Status status;
    private BigDecimal salesPrice;
    private Integer quantity;
    private List<PropertyValue> salesProperties;
    private List<Media> mediaList;
    private Map<String, Object> additionalAttributes;

    @Null(groups = {Create.class})
    @NotNull(groups = {Update.class})
    public Long getId() {
        return id;
    }

    @Null
    public Item getItem() {
        return item;
    }

    @Null
    public StandardCategory getCategory() {
        return category;
    }

    @NotEmpty(groups = {Create.class})
    public String getOuterId() {
        return outerId;
    }

    @NotNull(groups = {Create.class})
    public Status getStatus() {
        return status;
    }

    @NotNull(groups = {Create.class})
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    @NotNull(groups = {Create.class})
    public Integer getQuantity() {
        return quantity;
    }

    @Null
    public List<PropertyValue> getSalesProperties() {
        return salesProperties;
    }

    @Null
    public List<Media> getMediaList() {
        return mediaList;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setCategory(StandardCategory category) {
        this.category = category;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSalesProperties(List<PropertyValue> salesProperties) {
        this.salesProperties = salesProperties;
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
    public Item item() {
        return getItem();
    }

    @Override
    public StandardCategory category() {
        return getCategory();
    }

    @Override
    public String outerId() {
        return getOuterId();
    }

    @Override
    public Status status() {
        return getStatus();
    }

    @Override
    public BigDecimal salesPrice() {
        return getSalesPrice();
    }

    @Override
    public Integer quantity() {
        return getQuantity();
    }

    @Override
    public List<PropertyValue> salesProperties() {
        return getSalesProperties();
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
