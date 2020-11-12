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
package io.scleropages.sentarum.promotion.rule.entity.condition;

import io.scleropages.sentarum.promotion.rule.entity.AbstractConditionRuleEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "type_", discriminatorType = DiscriminatorType.INTEGER)
@Table(name = "prom_condition_base")
@SequenceGenerator(name = "prom_condition_base_id", sequenceName = "seq_prom_condition_base", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class BaseConditionRuleEntity extends AbstractConditionRuleEntity {

    private String ruleClass;

    private String rulePayload;

    @Column(name = "rule_clazz")
    public String getRuleClass() {
        return ruleClass;
    }

    @Column(name = "rule_payload")
    public String getRulePayload() {
        return rulePayload;
    }

    public void setRuleClass(String ruleClass) {
        this.ruleClass = ruleClass;
    }

    public void setRulePayload(String rulePayload) {
        this.rulePayload = rulePayload;
    }
}
