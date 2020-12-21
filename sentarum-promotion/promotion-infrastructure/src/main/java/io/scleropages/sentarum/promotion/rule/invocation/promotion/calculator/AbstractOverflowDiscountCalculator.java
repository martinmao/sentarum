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
import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.promotion.goods.DetailedGoodsSourceReader;
import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.PromotionCalculator;
import io.scleropages.sentarum.promotion.rule.model.calculator.GiftModel;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource;
import org.scleropages.core.mapper.JsonMapper2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractOverflowDiscountCalculator<C extends PromotionContext> implements PromotionCalculator<OverflowDiscountRule, C> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void calculate(OverflowDiscountRule rule, C promotionContext) {
        PromotionContext.PromotionResultBuilder resultBuilder = promotionContext.promotionResultBuilder();
        resultBuilder.withActivity(rule.activity());
        Amount adjustFee = new Amount();
        switch (rule.getOverflowDiscountType()) {
            case FIXED_FEE_OVERFLOW: {//固定满减计算
                List<OverflowDiscount> overflowDiscounts = rule.detailedConfigures();
                if (CollectionUtils.isEmpty(overflowDiscounts)) {
                    logger.warn("no price-break discounts found for rule: {}. ignore to calculating....", rule.id());
                    return;
                }
                OverflowDiscount overflowDiscount = overflowDiscounts.get(0);
                if (overflowDiscounts.size() > 1) {
                    logger.warn("found more than one price-break discounts for rule: {}. use first[{}] for calculating...", rule.id(), JsonMapper2.toJson(overflowDiscount));
                }
                Discount discount = overflowDiscount.getOverflowDiscount();
                if (null != discount) {//减钱
                    switch (discount.getDiscountType()) {
                        case DECREASE_WITHOUT_ORIGINAL_PRICE: {
                            adjustFee = calculateFixedAdjustFee(promotionContext, rule, overflowDiscount);
                            break;
                        }
                        case DISCOUNT_WITHOUT_ORIGINAL_PRICE: {
                            adjustFee = discount.discountAmount(calculateTotalAmount(promotionContext), true);
                            break;
                        }
                    }
                } else {
                    //todo 可用赠品,某些场景下需要用户选择（例如加价赠，或n选一场景），需在gift中进行标识.
                }
                break;
            }
            case STEPPED_FEE_OVERFLOW: {//阶梯满减计算
                Amount totalAmount = calculateTotalAmount(promotionContext);
                List<OverflowDiscount> overflowDiscounts = rule.detailedConfigures();
                if (CollectionUtils.isEmpty(overflowDiscounts)) {
                    logger.warn("no price-break discounts found for rule: {}. ignore to calculating....", rule.id());
                    return;
                }
                OverflowDiscount hit = null;
                for (OverflowDiscount overflowDiscount : overflowDiscounts) {
                    if (totalAmount.gte(overflowDiscount.getOverflowFee())) {
                        if (null == hit)
                            hit = overflowDiscount;
                        else if (hit.getOverflowFee().lt(overflowDiscount.getOverflowFee()))
                            hit = overflowDiscount;
                    }
                }
                if (null == hit) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("no price-break hits[total={}] for {}. ends calculating.....", totalAmount, rule.id());
                    }
                    return;
                }
                Discount discount = hit.getOverflowDiscount();
                if (null != discount) {
                    adjustFee = discount.discountAmount(totalAmount, true);
                } else {
                    //todo 可用赠品,某些场景下需要用户选择（例如加价赠，或n选一场景），需在gift中进行标识.
                    CalculatorGoodsSource calculatorGoodsSource = hit.getCalculatorGoodsSource();
                    DetailedGoodsSourceReader.AllOfGoods allOfGoods = calculatorGoodsSource.detailedGoodsSourceReader().allOfGoods();
                    for (Long id : allOfGoods.ids()) {
                        Goods goods = allOfGoods.goods(id).get();
                        addGift(resultBuilder, goods);
                    }
                }
                break;
            }
            case FIXED_GOODS_NUM_OVERFLOW: {
                //todo 满固定商品数时触发规则.
                break;
            }
            case STEPPED_GOODS_NUM_OVERFLOW: {
                //todo 满阶梯商品数时触发规则.
                break;
            }
        }
        resultBuilder.withAdjustFee(adjustFee);
        promotionContext.addPromotionResult(resultBuilder.build());

    }

    /**
     * 计算固定满减价格调整.
     *
     * @param promotionContext
     * @param rule
     * @param overflowDiscount
     * @return
     */
    private final Amount calculateFixedAdjustFee(C promotionContext, OverflowDiscountRule rule, OverflowDiscount overflowDiscount) {
        Amount totalAmount = calculateTotalAmount(promotionContext);
        Amount divisor = totalAmount.divide(overflowDiscount.getOverflowFee(), false);
        Integer limit = rule.getOverflowCycleLimit();
        divisor = limit == -1 ? divisor : divisor.min(new Amount(limit));
        return overflowDiscount.getOverflowDiscount().multiplyDiscountAmount(divisor, false);
    }

    /**
     * 添加赠品到上下文
     *
     * @param resultBuilder
     * @param goods
     */
    private final void addGift(PromotionContext.PromotionResultBuilder resultBuilder, Goods goods) {
        GiftModel gift = new GiftModel(goods);
        resultBuilder.withGift()
                .withGoods(goods)
                .withGoodsSpecsId(gift.getGoodsSpecsId())
                .withOuterGoodsSpecsId(gift.getOuterGoodsSpecsId())
                .withAdjustFee(gift.getAdjustFee())
                .withPrice(gift.getPrice());
    }

    /**
     * 计算订单总金额.
     *
     * @param promotionContext
     * @return
     */
    protected Amount calculateTotalAmount(C promotionContext) {
        return promotionContext.totalAmount();
    }

}
