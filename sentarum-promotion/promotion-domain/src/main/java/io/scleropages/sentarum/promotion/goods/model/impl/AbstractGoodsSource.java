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
package io.scleropages.sentarum.promotion.goods.model.impl;

import io.scleropages.sentarum.promotion.goods.model.GoodsSource;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractGoodsSource implements GoodsSource {

    private Long id;

    private Integer goodsSourceType;

    private String comment;

    private Integer bizType;

    private Long bizId;

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return id;
    }

    @NotNull(groups = Create.class)
    public Integer getGoodsSourceType() {
        return goodsSourceType;
    }

    @NotEmpty(groups = Create.class)
    public String getComment() {
        return comment;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Integer getBizType() {
        return bizType;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Long getBizId() {
        return bizId;
    }


    public void setId(Long id) {
        this.id = id;
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

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Integer goodsSourceType() {
        return getGoodsSourceType();
    }

    @Override
    public String comment() {
        return getComment();
    }

    @Override
    public Integer bizType() {
        return getBizType();
    }

    @Override
    public Long bizId() {
        return getBizId();
    }

    public interface Create {
    }

    public interface Update {
    }
}
