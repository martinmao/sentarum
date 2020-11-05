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
package io.scleropages.sentarum.promotion.rule.model;

import io.scleropages.sentarum.promotion.activity.model.Activity;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractRule implements Rule {

    private Long id;

    private Activity activity;

    private String ruleInvocationImplementation;

    private String description;

    public Long getId() {
        return id;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getRuleInvocationImplementation() {
        return ruleInvocationImplementation;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setRuleInvocationImplementation(String ruleInvocationImplementation) {
        this.ruleInvocationImplementation = ruleInvocationImplementation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }


    @Override
    public String ruleInvocationImplementation() {
        return getRuleInvocationImplementation();
    }

    @Override
    public String description() {
        return getDescription();
    }
}
