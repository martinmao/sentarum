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
package io.scleropages.sentarum.promotion.mgmt;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.activity.entity.ActivityClassifiedGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityDetailedGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityGoodsEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityGoodsSpecsEntity;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityClassifiedGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityDetailedGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityGoodsEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityGoodsSpecsEntityMapper;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoods;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSpecs;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityClassifiedGoodsSourceModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityDetailedGoodsSourceModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsSpecsModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel;
import io.scleropages.sentarum.promotion.activity.repo.ActivityClassifiedGoodsSourceRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityDetailedGoodsSourceRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityGoodsRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityGoodsSpecsRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityRepository;
import io.scleropages.sentarum.promotion.goods.model.ClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.goods.model.DetailedGoodsSource;
import io.scleropages.sentarum.promotion.goods.repo.AdditionalAttributesInitializer;
import io.scleropages.sentarum.promotion.goods.repo.DetailedGoodsSourceReaderInitializer;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.exception.BizError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * 促销活动管理器.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("20")
public class ActivityManager implements GenericManager<ActivityModel, Long, ActivityEntityMapper>, InitializingBean {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * activity base repositories.
     */
    private ActivityRepository activityRepository;

    /**
     * activity goods source repositories.
     */
    private ActivityClassifiedGoodsSourceRepository classifiedGoodsSourceRepository;
    private ActivityDetailedGoodsSourceRepository detailedGoodsSourceRepository;

    /**
     * activity goods repositories.
     */
    private ActivityGoodsRepository activityGoodsRepository;
    private ActivityGoodsSpecsRepository activityGoodsSpecsRepository;


    /**
     * activity goods source mappers.
     */
    private ActivityClassifiedGoodsSourceEntityMapper classifiedGoodsSourceEntityMapper;
    private ActivityDetailedGoodsSourceEntityMapper detailedGoodsSourceEntityMapper;

    /**
     * activity goods mappers.
     */
    private ActivityGoodsEntityMapper activityGoodsEntityMapper;
    private ActivityGoodsSpecsEntityMapper activityGoodsSpecsEntityMapper;

    /**
     * other components.
     */
    private AdditionalAttributesInitializer additionalAttributesInitializer;
    private DetailedGoodsSourceReaderInitializer detailedGoodsSourceReaderInitializer;


    /**
     * 创建一个活动.
     *
     * @param model
     * @return
     */
    @Validated({ActivityModel.Create.class})
    @Transactional
    @BizError("10")
    public Long createActivity(@Valid ActivityModel model) {
        ActivityEntity activityEntity = getModelMapper().mapForSave(model);
        activityRepository.save(activityEntity);
        return activityEntity.getId();
    }

