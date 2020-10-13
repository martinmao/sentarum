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
package io.scleropages.sentarum.promotion.coupon.model;

import io.scleropages.sentarum.promotion.activity.model.Activity;

import java.util.Date;

/**
 * defined a source of coupon information.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CouponSource {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();


    /**
     * 券名称
     *
     * @return
     */
    String name();

    /**
     * 券描述
     *
     * @return
     */
    String description();


    /**
     * 券关联的活动.
     *
     * @return
     */
    Activity activity();


    /**
     * total num of coupon.
     *
     * @return
     */
    Integer totalNum();


    /**
     * num of coupon per user.
     *
     * @return
     */
    Integer userNum();


    /**
     * 券领取后的有效天数.超过有效天数则处于过期状态
     *
     * @return
     */
    Integer validDays();


    /**
     * 券适用开始时间，activity可设定一个非常大的时间区间，而券可根据运营需要随时设定有效期.即优惠券活动可以始终存在
     * 但优惠券可根据需要随时创建.
     *
     * @return
     */
    Date startTime();

    /**
     * 券适用结束时间
     *
     * @return
     */
    Date endTime();

}
