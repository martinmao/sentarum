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
package io.scleropages.sentarum.trading.order.entity;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.LinePromotionModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class LinePromotionEntity extends AbstractPromotionEntity {

    private OrderEntity order;
    private OrderLineEntity orderLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity getOrder() {
        return order;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_line_id", nullable = false)
    public OrderLineEntity getOrderLine() {
        return orderLine;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public void setOrderLine(OrderLineEntity orderLine) {
        this.orderLine = orderLine;
    }
}