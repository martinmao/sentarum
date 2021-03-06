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
package io.scleropages.sentarum.promotion;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.Sku;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.goods.AdditionalAttributes;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.mgmt.ActivityRuleManager;
import io.scleropages.sentarum.promotion.mgmt.CalculatorGoodsManager;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarter;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarterFactory;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContextBuilder;
import io.scleropages.sentarum.promotion.rule.context.PromotionContextBuilder.PromotionGoodsSpecs;
import io.scleropages.sentarum.promotion.rule.impl.DefaultPromotionChainStarter;
import io.scleropages.sentarum.promotion.rule.impl.DefaultPromotionChainStarterFactory;
import io.scleropages.sentarum.promotion.rule.impl.SimplePromotionChainStarterRunner;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.SimpleGift;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoods;
import org.scleropages.crud.exception.BizError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_ALL;
import static io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_SELLER;

/**
 * application layer for promotion.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@BizError("25")
public class PromotionApplication {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ActivityManager activityManager;
    private ActivityRuleManager activityRuleManager;
    private CalculatorGoodsManager calculatorGoodsManager;
    private RuleContainer ruleContainer;
    private ItemApi itemApi;


    /**
     * 创建满减促销规则.
     *
     * @param rule
     * @param activityId
     * @return
     */
    @BizError("01")
    @Transactional
    public Long createOverflowDiscountRule(OverflowDiscountRule rule, Long activityId) {
        Long id = activityRuleManager.createCalculatingRule(rule, activityId);
        if (!CollectionUtils.isEmpty(rule.getOverflowDiscounts())) {
            for (OverflowDiscount overflowDiscount : rule.getOverflowDiscounts()) {
                createOverflowDiscount(overflowDiscount, id);
            }
        }
        return id;
    }

    /**
     * 创建满减促销规则明细.
     *
     * @param discount
     * @param overflowDiscountRuleId
     * @return
     */
    @BizError("01")
    @Transactional
    public Long createOverflowDiscount(OverflowDiscount discount, Long overflowDiscountRuleId) {
        return activityRuleManager.createCalculatingRuleDetailedConfig(discount, overflowDiscountRuleId);
    }

    /**
     * 创建满减促销赠品.
     *
     * @param gift
     * @param overflowDiscountId
     * @return
     */
    @Validated(SimpleGift.Create.class)
    @BizError("03")
    @Transactional
    public Long createOverflowDiscountGift(@Valid SimpleGift gift, Long overflowDiscountId) {
        calculatorGoodsManager.getGoodsSource(overflowDiscountId).orElseThrow(() -> new IllegalArgumentException("no overflow discount found: " + overflowDiscountId));
        CalculatorGoods goods = new CalculatorGoods();
        goods.setGoodsId(gift.getGoodsId());
        goods.setName(gift.getName());
        goods.setOuterGoodsId(gift.getOuterGoodsId());
        return calculatorGoodsManager.createCalculatorGoods(goods, overflowDiscountId, gift.buildAttributes());
    }


    /**
     * 创建商品折扣规则.
     *
     * @param goodsDiscountRule
     * @param activityId
     * @return
     */
    @Validated(GoodsDiscountRule.Create.class)
    @Transactional
    @BizError("04")
    public Long createGoodsDiscountRule(@Valid GoodsDiscountRule goodsDiscountRule, Long activityId) {
        List<ActivityGoodsSource> activityGoodsSources = activityManager.findAllActivityGoodsSource(activityId);
        Assert.notEmpty(activityGoodsSources, "no activity goods source found.");
        activityGoodsSources.forEach(activityGoodsSource -> {
            if (activityGoodsSource instanceof ActivityDetailedGoodsSource) {
                goodsDiscountRule.applyActivityDetailedGoodsSourceConfigure((ActivityDetailedGoodsSource) activityGoodsSource);
            } else if (activityGoodsSource instanceof ActivityClassifiedGoodsSource) {
                goodsDiscountRule.applyActivityClassifiedGoodsSourceConfigure((ActivityClassifiedGoodsSource) activityGoodsSource);
            } else
                throw new IllegalStateException("unsupported activity goods source: " + AopUtils.getTargetClass(activityGoodsSource).getSimpleName());
        });
        goodsDiscountRule.beforeSerialize();
        return activityRuleManager.createCalculatingRule(goodsDiscountRule, activityId);
    }


    public void calculateDiscount(PromotionCalculateRequest request) {
        Assert.notNull(request, "request must not be null.");
        PromotionContext promotionContext = buildPromotionContext(request);
        PromotionChainStarterFactory chainStarterFactory = new DefaultPromotionChainStarterFactory();
        PromotionChainStarter chainStarter = chainStarterFactory.createChainStarter(promotionContext, ruleContainer);
        SimplePromotionChainStarterRunner runner = new SimplePromotionChainStarterRunner();
        runner.run((DefaultPromotionChainStarter) chainStarter);
        System.out.println(promotionContext);
    }


    /**
     * build promotion context by given calculate request.
     *
     * @param request
     * @return
     */
    protected PromotionContext buildPromotionContext(PromotionCalculateRequest request) {
        PromotionContextBuilder builder = PromotionContextBuilder.newBuilder().withBuyerId(request.getBuyerId()).withChannelType(request.getChannelType()).withChannelId(request.getChannelId());
        request.getCalculatingGoodsSpecs().forEach(goodsSpecs -> {
            Sku sku = getSku(goodsSpecs);
            Item item = sku.item();
            List<Activity> activities = findAllActiveActivityBySku(sku);
            builder.withActivities(new PromotionGoodsSpecs(item.id(), item.outerId(), sku.id(), sku.outerId(), goodsSpecs.getNum(), new Amount(sku.salesPrice())), activities, item.sellerUnionId(), item.sellerId());
        });
        return builder.build();
    }


    /**
     * find all active activities by given sku.
     *
     * @param sku
     * @return
     */
    private List<Activity> findAllActiveActivityBySku(Sku sku) {
        Item item = sku.item();
        Long sellerUnionId = item.sellerUnionId();
        Long sellerId = item.sellerId();
        //可用的商家活动....
        List<Activity> activities = Lists.newArrayList(activityManager.getActivities(activityManager.findAllActivityIdsByClassifiedGoodsSource(1, CLASSIFIED_GOODS_SOURCE_TYPE_SELLER, sellerUnionId, sellerId), ActivityClassifiedGoodsSource.class));
        //可用的平台活动...
        activities.addAll(Lists.newArrayList(activityManager.getActivities(activityManager.findAllActivityIdsByClassifiedGoodsSource(1, CLASSIFIED_GOODS_SOURCE_TYPE_ALL, null, null), ActivityClassifiedGoodsSource.class)));
        //TODO
        //可用的品类活动...
        //TODO
        //可用的品牌活动...
        activities.addAll(activityManager.getActivities(activityManager.findAllActivityIdsByDetailedGoodsSource(1, item.id(), sku.id()), ActivityDetailedGoodsSource.class));
        return activities.stream().map(activity -> new CalculatingActivity(activity, activityRuleManager)).collect(Collectors.toList());
    }


    /**
     * getGoodsSource sku from item center.
     *
     * @param goodsSpecs
     * @return
     */
    private Sku getSku(PromotionCalculateRequest.CalculatingGoodsSpecs goodsSpecs) {
        return itemApi.getSku(goodsSpecs.getGoodsSpecsId(), true, true, true);
    }

    /**
     * proxied activity for promotion calculating.
     */
    private final class CalculatingActivity implements Activity {

        private final Activity actualActivity;
        private final ActivityRuleManager activityRuleManager;

        //always has one elements. merge all condition rules as root condition node.
        private List<Rule> conditionRules;

        private Rule promotionalRule;

        private CalculatingActivity(Activity actualActivity, ActivityRuleManager activityRuleManager) {
            this.actualActivity = actualActivity;
            this.activityRuleManager = activityRuleManager;
        }

        @Override
        public Long id() {
            return actualActivity.id();
        }

        @Override
        public String name() {
            return actualActivity.name();
        }

        @Override
        public String tag() {
            return actualActivity.tag();
        }

        @Override
        public String description() {
            return actualActivity.description();
        }

        @Override
        public Date startTime() {
            return actualActivity.startTime();
        }

        @Override
        public Date endTime() {
            return actualActivity.endTime();
        }

        @Override
        public Integer status() {
            return actualActivity.status();
        }

        @Override
        public List<Rule> conditionRules() {
            if (null == conditionRules) {
                ConditionRule conditionRule = activityRuleManager.getConditionRule(actualActivity.id(), null);
                conditionRules = null != conditionRule ? Lists.newArrayList(conditionRule) : Collections.emptyList();
            }
            return conditionRules;
        }

        @Override
        public Rule promotionalRule() {
            if (null == promotionalRule) {
                List<CalculatorRule> calculatorRules = activityRuleManager.findAllCalculatorRulesByActivityId(actualActivity.id(), null);
                if (calculatorRules.isEmpty()) {
                    logger.warn("detected activity has no calculator rule found: {}. ignoring to process.");
                }
                promotionalRule = calculatorRules.get(0);
            }
            return promotionalRule;
        }

        @Override
        public List<ActivityGoodsSource> goodsSource() {
            return actualActivity.goodsSource();
        }

        @Override
        public Float order() {
            return actualActivity.order();
        }

        @Override
        public AdditionalAttributes additionalAttributes() {
            return actualActivity.additionalAttributes();
        }
    }


    @Autowired
    public void setActivityManager(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    @Autowired
    public void setActivityRuleManager(ActivityRuleManager activityRuleManager) {
        this.activityRuleManager = activityRuleManager;
    }

    @Autowired
    public void setCalculatorGoodsManager(CalculatorGoodsManager calculatorGoodsManager) {
        this.calculatorGoodsManager = calculatorGoodsManager;
    }

    @Autowired
    public void setRuleContainer(RuleContainer ruleContainer) {
        this.ruleContainer = ruleContainer;
    }

    @Autowired
    public void setItemApi(ItemApi itemApi) {
        this.itemApi = itemApi;
    }
}
