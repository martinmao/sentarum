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
 * 订单交付（物流）打包信息
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface DeliveryPackage {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 物流单号
     *
     * @return
     */
    String expressNo();


    /**
     * 物流公司id
     *
     * @return
     */
    Integer expressId();

    /**
     * 物流公司名称
     *
     * @return
     */
    String expressName();

    /**
     * 备注
     *
     * @return
     */
    String remark();

    /**
     * 关联的订单
     *
     * @return
     */
    Order order();

    /**
     * 关联的订单交付
     *
     * @return
     */
    OrderDelivery orderDelivery();

    /**
     * 物流状态
     *
     * @return
     */
    Integer expressState();


    /**
     * 物流费用
     *
     * @return
     */
    Amount expressFee();
}
