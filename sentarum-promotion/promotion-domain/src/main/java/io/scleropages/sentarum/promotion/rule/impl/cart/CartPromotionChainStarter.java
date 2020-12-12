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
package io.scleropages.sentarum.promotion.rule.impl.cart;

import io.scleropages.sentarum.promotion.rule.PromotionChainStarter;
import io.scleropages.sentarum.promotion.rule.impl.cart.CartPromotionChainStarterFactory.HeadOfChain;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CartPromotionChainStarter implements PromotionChainStarter {

    private final HeadOfChain headOfChain;//仅一个cart head of chain.
    private final List<HeadOfChain> headOfOrdersChain;//每个订单一个head of chain.
    private final List<HeadOfChain> headOfGoodsChain;//每个商品一个head of chain.

    public CartPromotionChainStarter(HeadOfChain headOfChain, List<HeadOfChain> headOfOrdersChain, List<HeadOfChain> headOfGoodsChain) {
        this.headOfChain = headOfChain;
        this.headOfOrdersChain = headOfOrdersChain;
        this.headOfGoodsChain = headOfGoodsChain;
    }

    protected HeadOfChain getHeadOfChain() {
        return headOfChain;
    }

    protected List<HeadOfChain> getHeadOfOrdersChain() {
        return headOfOrdersChain;
    }

    protected List<HeadOfChain> getHeadOfGoodsChain() {
        return headOfGoodsChain;
    }
}
