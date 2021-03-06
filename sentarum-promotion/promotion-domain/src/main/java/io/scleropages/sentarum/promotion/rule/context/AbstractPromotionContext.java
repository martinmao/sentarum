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
package io.scleropages.sentarum.promotion.rule.context;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.promotion.activity.model.Activity;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AbstractPromotionContext implements PromotionContext {

    private Amount totalAmount;

    private List<Activity> activities = Collections.emptyList();

    private final List<PromotionResult> promotionResults = Lists.newArrayList();

    protected void setTotalAmount(Amount totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public Amount totalAmount() {
        return totalAmount;
    }

    @Override
    public List<Activity> activities() {
        return Collections.unmodifiableList(activities);
    }

    protected void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public void addPromotionResult(PromotionResult promotionResult) {
        promotionResults.add(promotionResult);
    }

    @Override
    public List<PromotionResult> promotionResults() {
        return Collections.unmodifiableList(promotionResults);
    }
}
