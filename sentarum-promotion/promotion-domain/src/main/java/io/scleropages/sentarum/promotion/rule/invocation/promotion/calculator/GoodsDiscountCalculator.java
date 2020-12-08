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

import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.context.GoodsPromotionContext;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class GoodsDiscountCalculator implements GoodsCalculator<GoodsDiscountRule> {


    @Override
    public void calculate(GoodsDiscountRule rule, GoodsPromotionContext promotionContext, InvocationChain chain) {

    }

    @Override
    public Integer id() {
        return PROMOTION_INVOCATION_ID + 1;
    }

    @Override
    public String name() {
        return "商品促销";
    }

    @Override
    public String description() {
        return "计算规则：根据购买的商品计算商品级促销.";
    }
}
