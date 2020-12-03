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
package io.scleropages.sentarum.promotion.activity.entity.mapper;

import io.scleropages.sentarum.promotion.activity.entity.ActivityClassifiedGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityDetailedGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel;
import io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface ActivityEntityMapper extends ModelMapper<ActivityEntity, ActivityModel> {

    default AbstractGoodsSourceEntity toEntity(ActivityGoodsSource activityGoodsSource) {
        return null;
    }

    default ActivityGoodsSource toModel(AbstractGoodsSourceEntity entity) {
        if (!isEntityInitialized(entity))
            return null;

        if (entity instanceof ActivityDetailedGoodsSourceEntity) {
            return (ActivityGoodsSource) ModelMapperRepository.getRequiredModelMapper(ActivityDetailedGoodsSourceEntityMapper.class).mapForRead(entity);
        } else if (entity instanceof ActivityClassifiedGoodsSourceEntity) {
            return (ActivityGoodsSource) ModelMapperRepository.getRequiredModelMapper(ActivityClassifiedGoodsSourceEntityMapper.class).mapForRead(entity);

        }
        throw new IllegalStateException("unsupported entity type: " + entity.getClass().getName());
    }

}
