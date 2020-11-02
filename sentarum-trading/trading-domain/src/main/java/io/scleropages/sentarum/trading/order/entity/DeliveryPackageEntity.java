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

/**
 * referenced {@link io.scleropages.sentarum.trading.order.model.impl.DeliveryPackageModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_delivery_package")
@SequenceGenerator(name = "td_order_delivery_package_id", sequenceName = "seq_td_order_delivery_package", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class DeliveryPackageEntity extends IdEntity {

    private String expressNo;
    private Integer expressId;
    private String expressName;
    private String remark;
    private Integer expressState;
    private Amount expressFee;

    private OrderEntity order;
    private OrderDeliveryEntity orderDelivery;

    @Column(name = "express_no", nullable = false)
    public String getExpressNo() {
        return expressNo;
    }

    @Column(name = "express_id", nullable = false)
    public Integer getExpressId() {
        return expressId;
    }

    @Column(name = "express_name", nullable = false)
    public String getExpressName() {
        return expressName;
    }

    @Column(name = "remark", nullable = false)
    public String getRemark() {
        return remark;
    }

    @Column(name = "express_state", nullable = false)
    public Integer getExpressState() {
        return expressState;
    }

    @Column(name = "express_fee", nullable = false)
    public Amount getExpressFee() {
        return expressFee;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_delivery_id", nullable = false)
    public OrderDeliveryEntity getOrderDelivery() {
        return orderDelivery;
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

    public void setExpressState(Integer expressState) {
        this.expressState = expressState;
    }

    public void setExpressFee(Amount expressFee) {
        this.expressFee = expressFee;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public void setOrderDelivery(OrderDeliveryEntity orderDelivery) {
        this.orderDelivery = orderDelivery;
    }
}
