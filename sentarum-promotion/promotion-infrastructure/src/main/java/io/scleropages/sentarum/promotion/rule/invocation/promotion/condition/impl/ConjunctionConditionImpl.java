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
package io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.impl;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.RuleContainer;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.PromotionCondition;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.ConjunctionCondition;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule.ConditionConjunction.*;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class ConjunctionConditionImpl implements ConjunctionCondition {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private RuleContainer ruleContainer;

    @Override
    public boolean match(ConjunctionConditionRule rule, InvocationContext invocationContext) {
        ConjunctionConditionRule.ConditionConjunction conjunction = rule.getConditionConjunction();
        List<ConditionRule> conditions = rule.getConditions();
        List<Boolean> matches = Lists.newArrayList();
        if (Objects.equals(NOT, conjunction)) {
            if (conditions.isEmpty()) {
                logger.warn("conjunction condition [{}] no child conditions found with operator 'NOT'. using true as result.", rule.id());
                return true;
            } else {
                ConditionRule not = conditions.get(0);
                if (conditions.size() > 1) {
                    logger.warn("conjunction condition [{}] has more than one child conditions with operator 'NOT'. using first [{}] as result.", rule.id(), not.id());
                }
                return !matchInternal(invocationContext, not);
            }
        }
        conditions.forEach(conditionRule -> matches.add(matchInternal(invocationContext, conditionRule)));
        if (Objects.equals(AND, conjunction)) {
            for (Boolean match : matches) {
                if (!match)
                    return false;
            }
            return true;
        } else if (Objects.equals(OR, conjunction)) {
            for (Boolean match : matches) {
                if (match)
                    return true;
            }
            return false;
        }
        return false;
    }

    public Boolean matchInternal(InvocationContext invocationContext, ConditionRule conditionRule) {
        if (conditionRule instanceof ConjunctionConditionRule) {
            return match((ConjunctionConditionRule) conditionRule, invocationContext);
        }
        PromotionCondition condition = ruleContainer.getCondition(conditionRule);
        return condition.match(conditionRule, invocationContext);
    }


    @Autowired
    public void setRuleContainer(RuleContainer ruleContainer) {
        this.ruleContainer = ruleContainer;
    }
}
