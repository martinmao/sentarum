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
import io.scleropages.sentarum.trading.order.model.Promotion;

/**
 *
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PromotionModel implements Promotion {


    @Override
    public Long id() {
        return null;
    }

    @Override
    public Long activityTypeId() {
        return null;
    }

    @Override
    public String activityTypeName() {
        return null;
    }

    @Override
    public Long activityId() {
        return null;
    }

    @Override
    public String activityName() {
        return null;
    }

    @Override
    public Long couponId() {
        return null;
    }

    @Override
    public Amount discountFee() {
        return null;
    }
}
