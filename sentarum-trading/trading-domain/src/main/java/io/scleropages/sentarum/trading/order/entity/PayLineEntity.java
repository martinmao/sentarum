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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.PayLineModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_pay_line")
@SequenceGenerator(name = "td_order_pay_line_id", sequenceName = "seq_td_order_pay_line", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class PayLineEntity extends IdEntity {

    private String outerId;
    private Amount payment;
    private Date startTime;
    private Date endTime;
    private OrderPayEntity orderPay;
    private OrderEntity order;

    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @Column(name = "payment", nullable = false)
    public Amount getPayment() {
        return payment;
    }

    @Column(name = "start_time", nullable = false)
    public Date getStartTime() {
        return startTime;
    }

    @Column(name = "end_time", nullable = false)
    public Date getEndTime() {
        return endTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_pay_id", nullable = false)
    public OrderPayEntity getOrderPay() {
        return orderPay;
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

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setOrderPay(OrderPayEntity orderPay) {
        this.orderPay = orderPay;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
