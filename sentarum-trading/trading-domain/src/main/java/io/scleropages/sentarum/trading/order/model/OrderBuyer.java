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

import io.scleropages.sentarum.core.model.primitive.Tel;

/**
 * 订单买家信息
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderBuyer {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 买家标识
     *
     * @return
     */
    Long buyerId();

    /**
     * 买家电话
     *
     * @return
     */
    Tel tel();


    /**
     * 买家名称
     *
     * @return
     */
    String name();

    /**
     * 买家外部统一用户标识（union id）
     *
     * @return
     */
    String outerUnionId();

    /**
     * 买家外部用户标识(open id)
     *
     * @return
     */
    String outerId();

    /**
     * 关联的订单
     *
     * @return
     */
    Order order();
}
