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
 * 订单发票
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderInvoice {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();


    /**
     * 买家税号
     *
     * @return
     */
    String taxpayerId();

    /**
     * 发票抬头
     *
     * @return
     */
    String title();

    /**
     * 抬头类型：personal 个人=0, enterprise 企业=1
     *
     * @return
     */
    Integer titleType();

    /**
     * 买家邮箱
     *
     * @return
     */
    String taxpayerEmail();

    /**
     * 发票详情类型 itemCategory 商品类别=0,itemDetail 商品明细=1
     *
     * @return
     */
    Integer detailType();


    /**
     * 关联的订单
     *
     * @return
     */
    Order order();
}
