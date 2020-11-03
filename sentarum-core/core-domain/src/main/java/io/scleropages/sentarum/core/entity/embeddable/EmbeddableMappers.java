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
package io.scleropages.sentarum.core.entity.embeddable;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.BaseAddress;
import io.scleropages.sentarum.core.model.primitive.BaseAddresses;

import java.util.Currency;

/**
 * utility facade class for map domain primitives <--to--> embeddable
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class EmbeddableMappers {

    public static EmbeddableAmount toEmbeddable(Amount amount) {
        if (null == amount)
            return null;
        EmbeddableAmount target = new EmbeddableAmount();
        target.setAmount(amount.getAmount());
        if (null != amount.getCurrency())
            target.setCurrencyCode(amount.getCurrency().getCurrencyCode());
        return target;
    }

    public static Amount toPrimitive(EmbeddableAmount amount) {
        if (null == amount)
            return null;
        Amount target = new Amount();
        target.setAmount(amount.getAmount());
        if (null != amount.getCurrencyCode())
            target.setCurrency(Currency.getInstance(amount.getCurrencyCode()));

        return target;
    }

    public static EmbeddableBaseAddress toEmbeddable(BaseAddress baseAddress) {
        if (null == baseAddress)
            return null;
        EmbeddableBaseAddress address = new EmbeddableBaseAddress();
        address.setCode(baseAddress.code());
        address.setName(baseAddress.fullName());
        return address;
    }

    public BaseAddress toPrimitive(EmbeddableBaseAddress baseAddress) {
        if (null == baseAddress)
            return null;
        return BaseAddresses.getBaseAddress(baseAddress.getCode());
    }

}
