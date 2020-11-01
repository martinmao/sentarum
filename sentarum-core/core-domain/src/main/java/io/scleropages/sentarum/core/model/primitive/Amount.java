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
package io.scleropages.sentarum.core.model.primitive;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * domain primitive of amount.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Amount {

    private BigDecimal amount;
    private Currency currency;


    public Amount() {
        this("0.00");
    }

    /**
     * NOTE: 建议使用 {@link #Amount(String, Currency)}.
     *
     * @param amount
     * @param currency
     */
    public Amount(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * NOTE: 建议使用 {@link #Amount(String)}.
     * @param amount
     */
    public Amount(BigDecimal amount) {
        this.amount = amount;
        this.currency = Currency.getInstance(Locale.SIMPLIFIED_CHINESE);
    }

    public Amount(String amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    public Amount(String amount) {
        this(new BigDecimal(amount));
    }

    public String getAmountText() {
        return String.valueOf(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    /**
     * this + augend
     *
     * @param augend
     * @return this + augend
     */
    public Amount add(Amount augend) {
        return new Amount(getAmount().add(augend.getAmount()));
    }

    /**
     * this - subtrahend
     *
     * @param subtrahend
     * @return this - subtrahend
     */
    public Amount subtract(Amount subtrahend) {
        return new Amount(getAmount().subtract(subtrahend.getAmount()));
    }

    /**
     * this * multiplicand
     *
     * @param multiplicand
     * @return this * multiplicand
     */
    public Amount multiply(Amount multiplicand) {
        return new Amount(getAmount().multiply(multiplicand.getAmount()));
    }

    /**
     * this / divisor
     *
     * @param divisor
     * @return this / divisor
     */
    public Amount divide(Amount divisor) {
        return new Amount(getAmount().divide(divisor.getAmount()));
    }
}
