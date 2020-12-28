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

/**
 * domain primitive of percentage
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Percentage {

    private Integer percentageValue;

    public Percentage(Integer percentageValue) {
        this.percentageValue = percentageValue;
        assertValid(percentageValue);
    }

    public Percentage(String percentageValue) {
        this(Integer.valueOf(percentageValue));
    }

    private final void assertValid(Integer percentageValue) {
        Assert.isTrue(percentageValue >= 1 || percentageValue <= 99, "value must ranging in 1-99(contains 1 and 99) with: " + percentageValue);
    }

    public BigDecimal finalValue(Integer calculatingValue) {
        Assert.notNull(calculatingValue, "calculatingValue must not be null.");
        return new BigDecimal(percentageValue * 0.01).multiply(new BigDecimal(calculatingValue));
    }

    public BigDecimal finalValue(String calculatingValue) {
        Assert.hasText(calculatingValue, "calculatingValue must not be null.");
        return new BigDecimal(percentageValue * 0.01).multiply(new BigDecimal(calculatingValue));
    }


    public BigDecimal finalValue(Float calculatingValue) {
        return finalValue(String.valueOf(calculatingValue));
    }

    public BigDecimal finalValue(Double calculatingValue) {
        return finalValue(String.valueOf(calculatingValue));
    }

}
