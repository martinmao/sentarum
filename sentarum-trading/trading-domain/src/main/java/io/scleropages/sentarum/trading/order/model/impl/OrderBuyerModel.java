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

import io.scleropages.sentarum.core.model.primitive.Tel;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderBuyer;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderBuyerModel implements OrderBuyer {

    private Long id;
    private Long buyerId;
    private Tel tel;
    private String name;
    private String outerUnionId;
    private String outerId;
    private Order order;


    public Long getId() {
        return id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Tel getTel() {
        return tel;
    }

    public String getName() {
        return name;
    }

    public String getOuterUnionId() {
        return outerUnionId;
    }

    public String getOuterId() {
        return outerId;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setTel(Tel tel) {
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

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long buyerId() {
        return getBuyerId();
    }

    @Override
    public Tel tel() {
        return getTel();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public String outerUnionId() {
        return getOuterUnionId();
    }

    @Override
    public String outerId() {
        return getOuterId();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
