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

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarter;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarterFactory;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.context.CartPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.GoodsPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.OrderPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.impl.ActivityPromotionInvocationChain;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CartPromotionChainStarterFactory implements PromotionChainStarterFactory<CartPromotionContext> {


    @Override
    public PromotionChainStarter createChainStarter(CartPromotionContext cartPromotionContext, RuleContainer ruleContainer) {
        Assert.notNull(cartPromotionContext, "cartPromotionContext must not be null.");
        HeadOfChain headOfChain = createHeadOfChain(cartPromotionContext, ruleContainer);//仅一个cart head of chain.
        List<HeadOfChain> headOfOrdersChain = Lists.newArrayList();//每个订单一个head of chain.
        List<HeadOfChain> headOfGoodsChain = Lists.newArrayList();//每个商品一个head of chain.
        for (OrderPromotionContext orderPromotionContext : cartPromotionContext.orderPromotionContexts()) {
            headOfOrdersChain.add(createHeadOfChain(orderPromotionContext, ruleContainer));
            for (GoodsPromotionContext goodsPromotionContext : orderPromotionContext.goodsPromotionContexts()) {
                headOfGoodsChain.add(createHeadOfChain(goodsPromotionContext, ruleContainer));
            }
        }
        return new CartPromotionChainStarter(headOfChain, headOfOrdersChain, headOfGoodsChain);
    }

    private HeadOfChain createHeadOfChain(PromotionContext promotionContext, RuleContainer ruleContainer) {
        ActivityPromotionInvocationChain previousChain = null;
        List<Activity> activities = promotionContext.activities();
        for (int i = activities.size() - 1; i > -1; i--) {
            ActivityPromotionInvocationChain orderChain = new ActivityPromotionInvocationChain(promotionContext.activities(), ruleContainer, previousChain);
            previousChain = orderChain;
        }
        return new HeadOfChain(previousChain, promotionContext);
    }


    protected class HeadOfChain {
        private final InvocationChain invocationChain;
        private final InvocationContext invocationContext;

        private HeadOfChain(InvocationChain invocationChain, InvocationContext invocationContext) {
            Assert.notNull(invocationContext, "invocationContext must not be null.");
            this.invocationChain = invocationChain;
            this.invocationContext = invocationContext;
        }

        protected void startInternal() {
            if (null != invocationChain)
                invocationChain.next(invocationContext);
        }
    }
}
