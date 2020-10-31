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
package io.scleropages.sentarum.trading.order.model;

import io.scleropages.sentarum.core.model.primitive.Amount;

/**
 * 优惠信息，其对应一个优惠活动的核销，每一笔交易单包含多个优惠核销，按类型分为：
 * <pre>
 *     {@link OrderPromotion}: 订单级优惠，优惠作用于整单中所有商品。适用于：满减、优惠套餐（打包价）、订单返现、通用优惠卡券、整单优惠等场景
 *     {@link LinePromotion}：商品级优惠，优惠仅限于单一商品。适用于：一口价、秒杀、单品优惠券等场景
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Promotion {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();


    /**
     * 促销活动类型id
     *
     * @return
     */
    Long activityTypeId();


    /**
     * 促销活动类型名称
     *
     * @return
     */
    String activityTypeName();

    /**
     * 促销活动id
     *
     * @return
     */
    Long activityId();


    /**
     * 促销活动名称
     *
     * @return
     */
    String activityName();

    /**
     * 券id，优惠券活动时有效
     *
     * @return
     */
    Long couponId();

    /**
     * 优惠金额
     *
     * @return
     */
    Amount discountFee();
}
