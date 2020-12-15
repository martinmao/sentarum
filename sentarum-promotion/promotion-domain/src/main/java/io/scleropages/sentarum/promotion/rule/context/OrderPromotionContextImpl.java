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
package io.scleropages.sentarum.promotion.rule.context;

import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderPromotionContextImpl extends AbstractPromotionContext implements OrderPromotionContext {


    private final CartPromotionContext cartPromotionContext;
    private final Long sellerUnionId;
    private final Long sellerId;
    private final List<GoodsPromotionContext> goodsPromotionContexts;


    protected OrderPromotionContextImpl(CartPromotionContext cartPromotionContext, Long sellerUnionId, Long sellerId, List<GoodsPromotionContext> goodsPromotionContexts) {
        this.cartPromotionContext = cartPromotionContext;
        this.sellerUnionId = sellerUnionId;
        this.sellerId = sellerId;
        this.goodsPromotionContexts = goodsPromotionContexts;
    }

    @Override
    public Long sellerUnionId() {
        return sellerUnionId;
    }

    @Override
    public Long sellerId() {
        return sellerId;
    }

    @Override
    public List<GoodsPromotionContext> goodsPromotionContexts() {
        return Collections.unmodifiableList(goodsPromotionContexts);
    }

    @Override
    public ChannelConditionRule.ChannelType channelType() {
        return cartPromotionContext.channelType();
    }

    @Override
    public Integer channelId() {
        return cartPromotionContext.channelId();
    }

    @Override
    public Long buyerId() {
        return cartPromotionContext.buyerId();
    }
}
