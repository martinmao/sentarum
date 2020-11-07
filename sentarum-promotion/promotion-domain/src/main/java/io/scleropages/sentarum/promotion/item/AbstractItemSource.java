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
package io.scleropages.sentarum.promotion.item;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractItemSource implements ItemSource {

    private Long id;

    private ItemSourceType itemSourceType;

    private Integer bizType;

    private Long bizId;

    public Long getId() {
        return id;
    }

    public ItemSourceType getItemSourceType() {
        return itemSourceType;
    }

    public Integer getBizType() {
        return bizType;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemSourceType(ItemSourceType itemSourceType) {
        this.itemSourceType = itemSourceType;
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
    public ItemSourceType itemSourceType() {
        return getItemSourceType();
    }


    @Override
    public Integer bizType() {
        return getBizType();
    }

    @Override
    public Long bizId() {
        return getBizId();
    }


    /**
     * 子类必须覆写的方法，执行数据读取的逻辑.
     *
     * @return
     */
    @Override
    public ItemSourceReader itemSourceReader() {
        throw new IllegalStateException("item source not initialized.");
    }
}
