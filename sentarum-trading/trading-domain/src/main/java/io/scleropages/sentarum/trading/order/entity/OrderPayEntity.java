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

import io.scleropages.sentarum.core.entity.embeddable.Amount;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderPayModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_pay")
@SequenceGenerator(name = "td_order_pay_id", sequenceName = "seq_td_order_pay", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OrderPayEntity extends IdEntity {

    private String outerId;
    private Amount payment;
    private Amount actualPayment;
    private Amount totalFee;
    private Amount totalOrderDiscountFee;
    private Amount totalItemDiscountFee;
    private Amount adjustFee;
    private Amount deliveryFee;
    private OrderEntity order;

    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "payment_", nullable = false))
    public Amount getPayment() {
        return payment;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "actual_payment", nullable = false))
    public Amount getActualPayment() {
        return actualPayment;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_fee", nullable = false))
    public Amount getTotalFee() {
        return totalFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_order_discount_fee", nullable = false))
    public Amount getTotalOrderDiscountFee() {
        return totalOrderDiscountFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_item_discount_fee", nullable = false))
    public Amount getTotalItemDiscountFee() {
        return totalItemDiscountFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "adjust_fee", nullable = false))
    public Amount getAdjustFee() {
        return adjustFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "delivery_fee", nullable = false))
    public Amount getDeliveryFee() {
        return deliveryFee;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
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

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
