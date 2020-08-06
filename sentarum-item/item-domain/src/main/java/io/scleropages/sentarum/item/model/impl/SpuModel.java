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
import io.scleropages.sentarum.item.model.Spu;
import io.scleropages.sentarum.item.property.model.PropertyValue;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SpuModel implements Spu {

    private Long id;
    private String name;
    private String tag;
    private Status status;
    private List<PropertyValue> keyProperties;
    private List<PropertyValue> properties;
    private BigDecimal marketPrice;
    private StandardCategory category;
    private Date marketTime;
    private List<Media> mediaList;
    private Map<String, Object> additionalAttributes;


    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return id;
    }

    @NotEmpty(groups = Create.class)
    public String getName() {
        return name;
    }

    @NotEmpty(groups = Create.class)
    public String getTag() {
        return tag;
    }

    @NotNull(groups = Create.class)
    public Status getStatus() {
        return status;
    }

    @Null
    public List<PropertyValue> getKeyProperties() {
        return keyProperties;
    }

    @Null
    public List<PropertyValue> getProperties() {
        return properties;
    }

    @NotNull(groups = Create.class)
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    @Null
    public StandardCategory getCategory() {
        return category;
    }

    @NotNull(groups = Create.class)
    public Date getMarketTime() {
        return marketTime;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setKeyProperties(List<PropertyValue> keyProperties) {
        this.keyProperties = keyProperties;
    }

    public void setProperties(List<PropertyValue> properties) {
        this.properties = properties;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public void setCategory(StandardCategory category) {
        this.category = category;
    }

    public void setMarketTime(Date marketTime) {
        this.marketTime = marketTime;
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
    public String name() {
        return getName();
    }

    @Override
    public String tag() {
        return getTag();
    }

    @Override
    public Status status() {
        return getStatus();
    }

    @Override
    public List<PropertyValue> keyProperties() {
        return getKeyProperties();
    }

    @Override
    public List<PropertyValue> properties() {
        return getProperties();
    }

    @Override
    public BigDecimal marketPrice() {
        return getMarketPrice();
    }

    @Override
    public StandardCategory category() {
        return getCategory();
    }

    @Override
    public Date marketTime() {
        return getMarketTime();
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
