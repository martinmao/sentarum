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

import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link io.scleropages.sentarum.promotion.rule.model.AbstractRule}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class AbstractRuleEntity extends IdEntity {

    private ActivityEntity activity;

    private Integer ruleInvocationImplementation;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    public ActivityEntity getActivity() {
        return activity;
    }

    @Column(name = "rule_invocation_impl", nullable = false)
    public Integer getRuleInvocationImplementation() {
        return ruleInvocationImplementation;
    }

    @Column(name = "desc_", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
    }

    public void setRuleInvocationImplementation(Integer ruleInvocationImplementation) {
        this.ruleInvocationImplementation = ruleInvocationImplementation;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
