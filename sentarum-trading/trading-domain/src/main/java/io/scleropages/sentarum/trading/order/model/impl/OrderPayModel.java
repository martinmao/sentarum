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

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderPayModel implements OrderPay {

    private Long id;
    private String outerId;
    private Amount payment;
    private Amount actualPayment;
    private Amount totalFee;
    private Amount totalOrderDiscountFee;
    private Amount totalItemDiscountFee;
    private Amount adjustFee;
    private Amount deliveryFee;
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

    public Amount getActualPayment() {
        return actualPayment;
    }

    public Amount getTotalFee() {
        return totalFee;
    }

    public Amount getTotalOrderDiscountFee() {
        return totalOrderDiscountFee;
    }

    public Amount getTotalItemDiscountFee() {
        return totalItemDiscountFee;
    }

    public Amount getAdjustFee() {
        return adjustFee;
    }

    public Amount getDeliveryFee() {
        return deliveryFee;
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

    public void setActualPayment(Amount actualPayment) {
        this.actualPayment = actualPayment;
    }

    public void setTotalFee(Amount totalFee) {
        this.totalFee = totalFee;
    }

    public void setTotalOrderDiscountFee(Amount totalOrderDiscountFee) {
        this.totalOrderDiscountFee = totalOrderDiscountFee;
    }

    public void setTotalItemDiscountFee(Amount totalItemDiscountFee) {
        this.totalItemDiscountFee = totalItemDiscountFee;
    }

    public void setAdjustFee(Amount adjustFee) {
        this.adjustFee = adjustFee;
    }

    public void setDeliveryFee(Amount deliveryFee) {
        this.deliveryFee = deliveryFee;
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
    public Amount actualPayment() {
        return getActualPayment();
    }

    @Override
    public Amount totalFee() {
        return getTotalFee();
    }

    @Override
    public Amount totalOrderDiscountFee() {
        return getTotalOrderDiscountFee();
    }

    @Override
    public Amount totalItemDiscountFee() {
        return getTotalItemDiscountFee();
    }

    @Override
    public Amount adjustFee() {
        return getAdjustFee();
    }

    @Override
    public Amount deliveryFee() {
        return getDeliveryFee();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
