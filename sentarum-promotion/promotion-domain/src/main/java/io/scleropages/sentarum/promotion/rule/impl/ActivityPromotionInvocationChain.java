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
package io.scleropages.sentarum.promotion.rule.impl;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.invocation.ActivityPromotionInvocation;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.TrueCondition;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * activity promotion calculating implementation.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ActivityPromotionInvocationChain implements InvocationChain {


    protected static final Logger logger = LoggerFactory.getLogger(ActivityPromotionInvocationChain.class);

    //sequential invocations of this chain.
    private final List<ActivityPromotionInvocationFrame> invocationFrames;
    //next chain if sequential invocations finished.
    private final InvocationChain nextInvocationChain;
    //position of current invocation in this chain.
    private int currentPosition = 0;
    private List<Activity> activities;

    public ActivityPromotionInvocationChain(List<Activity> activities, RuleContainer ruleContainer, InvocationChain nextInvocationChain) {
        this.activities = activities;
        this.invocationFrames = create(activities, ruleContainer);
        this.nextInvocationChain = nextInvocationChain;
    }


    @Override
    public void next(InvocationContext invocationContext) {
        if (null != nextInvocationChain && currentPosition == invocationFrames.size()) {
            if (logger.isDebugEnabled()) {
                logger.debug("forwarding to next invocation chain: {}", nextInvocationChain);
            }
            nextInvocationChain.next(invocationContext);
        } else {
            ActivityPromotionInvocationFrame next = invocationFrames.get(currentPosition++);
            ConditionRule rule = next.conditionRule;
            ActivityPromotionInvocation invocation = next.activityPromotionInvocation;
            if (logger.isDebugEnabled()) {
                logger.debug("invoking rule invocation: {}", invocation.information());
            }
            invocation.execute(rule, (PromotionContext) invocationContext, this);
        }
    }

    private List<ActivityPromotionInvocationFrame> create(List<Activity> activities, RuleContainer ruleContainer) {
        Assert.notEmpty(activities, "activities must not empty.");
        List<ActivityPromotionInvocationFrame> activityPromotionInvocations = Lists.newArrayList();
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            List<Rule> rules = activity.conditionRules();
            ConditionRule conditionRule = CollectionUtils.isEmpty(rules) ? null : TrueCondition.TRUE_CONDITION_RULE;
            if (activity.promotionalRule() == null) {
                logger.warn("detected activity[{}] no calculator rule set. ignoring to calculating.", activity.id());
                continue;
            }
            activityPromotionInvocations.add(new ActivityPromotionInvocationFrame(
                            conditionRule
                            , new ActivityPromotionInvocation(activity, ruleContainer.getCondition(conditionRule), ruleContainer)
                            , activity
                    )
            );
        }
        return activityPromotionInvocations;
    }


    /**
     * frame of promotion invocation.
     */
    private static class ActivityPromotionInvocationFrame {
        private final ConditionRule conditionRule;
        private final ActivityPromotionInvocation activityPromotionInvocation;
        private final Activity activity;

        public ActivityPromotionInvocationFrame(ConditionRule conditionRule, ActivityPromotionInvocation activityPromotionInvocation, Activity activity) {
            this.conditionRule = conditionRule;
            this.activityPromotionInvocation = activityPromotionInvocation;
            this.activity = activity;
        }
    }
}
