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
package io.scleropages.sentarum.trading.mgmt;

import io.scleropages.sentarum.trading.order.model.impl.OrderModel;
import io.scleropages.sentarum.trading.order.repo.OrderPayRepository;
import io.scleropages.sentarum.trading.order.repo.OrderRepository;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 订单管理器
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("10")
public class OrderManager {

    private OrderRepository orderRepository;
    private OrderPayRepository orderPayRepository;


    public void createOrder(OrderModel orderModel){

    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setOrderPayRepository(OrderPayRepository orderPayRepository) {
        this.orderPayRepository = orderPayRepository;
    }
}
