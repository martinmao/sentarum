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

/**
 * 订单整单优惠信息，一个订单包含多个订单级优惠，每一个订单优惠信息对应一个优惠活动的核销.
 * 订单级优惠适用于满减、优惠套餐（打包价）、订单返现、通用优惠卡券、整单优惠等场景
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderPromotion extends Promotion {

    /**
     * 关联的订单
     *
     * @return
     */
    Order order();

}
