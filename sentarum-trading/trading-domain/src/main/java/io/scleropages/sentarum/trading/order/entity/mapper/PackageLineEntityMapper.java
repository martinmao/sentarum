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

import io.scleropages.sentarum.trading.order.entity.DeliveryPackageEntity;
import io.scleropages.sentarum.trading.order.entity.OrderDeliveryEntity;
import io.scleropages.sentarum.trading.order.entity.OrderLineEntity;
import io.scleropages.sentarum.trading.order.entity.PackageLineEntity;
import io.scleropages.sentarum.trading.order.model.DeliveryPackage;
import io.scleropages.sentarum.trading.order.model.OrderDelivery;
import io.scleropages.sentarum.trading.order.model.OrderLine;
import io.scleropages.sentarum.trading.order.model.impl.PackageLineModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface PackageLineEntityMapper extends AbstractOrderEntityMapper<PackageLineEntity, PackageLineModel> {

    default OrderLine toOrderLine(OrderLineEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (OrderLine) ModelMapperRepository.getRequiredModelMapper(OrderLineEntityMapper.class).mapForRead(entity);
    }

    default OrderLineEntity toOrderLineEntity(OrderLine orderLine) {
        return null;
    }


    default OrderDelivery toOrderDelivery(OrderDeliveryEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (OrderDelivery) ModelMapperRepository.getRequiredModelMapper(OrderDeliveryEntityMapper.class).mapForRead(entity);
    }

    default OrderDeliveryEntity toOrderDeliveryEntity(OrderDelivery orderDelivery) {
        return null;
    }


    default DeliveryPackage toDeliveryPackage(DeliveryPackageEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (DeliveryPackage) ModelMapperRepository.getRequiredModelMapper(DeliveryPackageEntityMapper.class).mapForRead(entity);
    }

    default DeliveryPackageEntity toDeliveryPackageEntity(DeliveryPackage deliveryPackage) {
        return null;
    }

}
