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

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule.ChannelType;
import org.springframework.util.Assert;

import java.util.List;

/**
 * builder of promotion context(s). make a easy way to create promotion context.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public final class PromotionContextBuilder {

    private ChannelType channelType;
    private Integer channelId;
    private Long buyerId;
    private List<OrderPromotionContextBuilder> orderPromotionContextBuilders = Lists.newArrayList();

    private PromotionContextBuilder() {
    }

    /**
     * create new builder instance based cart.
     *
     * @return
     */
    public static PromotionContextBuilder newBuilder() {
        return new PromotionContextBuilder();
    }


    /**
     * with channelType to this builder.
     *
     * @param channelType
     * @return
     */
    public PromotionContextBuilder withChannelType(ChannelType channelType) {
        this.channelType = channelType;
        return this;
    }

    /**
     * with channelId to this builder.
     *
     * @param channelId
     * @return
     */
    public PromotionContextBuilder withChannelId(Integer channelId) {
        this.channelId = channelId;
        return this;
    }

    /**
     * with buyerId to this builder.
     *
     * @param buyerId
     * @return
     */
    public PromotionContextBuilder withBuyerId(Long buyerId) {
        this.buyerId = buyerId;
        return this;
    }

    /**
     * add builder of {@link OrderPromotionContext} to this builder.
     *
     * @return
     */
    public OrderPromotionContextBuilder orderPromotionContextBuilder() {
        OrderPromotionContextBuilder orderPromotionContextBuilder = new OrderPromotionContextBuilder(this);
        this.orderPromotionContextBuilders.add(orderPromotionContextBuilder);
        return orderPromotionContextBuilder;
    }

    /**
     * build {@link CartPromotionContext} from this builder.
     *
     * @return
     */
    public CartPromotionContext build() {
        Assert.notNull(channelType, "channelType must not be null.");
        Assert.notNull(channelId, "channelId must not be null.");
        Assert.notNull(buyerId, "buyerId must not be null.");

        List<OrderPromotionContext> orderPromotionContexts = Lists.newArrayList();
        CartPromotionContext cartPromotionContext = new CartPromotionContextImpl(channelType, channelId, buyerId, orderPromotionContexts);
        orderPromotionContextBuilders.forEach(orderPromotionContextBuilder -> {
            List<GoodsPromotionContext> goodsPromotionContexts = Lists.newArrayList();
            OrderPromotionContext orderPromotionContext = orderPromotionContextBuilder.build(cartPromotionContext, goodsPromotionContexts);
            orderPromotionContexts.add(orderPromotionContext);
            orderPromotionContextBuilder.goodsPromotionContextBuilders.forEach(goodsPromotionContextBuilder -> {
                goodsPromotionContextBuilder.build(cartPromotionContext, orderPromotionContext);
            });
        });
        return cartPromotionContext;
    }


    /**
     * inner builder of {@link OrderPromotionContext}
     */
    public final static class OrderPromotionContextBuilder {

        private Long sellerUnionId;
        private Long sellerId;

        private List<GoodsPromotionContextBuilder> goodsPromotionContextBuilders = Lists.newArrayList();

        private final PromotionContextBuilder promotionContextBuilder;


        private OrderPromotionContextBuilder(PromotionContextBuilder promotionContextBuilder) {
            this.promotionContextBuilder = promotionContextBuilder;
        }

        /**
         * with sellerUnionId to this builder.
         *
         * @param sellerUnionId
         * @return
         */
        public OrderPromotionContextBuilder withSellerUnionId(Long sellerUnionId) {
            this.sellerUnionId = sellerUnionId;
            return this;
        }

        /**
         * with sellerId to this builder.
         *
         * @param sellerId
         * @return
         */
        public OrderPromotionContextBuilder withSellerId(Long sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        /**
         * add builder of {@link GoodsPromotionContext} to this builder.
         *
         * @return
         */
        public GoodsPromotionContextBuilder goodsPromotionContextBuilder() {
            GoodsPromotionContextBuilder goodsPromotionContextBuilder = new GoodsPromotionContextBuilder(this);
            this.goodsPromotionContextBuilders.add(goodsPromotionContextBuilder);
            return goodsPromotionContextBuilder;
        }

        /**
         * internal build implements.
         *
         * @param cartPromotionContext
         * @param goodsPromotionContexts
         * @return
         */
        private OrderPromotionContext build(CartPromotionContext cartPromotionContext, List<GoodsPromotionContext> goodsPromotionContexts) {
            return new OrderPromotionContextImpl(cartPromotionContext, sellerUnionId, sellerId, goodsPromotionContexts);
        }

        /**
         * done for this builder. and return parent builder.
         *
         * @return
         */
        public PromotionContextBuilder done() {
            Assert.notNull(sellerUnionId, "sellerUnionId must not be null.");
            Assert.notNull(sellerId, "sellerId must not be null.");
            return promotionContextBuilder;
        }

    }

    /**
     * inner builder of {@link GoodsPromotionContext}
     */
    public static final class GoodsPromotionContextBuilder {

        private Long goodsId;
        private String outerGoodsId;
        private Long specsId;
        private String outerSpecsId;
        private Integer num;

        private final OrderPromotionContextBuilder orderPromotionContextBuilder;

        private GoodsPromotionContextBuilder(OrderPromotionContextBuilder orderPromotionContextBuilder) {
            this.orderPromotionContextBuilder = orderPromotionContextBuilder;
        }

        /**
         * with goodsId to this builder.
         *
         * @param goodsId
         * @return
         */
        public GoodsPromotionContextBuilder withGoodsId(Long goodsId) {
            this.goodsId = goodsId;
            return this;
        }

        /**
         * with outerGoodsId to this builder.
         *
         * @param outerGoodsId
         * @return
         */
        public GoodsPromotionContextBuilder withOuterGoodsId(String outerGoodsId) {
            this.outerGoodsId = outerGoodsId;
            return this;
        }

        /**
         * with specsId to this builder.
         *
         * @param specsId
         * @return
         */
        public GoodsPromotionContextBuilder withSpecsId(Long specsId) {
            this.specsId = specsId;
            return this;
        }

        /**
         * with outerSpecsId to this builder.
         *
         * @param outerSpecsId
         * @return
         */
        public GoodsPromotionContextBuilder withOuterSpecsId(String outerSpecsId) {
            this.outerSpecsId = outerSpecsId;
            return this;
        }

        /**
         * with num to this builder.
         *
         * @param num
         * @return
         */
        public GoodsPromotionContextBuilder withNum(Integer num) {
            this.num = num;
            return this;
        }

        /**
         * internal build implements.
         *
         * @param cartPromotionContext
         * @param orderPromotionContext
         * @return
         */
        private GoodsPromotionContext build(CartPromotionContext cartPromotionContext, OrderPromotionContext orderPromotionContext) {
            return new GoodsPromotionContextImpl(cartPromotionContext, orderPromotionContext, goodsId, outerGoodsId, specsId, outerSpecsId, num);
        }

        /**
         * done for this builder. and return parent builder.
         *
         * @return
         */
        public OrderPromotionContextBuilder done() {
            Assert.notNull(goodsId, "goodsId must not be null.");
            Assert.notNull(num, "num must not be null.");
            return orderPromotionContextBuilder;
        }

    }

}
