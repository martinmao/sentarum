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
import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.item.core.model.Item;
import io.scleropages.sentarum.item.core.model.Sku;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.goods.AdditionalAttributes;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.mgmt.ActivityRuleManager;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarter;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.context.CartPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContextBuilder;
import io.scleropages.sentarum.promotion.rule.context.PromotionContextBuilder.PromotionGoodsSpecs;
import io.scleropages.sentarum.promotion.rule.impl.cart.CartPromotionChainStarter;
import io.scleropages.sentarum.promotion.rule.impl.cart.CartPromotionChainStarterFactory;
import io.scleropages.sentarum.promotion.rule.impl.cart.SimpleCartPromotionChainStarterRunner;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import org.scleropages.crud.exception.BizError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

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
    private RuleContainer ruleContainer;
    private ItemApi itemApi;


    public void calculateDiscount(PromotionCalculateRequest request) {
        Assert.notNull(request, "request must not be null.");
        PromotionContextBuilder builder = PromotionContextBuilder.newBuilder().withBuyerId(request.getBuyerId()).withChannelType(request.getChannelType()).withChannelId(request.getChannelId());
        request.getCalculatingGoodsSpecs().forEach(goodsSpecs -> {
            Sku sku = getSku(goodsSpecs);
            Item item = sku.item();
            List<Activity> activities = findAllActiveActivityBySku(sku);
            builder.withActivities(new PromotionGoodsSpecs(item.id(), item.outerId(), sku.id(), sku.outerId(), goodsSpecs.getNum()), activities, item.sellerUnionId(), item.sellerId());
        });
        CartPromotionContext promotionContext = builder.build();

        CartPromotionChainStarterFactory chainStarterFactory = new CartPromotionChainStarterFactory();
        PromotionChainStarter chainStarter = chainStarterFactory.createChainStarter(promotionContext, ruleContainer);
        SimpleCartPromotionChainStarterRunner runner = new SimpleCartPromotionChainStarterRunner();
        runner.run((CartPromotionChainStarter) chainStarter);
    }


//    public Map<Rule, RuleInvocation> getActivityRuleInvocations(Activity activity) {
//
//        Map<Rule, RuleInvocation> invocationMap = Maps.newHashMap();
//        ConditionRule conditionRule = activityRuleManager.getConditionRule(activity.id(), activity);
//        PromotionCondition condition = null != conditionRule ? ruleContainer.getCondition(conditionRule) : new TrueCondition();
//        invocationMap.put(conditionRule, condition);
//        new LazyInitPromotionCalculator(ruleContainer, activityRuleManager, activity);
//        return invocationMap;
//    }


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
        List<Activity> activities = Lists.newArrayList(activityManager.getActivities(activityManager.findAllActivityIdsByClassifiedGoodsSource(1, CLASSIFIED_GOODS_SOURCE_TYPE_SELLER, sellerUnionId, sellerId), true));
        //可用的品类活动...
        //TODO
        //可用的品牌活动...
        //TODO
        //可用的商品明细活动...
        activities.addAll(activityManager.getActivities(activityManager.findAllActivityIdsByDetailedGoodsSource(1, item.id(), sku.id()), true));
        return activities;
    }


    /**
     * get sku from item center.
     *
     * @param goodsSpecs
     * @return
     */
    private Sku getSku(PromotionCalculateRequest.CalculatingGoodsSpecs goodsSpecs) {
        return itemApi.getSku(goodsSpecs.getGoodsSpecsId(), true, true, true);
    }


//    private static abstract class LazyInitPromotionInvocation implements RuleInvocation {
//
//        private static final Logger logger = LoggerFactory.getLogger(LazyInitPromotionInvocation.class);
//
//        private final RuleContainer ruleContainer;
//        private final ActivityRuleManager activityRuleManager;
//        private final Activity activity;
//
//        private AtomicBoolean initFlag = new AtomicBoolean(false);
//
//        private Rule rule;
//        private RuleInvocation ruleInvocation;
//
//        private LazyInitPromotionInvocation(RuleContainer ruleContainer, ActivityRuleManager activityRuleManager, Activity activity) {
//            this.ruleContainer = ruleContainer;
//            this.activityRuleManager = activityRuleManager;
//            this.activity = activity;
//        }
//
//        @Override
//        public void execute(Rule rule, InvocationContext invocationContext, InvocationChain chain) {
//            initIfNecessary().execute(rule, invocationContext, chain);
//        }
//
//        @Override
//        public Integer id() {
//            return initIfNecessary().id();
//        }
//
//        @Override
//        public String name() {
//            return initIfNecessary().name();
//        }
//
//        @Override
//        public String description() {
//            return initIfNecessary().description();
//        }
//
//        RuleInvocation initIfNecessary() {
//            if (initFlag.compareAndSet(false, true)) {
//                ruleInvocation = initIfNecessaryInternal();
//            }
//            return ruleInvocation;
//        }
//
//        abstract RuleInvocation initIfNecessaryInternal();
//    }

//    /**
//     * promotion calculator initializing when calculating start
//     */
//    static class LazyInitPromotionCalculator implements PromotionCalculator<CalculatorRule, PromotionContext> {
//
//        private static final Logger logger = LoggerFactory.getLogger(LazyInitPromotionCalculator.class);
//
//        private final RuleContainer ruleContainer;
//        private final ActivityRuleManager activityRuleManager;
//        private final Activity activity;
//        private CalculatorRule calculatorRule;
//        private PromotionCalculator calculator;
//
//
//        public LazyInitPromotionCalculator(RuleContainer ruleContainer, ActivityRuleManager activityRuleManager, Activity activity) {
//            this.ruleContainer = ruleContainer;
//            this.activityRuleManager = activityRuleManager;
//            this.activity = activity;
//        }
//
//        @Override
//        public void calculate(CalculatorRule rule, PromotionContext promotionContext) {
//            initIfNecessary().calculate(rule, promotionContext);
//        }
//
//        @Override
//        public Integer id() {
//            return initIfNecessary().id();
//        }
//
//        @Override
//        public String name() {
//            return initIfNecessary().name();
//        }
//
//        @Override
//        public String description() {
//            return initIfNecessary().description();
//        }
//
//        private PromotionCalculator initIfNecessary() {
//            if (null == calculatorRule) {
//                List<CalculatorRule> calculatorRules = activityRuleManager.findAllCalculatorRulesByActivityId(activity.id(), activity);
//                if (calculatorRules.isEmpty()) {
//                    logger.warn("detected activity has no calculator rule found: {}. ignoring to process.");
//                }
//                calculatorRule = calculatorRules.get(0);
//                calculator = ruleContainer.getPromotionCalculator(calculatorRule);
//            }
//            return calculator;
//        }
//    }


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
                conditionRules = Lists.newArrayList(activityRuleManager.getConditionRule(actualActivity.id(), null));
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
    public void setRuleContainer(RuleContainer ruleContainer) {
        this.ruleContainer = ruleContainer;
    }

    @Autowired
    public void setItemApi(ItemApi itemApi) {
        this.itemApi = itemApi;
    }
}
