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

import io.scleropages.sentarum.promotion.activity.model.impl.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel;
import io.scleropages.sentarum.promotion.mgmt.ActivityManager;
import io.scleropages.sentarum.promotion.mgmt.ActivityRuleManager;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
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

        ActivityClassifiedGoodsSource brandGoodsSource = new ActivityClassifiedGoodsSource();
        brandGoodsSource.setGoodsSourceType(1);
        brandGoodsSource.setGoodsSourceId(1l);
        brandGoodsSource.setGoodsSourceName("Apple Inc");
        brandGoodsSource.setQuery("brand=1");
        brandGoodsSource.setBizId(activityId);
        brandGoodsSource.setComment("活动关联品牌");

        activityManager.createActivityClassifiedGoodsSource(brandGoodsSource, activityId);

        brandGoodsSource.setGoodsSourceId(2l);
        brandGoodsSource.setGoodsSourceName("Amazon");
        brandGoodsSource.setQuery("brand=2");

        activityManager.createActivityClassifiedGoodsSource(brandGoodsSource, activityId);


        System.out.println(JsonMapper2.toJson(activityManager.findAllActivityByClassifiedGoodsSource(1, 1, null, null)));

        activity.setName("TEST2");
        activity.setTag("品类活动");
        activity.setDescription("品类活动测试1");
        activity.setStatus(1);
        activity.setStartTime(new Date());
        activity.setEndTime(DateUtils.addDays(new Date(), 20));
        activityId = activityManager.createActivity(activity);


        ActivityClassifiedGoodsSource categoryGoodsSource = new ActivityClassifiedGoodsSource();

        categoryGoodsSource.setGoodsSourceType(2);
        categoryGoodsSource.setGoodsSourceId(1l);
        categoryGoodsSource.setSecondaryGoodsSourceId(1l);
        categoryGoodsSource.setGoodsSourceName("Computer/office-notebook");
        categoryGoodsSource.setQuery("category=1");
        categoryGoodsSource.setBizId(activityId);
        categoryGoodsSource.setComment("活动关联品类");

        activityManager.createActivityClassifiedGoodsSource(categoryGoodsSource, activityId);

        categoryGoodsSource.setGoodsSourceType(2);
        categoryGoodsSource.setGoodsSourceId(1l);
        categoryGoodsSource.setSecondaryGoodsSourceId(2l);
        categoryGoodsSource.setGoodsSourceName("Computer/office-microphone");
        categoryGoodsSource.setQuery("category=2");
        categoryGoodsSource.setBizId(activityId);
        categoryGoodsSource.setComment("活动关联品类");

        activityManager.createActivityClassifiedGoodsSource(categoryGoodsSource, activityId);

        System.out.println(JsonMapper2.toJson(activityManager.findAllActivityByClassifiedGoodsSource(1, 2, null, null)));


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
