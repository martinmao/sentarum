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

import io.scleropages.sentarum.core.model.primitive.Address;
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Geo;
import io.scleropages.sentarum.core.model.primitive.Tel;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderConsignee;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderConsigneeModel implements OrderConsignee {

    private Long id;
    private Date startTime;
    private Date endTime;
    private Date deliveredTime;
    private Amount deliveryAmount;
    private String name;
    private Tel tel;
    private Address address;
    private String detailAddress;
    private String postalCode;
    private Geo geo;
    private Order order;

    public Long getId() {
        return id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getDeliveredTime() {
        return deliveredTime;
    }

    public Amount getDeliveryAmount() {
        return deliveryAmount;
    }

    public String getName() {
        return name;
    }

    public Tel getTel() {
        return tel;
    }

    public Address getAddress() {
        return address;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Geo getGeo() {
        return geo;
    }

    public Order getOrder() {
        return order;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setDeliveredTime(Date deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public void setDeliveryAmount(Amount deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(Tel tel) {
        this.tel = tel;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Date startTime() {
        return getStartTime();
    }

    @Override
    public Date endTime() {
        return getEndTime();
    }

    @Override
    public Date deliveredTime() {
        return getDeliveredTime();
    }

    @Override
    public Amount deliveryAmount() {
        return getDeliveryAmount();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public Tel tel() {
        return getTel();
    }

    @Override
    public Address address() {
        return getAddress();
    }

    @Override
    public String detailAddress() {
        return getDetailAddress();
    }

    @Override
    public String postalCode() {
        return getPostalCode();
    }

    @Override
    public Geo geo() {
        return getGeo();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
