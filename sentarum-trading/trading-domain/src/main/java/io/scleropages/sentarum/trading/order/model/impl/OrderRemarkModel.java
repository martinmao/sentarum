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
import io.scleropages.sentarum.trading.order.model.OrderRemark;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderRemarkModel implements OrderRemark {

    private Long id;
    private String buyerRemark;
    private String sellerRemark;
    private Order order;

    public Long getId() {
        return id;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public String getSellerRemark() {
        return sellerRemark;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public void setSellerRemark(String sellerRemark) {
        this.sellerRemark = sellerRemark;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public String buyerRemark() {
        return getBuyerRemark();
    }

    @Override
    public String sellerRemark() {
        return getSellerRemark();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
