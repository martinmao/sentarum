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
package io.scleropages.sentarum.promotion.rule.model.promotion;

/**
 * <pre>
 * 套装促销，组合商品促销或打包促销，即指定一组商品设置一个总的促销价，其计算规则为总价按 {@link #getAmount()}计 或 (单价*数量+单价*数量...)* {@link #getDiscount()}记
 * 多买优惠促销，线下促销场景较为多见，例如M元任选N件，或M件N折
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CombineGoodsRule extends GoodsDiscountRule {

    /**
     * 仅适用于多买优惠促销
     * <pre>
     * M元任选N件时：用户在主商品池中选择该数量的商品计价为 {@link #getAmount()}
     * M件N折时：用户在主商品池中选择该数量的商品计价为 (单价*数量+单价*数量...)* {@link #getDiscount()}
     * </pre>
     */
    private Integer num;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
