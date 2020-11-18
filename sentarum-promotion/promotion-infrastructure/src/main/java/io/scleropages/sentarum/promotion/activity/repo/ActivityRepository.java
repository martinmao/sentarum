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
import io.scleropages.sentarum.jooq.tables.PromActGoods;
import io.scleropages.sentarum.jooq.tables.PromActivity;
import io.scleropages.sentarum.jooq.tables.records.PromActivityRecord;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity;
import io.scleropages.sentarum.promotion.goods.repo.AbstractGoodsSourceRepository.GoodsSourceJoin;
import io.scleropages.sentarum.promotion.goods.repo.GoodsRepository.GoodsJoin;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.JoinType;
import org.jooq.Record;
import org.jooq.SelectQuery;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static io.scleropages.sentarum.promotion.goods.repo.AbstractGoodsSourceRepository.GoodsSourceConditionsAssembler.applyGoodsSourceCondition;
import static io.scleropages.sentarum.promotion.goods.repo.GoodsRepository.GoodsConditionsAssembler.applyGoodsCondition;

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

        applyGoodsSourceCondition(baseQuery, new GoodsSourceJoin(
                repository.dslTable(),
                JoinType.JOIN,
                promActivity.ID,
                condition,
                ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY
        ));
        return fetchRecordsInternal(promActivity, baseQuery, record -> true);
    }

    @Cacheable(key = "#goodsId+'-'+#goodsSpecsId")
    default List<ActivityEntity> findAllByDetailedGoodsSource(ActivityDetailedGoodsSourceRepository goodsSourceRepository, ActivityGoodsRepository goodsRepository, ActivityGoodsSpecsRepository goodsSpecsRepository, Integer activityStatus, Long goodsId, Long goodsSpecsId) {

        Assert.notNull(goodsSourceRepository, "goodsSourceRepository must not be null.");
        Assert.notNull(goodsRepository, "goodsRepository repository must not be null.");
        Assert.notNull(goodsSpecsRepository, "goodsSpecsRepository must not be null.");

        Assert.notNull(activityStatus, "activityStatus must not be null.");
        Assert.notNull(goodsId, "goodsId must not be null.");
        Assert.notNull(goodsSpecsId, "goodsSpecsId must not be null.");

        PromActivity promActivity = dslTable();

        List<Field> queryFields = Lists.newArrayList(promActivity.fields());
        PromActGoods promActGoods = goodsRepository.dslTable();
        queryFields.add(promActGoods.ID);
        SelectQuery<Record> baseQuery = dslContext().select(queryFields).from(promActivity).where(promActivity.STATUS.eq(activityStatus)).getQuery();

//        applyGoodsSourceCondition(baseQuery, new GoodsSourceJoin(
//                goodsSourceRepository.dslTable(),
//                JoinType.JOIN,
//                promActivity.ID, null, ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY
//        ));

        applyGoodsCondition(baseQuery, new GoodsJoin(
                promActGoods,
                promActivity.ID,
                promActGoods.GOODS_SOURCE_ID,
                JoinType.JOIN, promActGoods.GOODS_ID.eq(goodsId)
        ));

        return fetchRecordsInternal(promActivity, baseQuery, record -> {
            List<? extends GoodsSpecsEntity> goodsSpecs = goodsRepository.findAllGoodsSpecsByGoodsId(goodsSpecsRepository, record.getValue(promActGoods.ID));
            if (CollectionUtils.isEmpty(goodsSpecs))//没有任何商品规格关联代表全部.
                return true;
            for (GoodsSpecsEntity goodsSpec : goodsSpecs) {
                if (Objects.equals(goodsSpec.getSpecsId(), goodsSpecsId)) {
                    return true;
                }
            }
            return false;
        });
    }

    default List<ActivityEntity> fetchRecordsInternal(PromActivity promActivity, SelectQuery<Record> baseQuery, Function<Record, Boolean> recordCallback) {
        Set<Long> uniqueIds = Sets.newHashSet();
        List<ActivityEntity> entities = Lists.newArrayList();//客户端去重
        baseQuery.fetch().forEach(record -> {
            if (uniqueIds.add(record.getValue(promActivity.ID)) && recordCallback.apply(record)) {
                ActivityEntity entity = new ActivityEntity();
                dslRecordInto(record, entity);
                entities.add(entity);
            }
        });
        return entities;
    }
}
