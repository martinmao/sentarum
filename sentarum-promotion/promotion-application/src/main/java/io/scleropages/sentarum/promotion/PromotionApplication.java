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
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.dao.orm.jpa.Pages;
import org.scleropages.crud.exception.BizError;
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

    private ActivityManager activityManager;
    private ActivityRuleManager activityRuleManager;
    private ItemApi itemApi;

    public void calculateDiscount(DiscountCalculateRequest request) {
        Assert.notNull(request, "request must not be null.");
        request.getCalculatingGoods().forEach(goods -> {
            List<ConditionRule> goodsConditionRules = Lists.newArrayList();
            if (null != goods.getGoodsSpecsId()) {
                Sku sku = itemApi.getSku(goods.getGoodsSpecsId(), true, true, true);
                addSellerActivity(sku.item(), goodsConditionRules);
                List<? extends Activity> detailedActivities = activityManager.findAllActivityByDetailedGoodsSource(1, sku.item().id(), sku.id());
                detailedActivities.forEach(detailedActivity -> {
                    goodsConditionRules.add(activityRuleManager.getConditionRule(detailedActivity.id(), detailedActivity));
                });
            } else {
                Assert.notNull(goods.getGoodsId(), "goods id or goods specs id must least specify one.");
                Item item = itemApi.getItem(goods.getGoodsId(), true, true);
                addSellerActivity(item, goodsConditionRules);
            }
        });
    }

    private void addSellerActivity(Item item, List<ConditionRule> conditionRules) {
        Long sellerUnionId = item.sellerUnionId();
        Long sellerId = item.sellerId();
        List<? extends Activity> classifiedActivities = activityManager.findAllActivityByClassifiedGoodsSource(1, CLASSIFIED_GOODS_SOURCE_TYPE_SELLER, sellerUnionId, sellerId);
        classifiedActivities.forEach(classifiedActivity -> {
            conditionRules.add(activityRuleManager.getConditionRule(classifiedActivity.id(), classifiedActivity));
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(itemApi);
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(Pageable.unpaged()), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(0, 6, Sort.by("sales_price", "num"))), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(1, 3, Sort.by("sales_price", "num"))), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(2, 1, Sort.Direction.DESC, "sales_price", "num")), null)));
        System.out.println(JsonMapper2.toJson(itemApi.findItemPage(Maps.newHashMap(), Maps.newHashMap(), Pages.serializablePageable(PageRequest.of(1, 2, Sort.by(Sort.Order.asc("sales_price"), Sort.Order.desc("num")))), null)));

        DiscountCalculateRequest request = new DiscountCalculateRequest();
        request.setBuyerId(9527l);
        request.getCalculatingGoods().add(new DiscountCalculateRequest.CalculatingGoods(1l,1));
        request.getCalculatingGoods().add(new DiscountCalculateRequest.CalculatingGoods(2l,2));
        request.getCalculatingGoods().add(new DiscountCalculateRequest.CalculatingGoods(3l,3));
        request.getCalculatingGoods().add(new DiscountCalculateRequest.CalculatingGoods(4l,4));
        request.getCalculatingGoods().add(new DiscountCalculateRequest.CalculatingGoods(5l,5));

//        calculateDiscount(request);
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
    public void setItemApi(ItemApi itemApi) {
        this.itemApi = itemApi;
    }
}
