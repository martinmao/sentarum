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
import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoods;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSpecs;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityClassifiedGoodsSourceModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityDetailedGoodsSourceModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsSpecsModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.mgmt.ActivityRuleManager;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule.GoodsDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule.GoodsSpecsDiscount;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule.ConditionConjunction;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scleropages.core.mapper.JsonMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class ActivityManagerTestcase {

    @Autowired
    private ActivityManager activityManager;

    @Autowired
    private ActivityRuleManager activityRuleManager;

    @Test
    public void _1_createSimpleActivity() {

        ActivityModel activity = new ActivityModel();

        activity.setName("TEST1");
        activity.setTag("品牌活动");
        activity.setDescription("品牌活动测试1");
        activity.setStatus(1);
        activity.setStartTime(new Date());
        activity.setEndTime(DateUtils.addDays(new Date(), 20));
        Long activityId = activityManager.createActivity(activity);

        ActivityClassifiedGoodsSourceModel brandGoodsSource = new ActivityClassifiedGoodsSourceModel();
        brandGoodsSource.setGoodsSourceType(ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_BRAND);
        brandGoodsSource.setGoodsSourceId(1l);
        brandGoodsSource.setGoodsSourceName("Apple Inc");
        brandGoodsSource.setQuery("brand=1");
        brandGoodsSource.setBizId(activityId);
        brandGoodsSource.setComment("活动关联品牌");

        Long activityClassifiedGoodsSourceId = activityManager.createActivityClassifiedGoodsSource(brandGoodsSource, activityId);

        ActivityClassifiedGoodsSource activityClassifiedGoodsSource = activityManager.getActivityClassifiedGoodsSource(activityClassifiedGoodsSourceId);

        activityClassifiedGoodsSource.additionalAttributes().setAttribute("k1", "v1", true);
        activityClassifiedGoodsSource.additionalAttributes().setAttribute("k2", "v2", true);
        activityClassifiedGoodsSource.additionalAttributes().save();


        brandGoodsSource.setGoodsSourceId(2l);
        brandGoodsSource.setGoodsSourceName("Amazon");
        brandGoodsSource.setQuery("brand=2");

        activityManager.createActivityClassifiedGoodsSource(brandGoodsSource, activityId);


        System.out.println(JsonMapper2.toJson(activityManager.getActivities(activityManager.findAllActivityIdsByClassifiedGoodsSource(1, 1, null, null), true)));
        System.out.println(JsonMapper2.toJson(activityManager.findAllActivityIdsByClassifiedGoodsSource(1, 1, null, null)));


        activity.setName("TEST2");
        activity.setTag("品类活动");
        activity.setDescription("品类活动测试1");
        activity.setStatus(1);
        activity.setStartTime(new Date());
        activity.setEndTime(DateUtils.addDays(new Date(), 20));
        activityId = activityManager.createActivity(activity);


        ActivityClassifiedGoodsSourceModel categoryGoodsSource = new ActivityClassifiedGoodsSourceModel();

        categoryGoodsSource.setGoodsSourceType(ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_CATEGORY);
        categoryGoodsSource.setGoodsSourceId(1l);
        categoryGoodsSource.setSecondaryGoodsSourceId(1l);
        categoryGoodsSource.setGoodsSourceName("Computer/office-notebook");
        categoryGoodsSource.setQuery("category=1");
        categoryGoodsSource.setBizId(activityId);
        categoryGoodsSource.setComment("活动关联品类");

        activityManager.createActivityClassifiedGoodsSource(categoryGoodsSource, activityId);

        categoryGoodsSource.setGoodsSourceId(2l);
        categoryGoodsSource.setSecondaryGoodsSourceId(22l);
        categoryGoodsSource.setGoodsSourceName("Computer/office-microphone");
        categoryGoodsSource.setQuery("category=2");
        categoryGoodsSource.setBizId(activityId);
        categoryGoodsSource.setComment("活动关联品类");

        activityManager.createActivityClassifiedGoodsSource(categoryGoodsSource, activityId);

        System.out.println(JsonMapper2.toJson(activityManager.getActivities(activityManager.findAllActivityIdsByClassifiedGoodsSource(1, 2, null, null), true)));

        GoodsDiscountRule goodsDiscountRule = new GoodsDiscountRule(new Discount(Discount.DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE, 5, null), null);

        goodsDiscountRule.setDescription("品类打折，全场商品均直减5元.");

        activityRuleManager.createGoodsDiscountRule(goodsDiscountRule, activityId);


        activity.setName("TEST22");
        activity.setTag("商家活动");
        activity.setDescription("商家活动测试");
        activity.setStatus(1);
        activity.setStartTime(new Date());
        activity.setEndTime(DateUtils.addDays(new Date(), 20));
        activityId = activityManager.createActivity(activity);

        ActivityClassifiedGoodsSourceModel sellerGoodsSource = new ActivityClassifiedGoodsSourceModel();
        sellerGoodsSource.setGoodsSourceType(ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_SELLER);
        sellerGoodsSource.setGoodsSourceId(101l);
        sellerGoodsSource.setSecondaryGoodsSourceId(1011l);
        sellerGoodsSource.setGoodsSourceName("商家店铺商品");
        sellerGoodsSource.setQuery("sellerId=1011");
        sellerGoodsSource.setBizId(activityId);
        sellerGoodsSource.setComment("活动到关联店铺");
        activityManager.createActivityClassifiedGoodsSource(sellerGoodsSource, activityId);

        goodsDiscountRule = new GoodsDiscountRule(new Discount(Discount.DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE, 7, null), null);
        goodsDiscountRule.setDescription("商家店铺折扣，全店商品均直减7元.");
        activityRuleManager.createGoodsDiscountRule(goodsDiscountRule, activityId);


        activity.setName("TEST3");
        activity.setTag("商品活动");
        activity.setDescription("商品活动测试1");
        activity.setStatus(1);
        activity.setStartTime(new Date());
        activity.setEndTime(DateUtils.addDays(new Date(), 20));
        activityId = activityManager.createActivity(activity);


        ActivityDetailedGoodsSourceModel activityDetailedGoodsSourceModel = new ActivityDetailedGoodsSourceModel();


        activityDetailedGoodsSourceModel.setBizId(activityId);
        activityDetailedGoodsSourceModel.setComment("活动关联商品.");
        activityDetailedGoodsSourceModel.setGoodsSourceType(ActivityGoodsSource.DETAILED_GOODS_SOURCE_TYPE);

        Long activityDetailedGoodsSourceId = activityManager.createActivityDetailedGoodsSource(activityDetailedGoodsSourceModel, activityId);
        ActivityDetailedGoodsSource activityDetailedGoodsSource = activityManager.getActivityDetailedGoodsSource(activityDetailedGoodsSourceId);

        activityDetailedGoodsSource.additionalAttributes().setAttribute("k3", "v3", true);
        activityDetailedGoodsSource.additionalAttributes().setAttribute("k4", "v4", true);
        activityDetailedGoodsSource.additionalAttributes().save();

        ActivityGoodsModel goodsModel = new ActivityGoodsModel();
        goodsModel.setGoodsId(1l);
        goodsModel.setName("iPhoneX");
        goodsModel.setOuterGoodsId("x1221212121212121");
        goodsModel.setTotalNum(111);
        goodsModel.setUserNum(1);

        Long activityGoodsId = activityManager.createActivityGoods(goodsModel, activityDetailedGoodsSourceId);

        goodsModel.setGoodsId(2l);
        goodsModel.setName("iPhoneX");
        goodsModel.setOuterGoodsId("x1221212121212121");
        goodsModel.setTotalNum(222);
        goodsModel.setUserNum(2);

        Long activityGoodsId2 = activityManager.createActivityGoods(goodsModel, activityDetailedGoodsSourceId);

        goodsModel.setGoodsId(3l);
        goodsModel.setName("iPhoneX");
        goodsModel.setOuterGoodsId("x1221212121212121");
        goodsModel.setTotalNum(333);
        goodsModel.setUserNum(1);

        Long activityGoodsId3 = activityManager.createActivityGoods(goodsModel, activityDetailedGoodsSourceId);

        ActivityGoods activityGoods = activityManager.getActivityGoods(activityGoodsId);

        activityGoods.additionalAttributes().setAttribute("k5", "v5", true);
        activityGoods.additionalAttributes().setAttribute("k6", "v6", true);
        activityGoods.additionalAttributes().save();

        activityGoods = activityManager.getActivityGoods(activityGoodsId2);

        activityGoods.additionalAttributes().setAttribute("k51", "v51", true);
        activityGoods.additionalAttributes().setAttribute("k61", "v61", true);
        activityGoods.additionalAttributes().save();

        activityGoods = activityManager.getActivityGoods(activityGoodsId3);

        activityGoods.additionalAttributes().setAttribute("k52", "v52", true);
        activityGoods.additionalAttributes().setAttribute("k62", "v62", true);
        activityGoods.additionalAttributes().save();

        ActivityGoodsSpecsModel goodsSpecsModel = new ActivityGoodsSpecsModel();
        goodsSpecsModel.setSpecsId(1l);
        goodsSpecsModel.setName("iPhoneX- 32G");
        goodsSpecsModel.setOuterSpecsId("11");
        goodsSpecsModel.setTotalNum(10);
        goodsSpecsModel.setUserNum(1);

        Long activityGoodsSpecsId = activityManager.createActivityGoodsSpecs(goodsSpecsModel, activityGoodsId);

        ActivityGoodsSpecs activityGoodsSpecs = activityManager.getActivityGoodsSpecs(activityGoodsSpecsId);

        activityGoodsSpecs.additionalAttributes().setAttribute("k7", "v7", true);
        activityGoodsSpecs.additionalAttributes().setAttribute("k8", "v8", true);
        activityGoodsSpecs.additionalAttributes().save();


        goodsDiscountRule = new GoodsDiscountRule(null, Lists.newArrayList());
        goodsDiscountRule.setDescription("商品打折规则");
        goodsDiscountRule.getGoodsDiscounts().add(
                new GoodsDiscount(activityGoodsId, new Discount(Discount.DiscountType.DISCOUNT, 85, null), Lists.newArrayList(
                        new GoodsSpecsDiscount(activityGoodsSpecsId, new Discount(Discount.DiscountType.OVERRIDE_AMOUNT, 3500, new Amount(4444))))));


        goodsDiscountRule.getGoodsDiscounts().add(
                new GoodsDiscount(activityGoodsId2, new Discount(Discount.DiscountType.DISCOUNT, 95, new Amount(7999)), Lists.newArrayList()));


        goodsDiscountRule.getGoodsDiscounts().add(
                new GoodsDiscount(activityGoodsId3, Lists.newArrayList(
                        new GoodsDiscountRule.UserLevelDiscount(
                                new Discount(Discount.DiscountType.DECREASE_AMOUNT, 299, new Amount(7999)), 1l, 1l, "vip会员"))));

        activityRuleManager.createGoodsDiscountRule(goodsDiscountRule, activityId);


        System.out.println(JsonMapper2.toJson(activityManager.getActivities(activityManager.findAllActivityIdsByDetailedGoodsSource(1, 888l, 19888l), true)));

        System.out.println(JsonMapper2.toJson(activityManager.getActivities(activityManager.findAllActivityIdsByDetailedGoodsSource(1, 888l, 19889l), true)));


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ConjunctionConditionRule conjunctionConditionRule = new ConjunctionConditionRule();
        conjunctionConditionRule.setDescription("root");
        conjunctionConditionRule.setConditionConjunction(ConditionConjunction.AND);

        Long root = activityRuleManager.createConjunctionConditionRule(conjunctionConditionRule, activityId, null);
        conjunctionConditionRule.setDescription("sub1");
        Long sub1 = activityRuleManager.createConjunctionConditionRule(conjunctionConditionRule, activityId, root);
        conjunctionConditionRule.setDescription("sub2");
        conjunctionConditionRule.setConditionConjunction(ConditionConjunction.OR);
        Long sub2 = activityRuleManager.createConjunctionConditionRule(conjunctionConditionRule, activityId, root);


        ChannelConditionRule channelConditionRule = new ChannelConditionRule();
        channelConditionRule.setDescription("sub3");
        channelConditionRule.setChannelId(1);
        channelConditionRule.setChannelName("渠道规则");
        channelConditionRule.setOuterId("1");
        channelConditionRule.setSellerId(1l);
        channelConditionRule.setSellerUnionId(1l);

        activityRuleManager.createChannelConditionRule(channelConditionRule, activityId, root);
        channelConditionRule.setDescription("sub11");
        activityRuleManager.createChannelConditionRule(channelConditionRule, activityId, sub1);
        channelConditionRule.setDescription("sub12");
        activityRuleManager.createChannelConditionRule(channelConditionRule, activityId, sub1);
        channelConditionRule.setDescription("sub21");
        activityRuleManager.createChannelConditionRule(channelConditionRule, activityId, sub2);
        channelConditionRule.setDescription("sub22");
        activityRuleManager.createChannelConditionRule(channelConditionRule, activityId, sub2);

        System.out.println(JsonMapper2.toJson(activityRuleManager.getConditionRule(activityId, null)));
    }
}
