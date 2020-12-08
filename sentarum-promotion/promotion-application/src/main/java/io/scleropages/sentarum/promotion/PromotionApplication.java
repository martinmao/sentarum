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
import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.Sku;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.mgmt.ActivityRuleManager;
import io.scleropages.sentarum.promotion.rule.Condition;
import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.PromotionCalculator;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.RuleInvocation;
import io.scleropages.sentarum.promotion.rule.context.CartPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.GoodsPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.OrderPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContextBuilder;
import io.scleropages.sentarum.promotion.rule.impl.DefaultInvocationChain;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import org.apache.commons.collections.ComparatorUtils;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.dao.orm.jpa.Pages;
import org.scleropages.crud.exception.BizError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_SELLER;
import static io.scleropages.sentarum.promotion.rule.model.CalculatorRule.ATTRIBUTE_CALCULATE_LEVEL;

/**
 * application layer for promotion.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@BizError("25")
public class PromotionApplication implements InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ActivityManager activityManager;
    private ActivityRuleManager activityRuleManager;
    private RuleContainer ruleContainer;
    private ItemApi itemApi;


    public void calculateDiscount(DiscountCalculateRequest request) {
        Assert.notNull(request, "request must not be null.");
        PromotionContextBuilder builder = PromotionContextBuilder.newBuilder().withBuyerId(request.getBuyerId());
        request.getCalculatingGoodsSpecs().forEach(goodsSpecs -> {
            Sku sku = getSku(goodsSpecs);
            Item item = sku.item();
            List<Activity> activities = findAllActiveActivityBySku(sku);
            if (CollectionUtils.isEmpty(activities)) {

            }
            activities.forEach(activity -> {
                if (activity.goodsSource().size() > 1) {//多个分类(多店、多品牌、多品类)都属于购物车级促销.
                    builder.withActivity(activity);
                } else {
                    ActivityGoodsSource activityGoodsSource = activity.goodsSource().get(0);
                    if (activityGoodsSource instanceof ActivityClassifiedGoodsSource) {
                        if (Objects.equals(activityGoodsSource.goodsSourceType(), CLASSIFIED_GOODS_SOURCE_TYPE_SELLER)) {//只有一个店家分类为店铺活动对应到订单级促销.
                            builder.orderPromotionContextBuilder(item.sellerUnionId(), item.sellerId()).withActivity(activity);
                        } else {//非店家分类，如品牌，品类等，则为跨订单活动，将其对应到购物车级促销.
                            builder.withActivity(activity);
                        }
                    } else if (activityGoodsSource instanceof ActivityDetailedGoodsSource) {//对应到商品级促销.
                        builder.orderPromotionContextBuilder(item.sellerUnionId(), item.sellerId())
                                .withActivity(activity).goodsPromotionContextBuilder()
                                .withGoodsId(item.id())
                                .withOuterGoodsId(item.outerId())
                                .withSpecsId(sku.id())
                                .withOuterSpecsId(sku.outerId())
                                .withNum(goodsSpecs.getNum());
                    }
                }
            });
        });
        CartPromotionContext promotionContext = builder.build();
        List<DefaultInvocationChain> chainsOfCarts = Lists.newArrayList();
        List<DefaultInvocationChain> chainsOfOrders = Lists.newArrayList();
        List<Activity> activities = promotionContext.activities();
        DefaultInvocationChain previousChain = null;
        for (Activity activity : activities) {
            DefaultInvocationChain cartChain = new DefaultInvocationChain(getActivityRuleInvocations(activity), previousChain);
            chainsOfCarts.add(cartChain);
            previousChain = cartChain;
        }
        previousChain = null;
        List<OrderPromotionContext> orderPromotionContexts = promotionContext.orderPromotionContexts();
        for (OrderPromotionContext orderPromotionContext : orderPromotionContexts) {
            for (Activity activity : orderPromotionContext.activities()) {
                DefaultInvocationChain orderChain = new DefaultInvocationChain(getActivityRuleInvocations(activity), previousChain);
                chainsOfOrders.add(orderChain);
                previousChain = orderChain;
            }
            List<GoodsPromotionContext> goodsPromotionContexts = orderPromotionContext.goodsPromotionContexts();
            for (GoodsPromotionContext goodsPromotionContext : goodsPromotionContexts) {
                DefaultInvocationChain goodsPreviousChain = null;
                for (Activity activity : goodsPromotionContext.activities()) {
                    DefaultInvocationChain goodsChain = new DefaultInvocationChain(getActivityRuleInvocations(activity), goodsPreviousChain);
                    goodsPreviousChain = goodsChain;
                }
            }
        }
    }

    public List<RuleInvocation> getActivityRuleInvocations(Activity activity) {

        Condition condition = ruleContainer.getCondition(activityRuleManager.getConditionRule(activity.id(), activity));

        return Lists.newArrayList(condition, new LazyInitPromotionCalculator(ruleContainer, activityRuleManager, activity));
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
        List<Activity> activities = Lists.newArrayList(activityManager.findAllActivityByClassifiedGoodsSource(1, CLASSIFIED_GOODS_SOURCE_TYPE_SELLER, sellerUnionId, sellerId, true));
        //可用商品活动...
        activities.addAll(activityManager.findAllActivityByDetailedGoodsSource(1, item.id(), sku.id(), true));
        sortingActivitiesForCalculating(activities);
        return activities;
    }


    private final void sortingActivitiesForCalculating(List<Activity> activities) {
        activities.sort((o1, o2) -> {
            int compare = ComparatorUtils.naturalComparator().compare(o1.additionalAttributes().getAttribute(ATTRIBUTE_CALCULATE_LEVEL), o2.additionalAttributes().getAttribute(ATTRIBUTE_CALCULATE_LEVEL));
            if (0 == compare) {
                ComparatorUtils.naturalComparator().compare(o1.order(), o2.order());
            }
            return compare;
        });
    }

    /**
     * get sku from item center.
     *
     * @param goodsSpecs
     * @return
     */
    private Sku getSku(DiscountCalculateRequest.CalculatingGoodsSpecs goodsSpecs) {
        return itemApi.getSku(goodsSpecs.getGoodsSpecsId(), true, true, true);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    private void test() {
        System.out.println(itemApi);
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(Pageable.unpaged()), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(0, 6, Sort.by("sales_price", "num"))), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(1, 3, Sort.by("sales_price", "num"))), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(2, 1, Sort.Direction.DESC, "sales_price", "num")), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(1, 2, Sort.by(Sort.Order.asc("sales_price"), Sort.Order.desc("num")))), null)));
        System.out.println(JsonMapper2.toJson(itemApi.getItem(1l, true, true)));
        System.out.println(JsonMapper2.toJson(itemApi.getSku(1l, true, true, true)));

    }


    /**
     * promotion calculator initializing when calculating start
     */
    static class LazyInitPromotionCalculator implements PromotionCalculator<CalculatorRule, PromotionContext> {

        private static final Logger logger = LoggerFactory.getLogger(LazyInitPromotionCalculator.class);

        private final RuleContainer ruleContainer;
        private final ActivityRuleManager activityRuleManager;
        private final Activity activity;
        private CalculatorRule calculatorRule;
        private PromotionCalculator calculator;


        public LazyInitPromotionCalculator(RuleContainer ruleContainer, ActivityRuleManager activityRuleManager, Activity activity) {
            this.ruleContainer = ruleContainer;
            this.activityRuleManager = activityRuleManager;
            this.activity = activity;
        }

        @Override
        public void calculate(CalculatorRule rule, PromotionContext promotionContext, InvocationChain chain) {
            initIfNecessary().calculate(rule, promotionContext, chain);
        }

        @Override
        public Integer id() {
            return initIfNecessary().id();
        }

        @Override
        public String name() {
            return initIfNecessary().name();
        }

        @Override
        public String description() {
            return initIfNecessary().description();
        }

        private PromotionCalculator initIfNecessary() {
            if (null == calculatorRule) {
                List<CalculatorRule> calculatorRules = activityRuleManager.findAllCalculatorRulesByActivityId(activity.id(), activity);
                if (calculatorRules.isEmpty()) {
                    logger.warn("detected activity has no calculator rule found: {}. ignoring to process.");
                }
                calculatorRule = calculatorRules.get(0);
                calculator = ruleContainer.getPromotionCalculator(calculatorRule);
            }
            return calculator;
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
    public void setRuleContainer(RuleContainer ruleContainer) {
        this.ruleContainer = ruleContainer;
    }

    @Autowired
    public void setItemApi(ItemApi itemApi) {
        this.itemApi = itemApi;
    }
}
