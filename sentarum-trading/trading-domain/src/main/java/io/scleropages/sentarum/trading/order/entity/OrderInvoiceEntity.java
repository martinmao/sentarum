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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderInvoiceModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderInvoiceEntity extends IdEntity {

    private String taxpayerId;
    private String title;
    private Integer titleType;
    private String taxpayerEmail;
    private Integer detailType;
    private OrderEntity order;


    @Column(name = "taxpayer_id", nullable = false)
    public String getTaxpayerId() {
        return taxpayerId;
    }

    @Column(name = "title_", nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(name = "title_type", nullable = false)
    public Integer getTitleType() {
        return titleType;
    }

    @Column(name = "taxpayer_email", nullable = false)
    public String getTaxpayerEmail() {
        return taxpayerEmail;
    }

    @Column(name = "detail_type", nullable = false)
    public Integer getDetailType() {
        return detailType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleType(Integer titleType) {
        this.titleType = titleType;
    }

    public void setTaxpayerEmail(String taxpayerEmail) {
        this.taxpayerEmail = taxpayerEmail;
    }

    public void setDetailType(Integer detailType) {
        this.detailType = detailType;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }
}
