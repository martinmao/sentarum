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
package io.scleropages.sentarum.trading.order.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderRemarkModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderRemarkEntity extends IdEntity {

    private String buyerRemark;
    private String sellerRemark;
    private OrderEntity order;

    @Column(name = "buyer_remark", nullable = false)
    public String getBuyerRemark() {
        return buyerRemark;
    }

    @Column(name = "seller_remark", nullable = false)
    public String getSellerRemark() {
        return sellerRemark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
