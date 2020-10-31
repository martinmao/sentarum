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

import io.scleropages.sentarum.core.model.primitive.Address;
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Geo;
import io.scleropages.sentarum.core.model.primitive.Tel;
import io.scleropages.sentarum.trading.order.model.Order;
import io.scleropages.sentarum.trading.order.model.OrderConsignee;

import java.util.Date;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OrderConsigneeModel implements OrderConsignee {
    @Override
    public Long id() {
        return null;
    }

    @Override
    public Date startTime() {
        return null;
    }

    @Override
    public Date endTime() {
        return null;
    }

    @Override
    public Date deliveredTime() {
        return null;
    }

    @Override
    public Amount deliveryAmount() {
        return null;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Tel tel() {
        return null;
    }

    @Override
    public Address address() {
        return null;
    }

    @Override
    public String detailAddress() {
        return null;
    }

    @Override
    public String postalCode() {
        return null;
    }

    @Override
    public Geo geo() {
        return null;
    }

    @Override
    public Order order() {
        return null;
    }
}
