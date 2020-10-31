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
 * 订单交付（物流）信息
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface OrderDelivery {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 物流状态 0:待发货; 1:已发货; 2：部分发货
     *
     * @return
     */
    Integer state();

    /**
     * 发货方式: 0:手动发货（商家后台人工发货）；1:接口发货（与三方云仓对接自动发货）
     *
     * @return
     */
    Integer type();

    /**
     * 关联的订单.
     *
     * @return
     */
    Order order();
}
