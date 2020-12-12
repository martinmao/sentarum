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
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;
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

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CalculatorTestcase {

    @Autowired
    private PromotionApplication promotionApplication;
    @Autowired
    private ItemApi itemApi;


    @Test
    public void testCalculateDiscount() {
        PromotionCalculateRequest request = new PromotionCalculateRequest();
        request.setBuyerId(1l);
        request.setChannelType(ChannelConditionRule.ChannelType.APP_MALL);
        request.setChannelId(5);
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
