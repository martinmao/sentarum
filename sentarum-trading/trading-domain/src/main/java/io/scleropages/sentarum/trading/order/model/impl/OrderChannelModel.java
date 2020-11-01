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
package io.scleropages.sentarum.trading.order.model.impl;

import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderChannel;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderChannelModel implements OrderChannel {


    private Long id;
    private Boolean offlineOrder;
    private Integer channelId;
    private String name;
    private Integer secondaryChannelId;
    private String secondaryName;
    private Order order;

    public Long getId() {
        return id;
    }

    public Boolean getOfflineOrder() {
        return offlineOrder;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public String getName() {
        return name;
    }

    public Integer getSecondaryChannelId() {
        return secondaryChannelId;
    }

    public String getSecondaryName() {
        return secondaryName;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Boolean offlineOrder() {
        return getOfflineOrder();
    }

    @Override
    public Integer channelId() {
        return getChannelId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public Integer secondaryChannelId() {
        return getSecondaryChannelId();
    }

    @Override
    public String secondaryName() {
        return getSecondaryName();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
