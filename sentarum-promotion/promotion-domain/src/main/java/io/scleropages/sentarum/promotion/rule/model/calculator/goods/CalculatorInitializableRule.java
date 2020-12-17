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

import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.InitializableRule;

import java.util.List;

/**
 * 可初始化计算规则接口.统一并简化计算规则结构.<br>
 * 结构上，该接口将详情计算规则落在 {@link CalculatorGoodsSource}上. 并向下可扩展到第二维 {@link CalculatorGoods},甚至第三维 {@link CalculatorGoodsSpecs}
 * 该接口是一个延迟初始化接口，计算引擎在实际执行计算需要时才会对规则进行初始化.初始化意味着加载配置详情信息（{@link CalculatorInitializableRuleDetailedConfigure}）.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CalculatorInitializableRule<T extends CalculatorInitializableRuleDetailedConfigure> extends CalculatorRule, InitializableRule {


    /**
     * return goods source type of this rule.
     *
     * @return
     */
    Integer goodsSourceType();

    /**
     * initializing and return detailed configures of this rule.
     *
     * @return
     */
    default List<T> detailedConfigures() {
        throw new IllegalStateException("not initialized.");
    }
}
