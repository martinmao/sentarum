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
package io.scleropages.sentarum.item.entity.mapper;

import io.scleropages.sentarum.item.entity.CombineSkuEntity;
import io.scleropages.sentarum.item.entity.CombineSkuEntryEntity;
import io.scleropages.sentarum.item.model.CombineSkuEntry;
import io.scleropages.sentarum.item.model.impl.CombineSkuModel;
import org.mapstruct.Mapper;
import org.scleropages.crud.ModelMapper;
import org.scleropages.crud.ModelMapperRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface CombineSkuEntityMapper extends AbstractSkuEntityMapper<CombineSkuEntity, CombineSkuModel> {


    default CombineSkuEntryEntity toCombineSkuEntryEntity(CombineSkuEntry entry) {
        return null;
    }

    default CombineSkuEntry toCombineSkuEntry(CombineSkuEntryEntity entity) {
        if (!isEntityInitialized(entity)) {
            return null;
        }
        return (CombineSkuEntry) ModelMapperRepository.getRequiredModelMapper(CombineSkuEntryEntityMapper.class).mapForRead(entity);
    }

    default List<CombineSkuEntry> combineSkuEntryEntityListToCombineSkuEntryList(List<CombineSkuEntryEntity> list) {
        if (!isEntityInitialized(list))
            return null;
        List<CombineSkuEntry> list1 = new ArrayList<CombineSkuEntry>(list.size());
        for (CombineSkuEntryEntity combineSkuEntryEntity : list) {
            list1.add(toCombineSkuEntry(combineSkuEntryEntity));
        }

        return list1;
    }

}
