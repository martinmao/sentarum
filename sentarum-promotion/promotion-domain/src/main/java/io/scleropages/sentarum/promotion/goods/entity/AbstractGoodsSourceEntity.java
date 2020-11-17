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
package io.scleropages.sentarum.promotion.goods.entity;

import io.scleropages.sentarum.promotion.goods.model.impl.AbstractGoodsSource;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link AbstractGoodsSource}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public abstract class AbstractGoodsSourceEntity extends IdEntity {


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_GOODS_SOURCE_TYPE = "goods_source_type";
    public static final String COLUMN_BIZ_TYPE = "biz_type";
    public static final String COLUMN_BIZ_ID = "biz_id";
    public static final String COLUMN_ATTRIBUTE_PAYLOAD = "attribute_payload";

    private Integer goodsSourceType;

    private String comment;

    private Integer bizType;

    private Long bizId;

    private String attributePayLoad;

    @Column(name = COLUMN_GOODS_SOURCE_TYPE, nullable = false)
    public Integer getGoodsSourceType() {
        return goodsSourceType;
    }

    @Column(name = "comment_", nullable = false)
    public String getComment() {
        return comment;
    }

    @Column(name = COLUMN_BIZ_TYPE, nullable = false)
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = COLUMN_BIZ_ID, nullable = false)
    public Long getBizId() {
        return bizId;
    }

    @Column(name = COLUMN_ATTRIBUTE_PAYLOAD)
    public String getAttributePayLoad() {
        return attributePayLoad;
    }

    public void setGoodsSourceType(Integer goodsSourceType) {
        this.goodsSourceType = goodsSourceType;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public void setAttributePayLoad(String attributePayLoad) {
        this.attributePayLoad = attributePayLoad;
    }
}
