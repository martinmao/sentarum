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

import io.scleropages.sentarum.core.entity.embeddable.EmbeddableAmount;
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
    private EmbeddableAmount payment;
    private EmbeddableAmount actualPayment;
    private EmbeddableAmount totalFee;
    private EmbeddableAmount totalOrderDiscountFee;
    private EmbeddableAmount totalItemDiscountFee;
    private EmbeddableAmount adjustFee;
    private EmbeddableAmount deliveryFee;
    private OrderEntity order;

    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "payment_", nullable = false))
    public EmbeddableAmount getPayment() {
        return payment;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "actual_payment", nullable = false))
    public EmbeddableAmount getActualPayment() {
        return actualPayment;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_fee", nullable = false))
    public EmbeddableAmount getTotalFee() {
        return totalFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_order_discount_fee", nullable = false))
    public EmbeddableAmount getTotalOrderDiscountFee() {
        return totalOrderDiscountFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_item_discount_fee", nullable = false))
    public EmbeddableAmount getTotalItemDiscountFee() {
        return totalItemDiscountFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "adjust_fee", nullable = false))
    public EmbeddableAmount getAdjustFee() {
        return adjustFee;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "delivery_fee", nullable = false))
    public EmbeddableAmount getDeliveryFee() {
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

    public void setPayment(EmbeddableAmount payment) {
        this.payment = payment;
    }

    public void setActualPayment(EmbeddableAmount actualPayment) {
        this.actualPayment = actualPayment;
    }

    public void setTotalFee(EmbeddableAmount totalFee) {
        this.totalFee = totalFee;
    }

    public void setTotalOrderDiscountFee(EmbeddableAmount totalOrderDiscountFee) {
        this.totalOrderDiscountFee = totalOrderDiscountFee;
    }

    public void setTotalItemDiscountFee(EmbeddableAmount totalItemDiscountFee) {
        this.totalItemDiscountFee = totalItemDiscountFee;
    }

    public void setAdjustFee(EmbeddableAmount adjustFee) {
        this.adjustFee = adjustFee;
    }

    public void setDeliveryFee(EmbeddableAmount deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
