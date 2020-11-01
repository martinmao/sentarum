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
 * 订单明细优惠信息，适用于商品维度的优惠活动，例如：一口价、秒杀、单品优惠券等场景
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface LinePromotion extends Promotion {


    /**
     * 关联的订单
     *
     * @return
     */
    Order order();


    /**
     * 关联的订单明细
     *
     * @return
     */
    OrderLine orderLine();

}
