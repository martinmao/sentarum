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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * domain primitive of discount.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Discount {

    private DiscountType discountType;

    private BigDecimal discountValue;

    private Amount originalPrice;

    private boolean validFlag = false;

    public Discount() {
    }

    /**
     * NOTE: 不推荐
     *
     * @param discountType
     * @param discountValue
     * @param originalPrice
     */
    private Discount(DiscountType discountType, BigDecimal discountValue, Amount originalPrice) {
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.originalPrice = originalPrice;
        assertDiscount();
    }

    public Discount(DiscountType discountType, String discountValue, Amount originalPrice) {
        this(discountType, new BigDecimal(discountValue), originalPrice);
    }

    public Discount(DiscountType discountType, Integer discountValue, Amount originalPrice) {
        this(discountType, new BigDecimal(discountValue), originalPrice);
    }


    public Discount(DiscountType discountType, Float discountValue, Amount originalPrice) {
        this(discountType, String.valueOf(discountValue), originalPrice);
    }

    public Discount(DiscountType discountType, Double discountValue, Amount originalPrice) {
        this(discountType, String.valueOf(discountValue), originalPrice);
    }


    public void assertDiscount() {
        if (validFlag) return;
        Assert.notNull(discountType, "discountType must not be null.");
        Assert.notNull(discountValue, "discountValue must not be null.");

        switch (discountType) {
            case DECREASE_AMOUNT:
                Assert.notNull(originalPrice, "originalPrice must not be null.");
                Assert.isTrue(originalPrice.gt(new Amount(discountValue)), "discount value must less than original price with: " + discountType);
                break;
            case OVERRIDE_AMOUNT:
                Assert.notNull(originalPrice, "originalPrice must not be null.");
                Assert.isTrue(originalPrice.gt(new Amount(discountValue)), "discount value must less than original price with: " + discountType);
                break;
        }
        if (Objects.equals(discountType, DiscountType.DISCOUNT) || Objects.equals(discountType, DiscountType.DISCOUNT_WITHOUT_ORIGINAL_PRICE))
            try {
                int intValue = discountValue.intValueExact();
                Assert.isTrue(intValue >= 1 || intValue <= 99, "discount value must ranging in 1-99(contains 1 and 99) with: " + discountType);
            } catch (ArithmeticException e) {
                throw new IllegalArgumentException("discount value has a nonzero fractional part, or will not fit in an int with: " + discountType, e);
            }
        if (Objects.equals(discountType, DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE)
                || Objects.equals(discountType, DiscountType.DISCOUNT_WITHOUT_ORIGINAL_PRICE)) {
            Assert.isNull(originalPrice, "originalPrice must be null.");
        }
        validFlag = true;
    }

    /**
     * return final amount applying with this discount. supported discount type:
     * <pre>
     * {@link DiscountType#DECREASE_AMOUNT}
     * {@link DiscountType#OVERRIDE_AMOUNT}
     * {@link DiscountType#DISCOUNT}
     * </pre>
     *
     * @param keepScale true if keep scale(2)
     * @return
     */
    public Amount finalAmount(boolean keepScale) {
        assertDiscount();
        switch (discountType) {
            case DECREASE_AMOUNT:
                return originalPrice.subtract(new Amount(discountValue));
            case OVERRIDE_AMOUNT:
                return new Amount(discountValue);
            case DISCOUNT:
                return originalPrice.subtract(originalPrice.multiply(new Amount(discountValue.intValue() * 0.01), keepScale));
        }
        throw new IllegalStateException("illegal operation.");
    }

    /**
     * return final amount applying with this discount. supported discount type:
     * <pre>
     * {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE}
     * {@link DiscountType#DISCOUNT_WITHOUT_ORIGINAL_PRICE}
     * </pre>
     *
     * @param originalPrice
     * @param keepScale     true if keep scale(2)
     * @return
     */
    public Amount finalAmount(Amount originalPrice, boolean keepScale) {
        if (Objects.equals(discountType, DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE) || Objects.equals(discountType, DiscountType.DISCOUNT_WITHOUT_ORIGINAL_PRICE)) {
            assertDiscount();
            switch (discountType) {
                case DECREASE_WITHOUT_ORIGINAL_PRICE: {
                    Assert.isTrue(originalPrice.gt(new Amount(discountValue)), "discount value must less than original price with: " + discountType);
                    return originalPrice.subtract(new Amount(discountValue));
                }
                case DISCOUNT_WITHOUT_ORIGINAL_PRICE:
                    return originalPrice.subtract(originalPrice.multiply(new Amount(discountValue.intValue() * 0.01), keepScale));
            }
        }
        throw new IllegalStateException("illegal operation.");
    }


    /**
     * return discount amount.  supported discount type:
     * <pre>
     * {@link DiscountType#DECREASE_AMOUNT}
     * {@link DiscountType#OVERRIDE_AMOUNT}
     * {@link DiscountType#DISCOUNT}
     * </pre>
     *
     * @param keepScale
     * @return
     */
    public Amount discountAmount(boolean keepScale) {
        assertDiscount();
        switch (discountType) {
            case DECREASE_AMOUNT:
                return new Amount(discountValue);
            case OVERRIDE_AMOUNT:
                return new Amount(discountValue);
            case DISCOUNT:
                return originalPrice.multiply(new Amount(discountValue.intValue() * 0.01), keepScale);
        }
        throw new IllegalStateException("illegal operation.");
    }

    /**
     * return discount amount.  supported discount type:
     * <pre>
     * {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE}
     * {@link DiscountType#DISCOUNT_WITHOUT_ORIGINAL_PRICE}
     * </pre>
     *
     * @param originalPrice
     * @param keepScale
     * @return
     */
    public Amount discountAmount(Amount originalPrice, boolean keepScale) {
        if (Objects.equals(discountType, DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE) || Objects.equals(discountType, DiscountType.DISCOUNT_WITHOUT_ORIGINAL_PRICE)) {
            assertDiscount();
            switch (discountType) {
                case DECREASE_WITHOUT_ORIGINAL_PRICE:
                    Assert.isTrue(originalPrice.gt(new Amount(discountValue)), "discount value must less than original price with: " + discountType);
                    return new Amount(discountValue);
                case DISCOUNT_WITHOUT_ORIGINAL_PRICE:
                    return originalPrice.multiply(new Amount(discountValue.intValue() * 0.01), keepScale);
            }
        }
        throw new IllegalStateException("illegal operation.");
    }


    /**
     * return discount value amount and multiply given multiplicand. supported discount type:
     * <pre>
     * {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE}
     * </pre>
     *
     * @param multiplicand
     * @param keepScale
     * @return
     */
    public Amount multiplyDiscountAmount(Amount multiplicand, boolean keepScale) {
        if (Objects.equals(discountType, DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE)) {
            assertDiscount();
            return new Amount(discountValue).multiply(multiplicand, keepScale);
        }
        throw new IllegalStateException("illegal operation.");
    }


    public DiscountType getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public Amount getOriginalPrice() {
        return originalPrice;
    }


    public void setDiscountType(DiscountType discountType) {
        validFlag = false;
        this.discountType = discountType;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        validFlag = false;
        this.discountValue = discountValue;
    }

    public void setOriginalPrice(Amount originalPrice) {
        validFlag = false;
        this.originalPrice = originalPrice;
    }

    /**
     * 折扣类型
     */
    public enum DiscountType {

        /**
         * 减钱,在原有价格基础上减去设置金额，其值不能大于等于原价
         */
        DECREASE_AMOUNT(1, "减钱", "在原有价格基础上减去设置金额，其值不能大于等于原价"),

        /**
         * 打折,在原有价格基础上打折，该值必须在1-99之间（包含1和99)
         */
        DISCOUNT(2, "打折", "在原有价格基础上打折，该值必须在1-99之间（包含1和99)"),

        /**
         * 指定价格,直接覆盖原有价格，其值不能不能大于等于原价.
         */
        OVERRIDE_AMOUNT(3, "指定价格", "直接覆盖原有价格，其值不能不能大于等于原价."),

        /**
         * 无原价减钱，对于多商品场景可以统一设置减钱金额.
         */
        DECREASE_WITHOUT_ORIGINAL_PRICE(4, "无原价减钱", "无原价减钱，对于多商品场景可以统一设置减钱金额."),

        /**
         * 无原价打折，对于多商品场景可以统一设置折扣.该值必须在1-99之间（包含1和99)
         */
        DISCOUNT_WITHOUT_ORIGINAL_PRICE(5, "无原价打折", "无原价打折，对于多商品场景可以统一设置折扣,该值必须在1-99之间（包含1和99)");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        DiscountType(int ordinal, String tag, String desc) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public String getDesc() {
            return desc;
        }


        private static final Map<String, DiscountType> nameMappings = new HashMap<>();
        private static final Map<Integer, DiscountType> ordinalMappings = new HashMap<>();

        static {
            for (DiscountType discountType : DiscountType.values()) {
                nameMappings.put(discountType.name(), discountType);
                ordinalMappings.put(discountType.getOrdinal(), discountType);
            }
        }

        public static DiscountType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static DiscountType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountType=" + discountType +
                ", discountValue=" + discountValue +
                ", originalPrice=" + originalPrice +
                '}';
    }
}
