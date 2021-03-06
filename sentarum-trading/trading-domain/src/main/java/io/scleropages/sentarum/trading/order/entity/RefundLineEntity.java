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
import io.scleropages.sentarum.trading.order.model.OrderRefund;
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
import java.util.Date;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.RefundLineModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_refund_line")
@SequenceGenerator(name = "td_order_refund_line_id", sequenceName = "seq_td_order_refund_line", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class RefundLineEntity extends IdEntity {

    private EmbeddableAmount refundFee;
    private Date time;
    private Integer state;
    private OrderRefundEntity orderRefund;
    private OrderEntity order;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "refund_fee", nullable = false))
    public EmbeddableAmount getRefundFee() {
        return refundFee;
    }

    @Column(name = "time_", nullable = false)
    public Date getTime() {
        return time;
    }

    @Column(name = "state_", nullable = false)
    public Integer getState() {
        return state;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_refund_id", nullable = false)
    public OrderRefundEntity getOrderRefund() {
        return orderRefund;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setRefundFee(EmbeddableAmount refundFee) {
        this.refundFee = refundFee;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setOrderRefund(OrderRefundEntity orderRefund) {
        this.orderRefund = orderRefund;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
