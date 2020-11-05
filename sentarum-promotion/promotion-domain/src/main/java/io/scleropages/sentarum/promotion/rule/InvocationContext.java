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
package io.scleropages.sentarum.promotion.rule;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.promotion.rule.model.impl.ChannelRule.ChannelType;

/**
 * 规则调用上下文，其贯穿整个规则调用链，负责状态维护.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface InvocationContext {

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
     * 商家唯一标识（商业综合体).
     *
     * @return
     */
    Long sellerUnionId();

    /**
     * 商家唯一标识(单店).
     *
     * @return
     */
    Long sellerId();


    /**
     * 与当前规则调用链关联的购买行为
     *
     * @return
     */
    ItemBuying buying();


    /**
     * 描述单个商品的购买
     */
    interface ItemBuying {

        /**
         * 商品唯一标识
         *
         * @return
         */
        Long itemId();

        /**
         * 外部商品唯一标识
         *
         * @return
         */
        String outerItemId();

        /**
         * sku 唯一标识
         *
         * @return
         */
        Long skuId();

        /**
         * 外部sku唯一标识
         *
         * @return
         */
        String outerSkuId();

        /**
         * 买家唯一标识
         *
         * @return
         */
        Long buyerId();

        /**
         * 购买数量
         *
         * @return
         */
        Integer num();
    }

    class BuyingPromotionBuilder {

        private Long ruleId;
        private String name;
        private String description;
        private Amount adjustFee;


        private BuyingPromotionBuilder() {

        }

        public static BuyingPromotionBuilder newBuilder() {
            return new BuyingPromotionBuilder();
        }

        public BuyingPromotionBuilder withRuleId(Long ruleId) {
            this.ruleId = ruleId;
            return this;
        }

        public BuyingPromotionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BuyingPromotionBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public BuyingPromotionBuilder withAdjustFee(Amount adjustFee) {
            this.adjustFee = adjustFee;
            return this;
        }

        public BuyingPromotion build() {
            return new BuyingPromotion() {
                @Override
                public Long ruleId() {
                    return ruleId;
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
            };
        }
    }


    /**
     * 购买优惠
     */
    interface BuyingPromotion {

        /**
         * 规则id.
         *
         * @return
         */
        Long ruleId();

        /**
         * 规则名称
         *
         * @return
         */
        String name();


        /**
         * 规则描述
         *
         * @return
         */
        String description();


        /**
         * 计算后的价格调整，通常为减免金额(特殊情况下可能为负)
         *
         * @return
         */
        Amount adjustFee();
    }
}
