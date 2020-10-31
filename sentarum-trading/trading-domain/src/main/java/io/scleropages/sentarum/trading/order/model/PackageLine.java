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
 * 订单交付（物流）打包明细
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PackageLine {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();


    /**
     * 交易明细
     *
     * @return
     */
    OrderLine orderLine();


    /**
     * 物流包裹
     *
     * @return
     */
    DeliveryPackage orderDeliveryPackage();

    /**
     * 订单交付信息
     *
     * @return
     */
    OrderDelivery orderDelivery();


    /**
     * 商品件数
     *
     * @return
     */
    String num();


    /**
     * 关联的订单
     *
     * @return
     */
    Order order();
}
