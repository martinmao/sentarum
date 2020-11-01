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
package io.scleropages.sentarum.trading.order.model.impl;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.trading.order.model.Promotion;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class PromotionModel implements Promotion {

    private Long id;
    private Long activityTypeId;
    private String activityTypeName;
    private Long activityId;
    private String activityName;
    private Long couponId;
    private Amount discountFee;

    public Long getId() {
        return id;
    }

    public Long getActivityTypeId() {
        return activityTypeId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public Long getCouponId() {
        return couponId;
    }

    public Amount getDiscountFee() {
        return discountFee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setActivityTypeId(Long activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public void setDiscountFee(Amount discountFee) {
        this.discountFee = discountFee;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public Long activityTypeId() {
        return getActivityTypeId();
    }

    @Override
    public String activityTypeName() {
        return getActivityTypeName();
    }

    @Override
    public Long activityId() {
        return getActivityId();
    }

    @Override
    public String activityName() {
        return getActivityName();
    }

    @Override
    public Long couponId() {
        return getCouponId();
    }

    @Override
    public Amount discountFee() {
        return getDiscountFee();
    }
}
