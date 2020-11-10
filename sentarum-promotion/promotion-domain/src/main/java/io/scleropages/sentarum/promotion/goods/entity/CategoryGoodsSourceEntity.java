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

import io.scleropages.sentarum.promotion.goods.model.impl.CategoryGoodsSource;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link CategoryGoodsSource}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public abstract class CategoryGoodsSourceEntity extends RemotingGoodsSourceEntity {
    /**
     * 类目id
     */
    private Long categoryId;
    /**
     * 类目名称
     */
    private String categoryName;

    @Column(name = "category_id", nullable = false)
    public Long getCategoryId() {
        return categoryId;
    }

    @Column(name = "category_name", nullable = false)
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}