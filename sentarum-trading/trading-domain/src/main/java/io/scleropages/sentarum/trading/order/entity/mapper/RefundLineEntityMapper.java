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

import io.scleropages.sentarum.trading.order.entity.RefundLineEntity;
import io.scleropages.sentarum.trading.order.model.RefundLine;
import io.scleropages.sentarum.trading.order.model.impl.RefundLineModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface RefundLineEntityMapper extends AbstractOrderEntityMapper<RefundLineEntity, RefundLineModel> {

    default RefundLine toreRefundLine(RefundLineEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (RefundLine) ModelMapperRepository.getRequiredModelMapper(RefundLineEntityMapper.class).mapForRead(entity);
    }

    default RefundLineEntity toRefundLineEntity(RefundLine refundLine) {
        return null;
    }

}
