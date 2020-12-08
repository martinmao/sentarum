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
package io.scleropages.sentarum.promotion.rule.model;

import io.scleropages.sentarum.promotion.rule.invocation.promotion.calculator.CalculateLevel;
import org.scleropages.core.util.Namings;

/**
 * base interface of promotional calculator rule.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CalculatorRule extends Rule {


    String ATTRIBUTE_PREFIX = Namings.snakeCaseName(CalculatorRule.class.getSimpleName()) + ".";

    String ATTRIBUTE_CALCULATE_LEVEL = ATTRIBUTE_PREFIX + Namings.snakeCaseName("calculateLevel");


    /**
     * calculate level of this rule.
     *
     * @return
     */
    CalculateLevel calculateLevel();

}
