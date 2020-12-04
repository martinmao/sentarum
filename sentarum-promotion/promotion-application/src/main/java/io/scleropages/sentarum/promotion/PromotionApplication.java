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
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.mgmt.ActivityRuleManager;
import io.scleropages.sentarum.promotion.rule.Condition;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.context.CartPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContextBuilder;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
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

import java.util.List;

import static io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_SELLER;

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
        PromotionContextBuilder promotionContextBuilder = PromotionContextBuilder.newBuilder().withBuyerId(request.getBuyerId());
        request.getCalculatingGoodsSpecs().forEach(goodsSpecs -> {
            Sku sku = getSku(goodsSpecs);
            Item item = sku.item();
            applyBuilder(promotionContextBuilder.orderPromotionContextBuilder(item.sellerUnionId(), item.sellerId()).goodsPromotionContextBuilder(), item, sku, goodsSpecs);
            List<ConditionRule> activityConditionRules = getAllActivityConditionRules(sku);
            activityConditionRules.forEach(conditionRule -> {
                Activity activity = conditionRule.activity();
                Condition condition = ruleContainer.getCondition(conditionRule);
                activityManager.findAllActivityGoodsSource(activity.id());
            });
        });
        final CartPromotionContext promotionContext = promotionContextBuilder.build();
    }

    /**
     * apply all information to goods promotion context builder.
     *
     * @param builder    apply to
     * @param item       item information
     * @param sku        sku information
     * @param goodsSpecs calculating information
     */
    private void applyBuilder(PromotionContextBuilder.GoodsPromotionContextBuilder builder, Item item, Sku sku, DiscountCalculateRequest.CalculatingGoodsSpecs goodsSpecs) {
        builder.withGoodsId(item.id());
        builder.withOuterGoodsId(item.outerId());
        builder.withSpecsId(sku.id());
        builder.withOuterSpecsId(sku.outerId());
        builder.withNum(goodsSpecs.getNum());
    }

    /**
     * query all activity condition(associated activity) rules by given sku
     *
     * @param sku
     * @return
     */
    private List<ConditionRule> getAllActivityConditionRules(Sku sku) {
        Item item = sku.item();
        List<ConditionRule> activityConditionRules = Lists.newArrayList();
        addAllClassifiedActivityRules(item, activityConditionRules);
        List<? extends Activity> detailedActivities = activityManager.findAllActivityByDetailedGoodsSource(1, item.id(), sku.id(), true);
        detailedActivities.forEach(detailedActivity -> {
            activityConditionRules.add(activityRuleManager.getConditionRule(detailedActivity.id(), detailedActivity));
        });
        return activityConditionRules;
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

    /**
     * query and add all classified goods source activity rules.
     *
     * @param item
     * @param activityConditionRules
     */
    private void addAllClassifiedActivityRules(Item item, List<ConditionRule> activityConditionRules) {
        Long sellerUnionId = item.sellerUnionId();
        Long sellerId = item.sellerId();
        List<? extends Activity> classifiedActivities = activityManager.findAllActivityByClassifiedGoodsSource(1, CLASSIFIED_GOODS_SOURCE_TYPE_SELLER, sellerUnionId, sellerId, true);
        classifiedActivities.forEach(classifiedActivity -> {
            activityConditionRules.add(activityRuleManager.getConditionRule(classifiedActivity.id(), classifiedActivity));
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        test();
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
