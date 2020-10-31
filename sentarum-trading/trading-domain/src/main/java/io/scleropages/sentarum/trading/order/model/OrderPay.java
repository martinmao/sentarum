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
 * 订单支付信息(支付单)
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderPay {

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
     * 整单应付金额={@link #totalFee()} - sum({@link OrderPromotion#discountFee()} ()} - sum({@link OrderLinePromotion#discountFee()}) + {@link #deliveryFee()} + {@link #adjustFee()} <br>
     *
     * @return
     */
    Amount payment();


    /**
     * 实付金额，正常情况下于 {@link #payment()}保持一致，但存在以下例外：
     * <pre>
     *  定金预售时为定金金额
     *  未支付时为0
     *  货到付款为0
     * </pre>
     *
     * @return
     */
    Amount actualPayment();


    /**
     * 订单总金额(商品总价)= sum({@link OrderLine#totalFee()})
     *
     * @return
     */
    Amount totalFee();


    /**
     * 订单级优惠总金额
     * 计算规则：sum({@link OrderPromotion#discountFee()} ()})
     *
     * @return
     */
    Amount totalOrderDiscountFee();

    /**
     * 商品级优惠总金额
     * 计算规则： sum({@link OrderLinePromotion#discountFee()})
     *
     * @return
     */
    Amount totalItemDiscountFee();


    /**
     * 改价金额（卖家改价补邮费...）
     *
     * @return
     */
    Amount adjustFee();


    /**
     * 总配送金额(快递费)
     * 计算规则: sum({@link DeliveryPackage#expressFee()})
     *
     * @return
     */
    Amount deliveryFee();

    /**
     * 关联订单
     *
     * @return
     */
    Order order();
}
