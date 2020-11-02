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

import io.scleropages.sentarum.core.model.primitive.Address;
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Geo;
import io.scleropages.sentarum.core.model.primitive.Tel;

import java.util.Date;

/**
 * 描述订单交付信息(收货人信息)
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderConsignee {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 配送开始时间
     *
     * @return
     */
    Date startTime();

    /**
     * 配送结束时间
     *
     * @return
     */
    Date endTime();

    /**
     * 配送交付时间
     *
     * @return
     */
    Date deliveredTime();

    /**
     * 配送金额
     *
     * @return
     */
    Amount deliveryAmount();

    /**
     * 收货人姓名
     *
     * @return
     */
    String name();


    /**
     * 收货人电话
     *
     * @return
     */
    Tel tel();

    /**
     * 收货人地址
     *
     * @return
     */
    Address address();

    /**
     * 收货人详细地址
     *
     * @return
     */
    String detailAddress();


    /**
     * 邮编
     *
     * @return
     */
    String postalCode();


    /**
     * 配送地理信息
     *
     * @return
     */
    Geo geo();

    /**
     * 关联的订单
     *
     * @return
     */
    Order order();
}