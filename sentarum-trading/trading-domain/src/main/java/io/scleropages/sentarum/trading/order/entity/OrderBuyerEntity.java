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
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderBuyerModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_buyer")
@SequenceGenerator(name = "td_order_buyer_id", sequenceName = "seq_td_order_buyer", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OrderBuyerEntity extends IdEntity {

    private Long buyerId;
    private String tel;
    private String name;
    private String outerUnionId;
    private String outerId;
    private OrderEntity order;


    @Column(name = "buyer_id", nullable = false)
    public Long getBuyerId() {
        return buyerId;
    }

    @Column(name = "tel_", nullable = false)
    public String getTel() {
        return tel;
    }

    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "outer_union_id", nullable = false)
    public String getOuterUnionId() {
        return outerUnionId;
    }

    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOuterUnionId(String outerUnionId) {
        this.outerUnionId = outerUnionId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
