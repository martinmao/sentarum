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

import io.scleropages.sentarum.promotion.activity.entity.ActivityBrandGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityCategoryGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityGoodsEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivityNativeGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.ActivitySellerGoodsSourceEntity;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityBrandGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityCategoryGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityGoodsEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivityNativeGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.activity.entity.mapper.ActivitySellerGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityBrandGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityCategoryGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivityNativeGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.impl.ActivitySellerGoodsSource;
import io.scleropages.sentarum.promotion.activity.repo.ActivityBrandGoodsSourceRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityCategoryGoodsSourceRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityGoodsRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityGoodsSpecsRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityNativeGoodsSourceRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivityRepository;
import io.scleropages.sentarum.promotion.activity.repo.ActivitySellerGoodsSourceRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

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
    private ActivityBrandGoodsSourceRepository activityBrandGoodsSourceRepository;
    private ActivityCategoryGoodsSourceRepository activityCategoryGoodsSourceRepository;
    private ActivitySellerGoodsSourceRepository activitySellerGoodsSourceRepository;
    private ActivityNativeGoodsSourceRepository activityNativeGoodsSourceRepository;

    /**
     * activity goods repositories.
     */
    private ActivityGoodsRepository activityGoodsRepository;
    private ActivityGoodsSpecsRepository activityGoodsSpecsRepository;


    /**
     * activity goods source mappers.
     */
    private ActivityBrandGoodsSourceEntityMapper activityBrandGoodsSourceEntityMapper;
    private ActivityCategoryGoodsSourceEntityMapper activityCategoryGoodsSourceEntityMapper;
    private ActivitySellerGoodsSourceEntityMapper activitySellerGoodsSourceEntityMapper;
    private ActivityNativeGoodsSourceEntityMapper activityNativeGoodsSourceEntityMapper;

    /**
     * activity goods mappers.
     */
    private ActivityGoodsEntityMapper activityGoodsEntityMapper;


    @Transactional
    public void createActivityBrandGoodsSource(ActivityBrandGoodsSource activityBrandGoodsSource, Long activityId) {
        ActivityBrandGoodsSourceEntity activityBrandGoodsSourceEntity = activityBrandGoodsSourceEntityMapper.mapForSave(activityBrandGoodsSource);
        activityBrandGoodsSourceEntity.setActivity(getRequiredActivityEntity(activityId));
        activityBrandGoodsSourceRepository.save(activityBrandGoodsSourceEntity);
    }

    @Transactional
    public void createActivityCategoryGoodsSource(ActivityCategoryGoodsSource activityCategoryGoodsSource, Long activityId) {
        ActivityCategoryGoodsSourceEntity activityCategoryGoodsSourceEntity = activityCategoryGoodsSourceEntityMapper.mapForSave(activityCategoryGoodsSource);
        activityCategoryGoodsSourceEntity.setActivity(getRequiredActivityEntity(activityId));
        activityCategoryGoodsSourceRepository.save(activityCategoryGoodsSourceEntity);
    }

    @Transactional
    public void createActivitySellerGoodsSource(ActivitySellerGoodsSource activitySellerGoodsSource, Long activityId) {
        ActivitySellerGoodsSourceEntity activitySellerGoodsSourceEntity = activitySellerGoodsSourceEntityMapper.mapForSave(activitySellerGoodsSource);
        activitySellerGoodsSourceEntity.setActivity(getRequiredActivityEntity(activityId));
        activitySellerGoodsSourceRepository.save(activitySellerGoodsSourceEntity);
    }

    @Transactional
    public void createActivityNativeGoodsSource(ActivityNativeGoodsSource activityNativeGoodsSource, Long activityId) {
        ActivityNativeGoodsSourceEntity activityNativeGoodsSourceEntity = activityNativeGoodsSourceEntityMapper.mapForSave(activityNativeGoodsSource);
        activityNativeGoodsSourceEntity.setActivity(getRequiredActivityEntity(activityId));
        activityNativeGoodsSourceRepository.save(activityNativeGoodsSourceEntity);
    }

    @Transactional
    public void createActivityGoods(ActivityGoodsModel model, Long goodsSourceId) {
        ActivityGoodsEntity activityGoodsEntity = activityGoodsEntityMapper.mapForSave(model);
        ActivityNativeGoodsSourceEntity activityNativeGoodsSourceEntity = activityNativeGoodsSourceRepository.get(goodsSourceId).orElseThrow(() -> new IllegalArgumentException("no native goods source found: " + goodsSourceId));
        activityGoodsEntity.setGoodsSource(activityNativeGoodsSourceEntity);
        activityGoodsEntity.setActivity(activityNativeGoodsSourceEntity.getActivity());
        activityGoodsRepository.save(activityGoodsEntity);
    }


    @Transactional(readOnly = true)
    public List<Activity> findAllActivityByGoodsSourceBrandId(Long brandId) {
//        activityBrandGoodsSourceRepository.findAllByBrandId(brandId);
        return null;
    }


    private ActivityEntity getRequiredActivityEntity(Long activityId) {
        return activityRepository.get(activityId).orElseThrow(() -> new IllegalArgumentException("no activity found: " + activityId));
    }

    @Autowired
    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Autowired
    public void setActivityBrandGoodsSourceRepository(ActivityBrandGoodsSourceRepository activityBrandGoodsSourceRepository) {
        this.activityBrandGoodsSourceRepository = activityBrandGoodsSourceRepository;
    }

    @Autowired
    public void setActivityCategoryGoodsSourceRepository(ActivityCategoryGoodsSourceRepository activityCategoryGoodsSourceRepository) {
        this.activityCategoryGoodsSourceRepository = activityCategoryGoodsSourceRepository;
    }

    @Autowired
    public void setActivitySellerGoodsSourceRepository(ActivitySellerGoodsSourceRepository activitySellerGoodsSourceRepository) {
        this.activitySellerGoodsSourceRepository = activitySellerGoodsSourceRepository;
    }

    @Autowired
    public void setActivityNativeGoodsSourceRepository(ActivityNativeGoodsSourceRepository activityNativeGoodsSourceRepository) {
        this.activityNativeGoodsSourceRepository = activityNativeGoodsSourceRepository;
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
    public void setActivityBrandGoodsSourceEntityMapper(ActivityBrandGoodsSourceEntityMapper activityBrandGoodsSourceEntityMapper) {
        this.activityBrandGoodsSourceEntityMapper = activityBrandGoodsSourceEntityMapper;
    }

    @Autowired
    public void setActivityCategoryGoodsSourceEntityMapper(ActivityCategoryGoodsSourceEntityMapper activityCategoryGoodsSourceEntityMapper) {
        this.activityCategoryGoodsSourceEntityMapper = activityCategoryGoodsSourceEntityMapper;
    }

    @Autowired
    public void setActivitySellerGoodsSourceEntityMapper(ActivitySellerGoodsSourceEntityMapper activitySellerGoodsSourceEntityMapper) {
        this.activitySellerGoodsSourceEntityMapper = activitySellerGoodsSourceEntityMapper;
    }

    @Autowired
    public void setActivityNativeGoodsSourceEntityMapper(ActivityNativeGoodsSourceEntityMapper activityNativeGoodsSourceEntityMapper) {
        this.activityNativeGoodsSourceEntityMapper = activityNativeGoodsSourceEntityMapper;
    }
}
