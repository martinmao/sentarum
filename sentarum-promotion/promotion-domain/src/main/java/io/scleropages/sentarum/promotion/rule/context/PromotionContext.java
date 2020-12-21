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
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule.ChannelType;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * base interface of promotion context.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PromotionContext extends InvocationContext {

    /**
     * 渠道类型
     *
     * @return
     */
    ChannelType channelType();

    /**
     * 渠道标识
     *
     * @return
     */
    Integer channelId();


    /**
     * 买家唯一标识
     *
     * @return
     */
    Long buyerId();


    /**
     * 活动列表
     *
     * @return
     */
    List<Activity> activities();


    /**
     * return builder of {@link PromotionResult}
     *
     * @return
     */
    default PromotionResultBuilder promotionResultBuilder() {
        return PromotionResultBuilder.newBuilder();
    }

    /**
     * add {@link PromotionResult} to current context.
     *
     * @param promotionResult
     */
    void addPromotionResult(PromotionResult promotionResult);


    /**
     * return promotion results of this context.
     *
     * @return
     */
    List<PromotionResult> promotionResults();


    /**
     * builder class of promotion result.
     */
    class PromotionResultBuilder {

        private Long id;
        private String name;
        private String description;
        private Amount adjustFee;
        private AdjustMode adjustMode = AdjustMode.DECREASE;
        private List<GiftBuilder> giftsBuilder = Lists.newArrayList();


        private PromotionResultBuilder() {

        }

        public static PromotionResultBuilder newBuilder() {
            return new PromotionResultBuilder();
        }


        /**
         * use {@link #withActivity(Activity)} instead.
         *
         * @param id
         * @return
         */
        private PromotionResultBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        /**
         * use {@link #withActivity(Activity)} instead.
         *
         * @param name
         * @return
         */
        private PromotionResultBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * use {@link #withActivity(Activity)} instead.
         *
         * @param description
         * @return
         */
        private PromotionResultBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PromotionResultBuilder withActivity(Activity activity) {
            withId(activity.id());
            withName(activity.name() + "-" + activity.tag());
            withDescription(activity.description());
            return this;
        }

        public PromotionResultBuilder withAdjustFee(Amount adjustFee) {
            this.adjustFee = adjustFee;
            return this;
        }

        public PromotionResultBuilder withAdjustMode(AdjustMode adjustMode) {
            this.adjustMode = adjustMode;
            return this;
        }

        public GiftBuilder withGift() {
            GiftBuilder giftBuilder = new GiftBuilder(this);
            this.giftsBuilder.add(giftBuilder);
            return giftBuilder;
        }

        public PromotionResult build() {
            Assert.notNull(id, "id must not be null.");
            Assert.notNull(name, "name must not be null.");
            Assert.notNull(adjustFee, "adjustFee must not be null.");

            return new PromotionResult() {
                @Override
                public Long id() {
                    return id;
                }

                @Override
                public String name() {
                    return name;
                }

                @Override
                public String description() {
                    return description;
                }

                @Override
                public Amount adjustFee() {
                    return adjustFee;
                }

                @Override
                public AdjustMode adjustMode() {
                    return adjustMode;
                }

                @Override
                public List<Gift> gifts() {
                    return Collections.unmodifiableList(giftsBuilder.stream().map(giftBuilder -> giftBuilder.build()).collect(Collectors.toList()));
                }

                @Override
                public String toString() {
                    StringBuilder sb = new StringBuilder("{ ");
                    sb.append("name: ").append(name).append(", ");
                    sb.append("adjustFee: ").append(adjustFee).append(" }");
                    return sb.toString();
                }
            };
        }
    }


    /**
     * 优惠计算结果
     */
    interface PromotionResult {

        /**
         * 唯一标识.
         *
         * @return
         */
        Long id();

        /**
         * 名称
         *
         * @return
         */
        String name();


        /**
         * 描述
         *
         * @return
         */
        String description();


        /**
         * 计算后的价格调整
         *
         * @return
         */
        Amount adjustFee();

        /**
         * 价格调整模式 + or -
         *
         * @return
         */
        AdjustMode adjustMode();

        /**
         * 赠品
         *
         * @return
         */
        List<Gift> gifts();
    }


    interface Gift {
        /**
         * 赠品唯一标识.
         */
        Long id();

        /**
         * 商品id
         */
        Long goodsId();

        /**
         * 商品规格id
         */
        Long goodsSpecsId();

        /**
         * 商品外部编码
         */
        String outerGoodsId();

        /**
         * 商品规格外部编码
         */
        String outerGoodsSpecsId();

        /**
         * 名称
         */
        String name();

        /**
         * 补差价，例如：满100元+5元送牙刷
         */
        Amount adjustFee();

        /**
         * 赠品单价
         */
        Amount price();


    }

    class GiftBuilder {

        private final PromotionResultBuilder resultBuilder;

        private Long id;
        private Long goodsId;
        private Long goodsSpecsId;
        private String outerGoodsId;
        private String outerGoodsSpecsId;
        private String name;
        private Amount adjustFee;
        private Amount price;

        public GiftBuilder(PromotionResultBuilder resultBuilder) {
            this.resultBuilder = resultBuilder;
        }

        public PromotionResultBuilder done() {
            Assert.notNull(id, "id must not be null.");
            Assert.notNull(goodsId, "goodsId must not be null.");
            Assert.notNull(goodsSpecsId, "goodsSpecsId must not be null.");
            Assert.notNull(outerGoodsId, "outerGoodsId must not be null.");
            Assert.notNull(outerGoodsSpecsId, "outerGoodsSpecsId must not be null.");
            Assert.notNull(name, "name must not be null.");
            Assert.notNull(adjustFee, "adjustFee must not be null.");
            Assert.notNull(price, "price must not be null.");
            return resultBuilder;
        }

        public GiftBuilder withGoods(Goods goods) {
            this.id = goods.id();
            this.goodsId = goods.goodsId();
            this.outerGoodsId = goods.outerGoodsId();
            this.name = goods.name();
            return this;
        }

        public GiftBuilder withGoodsSpecsId(Long goodsSpecsId) {
            this.goodsSpecsId = goodsSpecsId;
            return this;
        }

        public GiftBuilder withOuterGoodsSpecsId(String outerGoodsSpecsId) {
            this.outerGoodsSpecsId = outerGoodsSpecsId;
            return this;
        }

        public GiftBuilder withAdjustFee(Amount adjustFee) {
            this.adjustFee = adjustFee;
            return this;
        }

        public GiftBuilder withPrice(Amount price) {
            this.price = price;
            return this;
        }


        private Gift build() {
            return new Gift() {

                @Override
                public Long id() {
                    return id;
                }

                @Override
                public Long goodsId() {
                    return goodsId;
                }

                @Override
                public Long goodsSpecsId() {
                    return goodsSpecsId;
                }

                @Override
                public String outerGoodsId() {
                    return outerGoodsId;
                }

                @Override
                public String outerGoodsSpecsId() {
                    return outerGoodsSpecsId;
                }

                @Override
                public String name() {
                    return name;
                }

                @Override
                public Amount adjustFee() {
                    return adjustFee;
                }

                @Override
                public Amount price() {
                    return price;
                }
            };
        }
    }

    /**
     * 改价模式，增，减.
     */
    enum AdjustMode {
        /**
         * 减少金额
         */
        DECREASE,
        /**
         * 增加金额
         */
        INCREASE
    }
}
