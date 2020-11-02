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

import javax.persistence.*;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderLineModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_line")
@SequenceGenerator(name = "td_order_line_id", sequenceName = "seq_td_order_line", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OrderLineEntity extends IdEntity {

    private Long itemId;
    private Long skuId;
    private String outerItemId;
    private String outerSkuId;
    private EmbeddableAmount price;
    private Integer num;
    private EmbeddableAmount totalFee;
    private EmbeddableAmount payment;
    private Boolean present;
    private OrderEntity order;

    @Column(name = "item_id", nullable = false)
    public Long getItemId() {
        return itemId;
    }

    @Column(name = "sku_id", nullable = false)
    public Long getSkuId() {
        return skuId;
    }

    @Column(name = "outer_item_id", nullable = false)
    public String getOuterItemId() {
        return outerItemId;
    }

    @Column(name = "outer_sku_id", nullable = false)
    public String getOuterSkuId() {
        return outerSkuId;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "price_", nullable = false))
    public EmbeddableAmount getPrice() {
        return price;
    }

    @Column(name = "num", nullable = false)
    public Integer getNum() {
        return num;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_fee", nullable = false))
    })
    public EmbeddableAmount getTotalFee() {
        return totalFee;
    }

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "payment_", nullable = false))
    })
    public EmbeddableAmount getPayment() {
        return payment;
    }

    @Column(name = "is_present", nullable = false)
    public Boolean getPresent() {
        return present;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setOuterItemId(String outerItemId) {
        this.outerItemId = outerItemId;
    }

    public void setOuterSkuId(String outerSkuId) {
        this.outerSkuId = outerSkuId;
    }

    public void setPrice(EmbeddableAmount price) {
        this.price = price;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setTotalFee(EmbeddableAmount totalFee) {
        this.totalFee = totalFee;
    }

    public void setPayment(EmbeddableAmount payment) {
        this.payment = payment;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
