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

    private Integer goodsSourceType;

    private String comment;

    private Integer bizType;

    private Long bizId;

    @Column(name = "goods_source_type", nullable = false)
    public Integer getGoodsSourceType() {
        return goodsSourceType;
    }

    @Column(name = "comment_", nullable = false)
    public String getComment() {
        return comment;
    }

    @Column(name = "biz_type", nullable = false)
    public Integer getBizType() {
        return bizType;
    }

    @Column(name = "biz_id", nullable = false)
    public Long getBizId() {
        return bizId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setGoodsSourceType(Integer goodsSourceType) {
        this.goodsSourceType = goodsSourceType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }
}
