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
package io.scleropages.sentarum.trading.order.model.impl;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderRefund;
import io.scleropages.sentarum.trading.order.model.RefundLine;

import java.util.Date;

/**
 *
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class RefundLineModel implements RefundLine {
    @Override
    public Long id() {
        return null;
    }

    @Override
    public Amount refundFee() {
        return null;
    }

    @Override
    public Date time() {
        return null;
    }

    @Override
    public Integer state() {
        return null;
    }

    @Override
    public OrderRefund orderRefund() {
        return null;
    }

    @Override
    public Order order() {
        return null;
    }
}
