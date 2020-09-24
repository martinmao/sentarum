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

import io.scleropages.sentarum.trading.flow.model.TradingFlow;

/**
 * 订单
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Order {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();


    /**
     * 外部订单编码
     *
     * @return
     */
    String outerId();


    /**
     * 父订单唯一标识，拆单前订单标识
     *
     * @return
     */
    Long parentId();


    /**
     * 商家类型，由商品中心确定
     * <pre>
     *         SUPPLIER(1, "供应商", "位于供应链上游"),
     *         VENDOR(2, "厂商(品牌商)", "售卖产品或服务"),
     *         RETAIL(3, "零售商", "其往往是一个商业综合体，包含多家门店"),
     *         STORE(4, "门店", "终端场所销售"),
     *         PLATFORM(5, "平台", "自营");
     * </pre>
     *
     * @return
     */
    Integer sellerType();

    /**
     * 商家唯一标识（商业综合体标识）
     *
     * @return
     */
    Long sellerUnionId();

    /**
     * 商家唯一标识 (商业综合体内具体销售场所，例如店铺)
     *
     * @return
     */
    Long sellerId();


    /**
     * 买家唯一标识
     *
     * @return
     */
    Long buyerId();

    /**
     * 订单关联的交易流
     *
     * @return
     */
    TradingFlow tradingFlow();

}
