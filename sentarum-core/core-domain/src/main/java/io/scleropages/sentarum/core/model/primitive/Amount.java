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

import org.springframework.util.Assert;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

/**
 * domain primitive of amount.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Amount {

    private BigDecimal amount;
    private Currency currency;


    /**
     * new amount with zero.
     */
    public Amount() {
        this(0);
    }

    /**
     * 不推荐
     *
     * @param amount
     * @param currency
     */
    public Amount(BigDecimal amount, Currency currency) {
        Assert.notNull(amount, "amount must not be null.");
        Assert.notNull(currency, "currency must not be null.");
        Assert.isTrue(amount.compareTo(new BigDecimal(0)) >= 0, "amount must more than 0.");
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * 不推荐
     *
     * @param amount
     */
    public Amount(BigDecimal amount) {
        this(amount, Currency.getInstance(Locale.SIMPLIFIED_CHINESE));
    }

    public Amount(String amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    public Amount(String amount) {
        this(new BigDecimal(amount));
    }


    public Amount(Integer amount, Currency currency) {
        this(new BigDecimal(amount), currency);
    }

    public Amount(Integer amount) {
        this(new BigDecimal(amount));
    }

    public Amount(Float amount, Currency currency) {
        this(new BigDecimal(String.valueOf(amount)), currency);
    }

    public Amount(Float amount) {
        this(new BigDecimal(String.valueOf(amount)));
    }

    public Amount(Double amount, Currency currency) {
        this(new BigDecimal(String.valueOf(amount)), currency);
    }

    public Amount(Double amount) {
        this(new BigDecimal(String.valueOf(amount)));
    }

    @Transient
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
     * this * multiplicand (scale=2 and round half up)
     *
     * @param multiplicand
     * @param keepScale    true if keep scale(2).
     * @return this * multiplicand
     */
    public Amount multiply(Amount multiplicand, boolean keepScale) {
        return new Amount(getAmount().multiply(multiplicand.getAmount()).setScale(keepScale ? 2 : 0, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * this * multiplicand (scale=2 and round half up)
     *
     * @param multiplicand
     * @param keepScale    true if keep scale(2).
     * @return this * multiplicand
     */
    public Amount multiply(Integer multiplicand, boolean keepScale) {
        return multiply(new Amount(multiplicand), keepScale);
    }

    /**
     * this / divisor (scale=2 and round half up)
     * <p>
     * NOTE: if keepScale set to false. the ROUND_DOWN(ignore any scales) as rounding mode.
     *
     * @param divisor
     * @param keepScale true if keep scale(2).
     * @return this / divisor
     */
    public Amount divide(Amount divisor, boolean keepScale) {
        return new Amount(getAmount().divide(divisor.getAmount(), keepScale ? 2 : 0, keepScale ? BigDecimal.ROUND_HALF_UP : BigDecimal.ROUND_DOWN));
    }

    /**
     * return true if this > compare
     *
     * @param compare
     * @return
     */
    public Boolean gt(Amount compare) {
        return compareTo(compare) > 0;
    }

    /**
     * return true if this >= compare
     *
     * @param compare
     * @return
     */
    public Boolean gte(Amount compare) {
        return compareTo(compare) >= 0;
    }

    /**
     * return true if this < compare
     *
     * @param compare
     * @return
     */
    public Boolean lt(Amount compare) {
        return compareTo(compare) < 0;
    }

    /**
     * return true if this <= compare
     *
     * @param compare
     * @return
     */
    public Boolean lte(Amount compare) {
        return compareTo(compare) <= 0;
    }

    /**
     * return true if this == compare
     *
     * @param compare
     * @return
     */
    public Boolean eq(Amount compare) {
        return compareTo(compare) == 0;
    }

    /**
     * return max amount.
     *
     * @param compare
     * @return
     */
    public Amount max(Amount compare) {
        return gte(compare) ? this : compare;
    }

    /**
     * return min amount.
     *
     * @param compare
     * @return
     */
    public Amount min(Amount compare) {
        return lte(compare) ? this : compare;
    }


    private int compareTo(Amount compare) {
        Assert.isTrue(Objects.equals(getCurrency(), compare.getCurrency()), "currency not match.");
        return getAmount().compareTo(compare.getAmount());
    }

    @Override
    public String toString() {
        return "Amount{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
