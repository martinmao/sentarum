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
package io.scleropages.sentarum.promotion.rule.model.calculator.goods;

import io.scleropages.sentarum.promotion.goods.AdditionalAttributes;

/**
 * represent a detailed configure of {@link CalculatorInitializableRule}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CalculatorInitializableRuleDetailedConfigure<T extends CalculatorInitializableRule> {

    /**
     * inject aware callback that wish to holds associated {@link CalculatorGoodsSource}.
     *
     * @return
     */
    void setCalculatorGoodsSource(CalculatorGoodsSource calculatorGoodsSource);


    /**
     * not required extension method. determined how to recovery states(not serialize fields) from {@link AdditionalAttributes}(from holds goods source ).
     * by default. this object will serialized and write to {@link CalculatorGoodsSource#additionalAttributes()}. also read and deserialize.
     *
     * @param additionalAttributes
     */
    default void recovery(AdditionalAttributes additionalAttributes) {
    }

    /**
     * assert this configure is valid for given initializableRule.
     *
     * @param initializableRule
     */
    default void assertConfigure(T initializableRule) {

    }

    /**
     * comment of this object.
     *
     * @return
     */
    String comment();
}
