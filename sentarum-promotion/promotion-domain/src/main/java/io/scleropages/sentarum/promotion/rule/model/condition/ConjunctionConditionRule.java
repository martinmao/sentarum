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
package io.scleropages.sentarum.promotion.rule.model.condition;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.rule.model.AbstractConditionRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * represent a conjunction condition rule. using {@link ConditionConjunction} as operator to combined group of rules.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ConjunctionConditionRule extends AbstractConditionRule {

    /**
     * conjunction operator of this condition.
     */
    private ConditionConjunction conditionConjunction;


    /**
     * combined conditions.
     */
    private List<ConditionRule> conditions;

    /**
     * return conjunction operator of this condition.
     *
     * @return
     */
    public ConditionConjunction getConditionConjunction() {
        return conditionConjunction;
    }

    /**
     * return all conditions combined from this with operator {@link #getConditionConjunction()}.
     *
     * @return
     */
    public List<ConditionRule> getConditions() {
        if (null == conditions)
            conditions = Lists.newArrayList();
        return conditions;
    }

    public void setConditionConjunction(ConditionConjunction conditionConjunction) {
        this.conditionConjunction = conditionConjunction;
    }

    public void setConditions(List<ConditionRule> conditions) {
        this.conditions = conditions;
    }

    /**
     * combine this condition with another one using {@link #getConditionConjunction()}.
     *
     * @param conditionRule
     */
    public void addCondition(ConditionRule conditionRule) {
        Assert.notNull(conditionRule, "conditionRule must not be null.");
        if (conditionRule instanceof AbstractConditionRule) {
            ((AbstractConditionRule) conditionRule).setParent(this);
        }
        getConditions().add(conditionRule);
    }


    /**
     * enumerate of condition conjunction.
     */
    public enum ConditionConjunction {

        AND(0, "AND"), OR(1, "OR"), NOT(2, "NOT");

        private final int ordinal;

        private final String tag;

        ConditionConjunction(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }


        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }


        private static final Map<String, ConditionConjunction> nameMappings = new HashMap<>();
        private static final Map<Integer, ConditionConjunction> ordinalMappings = new HashMap<>();

        static {
            for (ConditionConjunction conditionConjunction : ConditionConjunction.values()) {
                nameMappings.put(conditionConjunction.name(), conditionConjunction);
                ordinalMappings.put(conditionConjunction.getOrdinal(), conditionConjunction);
            }
        }


        public static ConditionConjunction getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ConditionConjunction getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}
