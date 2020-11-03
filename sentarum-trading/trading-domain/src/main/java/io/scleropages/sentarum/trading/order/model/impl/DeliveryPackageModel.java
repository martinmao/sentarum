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
import io.scleropages.sentarum.trading.order.model.DeliveryPackage;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderDelivery;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class DeliveryPackageModel implements DeliveryPackage {

    private Long id;
    private Date startTime;
    private Date endTime;
    private Date deliveredTime;
    private String expressNo;
    private Integer expressId;
    private String expressName;
    private String remark;
    private Order order;
    private OrderDelivery orderDelivery;
    private Integer expressState;
    private Amount expressFee;


    public Long getId() {
        return id;
    }


    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getDeliveredTime() {
        return deliveredTime;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public Integer getExpressId() {
        return expressId;
    }

    public String getExpressName() {
        return expressName;
    }

    public String getRemark() {
        return remark;
    }

    public Order getOrder() {
        return order;
    }

    public OrderDelivery getOrderDelivery() {
        return orderDelivery;
    }

    public Integer getExpressState() {
        return expressState;
    }

    public Amount getExpressFee() {
        return expressFee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDeliveredTime(Date deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setOrderDelivery(OrderDelivery orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public void setExpressState(Integer expressState) {
        this.expressState = expressState;
    }

    public void setExpressFee(Amount expressFee) {
        this.expressFee = expressFee;
    }

    @Override
    public Long id() {
        return getId();
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
    public Date deliveredTime() {
        return getDeliveredTime();
    }

    @Override
    public String expressNo() {
        return getExpressNo();
    }

    @Override
    public Integer expressId() {
        return getExpressId();
    }

    @Override
    public String expressName() {
        return getExpressName();
    }

    @Override
    public String remark() {
        return getRemark();
    }

    @Override
    public Order order() {
        return getOrder();
    }

    @Override
    public OrderDelivery orderDelivery() {
        return getOrderDelivery();
    }

    @Override
    public Integer expressState() {
        return getExpressState();
    }

    @Override
    public Amount expressFee() {
        return getExpressFee();
    }
}
