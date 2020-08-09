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
package io.scleropages.sentarum.item.model.impl;

import io.scleropages.sentarum.item.model.CombineSkuEntry;
import io.scleropages.sentarum.item.model.Sku;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CombineSkuEntryModel implements CombineSkuEntry {

    private Long id;
    private Sku sku;
    private Float order;
    private Integer num;
    private BigDecimal salesPrice;
    private Map<String, Object> additionalAttributes;

    @Null(groups = {Create.class})
    @NotNull(groups = {Update.class})
    public Long getId() {
        return id;
    }

    @Null
    public Sku getSku() {
        return sku;
    }

    @NotNull(groups = {Create.class})
    public Float getOrder() {
        return order;
    }

    @NotNull(groups = {Create.class})
    public Integer getNum() {
        return num;
    }

    @NotNull(groups = {Create.class})
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }


    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setSalesPrice(BigDecimal salesPrice) {
        this.salesPrice = salesPrice;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Sku sku() {
        return getSku();
    }

    @Override
    public Float order() {
        return getOrder();
    }

    @Override
    public Integer num() {
        return getNum();
    }

    @Override
    public BigDecimal salesPrice() {
        return getSalesPrice();
    }

    @Override
    public Map<String, Object> additionalAttributes() {
        return getAdditionalAttributes();
    }


    public interface Create {
    }

    public interface Update {
    }
}
