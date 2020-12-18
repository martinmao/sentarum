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

import com.google.common.collect.Maps;
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityClassifiedGoodsSourceModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.rule.model.calculator.Gift;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule;
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
import org.springframework.transaction.annotation.Transactional;

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
        overflowActivity.setTag("平台满减活动");
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
        Long overflowDiscountRuleId = promotionApplication.createOverflowDiscountRule(rule, activityId);
        OverflowDiscount overflowDiscount = new OverflowDiscount();
        overflowDiscount.setOverflowFee(new Amount(200));
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
        rule.setOverflowDiscountType(OverflowDiscountRule.OverflowDiscountType.FIXED_FEE_OVERFLOW);
        rule.setDescription("店铺满6666赠蓝牙耳机，满7777赠VR眼镜，满8888赠magic2 mouse");
        Long overflowDiscountRuleId = promotionApplication.createOverflowDiscountRule(rule, activityId);

        OverflowDiscount overflowDiscount6666 = new OverflowDiscount();
        overflowDiscount6666.setOverflowFee(new Amount(6666));
        overflowDiscount6666.setUserGiftNum(1);
        Long overflowDiscountId6666 = promotionApplication.createOverflowDiscount(overflowDiscount6666, overflowDiscountRuleId);
        Gift lyrj = new Gift();
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
        Gift vryj = new Gift();
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
        Gift magicMouse = new Gift();
        magicMouse.setGoodsId(936l);
        magicMouse.setGoodsSpecsId(9361l);
        magicMouse.setName("Magic2 mouse");
        magicMouse.setPrice(new Amount(699));
        magicMouse.setTotalNum(150);
        magicMouse.setUserNum(1);
        magicMouse.setOuterGoodsId("gift936l");
        magicMouse.setOuterGoodsSpecsId("gift9361l");
        promotionApplication.createOverflowDiscountGift(vryj, overflowDiscountId8888);
    }


    @Test
    public void testCalculateDiscount() {
//        initPlatformActivity();
//        initSellerActivity();
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
