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
package io.scleropages.sentarum.trading.order.model.impl;

import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderSeller;

/**
 *
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderSellerModel implements OrderSeller {


    private Long id;
    private Integer sellerType;
    private Long sellerUnionId;
    private Long sellerId;
    private Order order;

    public Long getId() {
        return id;
    }

    public Integer getSellerType() {
        return sellerType;
    }

    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Integer sellerType() {
        return getSellerType();
    }

    @Override
    public Long sellerUnionId() {
        return getSellerUnionId();
    }

    @Override
    public Long sellerId() {
        return getSellerId();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
