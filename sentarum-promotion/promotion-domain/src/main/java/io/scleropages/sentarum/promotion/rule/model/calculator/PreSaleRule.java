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
package io.scleropages.sentarum.promotion.rule.model.calculator;

import io.scleropages.sentarum.promotion.rule.model.AbstractRule;

import java.math.BigDecimal;

/**
 * 预售促销
 * <pre>
 *     全款预购：全款缴纳订金，商品到货可直接发货(preAmount等于预售价)
 *     订金杠杆：支付一定的订金，可抵扣部分金额(通常大于订金)，商品到货后需交齐尾款(实际价格-deductionAmount)
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PreSaleRule extends AbstractRule {

    /**
     * 预订金额
     */
    private BigDecimal preAmount;

    /**
     * 可抵扣金额
     */
    private BigDecimal deductionAmount;

    public BigDecimal getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(BigDecimal preAmount) {
        this.preAmount = preAmount;
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }
}
