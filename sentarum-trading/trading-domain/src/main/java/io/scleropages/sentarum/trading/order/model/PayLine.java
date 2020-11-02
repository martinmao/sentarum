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

import java.util.Date;

/**
 * 支付单明细，类似预售等场景下: 将付款分为两个阶段（订金，尾款），此时将会产生两条子支付信息
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PayLine {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 外部支付单号
     *
     * @return
     */
    String outerId();

    /**
     * 支付金额
     *
     * @return
     */
    Amount payment();

    /**
     * 支付开始时间
     *
     * @return
     */
    Date startTime();

    /**
     * 支付结束时间
     *
     * @return
     */
    Date endTime();

    /**
     * 关联订单支付
     *
     * @return
     */
    OrderPay orderPay();

    /**
     * 关联的订单
     *
     * @return
     */
    Order order();
}
