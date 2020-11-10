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

import io.scleropages.sentarum.promotion.goods.model.impl.BrandGoodsSource;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link BrandGoodsSource}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public abstract class BrandGoodsSourceEntity extends RemotingGoodsSourceEntity {

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    private String brandName;


    @Column(name = "brand_id", nullable = false)
    public Long getBrandId() {
        return brandId;
    }

    @Column(name = "brand_name", nullable = false)
    public String getBrandName() {
        return brandName;
    }


    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
