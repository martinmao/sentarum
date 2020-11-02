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

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.PackageLineModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_delivery_package_line")
@SequenceGenerator(name = "td_order_delivery_package_line_id", sequenceName = "seq_td_order_delivery_package_line", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class PackageLineEntity extends IdEntity {

    private OrderLineEntity orderLine;
    private DeliveryPackageEntity orderDeliveryPackage;
    private OrderDeliveryEntity orderDelivery;
    private Integer num;
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_line_id", nullable = false)
    public OrderLineEntity getOrderLine() {
        return orderLine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_delivery_package_id", nullable = false)
    public DeliveryPackageEntity getOrderDeliveryPackage() {
        return orderDeliveryPackage;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_delivery_id", nullable = false)
    public OrderDeliveryEntity getOrderDelivery() {
        return orderDelivery;
    }

    @Column(name = "num_", nullable = false)
    public Integer getNum() {
        return num;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setOrderLine(OrderLineEntity orderLine) {
        this.orderLine = orderLine;
    }

    public void setOrderDeliveryPackage(DeliveryPackageEntity orderDeliveryPackage) {
        this.orderDeliveryPackage = orderDeliveryPackage;
    }

    public void setOrderDelivery(OrderDeliveryEntity orderDelivery) {
        this.orderDelivery = orderDelivery;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
