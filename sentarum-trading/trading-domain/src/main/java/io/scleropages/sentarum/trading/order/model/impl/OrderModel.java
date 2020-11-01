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

import io.scleropages.sentarum.trading.order.model.*;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderModel implements Order {

    private Long id;
    private String outerId;
    private Integer sellerType;
    private Long sellerUnionId;
    private Long sellerId;
    private Long buyerId;
    private String buyerName;
    private Date createTime;
    private Date payTime;
    private Date deliveryTime;
    private Date deliveryConfirmTime;
    private OrderSeller orderSeller;
    private OrderBuyer orderBuyer;
    private OrderDelivery orderDelivery;
    private OrderChannel orderChannel;
    private OrderConsignee orderConsignee;
    private OrderInvoice orderInvoice;
    private OrderPay orderPay;
    private OrderRefund orderRefund;
    private OrderRemark orderRemark;
    private List<? extends OrderLine> orderLines;
    private List<? extends OrderPromotion> orderPromotions;

    public Long getId() {
        return id;
    }

    public String getOuterId() {
        return outerId;
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

    public Long getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public Date getDeliveryConfirmTime() {
        return deliveryConfirmTime;
    }

    public OrderSeller getOrderSeller() {
        return orderSeller;
    }

    public OrderBuyer getOrderBuyer() {
        return orderBuyer;
    }

    public OrderDelivery getOrderDelivery() {
        return orderDelivery;
    }

    public OrderChannel getOrderChannel() {
        return orderChannel;
    }

    public OrderConsignee getOrderConsignee() {
        return orderConsignee;
    }

    public OrderInvoice getOrderInvoice() {
        return orderInvoice;
    }

    public OrderPay getOrderPay() {
        return orderPay;
    }

    public OrderRefund getOrderRefund() {
        return orderRefund;
    }

    public OrderRemark getOrderRemark() {
        return orderRemark;
    }

    public List<? extends OrderLine> getOrderLines() {
        return orderLines;
    }

    public List<? extends OrderPromotion> getOrderPromotions() {
        return orderPromotions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
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

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryConfirmTime(Date deliveryConfirmTime) {
        this.deliveryConfirmTime = deliveryConfirmTime;
    }

    public void setOrderSeller(OrderSeller orderSeller) {
        this.orderSeller = orderSeller;
    }

    public void setOrderBuyer(OrderBuyer orderBuyer) {
        this.orderBuyer = orderBuyer;
    }

    public void setOrderDelivery(OrderDelivery orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public void setOrderChannel(OrderChannel orderChannel) {
        this.orderChannel = orderChannel;
    }

    public void setOrderConsignee(OrderConsignee orderConsignee) {
        this.orderConsignee = orderConsignee;
    }

    public void setOrderInvoice(OrderInvoice orderInvoice) {
        this.orderInvoice = orderInvoice;
    }

    public void setOrderPay(OrderPay orderPay) {
        this.orderPay = orderPay;
    }

    public void setOrderRefund(OrderRefund orderRefund) {
        this.orderRefund = orderRefund;
    }

    public void setOrderRemark(OrderRemark orderRemark) {
        this.orderRemark = orderRemark;
    }

    public void setOrderLines(List<? extends OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public void setOrderPromotions(List<? extends OrderPromotion> orderPromotions) {
        this.orderPromotions = orderPromotions;
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
    public Long buyerId() {
        return getBuyerId();
    }

    @Override
    public String buyerName() {
        return getBuyerName();
    }

    @Override
    public Date createTime() {
        return getCreateTime();
    }

    @Override
    public Date payTime() {
        return getPayTime();
    }

    @Override
    public Date deliveryTime() {
        return getDeliveryTime();
    }

    @Override
    public Date deliveryConfirmTime() {
        return getDeliveryConfirmTime();
    }

    @Override
    public OrderSeller orderSeller() {
        return getOrderSeller();
    }

    @Override
    public OrderBuyer orderBuyer() {
        return getOrderBuyer();
    }

    @Override
    public OrderDelivery orderDelivery() {
        return getOrderDelivery();
    }

    @Override
    public OrderChannel orderChannel() {
        return getOrderChannel();
    }

    @Override
    public OrderConsignee orderConsignee() {
        return getOrderConsignee();
    }

    @Override
    public OrderInvoice orderInvoice() {
        return getOrderInvoice();
    }

    @Override
    public OrderPay orderPay() {
        return getOrderPay();
    }

    @Override
    public OrderRefund orderRefund() {
        return getOrderRefund();
    }

    @Override
    public OrderRemark orderRemark() {
        return getOrderRemark();
    }

    @Override
    public List<? extends OrderLine> orderLines() {
        return getOrderLines();
    }

    @Override
    public List<? extends OrderPromotion> orderPromotions() {
        return getOrderPromotions();
    }
}
