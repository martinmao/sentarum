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
import io.scleropages.sentarum.core.entity.mapper.MapAttributesMapper;
import io.scleropages.sentarum.jooq.tables.PromActGoods;
import io.scleropages.sentarum.jooq.tables.PromActivity;
import io.scleropages.sentarum.jooq.tables.records.PromActivityRecord;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity;
import io.scleropages.sentarum.promotion.goods.model.GoodsSource;
import io.scleropages.sentarum.promotion.goods.repo.AbstractGoodsSourceRepository.GoodsSourceJoin;
import io.scleropages.sentarum.promotion.goods.repo.AdditionalAttributesInitializer;
import io.scleropages.sentarum.promotion.goods.repo.AdditionalAttributesInitializer.AdditionalAttributesSavingCallback;
import io.scleropages.sentarum.promotion.goods.repo.GoodsRepository.GoodsJoin;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SelectQuery;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity.COLUMN_ATTRIBUTE_PAYLOAD;
import static io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity.COLUMN_ID;
import static io.scleropages.sentarum.promotion.goods.repo.AbstractGoodsSourceRepository.GoodsSourceConditionsAssembler.applyGoodsSourceCondition;
import static io.scleropages.sentarum.promotion.goods.repo.GoodsRepository.GoodsConditionsAssembler.applyGoodsCondition;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ActivityRepository extends GenericRepository<ActivityEntity, Long>, JooqRepository<PromActivity, PromActivityRecord, ActivityEntity>, AdditionalAttributesSavingCallback<Activity, ActivityEntity>, MapAttributesMapper {


    @Cacheable(key = "#activityStatus+'-'+#goodsSourceType+'-'+#goodsSourceId+'-'+#secondaryGoodsSourceId")
    default List<Long> findAllActivityIdByClassifiedGoodsSource(ActivityClassifiedGoodsSourceRepository repository, Integer activityStatus, Integer goodsSourceType, Long goodsSourceId, Long secondaryGoodsSourceId) {

        Assert.notNull(repository, "repository must not be null.");
        Assert.notNull(activityStatus, "activityStatus must not be null.");

        PromActivity promActivity = dslTable();

        SelectQuery<Record1<Long>> baseQuery = dslContext().select(promActivity.ID).getQuery();

        Condition condition = repository.conditionByGoodsSourceTypeAndGoodsSourceIdAndSecondaryGoodsSourceId(goodsSourceType, goodsSourceId, secondaryGoodsSourceId);

        applyGoodsSourceCondition(baseQuery, new GoodsSourceJoin(
                repository.dslTable(),
                null,
                promActivity.ID,
                condition,
                ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY
        ));
        baseQuery.addFrom(promActivity);
        baseQuery.addConditions(promActivity.STATUS.eq(activityStatus));
        return fetchRecordsInternal(promActivity, () -> baseQuery, record -> true);
    }



    @Cacheable(key = "#goodsId+'-'+#goodsSpecsId")
    default List<Long> findAllActivityIdByDetailedGoodsSource(ActivityDetailedGoodsSourceRepository goodsSourceRepository, ActivityGoodsRepository goodsRepository, ActivityGoodsSpecsRepository goodsSpecsRepository, Integer activityStatus, Long goodsId, Long goodsSpecsId) {

        Assert.notNull(goodsSourceRepository, "goodsSourceRepository must not be null.");
        Assert.notNull(goodsRepository, "goodsRepository repository must not be null.");
        Assert.notNull(goodsSpecsRepository, "goodsSpecsRepository must not be null.");

        Assert.notNull(activityStatus, "activityStatus must not be null.");
        Assert.notNull(goodsId, "goodsId must not be null.");
        Assert.notNull(goodsSpecsId, "goodsSpecsId must not be null.");

        PromActivity promActivity = dslTable();

        List<Field> queryFields = Lists.newArrayList(promActivity.ID);
        PromActGoods promActGoods = goodsRepository.dslTable();
        queryFields.add(promActGoods.ID);
        SelectQuery<Record> baseQuery = dslContext().select(queryFields).getQuery();

//        applyGoodsSourceCondition(baseQuery, new GoodsSourceJoin(
//                goodsSourceRepository.dslTable(),
//                JoinType.JOIN,
//                promActivity.ID, null, ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY
//        ));

        applyGoodsCondition(baseQuery, new GoodsJoin(
                promActGoods,
                promActivity.ID,
                promActGoods.ACTIVITY_ID,
                null, promActGoods.GOODS_ID.eq(goodsId)
        ));

        baseQuery.addFrom(promActivity);
        baseQuery.addConditions(promActivity.STATUS.eq(activityStatus));

        return fetchRecordsInternal(promActivity, () -> baseQuery, record -> {
            List<? extends GoodsSpecsEntity> goodsSpecs = goodsRepository.findAllCacheablesGoodsSpecsByGoodsId(goodsSpecsRepository, record.getValue(promActGoods.ID));
            if (CollectionUtils.isEmpty(goodsSpecs))//没有任何商品规格关联即对所有规格匹配.
                return true;
            for (GoodsSpecsEntity goodsSpec : goodsSpecs) {
                if (Objects.equals(goodsSpec.getSpecsId(), goodsSpecsId)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Cacheable
    default Optional<PromActivityRecord> readById(Long activityId) {
        PromActivity promActivity = dslTable();
        return dslContext().selectFrom(promActivity).where(promActivity.ID.eq(activityId)).fetchOptional();
    }

    /**
     * read activity by given record
     *
     * @param optionalRecord                  required record use {@link #readById(Long)} to fetch.
     * @param classifiedGoodsSourceRepository (optional) required if want to fetch classified goods source
     * @param detailedGoodsSourceRepository   (optional) required if want to fetch detailed goods source
     * @return
     */
    default Optional<ActivityEntity> readByRecord(Optional<PromActivityRecord> optionalRecord, ActivityClassifiedGoodsSourceRepository classifiedGoodsSourceRepository, ActivityDetailedGoodsSourceRepository detailedGoodsSourceRepository) {
        if (!optionalRecord.isPresent())
            return Optional.empty();
        PromActivityRecord record = optionalRecord.get();
        Long activityId = record.getId();
        ActivityEntity entity = new ActivityEntity();
        dslRecordInto(record, entity);
        if (null != classifiedGoodsSourceRepository) {
            classifiedGoodsSourceRepository.consumeEntitiesByRecord(classifiedGoodsSourceRepository.readByBizTypeAndBizId(
                    ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY
                    , activityId)
                    , e -> entity.getGoodsSource().add(e));
        }
        if (null != detailedGoodsSourceRepository) {
            detailedGoodsSourceRepository.consumeEntitiesByRecord(detailedGoodsSourceRepository.readByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId), e -> entity.getGoodsSource().add(e));
        }
        return Optional.of(entity);
    }


    default List<Long> fetchRecordsInternal(PromActivity promActivity, Supplier<SelectQuery<? extends Record>> baseQuerySupplier, Function<Record, Boolean> recordCallback) {
        Set<Long> uniqueIds = Sets.newHashSet();//客户端去重
        SelectQuery<? extends Record> baseQuery = baseQuerySupplier.get();
        baseQuery.fetch().forEach(record -> {
            Long activityId = record.getValue(promActivity.ID);
            if (!uniqueIds.contains(activityId) && recordCallback.apply(record)) {
                uniqueIds.add(activityId);
            }
        });
        return Lists.newArrayList(uniqueIds);
    }


    @Override
    default void save(Activity activity, Map<String, Object> attributes){
        Assert.notNull(activity, "activity must not be null.");
        Long id = activity.id();
        Assert.notNull(id, "activity id must not be null.");
        PromActivity t = dslTable();
        dslContext().update(t)
                .set(t.ATTRIBUTE_PAYLOAD, attributesToPayload(attributes))
                .where(t.ID.eq(id)).execute();
    }

    @Override
    default Map<String, Object> additionalAttributesMap(ActivityEntity entity){
        Assert.notNull(entity, "activity entity must not be null.");
        return payloadToAttributes(entity.getAttributePayLoad());
    }
}
