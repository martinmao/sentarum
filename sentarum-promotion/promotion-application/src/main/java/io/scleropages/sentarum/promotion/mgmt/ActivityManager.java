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
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
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
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

/**
 * 促销活动管理器.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("10")
public class ActivityManager implements GenericManager<ActivityModel, Long, ActivityEntityMapper> {


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
     *
     */
    private AdditionalAttributesInitializer additionalAttributesInitializer;

    /**
     * 创建一个活动.
     *
     * @param model
     * @return
     */
    @Validated({ActivityModel.Create.class})
    @Transactional
    @BizError("10")
    public Long createActivity(ActivityModel model) {
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
    public Long createActivityClassifiedGoodsSource(ActivityClassifiedGoodsSourceModel goodsSource, Long activityId) {
        ActivityClassifiedGoodsSourceEntity entity = classifiedGoodsSourceEntityMapper.mapForSave(goodsSource);
        entity.setBizId(getRequiredActivityEntity(activityId).getId());
        classifiedGoodsSourceRepository.getTop1ByBizTypeAndBizId(ActivityGoodsSource.BIZ_TYPE_OF_ACTIVITY, activityId).ifPresent(activityClassifiedGoodsSourceEntity -> {
            Assert.isTrue(Objects.equals(activityClassifiedGoodsSourceEntity.getGoodsSourceType(), goodsSource.getGoodsSourceType()), () -> "not allowed difference goods source type in same activity. expected: " + activityClassifiedGoodsSourceEntity.getGoodsSourceType() + " actual:" + goodsSource.getGoodsSourceType());
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
    public Long createActivityDetailedGoodsSource(ActivityDetailedGoodsSourceModel goodsSource, Long activityId) {
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
    public Long createActivityGoods(ActivityGoodsModel model, Long detailedGoodsSourceId) {
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
    public Long createActivityGoodsSpecs(ActivityGoodsSpecsModel model, Long goodsId) {
        ActivityGoodsSpecsEntity activityGoodsSpecsEntity = activityGoodsSpecsEntityMapper.mapForSave(model);
        ActivityGoodsEntity activityGoodsEntity = activityGoodsRepository.get(goodsId).orElseThrow(() -> new IllegalArgumentException("no activity goods found: " + goodsId));
        activityGoodsSpecsEntity.setGoods(activityGoodsEntity);
        activityGoodsSpecsEntity.setActivity(activityGoodsEntity.getActivity());
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
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("50")
    public List<? extends Activity> findAllActivityByClassifiedGoodsSource(Integer status, Integer goodSourceType, Long goodSourceId, Long secondaryGoodSourceId) {
        List<ActivityEntity> activityEntities = activityRepository.findAllByClassifiedGoodsSource(classifiedGoodsSourceRepository, status, goodSourceType, goodSourceId, secondaryGoodSourceId);
        return (List<? extends Activity>) getModelMapper().mapForReads(activityEntities);
    }


    /**
     * 根据 {@link DetailedGoodsSource} 中的条件检索活动
     *
     * @param status       活动状态
     * @param goodsId      商品id
     * @param goodsSpecsId 商品规格id.
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("51")
    public List<? extends Activity> findAllActivityByDetailedGoodsSource(Integer status, Long goodsId, Long goodsSpecsId) {
        List<ActivityEntity> activityEntities = activityRepository.findAllByDetailedGoodsSource(detailedGoodsSourceRepository, activityGoodsRepository, activityGoodsSpecsRepository, status, goodsId, goodsSpecsId);
        return (List<? extends Activity>) getModelMapper().mapForReads(activityEntities);
    }

    /**
     * 获取活动商品来源
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("52")
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
    @BizError("53")
    public ActivityDetailedGoodsSource getActivityDetailedGoodsSource(Long id) {
        ActivityDetailedGoodsSourceEntity entity = detailedGoodsSourceRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no activity detailed goods source found: " + id));
        ActivityDetailedGoodsSourceModel model = detailedGoodsSourceEntityMapper.mapForRead(entity);
        return (ActivityDetailedGoodsSource) additionalAttributesInitializer.initializeAdditionalAttributes(model, entity, detailedGoodsSourceRepository, false);
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
}
