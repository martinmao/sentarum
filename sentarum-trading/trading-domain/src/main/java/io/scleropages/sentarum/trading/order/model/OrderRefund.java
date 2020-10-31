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
 * 退款（退货）单
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderRefund {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 关联订单
     *
     * @return
     */
    Order order();


    /**
     * 申请退款时间
     *
     * @return
     */
    Date requestTime();


    /**
     * 申请退款描述
     *
     * @return
     */
    String requestContent();


    /**
     * 退款总金额= sum({@link RefundLine#refundFee()})
     *
     * @return
     */
    Amount totalRefundFee();

    /**
     * 退款状态：
     * <pre>
     * 买家已经申请退款，等待卖家同意
     * 卖家拒绝退款
     * 卖家已经同意退货，等待买家退货
     * 买家已经退货，等待卖家确认收货
     * 卖家未收到货,拒绝退款
     * 退款关闭
     * 退款成功
     * </pre>
     *
     * @return
     */
    Integer refundState();

    /**
     * 退款类型: 1买家申请退款，2:商家主动退款， 3:自动退款(订单关闭退款，拼团未成团退款，返现退款，团购返现退款，拒单退款...)
     *
     * @return
     */
    Integer refundType();

    /**
     * 备注说明
     *
     * @return
     */
    String refundTypeRemark();
}
