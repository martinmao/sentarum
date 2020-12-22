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
package io.scleropages.sentarum.promotion.rule.impl;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarter;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarterFactory;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.context.CartPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.GoodsPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.OrderPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class DefaultPromotionChainStarterFactory implements PromotionChainStarterFactory<CartPromotionContext> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * create chain starter by given cart promotion context.
     *
     * @param cartPromotionContext
     * @param ruleContainer        container of rules.
     * @return
     */
    @Override
    public PromotionChainStarter createChainStarter(CartPromotionContext cartPromotionContext, RuleContainer ruleContainer) {
        Assert.notNull(cartPromotionContext, "cartPromotionContext must not be null.");
        //group by order dimension and add head chain(from goods chain's list).
        List<ActivityPromotionInvocationChain> headOfChains = Lists.newArrayList();
        for (OrderPromotionContext orderPromotionContext : cartPromotionContext.orderPromotionContexts()) {
            //order chain is last chain in goods calculating chains.
            ActivityPromotionInvocationChain nextGoodsChain = createChain(orderPromotionContext, ruleContainer, null, "order-calculating-> {seller union id: " + orderPromotionContext.sellerUnionId() + ", seller id: " + orderPromotionContext.sellerId() + "}");
            List<GoodsPromotionContext> goodsPromotionContexts = orderPromotionContext.goodsPromotionContexts();
            for (int i = goodsPromotionContexts.size() - 1; i > -1; i--) {//reverse iterator for building goods chain's.
                GoodsPromotionContext goodsPromotionContext = goodsPromotionContexts.get(i);
                nextGoodsChain = createChain(goodsPromotionContext, ruleContainer, nextGoodsChain, "goods-calculating-> {goods id: " + goodsPromotionContext.goodsId() + ", goods specs id: " + goodsPromotionContext.specsId() + "}");
            }
            //add head chain.
            headOfChains.add(nextGoodsChain);
        }
        //cart chain as final chain for calculating.
        ActivityPromotionInvocationChain finalChain = createChain(cartPromotionContext, ruleContainer, null, "cart-calculating-> {buyer id: " + cartPromotionContext.buyerId() + ", channel type: " + cartPromotionContext.channelType() + ", channel id: " + cartPromotionContext.channelId() + "}");
        if (logger.isDebugEnabled()) {
            logger.debug("creating promotion chain starter: ");
            for (int i = 0; i < headOfChains.size(); i++) {
                ActivityPromotionInvocationChain headChain = headOfChains.get(i);
                logger.debug(" HeadChain[{}]->{}", i, headChain.name());
                headChain.logInformation(logger);
            }
            logger.debug(" FinalChain->{}", finalChain.name());
            finalChain.logInformation(logger);
        }
        return new DefaultPromotionChainStarter(cartPromotionContext, headOfChains, finalChain);
    }

    private ActivityPromotionInvocationChain createChain(PromotionContext promotionContext, RuleContainer ruleContainer, InvocationChain nextChain, String name) {
        return new ActivityPromotionInvocationChain(promotionContext, ruleContainer, nextChain, name);
    }
}
