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
 * 订单明细
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderLine {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();


    /**
     * 商品唯一标识
     *
     * @return
     */
    Long itemId();

    /**
     * sku 唯一标识
     *
     * @return
     */
    Long skuId();

    /**
     * 外部商品编码
     *
     * @return
     */
    String outerItemId();

    /**
     * 外部sku编码
     *
     * @return
     */
    String outerSkuId();

    /**
     * 购买单价
     *
     * @return
     */
    Long price();

    /**
     * 购买数量
     *
     * @return
     */
    Integer num();


    /**
     * 单品总价 = {@link #price()} * {@link #num()}
     *
     * @return
     */
    Amount totalFee();


    /**
     * 订单明细应付金额= {@link #price()} * {@link #num()} - sum({@link LinePromotion#discountFee()}) - others...
     *
     * @return
     */
    Amount payment();


    /**
     * 是否为赠品
     *
     * @return
     */
    Boolean present();

    /**
     * 关联的订单.
     *
     * @return
     */
    Order order();


    /**
     * sku 快照.
     *
     * @return
     */
    SkuSnapshot sku();
}
