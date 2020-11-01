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

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderRefundModel implements OrderRefund {

    private Long id;
    private Order order;
    private Date requestTime;
    private String requestContent;
    private Amount totalRefundFee;
    private Integer refundState;
    private Integer refundType;
    private String refundTypeRemark;

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public Amount getTotalRefundFee() {
        return totalRefundFee;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public String getRefundTypeRemark() {
        return refundTypeRemark;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    public void setTotalRefundFee(Amount totalRefundFee) {
        this.totalRefundFee = totalRefundFee;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public void setRefundTypeRemark(String refundTypeRemark) {
        this.refundTypeRemark = refundTypeRemark;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Order order() {
        return getOrder();
    }

    @Override
    public Date requestTime() {
        return getRequestTime();
    }

    @Override
    public String requestContent() {
        return getRequestContent();
    }

    @Override
    public Amount totalRefundFee() {
        return getTotalRefundFee();
    }

    @Override
    public Integer refundState() {
        return getRefundState();
    }

    @Override
    public Integer refundType() {
        return getRefundType();
    }

    @Override
    public String refundTypeRemark() {
        return getRefundTypeRemark();
    }
}