    /**
     * 创建商品来源并与活动关联.
     *
     * @param goodsSource
     * @param activityId
     * @return
     */
    @Validated({ActivityClassifiedGoodsSourceModel.Create.class})
    @Transactional
    @BizError("15")
    public Long createActivityClassifiedGoodsSource(@Valid ActivityClassifiedGoodsSourceModel goodsSource, Long activityId) {
        ActivityClassifiedGoodsSourceEntity entity = classifiedGoodsSourceEntityMapper.mapForSave(goodsSource);
        entity.setBizId(getRequiredActivityEntity(activityId).getId());
        classifiedGoodsSourceRepository.getTop1ByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId).ifPresent(activityClassifiedGoodsSourceEntity -> {
            Assert.isTrue(Objects.equals(activityClassifiedGoodsSourceEntity.getGoodsSourceType(), goodsSource.getGoodsSourceType()), () -> "not allowed difference goods source type in same activity. expected: " + activityClassifiedGoodsSourceEntity.getGoodsSourceType() + " actual:" + goodsSource.getGoodsSourceType());
        });
        detailedGoodsSourceRepository.getTop1ByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId).ifPresent(activityDetailedGoodsSourceEntity -> {
            throw new IllegalArgumentException("given activity already associated detailed goods source: " + activityId);
        });
        classifiedGoodsSourceRepository.save(entity);
        return entity.getId();
    }


    /**
     * 创建商品来源并与活动关联.
     *
     * @param goodsSource
     * @param activityId
     * @return
     */
    @Validated({ActivityDetailedGoodsSourceModel.Create.class})
    @Transactional
    @BizError("20")
    public Long createActivityDetailedGoodsSource(@Valid ActivityDetailedGoodsSourceModel goodsSource, Long activityId) {
        ActivityDetailedGoodsSourceEntity entity = detailedGoodsSourceEntityMapper.mapForSave(goodsSource);
        classifiedGoodsSourceRepository.getTop1ByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId).ifPresent(activityClassifiedGoodsSourceEntity -> {
            throw new IllegalArgumentException("given activity already configured as classified goods source: " + activityId);
        });
        detailedGoodsSourceRepository.getTop1ByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId).ifPresent(activityDetailedGoodsSourceEntity -> {
            throw new IllegalArgumentException("given activity already associated detailed goods source: " + activityId);
        });
        entity.setBizId(getRequiredActivityEntity(activityId).getId());
        detailedGoodsSourceRepository.save(entity);
        return entity.getId();
    }

    /**
     * 创建一个活动商品并关联到指定的本地商品来源.
     *
     * @param model
     * @param detailedGoodsSourceId
     * @return
     */
    @Validated({ActivityGoodsModel.Create.class})
    @Transactional
    @BizError("25")
    public Long createActivityGoods(@Valid ActivityGoodsModel model, Long detailedGoodsSourceId) {
        ActivityGoodsEntity activityGoodsEntity = activityGoodsEntityMapper.mapForSave(model);
        ActivityDetailedGoodsSourceEntity entity = detailedGoodsSourceRepository.get(detailedGoodsSourceId).orElseThrow(() -> new IllegalArgumentException("no detailed goods source found: " + detailedGoodsSourceId));
        activityGoodsEntity.setGoodsSource(entity);
        activityGoodsEntity.setActivity(getRequiredActivityEntity(entity.getBizId()));
        activityGoodsRepository.save(activityGoodsEntity);
        return activityGoodsEntity.getId();
    }

    /**
     * 创建一个活动商品规格并关联到活动商品.
     *
     * @param model
     * @param goodsId
     * @return
     */
    @Validated({ActivityGoodsSpecsModel.Create.class})
    @Transactional
    @BizError("30")
    public Long createActivityGoodsSpecs(@Valid ActivityGoodsSpecsModel model, Long goodsId) {
        ActivityGoodsSpecsEntity activityGoodsSpecsEntity = activityGoodsSpecsEntityMapper.mapForSave(model);
        ActivityGoodsEntity activityGoodsEntity = activityGoodsRepository.getById(goodsId).orElseThrow(() -> new IllegalArgumentException("no activity goods found: " + goodsId));
        activityGoodsSpecsEntity.setGoods(activityGoodsEntity);
        activityGoodsSpecsEntity.setActivity(activityGoodsEntity.getActivity());
        activityGoodsSpecsEntity.setGoodsSource(activityGoodsEntity.getGoodsSource());
        activityGoodsSpecsRepository.save(activityGoodsSpecsEntity);
        return activityGoodsSpecsEntity.getId();
    }


    /**
     * 根据 {@link ClassifiedGoodsSource} 中的条件检索活动.
     *
     * @param status                活动状态
     * @param goodSourceType        商品来源类型
     * @param goodSourceId          商品来源id，optional
     * @param secondaryGoodSourceId 二级商品来源id,optional
     * @param fetchGoodsSource      true if want to fetch {@link Activity#goodsSource()}.
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("50")
    public List<? extends Activity> findAllActivityByClassifiedGoodsSource(Integer status, Integer goodSourceType, Long goodSourceId, Long secondaryGoodSourceId, boolean fetchGoodsSource) {
        List<Long> ids = activityRepository.findAllActivityIdByClassifiedGoodsSource(classifiedGoodsSourceRepository, status, goodSourceType, goodSourceId, secondaryGoodSourceId);
        return getActivities(ids, fetchGoodsSource);
    }


    /**
     * 根据 {@link DetailedGoodsSource} 中的条件检索活动
     *
     * @param status           活动状态
     * @param goodsId          商品id
     * @param goodsSpecsId     商品规格id.
     * @param fetchGoodsSource true if want to fetch {@link Activity#goodsSource()}.
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("51")
    public List<? extends Activity> findAllActivityByDetailedGoodsSource(Integer status, Long goodsId, Long goodsSpecsId, boolean fetchGoodsSource) {
        List<Long> ids = activityRepository.findAllActivityIdByDetailedGoodsSource(detailedGoodsSourceRepository, activityGoodsRepository, activityGoodsSpecsRepository, status, goodsId, goodsSpecsId);
        return getActivities(ids, fetchGoodsSource);
    }


    /**
     * 获取活动详情
     *
     * @param id               id of activity
     * @param fetchGoodsSource true if want to fetch goods source.
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("52")
    public Activity getActivity(Long id, boolean fetchGoodsSource) {
        ActivityEntity activityEntity = activityRepository.readByRecord(activityRepository.readById(id), fetchGoodsSource ? classifiedGoodsSourceRepository : null, fetchGoodsSource ? detailedGoodsSourceRepository : null).orElseThrow(() -> new IllegalArgumentException("no activity found: " + id));
        return getModelMapper().mapForRead(activityEntity);
    }

    /**
     * 获取活动商品来源
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("53")
    public ActivityClassifiedGoodsSource getActivityClassifiedGoodsSource(Long id) {
        ActivityClassifiedGoodsSourceEntity entity = classifiedGoodsSourceRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no activity classified goods source found: " + id));
        ActivityClassifiedGoodsSourceModel model = classifiedGoodsSourceEntityMapper.mapForRead(entity);
        return (ActivityClassifiedGoodsSource) additionalAttributesInitializer.initializeAdditionalAttributes(model, entity, classifiedGoodsSourceRepository, false);
    }

    /**
     * 获取商品来源
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("54")
    public ActivityDetailedGoodsSource getActivityDetailedGoodsSource(Long id) {
        ActivityDetailedGoodsSourceEntity entity = detailedGoodsSourceRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no activity detailed goods source found: " + id));
        ActivityDetailedGoodsSourceModel model = detailedGoodsSourceEntityMapper.mapForRead(entity);
        return (ActivityDetailedGoodsSource) additionalAttributesInitializer.initializeAdditionalAttributes(model, entity, detailedGoodsSourceRepository, false);
    }

    /**
     * 获取商品
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("55")
    public ActivityGoods getActivityGoods(Long id) {
        ActivityGoodsEntity entity = activityGoodsRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no activity goods found: " + id));
        ActivityGoodsModel model = activityGoodsEntityMapper.mapForRead(entity);
        return (ActivityGoods) additionalAttributesInitializer.initializeAdditionalAttributes(model, entity, activityGoodsRepository, false);
    }

    /**
     * 获取商品规格.
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("56")
    public ActivityGoodsSpecs getActivityGoodsSpecs(Long id) {
        ActivityGoodsSpecsEntity entity = activityGoodsSpecsRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no activity goods specs found: " + id));
        ActivityGoodsSpecsModel model = activityGoodsSpecsEntityMapper.mapForRead(entity);
        return (ActivityGoodsSpecs) additionalAttributesInitializer.initializeAdditionalAttributes(model, entity, activityGoodsSpecsRepository, false);
    }


    /**
     * 获取活动商品来源.
     *
     * @param activityId
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("57")
    public List<ActivityGoodsSource> findAllActivityGoodsSource(Long activityId) {

        List<ActivityGoodsSource> goodsSources = Lists.newArrayList();
        List<ActivityClassifiedGoodsSourceEntity> classifiedGoodsSourceEntities = classifiedGoodsSourceRepository.findByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId);
        for (ActivityClassifiedGoodsSourceEntity entity : classifiedGoodsSourceEntities) {
            ActivityClassifiedGoodsSourceModel model = classifiedGoodsSourceEntityMapper.mapForRead(entity);
            goodsSources.add((ActivityGoodsSource) additionalAttributesInitializer.initializeAdditionalAttributes(model, entity, classifiedGoodsSourceRepository, false));
        }
        if (goodsSources.isEmpty()) {
            List<ActivityDetailedGoodsSourceEntity> detailedGoodsSourceEntities = detailedGoodsSourceRepository.findByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId);
            if (!detailedGoodsSourceEntities.isEmpty()) {
                if (detailedGoodsSourceEntities.size() > 1) {
                    throw new IllegalStateException("internal error. more than one detailed goods source found with activity[" + activityId + "].");
                }
                ActivityDetailedGoodsSourceEntity entity = detailedGoodsSourceEntities.get(0);
                ActivityDetailedGoodsSourceModel model = detailedGoodsSourceEntityMapper.mapForRead(entity);
                goodsSources.add((ActivityGoodsSource) additionalAttributesInitializer.initializeAdditionalAttributes(model, entity, detailedGoodsSourceRepository, false, detailedGoodsSourceReaderInitializer));
            }
        }
        return goodsSources;
    }


    /**
     * get activities by ids
     *
     * @param ids              id list for activity
     * @param fetchGoodsSource true if want to fetch goods source.
     * @return
     */
    public List<? extends Activity> getActivities(List<Long> ids, boolean fetchGoodsSource) {
        List<Activity> activities = Lists.newArrayList();
        ids.forEach(id -> activities.add(getActivity(id, fetchGoodsSource)));
        //activities.sort((o1, o2) -> ComparatorUtils.naturalComparator().compare(o1.order(), o2.order()));
        return activities;
    }


    private ActivityEntity getRequiredActivityEntity(Long activityId) {
        return activityRepository.get(activityId).orElseThrow(() -> new IllegalArgumentException("no activity found: " + activityId));
    }


    @Autowired
    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Autowired
    public void setClassifiedGoodsSourceRepository(ActivityClassifiedGoodsSourceRepository classifiedGoodsSourceRepository) {
        this.classifiedGoodsSourceRepository = classifiedGoodsSourceRepository;
    }

    @Autowired
    public void setDetailedGoodsSourceRepository(ActivityDetailedGoodsSourceRepository detailedGoodsSourceRepository) {
        this.detailedGoodsSourceRepository = detailedGoodsSourceRepository;
    }

    @Autowired
    public void setActivityGoodsRepository(ActivityGoodsRepository activityGoodsRepository) {
        this.activityGoodsRepository = activityGoodsRepository;
    }

    @Autowired
    public void setActivityGoodsSpecsRepository(ActivityGoodsSpecsRepository activityGoodsSpecsRepository) {
        this.activityGoodsSpecsRepository = activityGoodsSpecsRepository;
    }

    @Autowired
    public void setClassifiedGoodsSourceEntityMapper(ActivityClassifiedGoodsSourceEntityMapper classifiedGoodsSourceEntityMapper) {
        this.classifiedGoodsSourceEntityMapper = classifiedGoodsSourceEntityMapper;
    }

    @Autowired
    public void setDetailedGoodsSourceEntityMapper(ActivityDetailedGoodsSourceEntityMapper detailedGoodsSourceEntityMapper) {
        this.detailedGoodsSourceEntityMapper = detailedGoodsSourceEntityMapper;
    }

    @Autowired
    public void setActivityGoodsEntityMapper(ActivityGoodsEntityMapper activityGoodsEntityMapper) {
        this.activityGoodsEntityMapper = activityGoodsEntityMapper;
    }

    @Autowired
    public void setActivityGoodsSpecsEntityMapper(ActivityGoodsSpecsEntityMapper activityGoodsSpecsEntityMapper) {
        this.activityGoodsSpecsEntityMapper = activityGoodsSpecsEntityMapper;
    }

    @Autowired
    public void setAdditionalAttributesInitializer(AdditionalAttributesInitializer additionalAttributesInitializer) {
        this.additionalAttributesInitializer = additionalAttributesInitializer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(activityGoodsRepository, "activityGoodsRepository must not be null.");
        Assert.notNull(activityGoodsSpecsRepository, "activityGoodsSpecsRepository must not be null.");
        Assert.notNull(activityGoodsEntityMapper, "activityGoodsEntityMapper must not be null.");
        Assert.notNull(activityGoodsSpecsEntityMapper, "activityGoodsSpecsEntityMapper must not be null.");
        Assert.notNull(additionalAttributesInitializer, "additionalAttributesInitializer must not be null.");

        detailedGoodsSourceReaderInitializer = new DetailedGoodsSourceReaderInitializer(activityGoodsRepository, activityGoodsSpecsRepository, activityGoodsEntityMapper, activityGoodsSpecsEntityMapper, additionalAttributesInitializer);
    }
}
