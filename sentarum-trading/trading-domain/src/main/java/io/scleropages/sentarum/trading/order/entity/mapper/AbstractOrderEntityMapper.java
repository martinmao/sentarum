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
package io.scleropages.sentarum.trading.order.entity.mapper;

import io.scleropages.sentarum.core.entity.embeddable.EmbeddableAddress;
import io.scleropages.sentarum.core.entity.embeddable.EmbeddableAmount;
import io.scleropages.sentarum.core.entity.embeddable.EmbeddableGeo;
import io.scleropages.sentarum.core.entity.embeddable.EmbeddableMappers;
import io.scleropages.sentarum.core.model.primitive.Address;
import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Geo;
import io.scleropages.sentarum.core.model.primitive.Tel;
import io.scleropages.sentarum.trading.order.entity.OrderEntity;
import io.scleropages.sentarum.trading.order.model.Order;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;
import org.springframework.util.StringUtils;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface AbstractOrderEntityMapper<T, M> extends ModelMapper<T, M> {


    default Address toAddress(EmbeddableAddress address) {
        return EmbeddableMappers.toPrimitive(address);
    }

    default EmbeddableAddress toEmbeddableAddress(Address address) {
        return EmbeddableMappers.toEmbeddable(address);
    }

    default Geo toGeo(EmbeddableGeo geo) {
        return EmbeddableMappers.toPrimitive(geo);
    }

    default EmbeddableGeo toEmbeddableGeo(Geo geo) {
        return EmbeddableMappers.toEmbeddable(geo);
    }

    default Amount toAmount(EmbeddableAmount embeddableAmount) {
        return EmbeddableMappers.toPrimitive(embeddableAmount);
    }

    default EmbeddableAmount toEmbeddableAmount(Amount amount) {
        return EmbeddableMappers.toEmbeddable(amount);
    }


    default Tel toTel(String tel) {
        if (!StringUtils.hasText(tel))
            return null;
        return new Tel(tel);
    }

    default String toTelText(Tel tel) {
        if (null == tel)
            return null;
        return tel.getTel();
    }


    default Order toOrder(OrderEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (Order) ModelMapperRepository.getRequiredModelMapper(OrderEntityMapper.class).mapForRead(entity);
    }

    default OrderEntity toOrderEntity(Order order) {
        return null;
    }
}
