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

import io.scleropages.sentarum.promotion.goods.model.DetailedGoodsSource;
import org.scleropages.core.util.Namings;

/**
 * promotional goods source for calculator rule references.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CalculatorGoodsSource extends DetailedGoodsSource {


    int BIZ_TYPE_OF_CALCULATOR = 1;


    int GOODS_SOURCE_TYPE_OVERFLOW_DISCOUNT = 1;


    int DETAILED_GOODS_SOURCE_TYPE = 4;//商品明细


    String ADDITIONAL_ATTRIBUTE_GOODS_SOURCE_HOLDER_CLASS = Namings.snakeCaseName(CalculatorInitializableRuleDetailedConfigure.class.getSimpleName()) + ".clazz";
    String ADDITIONAL_ATTRIBUTE_GOODS_SOURCE_HOLDER_PAYLOAD = Namings.snakeCaseName(CalculatorInitializableRuleDetailedConfigure.class.getSimpleName()) + ".payload";

}
