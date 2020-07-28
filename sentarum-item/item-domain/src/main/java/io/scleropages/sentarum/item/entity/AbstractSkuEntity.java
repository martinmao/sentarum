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
package io.scleropages.sentarum.item.entity;

import io.scleropages.sentarum.item.ge.entity.MediaEntity;
import io.scleropages.sentarum.item.ge.entity.StructureTextEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class AbstractSkuEntity extends IdEntity {

    private ItemEntity item;
    private String outerId;
    private Integer status;
    private BigDecimal salesPrice;
    private Integer quantity;
    private StructureTextEntity salesProperties;
    private List<MediaEntity> mediaList;
    private String additionalAttributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    public ItemEntity getItem() {
        return item;
    }

    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @Column(name = "status_", nullable = false)
    public Integer getStatus() {
        return status;
    }

    @Column(name = "sales_price", nullable = false)
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    @Column(name = "quantity_", nullable = false)
    public Integer getQuantity() {
        return quantity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "props_struct_text_id")
    public StructureTextEntity getSalesProperties() {
        return salesProperties;
    }

    @OneToMany
    @JoinTable(name = "sku_media", joinColumns = {@JoinColumn(name = "sku_id")}, inverseJoinColumns = {@JoinColumn(name = "media_id")})
    public List<MediaEntity> getMediaList() {
        return mediaList;
    }

    @Column(name = "attrs_payload")
    public String getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setSalesProperties(StructureTextEntity salesProperties) {
        this.salesProperties = salesProperties;
    }

    public void setMediaList(List<MediaEntity> mediaList) {
        this.mediaList = mediaList;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }
}
