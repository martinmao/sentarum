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
import io.scleropages.sentarum.promotion.rule.context.GoodsPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.OrderPromotionContext;
import io.scleropages.sentarum.promotion.rule.context.PromotionContext;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.ActivityPromotionInvocation;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.TrueCondition;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import org.scleropages.core.mapper.JsonMapper2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * activity promotion calculating implementation.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ActivityPromotionInvocationChain implements InvocationChain {


    protected static final Logger logger = LoggerFactory.getLogger(ActivityPromotionInvocationChain.class);

    //sequential invocations of this chain.
    private final List<ActivityPromotionInvocationChainFrame> invocationFrames;
    //next chain if sequential invocations finished.
    private final InvocationChain nextInvocationChain;
    private final PromotionContext initialContext;
    //position of current invocation in this chain.
    private int currentPosition = 0;

    private final String name;

    public ActivityPromotionInvocationChain(PromotionContext initialContext, RuleContainer ruleContainer, InvocationChain nextInvocationChain, String name) {
        this.initialContext = initialContext;
        this.invocationFrames = create(initialContext.activities(), ruleContainer);
        this.nextInvocationChain = nextInvocationChain;
        this.name = name;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public void start() {
        if (logger.isDebugEnabled()) {
            logger.debug("promotion-calculating-chain starting: " + name());
        }
        next(initialContext);
    }

    @Override
    public void next(InvocationContext invocationContext) {
        if (null != nextInvocationChain && currentPosition == invocationFrames.size()) {
            if (logger.isDebugEnabled()) {
                logger.debug("{}forwarding to next promotion-calculating-chain: {}", invocationFrames.size() == 0 ? "no calculating found. " : "", nextInvocationChain.name());
            }
            nextInvocationChain.next(invocationContext);
        } else {
            if (null == nextInvocationChain) {
                if (logger.isDebugEnabled()) {
                    logger.debug("no more promotion-calculating and chains to forwarding. exit.");
                }
                return;
            }
            ActivityPromotionInvocationChainFrame next = invocationFrames.get(currentPosition++);
            ConditionRule rule = next.conditionRule;
            ActivityPromotionInvocation invocation = next.activityPromotionInvocation;
            Activity activity = next.activity;
            if (logger.isDebugEnabled()) {
                logger.debug("invoking promotion-calculating[{}/{}]: {} for activity: {}", currentPosition, invocationFrames.size(), invocation.information(), activity.name());
            }
            invocation.execute(rule, (PromotionContext) invocationContext, this);
        }
    }

    @Override
    public void logInformation(Logger logger) {
        if (invocationFrames.size() == 0) {
            logger.debug("      no activities");
        }
        for (ActivityPromotionInvocationChainFrame invocationFrame : invocationFrames) {
            Activity activity = invocationFrame.activity;
            ConditionRule conditionRule = invocationFrame.conditionRule;
            ActivityPromotionInvocation invocation = invocationFrame.activityPromotionInvocation;
            logger.debug("      activity[{}-{}-{}]: with context: {}", activity.id(), activity.name(), activity.tag(), initialContextInformation(initialContext));
            logger.debug("         condition-rule[{}-{}]: {}", conditionRule.id(), conditionRule.description(), JsonMapper2.toJson(conditionRule));
            logger.debug("         {}", invocation.information());
        }
        if (null != nextInvocationChain) {
            logger.debug("  ->next chain:{}", nextInvocationChain.name());
            nextInvocationChain.logInformation(logger);
        }
    }

    private final String initialContextInformation(PromotionContext initialContext) {
        if (initialContext instanceof GoodsPromotionContext) {
            GoodsPromotionContext goodsPromotionContext = (GoodsPromotionContext) initialContext;
            return String.format("GoodsPromotionContext: goods-specs [%s-%s] with num %s.", goodsPromotionContext.goodsId(), goodsPromotionContext.specsId(), goodsPromotionContext.num());
        }
        if (initialContext instanceof OrderPromotionContext) {
            OrderPromotionContext orderPromotionContext = (OrderPromotionContext) initialContext;
            return String.format("OrderPromotionContext: seller [%s-%s] sell goods size: %s.", orderPromotionContext.sellerUnionId(), orderPromotionContext.sellerId(), orderPromotionContext.goodsPromotionContexts().size());
        } else {
            return String.format("CartPromotionContext: buyer %s from %s[%s].", initialContext.buyerId(), initialContext.channelType(), initialContext.channelId());
        }
    }

    private List<ActivityPromotionInvocationChainFrame> create(List<Activity> activities, RuleContainer ruleContainer) {
        if (activities.isEmpty())
            return Collections.emptyList();
        List<ActivityPromotionInvocationChainFrame> activityPromotionInvocations = Lists.newArrayList();
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            List<Rule> rules = activity.conditionRules();
            ConditionRule conditionRule = CollectionUtils.isEmpty(rules) ? TrueCondition.TRUE_CONDITION_RULE : (ConditionRule) rules.get(0);
            if (activity.promotionalRule() == null) {
                logger.warn("detected activity[{}] no calculator rule set. ignoring to calculating.", activity.id());
                continue;
            }
            activityPromotionInvocations.add(new ActivityPromotionInvocationChainFrame(
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
    private static class ActivityPromotionInvocationChainFrame {
        private final ConditionRule conditionRule;
        private final ActivityPromotionInvocation activityPromotionInvocation;
        private final Activity activity;

        public ActivityPromotionInvocationChainFrame(ConditionRule conditionRule, ActivityPromotionInvocation activityPromotionInvocation, Activity activity) {
            this.conditionRule = conditionRule;
            this.activityPromotionInvocation = activityPromotionInvocation;
            this.activity = activity;
        }
    }
}
