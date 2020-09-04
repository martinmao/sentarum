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
package io.scleropages.sentarum.promotion.activity.model.rule;

import java.math.BigDecimal;

/**
 * 单品促销，直接对单个单品设置促销价，或一组单品设置折扣
 * <pre>
 * NOTE：
 * 如果要设置促销价，必须确保 {@link io.scleropages.sentarum.promotion.activity.model.Activity}中关联的 item只能有一个
 * 如果设置为折扣，则{@link io.scleropages.sentarum.promotion.activity.model.Activity}中关联的 item 可以包括任意个
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ItemPromotion extends AbstractPromotion {

    private BigDecimal amount;

    private BigDecimal discount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
