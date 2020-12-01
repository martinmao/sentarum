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
import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.mgmt.ActivityRuleManager;
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
        request.getCalculatingGoods().forEach(calculatingGoods -> {
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
