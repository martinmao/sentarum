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
package io.scleropages.sentarum.item.core.entity;

import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.ge.entity.MediaEntity;
import io.scleropages.sentarum.item.ge.entity.StructureTextEntity;
import org.scleropages.crud.dao.orm.jpa.entity.EntityAware;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Referenced model: {@link io.scleropages.sentarum.item.core.model.impl.ItemModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "item")
@SequenceGenerator(name = "item_id", sequenceName = "seq_item", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class ItemEntity extends IdEntity implements EntityAware<Object> {

    private Integer itemType;
    private Integer sellerType;
    private Long sellerUnionId;
    private Long sellerId;
    private String sellerCode;
    private String outerId;
    private String tag;
    private String description;
    private Integer status;
    private SpuEntity spu;
    private StandardCategoryEntity category;
    private BigDecimal salesPrice;
    private Integer num;
    private StructureTextEntity properties;
    private List<MediaEntity> mediaList;
    private String additionalAttributes;

    @Column(name = "item_type", nullable = false)
    public Integer getItemType() {
        return itemType;
    }

    @Column(name = "seller_type", nullable = false)
    public Integer getSellerType() {
        return sellerType;
    }

    @Column(name = "seller_union_id", nullable = false)
    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    @Column(name = "seller_id", nullable = false)
    public Long getSellerId() {
        return sellerId;
    }

    @Column(name = "seller_code", nullable = false)
    public String getSellerCode() {
        return sellerCode;
    }

    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @Column(name = "tag_", nullable = false)
    public String getTag() {
        return tag;
    }

    @Column(name = "desc_", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "status_", nullable = false)
    public Integer getStatus() {
        return status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spu_id", nullable = false)
    public SpuEntity getSpu() {
        return spu;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "std_category_id", nullable = false)
    public StandardCategoryEntity getCategory() {
        return category;
    }

    @Column(name = "sales_price", nullable = false)
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    @Column(name = "num", nullable = false)
    public Integer getNum() {
        return num;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "props_struct_text_id")
    public StructureTextEntity getProperties() {
        return properties;
    }

    @OneToMany
    @JoinTable(name = "item_media", uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "media_id"}), joinColumns = {@JoinColumn(name = "item_id")}, inverseJoinColumns = {@JoinColumn(name = "media_id")})
    public List<MediaEntity> getMediaList() {
        return mediaList;
    }

    @Column(name = "attrs_payload")
    public String getAdditionalAttributes() {
        return additionalAttributes;
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSpu(SpuEntity spu) {
        this.spu = spu;
    }

    public void setCategory(StandardCategoryEntity category) {
        this.category = category;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setProperties(StructureTextEntity properties) {
        this.properties = properties;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setMediaList(List<MediaEntity> mediaList) {
        this.mediaList = mediaList;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public void setEntity(Object entity) {
        if (entity instanceof SpuEntity)
            setSpu((SpuEntity) entity);
        else if (entity instanceof StandardCategoryEntity)
            setCategory((StandardCategoryEntity) entity);
    }
}
