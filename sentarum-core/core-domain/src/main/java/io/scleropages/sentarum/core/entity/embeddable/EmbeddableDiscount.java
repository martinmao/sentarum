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
package io.scleropages.sentarum.core.entity.embeddable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.math.BigDecimal;

/**
 * referenced from {@link io.scleropages.sentarum.core.model.primitive.Discount}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Embeddable
public class EmbeddableDiscount {

    private Integer discountType;

    private BigDecimal discountValue;

    private EmbeddableAmount originalPrice;

    @Column(name = "discount_type")
    public Integer getDiscountType() {
        return discountType;
    }

    @Column(name = "discount_value")
    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "discount_original_price"))
    public EmbeddableAmount getOriginalPrice() {
        return originalPrice;
    }

    public void setDiscountType(Integer discountType) {
        this.discountType = discountType;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public void setOriginalPrice(EmbeddableAmount originalPrice) {
        this.originalPrice = originalPrice;
    }
}
