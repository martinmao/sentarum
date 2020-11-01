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
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order",
        indexes = {
                @Index(columnList = "seller_type,seller_union_id,seller_id")
        })
@SequenceGenerator(name = "td_order_id", sequenceName = "seq_td_order", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OrderEntity extends IdEntity {

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

    @Column(name = "outer_id", nullable = false, unique = true)
    public String getOuterId() {
        return outerId;
    }

    @Column(name = "seller_type", nullable = false)
    public Integer getSellerType() {
        return sellerType;
    }

    @Column(name = "seller_union_id", nullable = false)
    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    @Column(name = "seller_id", nullable = false)
    public Long getSellerId() {
        return sellerId;
    }

    @Column(name = "buyer_id", nullable = false)
    public Long getBuyerId() {
        return buyerId;
    }

    @Column(name = "buyer_name", nullable = false)
    public String getBuyerName() {
        return buyerName;
    }

    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    @Column(name = "pay_time")
    public Date getPayTime() {
        return payTime;
    }

    @Column(name = "delivery_time")
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    @Column(name = "delivery_confirm_time")
    public Date getDeliveryConfirmTime() {
        return deliveryConfirmTime;
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
}
