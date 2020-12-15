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

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class GoodsPromotionContextImpl extends AbstractPromotionContext implements GoodsPromotionContext {


    private final CartPromotionContext cartPromotionContext;
    private final OrderPromotionContext orderPromotionContext;

    private final Long goodsId;
    private final String outerGoodsId;
    private final Long specsId;
    private final String outerSpecsId;
    private final Integer num;
    private final Amount originalPrice;


    protected GoodsPromotionContextImpl(CartPromotionContext cartPromotionContext, OrderPromotionContext orderPromotionContext, Long goodsId, String outerGoodsId, Long specsId, String outerSpecsId, Integer num, Amount originalPrice) {
        this.cartPromotionContext = cartPromotionContext;
        this.orderPromotionContext = orderPromotionContext;
        this.goodsId = goodsId;
        this.outerGoodsId = outerGoodsId;
        this.specsId = specsId;
        this.outerSpecsId = outerSpecsId;
        this.num = num;
        this.originalPrice = originalPrice;
    }

    @Override
    public Long goodsId() {
        return goodsId;
    }

    @Override
    public String outerGoodsId() {
        return outerGoodsId;
    }

    @Override
    public Long specsId() {
        return specsId;
    }

    @Override
    public String outerSpecsId() {
        return outerSpecsId;
    }

    @Override
    public Integer num() {
        return num;
    }

    @Override
    public Amount originalPrice() {
        return originalPrice;
    }

    @Override
    public Long sellerUnionId() {
        return orderPromotionContext.sellerUnionId();
    }

    @Override
    public Long sellerId() {
        return orderPromotionContext.sellerId();
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
