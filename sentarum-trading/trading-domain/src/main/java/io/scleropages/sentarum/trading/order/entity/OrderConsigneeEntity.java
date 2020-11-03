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

import io.scleropages.sentarum.core.entity.embeddable.EmbeddableAddress;
import io.scleropages.sentarum.core.entity.embeddable.EmbeddableGeo;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderConsigneeModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_consignee")
@SequenceGenerator(name = "td_order_consignee_id", sequenceName = "seq_td_order_consignee", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OrderConsigneeEntity extends IdEntity {


    private String name;
    private String tel;
    private EmbeddableAddress address;
    private String postalCode;
    private EmbeddableGeo geo;
    private OrderEntity order;

    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "tel_", nullable = false)
    public String getTel() {
        return tel;
    }

    @Embedded
    public EmbeddableAddress getAddress() {
        return address;
    }

    @Column(name = "postal_code", nullable = false)
    public String getPostalCode() {
        return postalCode;
    }

    @Embedded
    public EmbeddableGeo getGeo() {
        return geo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setAddress(EmbeddableAddress address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setGeo(EmbeddableGeo geo) {
        this.geo = geo;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
