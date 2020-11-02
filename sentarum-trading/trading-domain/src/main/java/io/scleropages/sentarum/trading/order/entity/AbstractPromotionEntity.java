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
package io.scleropages.sentarum.trading.order.entity;

import io.scleropages.sentarum.core.entity.embeddable.EmbeddableAmount;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.PromotionModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@MappedSuperclass
public class AbstractPromotionEntity extends IdEntity {

    private Long activityTypeId;
    private String activityTypeName;
    private Long activityId;
    private String activityName;
    private Long couponId;
    private EmbeddableAmount discountFee;

    @Column(name = "activity_type_id", nullable = false)
    public Long getActivityTypeId() {
        return activityTypeId;
    }

    @Column(name = "activity_type_name", nullable = false)
    public String getActivityTypeName() {
        return activityTypeName;
    }

    @Column(name = "activity_id", nullable = false)
    public Long getActivityId() {
        return activityId;
    }

    @Column(name = "activity_name", nullable = false)
    public String getActivityName() {
        return activityName;
    }

    @Column(name = "coupon_id", nullable = false)
    public Long getCouponId() {
        return couponId;
    }

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "discount_fee", nullable = false))
    public EmbeddableAmount getDiscountFee() {
        return discountFee;
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

    public void setDiscountFee(EmbeddableAmount discountFee) {
        this.discountFee = discountFee;
    }
}
