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

import io.scleropages.sentarum.trading.order.model.DeliveryPackage;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderDelivery;
import io.scleropages.sentarum.trading.order.model.OrderLine;
import io.scleropages.sentarum.trading.order.model.PackageLine;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PackageLineModel implements PackageLine {

    private Long id;
    private OrderLine orderLine;
    private DeliveryPackage orderDeliveryPackage;
    private OrderDelivery orderDelivery;
    private Integer num;
    private Order order;

    public Long getId() {
        return id;
    }

    public OrderLine getOrderLine() {
        return orderLine;
    }

    public DeliveryPackage getOrderDeliveryPackage() {
        return orderDeliveryPackage;
    }

    public OrderDelivery getOrderDelivery() {
        return orderDelivery;
    }

    public Integer getNum() {
        return num;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderLine(OrderLine orderLine) {
        this.orderLine = orderLine;
    }

    public void setOrderDeliveryPackage(DeliveryPackage orderDeliveryPackage) {
        this.orderDeliveryPackage = orderDeliveryPackage;
    }

    public void setOrderDelivery(OrderDelivery orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public OrderLine orderLine() {
        return getOrderLine();
    }

    @Override
    public DeliveryPackage orderDeliveryPackage() {
        return getOrderDeliveryPackage();
    }

    @Override
    public OrderDelivery orderDelivery() {
        return getOrderDelivery();
    }

    @Override
    public Integer num() {
        return getNum();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
