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
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityClassifiedGoodsSourceModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityDetailedGoodsSourceModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsSpecsModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule.GoodsDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule.GoodsSpecsDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.SimpleGift;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.dao.orm.jpa.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class CalculatorTestcase {

    @Autowired
    private PromotionApplication promotionApplication;
    @Autowired
    private ActivityManager activityManager;
    @Autowired
    private ItemApi itemApi;


    private void initPlatformActivity() {
        ActivityModel overflowActivity = new ActivityModel();
        overflowActivity.setName("overflowActivity");
        overflowActivity.setTag("平台满500减20活动，无条件");
        overflowActivity.setDescription("平台满500减20活动，无条件");
        overflowActivity.setStatus(1);
        overflowActivity.setStartTime(new Date());
        overflowActivity.setEndTime(DateUtils.addDays(new Date(), 20));
        Long activityId = activityManager.createActivity(overflowActivity);

        ActivityClassifiedGoodsSourceModel allGoodsSource = new ActivityClassifiedGoodsSourceModel();
        allGoodsSource.setGoodsSourceType(ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_ALL);
        allGoodsSource.setGoodsSourceId(1l);
        allGoodsSource.setGoodsSourceName("全平台");
        allGoodsSource.setQuery("all");
        allGoodsSource.setBizId(activityId);
        allGoodsSource.setComment("全平台活动");
        activityManager.createActivityClassifiedGoodsSource(allGoodsSource, activityId);

        OverflowDiscountRule rule = new OverflowDiscountRule();
        rule.setOverflowCycleLimit(-1);
        rule.setOverflowDiscountType(OverflowDiscountRule.OverflowDiscountType.FIXED_FEE_OVERFLOW);
        rule.setDescription("平台满500减20活动，无条件");
        rule.setScope(OverflowDiscountRule.Scope.PLATFORM);
        Long overflowDiscountRuleId = promotionApplication.createOverflowDiscountRule(rule, activityId);
        OverflowDiscount overflowDiscount = new OverflowDiscount();
        overflowDiscount.setOverflowFee(new Amount(500));
        overflowDiscount.setOverflowDiscount(new Discount(Discount.DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE, 20, null));
        promotionApplication.createOverflowDiscount(overflowDiscount, overflowDiscountRuleId);
    }

    private void initSellerActivity() {
        ActivityModel overflowActivity = new ActivityModel();
        overflowActivity.setName("overflowActivity");
        overflowActivity.setTag("店铺满赠活动，无条件");
        overflowActivity.setDescription("店铺满6666赠蓝牙耳机，满7777赠VR眼镜，满8888赠magic2 mouse");
        overflowActivity.setStatus(1);
        overflowActivity.setStartTime(new Date());
        overflowActivity.setEndTime(DateUtils.addDays(new Date(), 20));
        Long activityId = activityManager.createActivity(overflowActivity);


        ActivityClassifiedGoodsSourceModel sellerGoodsSource = new ActivityClassifiedGoodsSourceModel();
        sellerGoodsSource.setGoodsSourceType(ActivityGoodsSource.CLASSIFIED_GOODS_SOURCE_TYPE_SELLER);
        sellerGoodsSource.setGoodsSourceId(101l);
        sellerGoodsSource.setSecondaryGoodsSourceId(1011l);
        sellerGoodsSource.setGoodsSourceName("商家店铺活动");
        sellerGoodsSource.setQuery("sellerId=1011");
        sellerGoodsSource.setBizId(activityId);
        sellerGoodsSource.setComment("活动到关联店铺");
        activityManager.createActivityClassifiedGoodsSource(sellerGoodsSource, activityId);


        OverflowDiscountRule rule = new OverflowDiscountRule();
        rule.setOverflowCycleLimit(-1);
        rule.setOverflowDiscountType(OverflowDiscountRule.OverflowDiscountType.STEPPED_FEE_OVERFLOW);
        rule.setDescription("店铺满6666赠蓝牙耳机，满7777赠VR眼镜，满8888赠magic2 mouse");
        rule.setScope(OverflowDiscountRule.Scope.SELLER);
        Long overflowDiscountRuleId = promotionApplication.createOverflowDiscountRule(rule, activityId);

        OverflowDiscount overflowDiscount6666 = new OverflowDiscount();
        overflowDiscount6666.setOverflowFee(new Amount(6666));
        overflowDiscount6666.setUserGiftNum(1);
        Long overflowDiscountId6666 = promotionApplication.createOverflowDiscount(overflowDiscount6666, overflowDiscountRuleId);
        SimpleGift lyrj = new SimpleGift();
        lyrj.setGoodsId(934l);
        lyrj.setGoodsSpecsId(9341l);
        lyrj.setName("蓝牙耳机");
        lyrj.setPrice(new Amount(95.5));
        lyrj.setTotalNum(4513);
        lyrj.setUserNum(1);
        lyrj.setOuterGoodsId("gift934l");
        lyrj.setOuterGoodsSpecsId("gift9341l");
        promotionApplication.createOverflowDiscountGift(lyrj, overflowDiscountId6666);

        OverflowDiscount overflowDiscount7777 = new OverflowDiscount();
        overflowDiscount7777.setOverflowFee(new Amount(7777));
        overflowDiscount7777.setUserGiftNum(1);
        Long overflowDiscountId7777 = promotionApplication.createOverflowDiscount(overflowDiscount7777, overflowDiscountRuleId);
        SimpleGift vryj = new SimpleGift();
        vryj.setGoodsId(935l);
        vryj.setGoodsSpecsId(9351l);
        vryj.setName("VR眼镜");
        vryj.setPrice(new Amount(155.99));
        vryj.setTotalNum(3877);
        vryj.setUserNum(1);
        vryj.setOuterGoodsId("gift935l");
        vryj.setOuterGoodsSpecsId("gift9351l");
        promotionApplication.createOverflowDiscountGift(vryj, overflowDiscountId7777);

        OverflowDiscount overflowDiscount8888 = new OverflowDiscount();
        overflowDiscount8888.setOverflowFee(new Amount(8888));
        overflowDiscount8888.setUserGiftNum(1);
        Long overflowDiscountId8888 = promotionApplication.createOverflowDiscount(overflowDiscount8888, overflowDiscountRuleId);
        SimpleGift magicMouse = new SimpleGift();
        magicMouse.setGoodsId(936l);
        magicMouse.setGoodsSpecsId(9361l);
        magicMouse.setName("Magic2 mouse");
        magicMouse.setPrice(new Amount(699));
        magicMouse.setTotalNum(150);
        magicMouse.setUserNum(1);
        magicMouse.setOuterGoodsId("gift936l");
        magicMouse.setOuterGoodsSpecsId("gift9361l");
        promotionApplication.createOverflowDiscountGift(magicMouse, overflowDiscountId8888);
    }

    private void initGoodsActivity() {
        ActivityModel activity = new ActivityModel();
        activity.setName("goodsDiscountActivity");
        activity.setTag("商品折扣");
        activity.setDescription("商品折扣");
        activity.setStatus(1);
        activity.setStartTime(new Date());
        activity.setEndTime(DateUtils.addDays(new Date(), 20));
        Long activityId = activityManager.createActivity(activity);

        ActivityDetailedGoodsSourceModel activityDetailedGoodsSourceModel = new ActivityDetailedGoodsSourceModel();
        activityDetailedGoodsSourceModel.setBizId(activityId);
        activityDetailedGoodsSourceModel.setComment("活动关联商品.");
        activityDetailedGoodsSourceModel.setGoodsSourceType(ActivityGoodsSource.DETAILED_GOODS_SOURCE_TYPE);
        Long activityDetailedGoodsSourceId = activityManager.createActivityDetailedGoodsSource(activityDetailedGoodsSourceModel, activityId);

        ActivityGoodsModel goodsModel = new ActivityGoodsModel();
        goodsModel.setGoodsId(1l);
        goodsModel.setName("iPhoneX1");
        goodsModel.setOuterGoodsId("iPhoneX1");
        goodsModel.setTotalNum(111);
        goodsModel.setUserNum(1);
        Long activityGoodsId = activityManager.createActivityGoods(goodsModel, activityDetailedGoodsSourceId);

        ActivityGoodsSpecsModel goodsSpecsModel = new ActivityGoodsSpecsModel();
        goodsSpecsModel.setSpecsId(1l);
        goodsSpecsModel.setName("iPhoneX1-128g");
        goodsSpecsModel.setOuterSpecsId("iPhoneX1-128g");
        goodsSpecsModel.setTotalNum(10);
        goodsSpecsModel.setUserNum(1);
        Long activityGoodsSpecsId = activityManager.createActivityGoodsSpecs(goodsSpecsModel, activityGoodsId);

        goodsModel.setGoodsId(2l);
        goodsModel.setName("iPhoneX2");
        goodsModel.setOuterGoodsId("x1221212121212121");
        goodsModel.setTotalNum(222);
        goodsModel.setUserNum(2);
        Long activityGoodsId2 = activityManager.createActivityGoods(goodsModel, activityDetailedGoodsSourceId);

        goodsModel.setGoodsId(3l);
        goodsModel.setName("iPhoneX3");
        goodsModel.setOuterGoodsId("x1221212121212121");
        goodsModel.setTotalNum(333);
        goodsModel.setUserNum(1);
        Long activityGoodsId3 = activityManager.createActivityGoods(goodsModel, activityDetailedGoodsSourceId);


        GoodsDiscountRule goodsDiscountRule = new GoodsDiscountRule(null, Lists.newArrayList());
        goodsDiscountRule.setDescription("商品折扣规则");
        goodsDiscountRule.getGoodsDiscounts().add(
                new GoodsDiscount(activityGoodsId, new Discount(Discount.DiscountType.DISCOUNT, 85, null), Lists.newArrayList(
                        new GoodsSpecsDiscount(activityGoodsSpecsId, new Discount(Discount.DiscountType.OVERRIDE_AMOUNT, 3500, new Amount(4444))))));


        goodsDiscountRule.getGoodsDiscounts().add(
                new GoodsDiscount(activityGoodsId2, new Discount(Discount.DiscountType.DISCOUNT, 95, new Amount(7999)), Lists.newArrayList()));


        goodsDiscountRule.getGoodsDiscounts().add(
                new GoodsDiscount(activityGoodsId3, Lists.newArrayList(
                        new GoodsDiscountRule.UserLevelDiscount(
                                new Discount(Discount.DiscountType.DECREASE_AMOUNT, 299, new Amount(7999)), 1l, 1l, "vip会员"))));

        promotionApplication.createGoodsDiscountRule(goodsDiscountRule, activityId);
    }


    @Test
    public void testCalculateDiscount() {
//        initPlatformActivity();
//        initSellerActivity();
//        initGoodsActivity();
        PromotionCalculateRequest request = new PromotionCalculateRequest();
        request.setBuyerId(1l);
        request.setChannelType(ChannelConditionRule.ChannelType.APP_MALL);
        request.setChannelId(1);
        request.getCalculatingGoodsSpecs().add(new PromotionCalculateRequest.CalculatingGoodsSpecs(1l, 1));
        request.getCalculatingGoodsSpecs().add(new PromotionCalculateRequest.CalculatingGoodsSpecs(2l, 1));
        request.getCalculatingGoodsSpecs().add(new PromotionCalculateRequest.CalculatingGoodsSpecs(3l, 1));
        request.getCalculatingGoodsSpecs().add(new PromotionCalculateRequest.CalculatingGoodsSpecs(4l, 1));
        request.getCalculatingGoodsSpecs().add(new PromotionCalculateRequest.CalculatingGoodsSpecs(5l, 1));
        promotionApplication.calculateDiscount(request);
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

}
