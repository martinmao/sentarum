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

import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.ge.entity.MediaEntity;
import io.scleropages.sentarum.item.ge.entity.StructureTextEntity;
import org.scleropages.crud.dao.orm.jpa.entity.EntityAware;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Referenced Model {@link io.scleropages.sentarum.item.model.impl.SpuModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "item_spu")
@SequenceGenerator(name = "item_spu_id", sequenceName = "seq_item_spu", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class SpuEntity extends IdEntity implements EntityAware<StandardCategoryEntity> {

    private String name;
    private String tag;
    private Integer status;
    private StructureTextEntity properties;
    private BigDecimal marketPrice;
    private StandardCategoryEntity category;
    private Date marketTime;
    private List<MediaEntity> mediaList;
    private String additionalAttributes;


    @Column(name = "name_", unique = true)
    public String getName() {
        return name;
    }

    @Column(name = "tag_", nullable = false)
    public String getTag() {
        return tag;
    }

    @Column(name = "status_", nullable = false)
    public Integer getStatus() {
        return status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "props_struct_text_id")
    public StructureTextEntity getProperties() {
        return properties;
    }

    @Column(name = "mkt_price", nullable = false)
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "std_category_id")
    public StandardCategoryEntity getCategory() {
        return category;
    }

    @Column(name = "mkt_time", nullable = false)
    public Date getMarketTime() {
        return marketTime;
    }

    @OneToMany
    @JoinTable(name = "item_spu_media", joinColumns = {@JoinColumn(name = "spu_id")}, inverseJoinColumns = {@JoinColumn(name = "media_id")})
    public List<MediaEntity> getMediaList() {
        return mediaList;
    }

    @Column(name = "attrs_payload")
    public String getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setProperties(StructureTextEntity properties) {
        this.properties = properties;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public void setCategory(StandardCategoryEntity category) {
        this.category = category;
    }

    public void setMarketTime(Date marketTime) {
        this.marketTime = marketTime;
    }

    public void setMediaList(List<MediaEntity> mediaList) {
        this.mediaList = mediaList;
    }

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public void setEntity(StandardCategoryEntity entity) {
        setCategory(entity);
    }
}
