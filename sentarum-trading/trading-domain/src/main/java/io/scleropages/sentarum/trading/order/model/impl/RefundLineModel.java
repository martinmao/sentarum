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
import io.scleropages.sentarum.trading.order.model.OrderRefund;
import io.scleropages.sentarum.trading.order.model.RefundLine;

import java.util.Date;

/**
 *
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class RefundLineModel implements RefundLine {

    private Long id;
    private Amount refundFee;
    private Date time;
    private Integer state;
    private OrderRefund orderRefund;
    private Order order;


    public Long getId() {
        return id;
    }

    public Amount getRefundFee() {
        return refundFee;
    }

    public Date getTime() {
        return time;
    }

    public Integer getState() {
        return state;
    }

    public OrderRefund getOrderRefund() {
        return orderRefund;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRefundFee(Amount refundFee) {
        this.refundFee = refundFee;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setOrderRefund(OrderRefund orderRefund) {
        this.orderRefund = orderRefund;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Amount refundFee() {
        return getRefundFee();
    }

    @Override
    public Date time() {
        return getTime();
    }

    @Override
    public Integer state() {
        return getState();
    }

    @Override
    public OrderRefund orderRefund() {
        return getOrderRefund();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
