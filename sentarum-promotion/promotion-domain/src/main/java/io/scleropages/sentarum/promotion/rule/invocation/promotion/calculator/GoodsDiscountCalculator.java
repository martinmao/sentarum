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

import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;

/**
 * spi interface for {@link GoodsDiscountRule} calculating.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface GoodsDiscountCalculator extends GoodsCalculator<GoodsDiscountRule> {

    Integer ID = PROMOTION_INVOCATION_ID + 1;

    @Override
    default Integer id() {
        return ID;
    }

    @Override
    default String name() {
        return "商品促销";
    }

    @Override
    default String description() {
        return "商品折扣规则，可对活动关联的一组商品来源设置折扣，支持统一折扣（品牌，品类，店铺）、也支持商品折扣（可具体到规格）、也支持会员折扣（对应到不同的会员级别）.";
    }
}
