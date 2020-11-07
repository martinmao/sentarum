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

import io.scleropages.sentarum.promotion.goods.model.impl.SellerGoodsSource;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link SellerGoodsSource}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class SellerGoodsSourceEntity extends AbstractGoodsSourceEntity {

    /**
     * 商家类型
     *
     * @return
     */
    private Integer sellerType;

    /**
     * 商家唯一标识（商业综合体标识）
     *
     * @return
     */
    private Long sellerUnionId;

    /**
     * 商家唯一标识 (商业综合体内具体销售场所，例如店铺)
     *
     * @return
     */
    private Long sellerId;

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

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public void setSellerUnionId(Long sellerUnionId) {
        this.sellerUnionId = sellerUnionId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
