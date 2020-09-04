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
package io.scleropages.sentarum.item.core.entity;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * referenced model: {@link io.scleropages.sentarum.item.core.model.impl.CombineSkuEntryModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "item_combine_sku_entry")
@SequenceGenerator(name = "item_combine_sku_entry_id", sequenceName = "seq_item_combine_sku_entry", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class CombineSkuEntryEntity extends IdEntity {

    private SkuEntity sku;
    private CombineSkuEntity combineSku;
    private Float order;
    private Integer num;
    private BigDecimal salesPrice;
    private String additionalAttributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_id")
    public SkuEntity getSku() {
        return sku;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combine_sku_id")
    public CombineSkuEntity getCombineSku() {
        return combineSku;
    }

    @Column(name = "order_", nullable = false)
    public Float getOrder() {
        return order;
    }

    @Column(name = "num_", nullable = false)
    public Integer getNum() {
        return num;
    }

    @Column(name = "sales_price", nullable = false)
    public BigDecimal getSalesPrice() {
        return salesPrice;
    }

    @Column(name = "attrs_payload")
    public String getAdditionalAttributes() {
        return additionalAttributes;
    }

    public void setSku(SkuEntity sku) {
        this.sku = sku;
    }

    public void setCombineSku(CombineSkuEntity combineSku) {
        this.combineSku = combineSku;
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

    public void setAdditionalAttributes(String additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }
}
