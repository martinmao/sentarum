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

import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.model.AbstractEvaluatorRule;

/**
 * 商品打折规则，直接对单个商品设置促销价，或一组商品设置折扣
 * <pre>
 * NOTE：
 * 如果要设置促销价，必须确保 {@link Activity#goodsSource()}中关联的 goods只能有一个
 * 如果设置为折扣，则{@link Activity#goodsSource()}中关联的 goods 可以包括任意个
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class GoodsDiscountRule extends AbstractEvaluatorRule {

    /**
     * 多个商品统一折扣.
     */
    private Discount discount;

    /**
     * return discount of multiple goods associated from {@link Activity#goodsSource()}.
     *
     * @return
     */
    public Discount getDiscount() {
        return discount;
    }


    /**
     * set discount of multiple goods associated from {@link Activity#goodsSource()}.
     *
     * @param discount
     */
    public void setDiscount(Discount discount) {
        this.discount = discount;
    }



}
