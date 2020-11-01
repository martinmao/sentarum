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

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.trading.order.model.LinePromotion;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderLine;
import io.scleropages.sentarum.trading.order.model.SkuSnapshot;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderLineModel implements OrderLine {


    private Long id;
    private Long itemId;
    private Long skuId;
    private String outerItemId;
    private String outerSkuId;
    private Amount price;
    private Integer num;
    private Amount totalFee;
    private Amount payment;
    private Boolean present;
    private Order order;
    private SkuSnapshot sku;
    private List<LinePromotion> linePromotions;

    public Long getId() {
        return id;
    }

    public Long getItemId() {
        return itemId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public String getOuterItemId() {
        return outerItemId;
    }

    public String getOuterSkuId() {
        return outerSkuId;
    }

    public Amount getPrice() {
        return price;
    }

    public Integer getNum() {
        return num;
    }

    public Amount getTotalFee() {
        return totalFee;
    }

    public Amount getPayment() {
        return payment;
    }

    public Boolean getPresent() {
        return present;
    }

    public Order getOrder() {
        return order;
    }

    public SkuSnapshot getSku() {
        return sku;
    }

    public List<LinePromotion> getLinePromotions() {
        return linePromotions;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPrice(Amount price) {
        this.price = price;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setTotalFee(Amount totalFee) {
        this.totalFee = totalFee;
    }

    public void setPayment(Amount payment) {
        this.payment = payment;
    }

    public void setPresent(Boolean present) {
        this.present = present;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setSku(SkuSnapshot sku) {
        this.sku = sku;
    }

    public void setLinePromotions(List<LinePromotion> linePromotions) {
        this.linePromotions = linePromotions;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long itemId() {
        return getItemId();
    }

    @Override
    public Long skuId() {
        return getSkuId();
    }

    @Override
    public String outerItemId() {
        return getOuterItemId();
    }

    @Override
    public String outerSkuId() {
        return getOuterSkuId();
    }

    @Override
    public Amount price() {
        return getPrice();
    }

    @Override
    public Integer num() {
        return getNum();
    }

    @Override
    public Amount totalFee() {
        return getTotalFee();
    }

    @Override
    public Amount payment() {
        return getPayment();
    }

    @Override
    public Boolean present() {
        return getPresent();
    }

    @Override
    public Order order() {
        return getOrder();
    }

    @Override
    public SkuSnapshot sku() {
        return getSku();
    }

    @Override
    public List<LinePromotion> linePromotions() {
        return getLinePromotions();
    }
}
