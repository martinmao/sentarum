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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderRefundModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderRefundEntity extends IdEntity {

    private Date requestTime;
    private String requestContent;
    private Amount totalRefundFee;
    private Integer refundState;
    private Integer refundType;
    private String refundTypeRemark;
    private OrderEntity order;

    @Column(name = "request_time", nullable = false)
    public Date getRequestTime() {
        return requestTime;
    }

    @Column(name = "request_content", nullable = false)
    public String getRequestContent() {
        return requestContent;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_refund_fee", nullable = false))
    public Amount getTotalRefundFee() {
        return totalRefundFee;
    }

    @Column(name = "refund_state", nullable = false)
    public Integer getRefundState() {
        return refundState;
    }

    @Column(name = "refund_type", nullable = false)
    public Integer getRefundType() {
        return refundType;
    }

    @Column(name = "refund_type_remark", nullable = false)
    public String getRefundTypeRemark() {
        return refundTypeRemark;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
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

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
