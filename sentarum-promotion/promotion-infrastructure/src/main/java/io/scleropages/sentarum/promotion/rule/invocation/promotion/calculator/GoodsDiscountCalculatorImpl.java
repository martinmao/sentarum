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
package io.scleropages.sentarum.promotion.rule.invocation.promotion.calculator;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.goods.DetailedGoodsSourceReader;
import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.rule.context.GoodsPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class GoodsDiscountCalculatorImpl implements GoodsDiscountCalculator {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void calculate(GoodsDiscountRule rule, GoodsPromotionContext promotionContext) {
        Activity activity = rule.activity();
        PromotionContext.PromotionResultBuilder resultBuilder = promotionContext.promotionResultBuilder();
        resultBuilder.withActivity(rule.activity());
        Amount adjustFee = new Amount();
        List<ActivityGoodsSource> goodsSources = activity.goodsSource();
        if (CollectionUtils.isEmpty(goodsSources)) {
            logger.warn("detected activity has no goods source associated. ignore to calculating....");
            return;
        }
        if (goodsSources.size() > 1) {//多品牌，品类，店铺统一折扣.
            adjustFee = calculateUnifiedDiscount(rule, promotionContext);
        } else {
            ActivityGoodsSource activityGoodsSource = goodsSources.get(0);
            if (activityGoodsSource instanceof ActivityClassifiedGoodsSource) {//单品牌、品类、店铺统一折扣.
                adjustFee = calculateUnifiedDiscount(rule, promotionContext);
            } else {//商品促销
                ActivityDetailedGoodsSource detailedGoodsSource = (ActivityDetailedGoodsSource) activityGoodsSource;
                DetailedGoodsSourceReader.GoodsHolder goodsHolder = detailedGoodsSource.detailedGoodsSourceReader().allOfGoods().goods(promotionContext.goodsId());
                Goods goods = goodsHolder.get();

            }
        }
        resultBuilder.withAdjustFee(adjustFee);
        promotionContext.addPromotionResult(resultBuilder.build());
    }

    private final Amount calculateUnifiedDiscount(GoodsDiscountRule rule, GoodsPromotionContext promotionContext) {
        return rule.getDiscount().discountAmount(promotionContext.originalPrice(), true).multiply(promotionContext.num(), true);
    }
}
