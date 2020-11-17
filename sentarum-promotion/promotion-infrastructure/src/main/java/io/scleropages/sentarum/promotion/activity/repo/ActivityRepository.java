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
package io.scleropages.sentarum.promotion.activity.repo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.scleropages.sentarum.jooq.tables.PromActivity;
import io.scleropages.sentarum.jooq.tables.records.PromActivityRecord;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.goods.repo.AbstractGoodsSourceRepository;
import org.jooq.Condition;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;

import static io.scleropages.sentarum.promotion.goods.repo.AbstractGoodsSourceRepository.GoodsSourceConditionsAssembler.applyConditions;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ActivityRepository extends GenericRepository<ActivityEntity, Long>, JooqRepository<PromActivity, PromActivityRecord, ActivityEntity> {


    @Cacheable(key = "#activityStatus+'-'+#goodsSourceType+'-'+#goodsSourceId+'-'+#secondaryGoodsSourceId")
    default List<ActivityEntity> findAllByClassifiedGoodsSource(ActivityClassifiedGoodsSourceRepository repository, Integer activityStatus, Integer goodsSourceType, Long goodsSourceId, Long secondaryGoodsSourceId) {

        Assert.notNull(repository, "repository must not be null.");
        Assert.notNull(activityStatus, "activityStatus must not be null.");

        PromActivity promActivity = dslTable();

        SelectQuery<Record> baseQuery = dslContext().select(promActivity.fields()).from(promActivity).where(promActivity.STATUS.eq(activityStatus)).getQuery();

        Condition condition = repository.conditionByGoodsSourceTypeAndGoodsSourceIdAndSecondaryGoodsSourceId(goodsSourceType, goodsSourceId, secondaryGoodsSourceId);

        applyConditions(baseQuery, new AbstractGoodsSourceRepository.GoodsSourceJoin(
                repository.dslTable(),
                JoinType.JOIN,
                promActivity.ID,
                condition,
                ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY
        ));
        Set<Long> uniqueIds = Sets.newHashSet();
        List<ActivityEntity> entities = Lists.newArrayList();//客户端去重
        baseQuery.fetch().forEach(record -> {
            if (uniqueIds.add(record.getValue(promActivity.ID))) {
                ActivityEntity entity = new ActivityEntity();
                dslRecordInto(record, entity);
                entities.add(entity);
            }
        });
        return entities;
    }
}
