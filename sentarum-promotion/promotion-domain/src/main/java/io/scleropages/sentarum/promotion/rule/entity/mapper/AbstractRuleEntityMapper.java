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
package io.scleropages.sentarum.promotion.rule.entity.mapper;

import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityEntityMapper;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.entity.AbstractRuleEntity;
import io.scleropages.sentarum.promotion.rule.model.AbstractRule;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface AbstractRuleEntityMapper<T extends AbstractRuleEntity, M extends AbstractRule> extends ModelMapper<T, M> {

    default Activity toActivity(ActivityEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (Activity) ModelMapperRepository.getRequiredModelMapper(ActivityEntityMapper.class).mapForRead(entity);
    }

    default ActivityEntity toActivityEntity(Activity activity) {
        return null;
    }
}
