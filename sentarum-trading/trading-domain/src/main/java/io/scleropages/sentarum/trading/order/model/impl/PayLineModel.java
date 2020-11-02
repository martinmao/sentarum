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

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderPay;
import io.scleropages.sentarum.trading.order.model.PayLine;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PayLineModel implements PayLine {


    private Long id;
    private String outerId;
    private Amount payment;
    private Date startTime;
    private Date endTime;
    private OrderPay orderPay;
    private Order order;

    public Long getId() {
        return id;
    }

    public String getOuterId() {
        return outerId;
    }

    public Amount getPayment() {
        return payment;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public OrderPay getOrderPay() {
        return orderPay;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setPayment(Amount payment) {
        this.payment = payment;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setOrderPay(OrderPay orderPay) {
        this.orderPay = orderPay;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public String outerId() {
        return getOuterId();
    }

    @Override
    public Amount payment() {
        return getPayment();
    }

    @Override
    public Date startTime() {
        return getStartTime();
    }

    @Override
    public Date endTime() {
        return getEndTime();
    }

    @Override
    public OrderPay orderPay() {
        return getOrderPay();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
