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
package io.scleropages.sentarum.promotion.rule.invocation.promotion.calculator;

import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule;

/**
 * 满减对应的购物车级促销.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CartOverflowDiscountCalculator extends CartCalculator<OverflowDiscountRule> {

    Integer ID = PROMOTION_INVOCATION_ID + 3;

    @Override
    default Integer id() {
        return ID;
    }

    @Override
    default String name() {
        return "平台满减促销";
    }

    @Override
    default String description() {
        return "满减：支持\"每满减(固定满减)，每满多少固定减\"，\"阶梯满减，满多少减多少等，\"满赠等规则\"";
    }

}
