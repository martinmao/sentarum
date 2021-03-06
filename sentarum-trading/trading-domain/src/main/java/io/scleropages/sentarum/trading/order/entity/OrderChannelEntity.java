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
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderChannelModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_channel")
@SequenceGenerator(name = "td_order_channel_id", sequenceName = "seq_td_order_channel", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OrderChannelEntity extends IdEntity {

    private Boolean offlineOrder;
    private Integer channelId;
    private String name;
    private Integer secondaryChannelId;
    private String secondaryName;
    private OrderEntity order;

    @Column(name = "is_offline_order", nullable = false)
    public Boolean getOfflineOrder() {
        return offlineOrder;
    }

    @Column(name = "channel_id", nullable = false)
    public Integer getChannelId() {
        return channelId;
    }

    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "secondary_channel_id", nullable = false)
    public Integer getSecondaryChannelId() {
        return secondaryChannelId;
    }

    @Column(name = "secondary_name", nullable = false)
    public String getSecondaryName() {
        return secondaryName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setOfflineOrder(Boolean offlineOrder) {
        this.offlineOrder = offlineOrder;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondaryChannelId(Integer secondaryChannelId) {
        this.secondaryChannelId = secondaryChannelId;
    }

    public void setSecondaryName(String secondaryName) {
        this.secondaryName = secondaryName;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
