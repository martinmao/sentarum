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
package io.scleropages.sentarum.item.category.entity.mapper;

import io.scleropages.sentarum.item.category.entity.MarketingCategoryEntity;
import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.category.entity.StandardCategoryLinkEntity;
import io.scleropages.sentarum.item.category.model.MarketingCategory;
import io.scleropages.sentarum.item.category.model.StandardCategory;
import io.scleropages.sentarum.item.category.model.StandardCategoryLink;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryLinkModel;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;
import org.scleropages.crud.dao.orm.SearchFilter;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface StandardCategoryLinkEntityMapper extends ModelMapper<StandardCategoryLinkEntity, StandardCategoryLinkModel> {


    default MarketingCategory toMarketingCategory(MarketingCategoryEntity entity) {
        MarketingCategoryEntityMapper mapper = (MarketingCategoryEntityMapper) ModelMapperRepository.getRequiredModelMapper(MarketingCategoryEntityMapper.class);
        return mapper.mapForRead(entity);
    }

    default MarketingCategoryEntity toMarketingCategoryEntity(MarketingCategory model) {
        return null;
    }

    default StandardCategory toStandardCategory(StandardCategoryEntity entity) {
        StandardCategoryEntityMapper mapper = (StandardCategoryEntityMapper) ModelMapperRepository.getRequiredModelMapper(StandardCategoryEntityMapper.class);
        return mapper.mapForRead(entity);
    }

    default StandardCategoryEntity toStandardCategoryEntity(StandardCategory model) {
        return null;
    }


    default Integer toOrdinal(StandardCategoryLink.LinkType linkType) {
        return linkType.ordinal();
    }

    default StandardCategoryLink.LinkType toLinkType(Integer ordinal) {
        return StandardCategoryLink.LinkType.getByOrdinal(ordinal);
    }

    default Integer toOrdinal(StandardCategoryLink.LinkStatus linkStatus) {
        return linkStatus.ordinal();
    }

    default StandardCategoryLink.LinkStatus toLinkStatus(Integer ordinal) {
        return StandardCategoryLink.LinkStatus.getByOrdinal(ordinal);
    }

    default String toSearchFilterPayload(SearchFilter searchFilter) {
        if (null == searchFilter)
            return null;
        return JsonMapper2.toJson(searchFilter);
    }

    default SearchFilter toSearchFilter(String payload) {
        if (null == payload)
            return null;
        return JsonMapper2.fromJson(payload);
    }

}
