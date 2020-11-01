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
import io.scleropages.sentarum.trading.order.model.OrderInvoice;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderInvoiceModel implements OrderInvoice {

    private Long id;
    private String taxpayerId;
    private String tittle;
    private Integer tittleType;
    private String taxpayerEmail;
    private Integer detailType;
    private Order order;


    public Long getId() {
        return id;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public String getTittle() {
        return tittle;
    }

    public Integer getTittleType() {
        return tittleType;
    }

    public String getTaxpayerEmail() {
        return taxpayerEmail;
    }

    public Integer getDetailType() {
        return detailType;
    }

    public Order getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setTittleType(Integer tittleType) {
        this.tittleType = tittleType;
    }

    public void setTaxpayerEmail(String taxpayerEmail) {
        this.taxpayerEmail = taxpayerEmail;
    }

    public void setDetailType(Integer detailType) {
        this.detailType = detailType;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public String taxpayerId() {
        return getTaxpayerId();
    }

    @Override
    public String tittle() {
        return getTittle();
    }

    @Override
    public Integer tittleType() {
        return getTittleType();
    }

    @Override
    public String taxpayerEmail() {
        return getTaxpayerEmail();
    }

    @Override
    public Integer detailType() {
        return getDetailType();
    }

    @Override
    public Order order() {
        return getOrder();
    }
}
