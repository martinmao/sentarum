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
package io.scleropages.sentarum.promotion.rule.entity;

import io.scleropages.sentarum.promotion.rule.model.AbstractConditionRule;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link AbstractConditionRule}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class AbstractConditionRuleEntity extends AbstractRuleEntity {

    private Integer conditionConjunction;

    private Long parentCondition;

    @Column(name = "condition_conjunction")
    public Integer getConditionConjunction() {
        return conditionConjunction;
    }

    @Column(name = "parent_condition_id")
    public Long getParentCondition() {
        return parentCondition;
    }

    public void setConditionConjunction(Integer conditionConjunction) {
        this.conditionConjunction = conditionConjunction;
    }

    public void setParentCondition(Long parentCondition) {
        this.parentCondition = parentCondition;
    }
}
