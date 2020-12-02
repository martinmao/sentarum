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
import com.google.common.collect.Maps;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.activity.model.ActivityClassifiedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityDetailedGoodsSource;
import io.scleropages.sentarum.promotion.activity.model.ActivityGoodsSource;
import io.scleropages.sentarum.promotion.activity.repo.ActivityRepository;
import io.scleropages.sentarum.promotion.rule.calculator.goods.repo.CalculatorGoodsRepository;
import io.scleropages.sentarum.promotion.rule.calculator.goods.repo.CalculatorGoodsSourceRepository;
import io.scleropages.sentarum.promotion.rule.calculator.goods.repo.CalculatorGoodsSpecsRepository;
import io.scleropages.sentarum.promotion.rule.calculator.repo.BaseCalculatorRuleRepository;
import io.scleropages.sentarum.promotion.rule.calculator.repo.OverflowDiscountRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.BaseConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.ChannelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.SellerUserLevelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.UserLevelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.UserTagConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.entity.AbstractRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.BaseCalculatorRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.OverflowDiscountRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.CalculatorGoodsEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.CalculatorGoodsSourceEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.CalculatorGoodsSpecsEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.mapper.CalculatorGoodsEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.mapper.CalculatorGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.mapper.CalculatorGoodsSpecsEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.calculator.mapper.BaseCalculatorRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.calculator.mapper.OverflowDiscountRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.condition.BaseConditionRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.condition.ChannelConditionRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.condition.SellerUserLevelConditionRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.condition.UserLevelConditionRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.condition.UserTagConditionRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.condition.mapper.BaseConditionRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.condition.mapper.ChannelConditionRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.condition.mapper.SellerUserLevelConditionRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.condition.mapper.UserLevelConditionRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.condition.mapper.UserTagConditionRuleEntityMapper;
import io.scleropages.sentarum.promotion.rule.model.AbstractConditionRule;
import io.scleropages.sentarum.promotion.rule.model.AbstractRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import io.scleropages.sentarum.promotion.rule.model.calculator.Gift;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscount;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoods;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSpecs;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule.ConditionConjunction;
import io.scleropages.sentarum.promotion.rule.model.condition.SellerUserLevelConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.UserLevelConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.UserTagConditionRule;
import io.scleropages.sentarum.promotion.rule.repo.AbstractConditionRuleRepository;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.exception.BizError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource.GOODS_SOURCE_TYPE_OVERFLOW_DISCOUNT;

/**
 * 活动规则管理器.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("21")
public class ActivityRuleManager implements BeanClassLoaderAware {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * managers
     */
    private ActivityManager activityManager;

    /**
     * activity base repositories.
     */
    private ActivityRepository activityRepository;

    /**
     * condition rule repositories.
     */
    private BaseConditionRuleRepository baseConditionRuleRepository;
    private ChannelConditionRuleRepository channelConditionRuleRepository;
    private UserTagConditionRuleRepository userTagConditionRuleRepository;
    private UserLevelConditionRuleRepository userLevelConditionRuleRepository;
    private SellerUserLevelConditionRuleRepository sellerUserLevelConditionRuleRepository;

    /**
     * calculator rule repositories.
     */
    private BaseCalculatorRuleRepository baseCalculatorRuleRepository;
    private OverflowDiscountRuleRepository overflowDiscountRuleRepository;

    /**
     * calculator goods repositories.
     */
    private CalculatorGoodsSourceRepository calculatorGoodsSourceRepository;
    private CalculatorGoodsRepository calculatorGoodsRepository;
    private CalculatorGoodsSpecsRepository calculatorGoodsSpecsRepository;

    /**
     * condition rule mappers.
     */
    private BaseConditionRuleEntityMapper baseConditionRuleEntityMapper;
    private ChannelConditionRuleEntityMapper channelConditionRuleEntityMapper;
    private UserTagConditionRuleEntityMapper userTagConditionRuleEntityMapper;
    private UserLevelConditionRuleEntityMapper userLevelConditionRuleEntityMapper;
    private SellerUserLevelConditionRuleEntityMapper sellerUserLevelConditionRuleEntityMapper;

    /**
     * calculator rule mappers.
     */
    private BaseCalculatorRuleEntityMapper baseCalculatorRuleEntityMapper;
    private OverflowDiscountRuleEntityMapper overflowDiscountRuleEntityMapper;

    /**
     * promotion goods mappers.
     */
    private CalculatorGoodsSourceEntityMapper calculatorGoodsSourceEntityMapper;
    private CalculatorGoodsEntityMapper calculatorGoodsEntityMapper;
    private CalculatorGoodsSpecsEntityMapper calculatorGoodsSpecsEntityMapper;


    /**
     * 创建条件规则
     *
     * @param conditionRule
     * @param activityId
     * @param parentConditionId
     * @return
     */
    @Validated(ConjunctionConditionRule.Create.class)
    @Transactional
    @BizError("10")
    public Long createConjunctionConditionRule(@Valid ConjunctionConditionRule conditionRule, Long activityId, Long parentConditionId) {
        BaseConditionRuleEntity entity = baseConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, baseConditionRuleRepository);
    }

    /**
     * 创建渠道条件规则
     *
     * @param conditionRule
     * @param activityId
     * @param parentConditionId
     * @return
     */
    @Validated(ChannelConditionRule.Create.class)
    @Transactional
    @BizError("11")
    public Long createChannelConditionRule(@Valid ChannelConditionRule conditionRule, Long activityId, Long parentConditionId) {
        ChannelConditionRuleEntity entity = channelConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, channelConditionRuleRepository);
    }

    /**
     * 创建用户标签规则
     *
     * @param conditionRule
     * @param activityId
     * @param parentConditionId
     * @return
     */
    @Validated(UserTagConditionRule.Create.class)
    @Transactional
    @BizError("12")
    public Long createUserTagConditionRule(@Valid UserTagConditionRule conditionRule, Long activityId, Long parentConditionId) {
        UserTagConditionRuleEntity entity = userTagConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, userTagConditionRuleRepository);
    }

    /**
     * 创建用户等级规则
     *
     * @param conditionRule
     * @param activityId
     * @param parentConditionId
     * @return
     */
    @Validated(UserLevelConditionRule.Create.class)
    @Transactional
    @BizError("13")
    public Long createUserLevelConditionRule(@Valid UserLevelConditionRule conditionRule, Long activityId, Long parentConditionId) {
        UserLevelConditionRuleEntity entity = userLevelConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, userLevelConditionRuleRepository);
    }

    /**
     * 创建商家用户等级规则
     *
     * @param conditionRule
     * @param activityId
     * @param parentConditionId
     * @return
     */
    @Validated(SellerUserLevelConditionRule.Create.class)
    @Transactional
    @BizError("14")
    public Long createSellerUserLevelConditionRule(@Valid SellerUserLevelConditionRule conditionRule, Long activityId, Long parentConditionId) {
        SellerUserLevelConditionRuleEntity entity = sellerUserLevelConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, sellerUserLevelConditionRuleRepository);
    }

    /**
     * 创建商品折扣规则.
     *
     * @param goodsDiscountRule
     * @param activityId
     * @return
     */
    @Validated(GoodsDiscountRule.Create.class)
    @Transactional
    @BizError("15")
    public Long createGoodsDiscountRule(@Valid GoodsDiscountRule goodsDiscountRule, Long activityId) {
        List<ActivityGoodsSource> activityGoodsSources = activityManager.findAllActivityGoodsSource(activityId);
        Assert.notEmpty(activityGoodsSources, "no activity goods source found.");
        activityGoodsSources.forEach(activityGoodsSource -> {
            if (activityGoodsSource instanceof ActivityDetailedGoodsSource) {
                goodsDiscountRule.applyActivityDetailedGoodsSourceConfigure((ActivityDetailedGoodsSource) activityGoodsSource);
            } else if (activityGoodsSource instanceof ActivityClassifiedGoodsSource) {
                goodsDiscountRule.applyActivityClassifiedGoodsSourceConfigure((ActivityClassifiedGoodsSource) activityGoodsSource);
            } else
                throw new IllegalStateException("unsupported activity goods source: " + AopUtils.getTargetClass(activityGoodsSource).getSimpleName());
        });
        BaseCalculatorRuleEntity entity = baseCalculatorRuleEntityMapper.mapForSave(goodsDiscountRule);
        ActivityEntity requiredActivityEntity = getRequiredActivityEntity(activityId);
        entity.setActivity(requiredActivityEntity);
        preRuleCreating(goodsDiscountRule, entity);
        baseCalculatorRuleRepository.save(entity);
        return entity.getId();
    }

    /**
     * 创建满减促销规则.
     *
     * @param overflowDiscountRule
     * @param activityId
     * @return
     */
    @Validated(OverflowDiscountRule.Create.class)
    @Transactional
    @BizError("16")
    public Long createOverflowDiscountRule(@Valid OverflowDiscountRule overflowDiscountRule, Long activityId) {
        OverflowDiscountRuleEntity entity = overflowDiscountRuleEntityMapper.mapForSave(overflowDiscountRule);
        entity.setActivity(getRequiredActivityEntity(activityId));
        preRuleCreating(overflowDiscountRule, entity);
        overflowDiscountRuleRepository.save(entity);
        return entity.getId();
    }

    /**
     * 创建满减促销规则明细.
     *
     * @param overflowDiscount
     * @param overflowDiscountRuleId
     * @return
     */
    @Transactional
    @BizError("17")
    public Long createOverflowDiscount(OverflowDiscount overflowDiscount, Long overflowDiscountRuleId) {
        Assert.notNull(overflowDiscount, "overflowDiscount must not be null.");
        OverflowDiscountRule overflowDiscountRule = overflowDiscountRuleEntityMapper.mapForRead(overflowDiscountRuleRepository.get(overflowDiscountRuleId).orElseThrow(() -> new IllegalArgumentException("no overflow discount rule found: " + overflowDiscountRuleId)));
        overflowDiscount.assertValid(overflowDiscountRule);
        CalculatorGoodsSource calculatorGoodsSource = new CalculatorGoodsSource();
        calculatorGoodsSource.setBizType(CalculatorGoodsSource.BIZ_TYPE_OF_CALCULATOR);
        calculatorGoodsSource.setBizId(overflowDiscountRule.id());
        calculatorGoodsSource.setComment("满减促销");
        calculatorGoodsSource.setGoodsSourceType(GOODS_SOURCE_TYPE_OVERFLOW_DISCOUNT);
        return createCalculatorGoodsSource(calculatorGoodsSource, overflowDiscountRuleId, overflowDiscount);
    }


    /**
     * 创建满减促销赠品.
     *
     * @param gift
     * @param overflowDiscountId
     * @return
     */
    @Validated(Gift.Create.class)
    @Transactional
    @BizError("18")
    public Long createOverflowDiscountGift(@Valid Gift gift, Long overflowDiscountId) {
        calculatorGoodsSourceRepository.get(overflowDiscountId).orElseThrow(() -> new IllegalArgumentException("no overflow discount found: " + overflowDiscountId));
        CalculatorGoods goods = new CalculatorGoods();
        goods.setGoodsId(gift.getGoodsId());
        goods.setName(gift.getName());
        goods.setOuterGoodsId(gift.getOuterGoodsId());
        return createCalculatorGoods(goods, overflowDiscountId, gift);
    }


    /**
     * 创建规则维度商品来源.
     *
     * @param calculatorGoodsSource
     * @param calculatorRuleId
     * @param initialAdditionalAttributesObject
     * @return
     */
    @Validated(CalculatorGoodsSource.Create.class)
    @Transactional
    @BizError("30")
    public Long createCalculatorGoodsSource(CalculatorGoodsSource calculatorGoodsSource, Long calculatorRuleId, Object initialAdditionalAttributesObject) {
        CalculatorGoodsSourceEntity entity = calculatorGoodsSourceEntityMapper.mapForSave(calculatorGoodsSource);
        entity.setAttributePayLoad(JsonMapper2.toJson(initialAdditionalAttributesObject));
        Assert.isTrue(baseCalculatorRuleRepository.existsById(calculatorRuleId), () -> " no calculator rule found by id: " + calculatorRuleId);
        entity.setBizId(calculatorRuleId);
        calculatorGoodsSourceRepository.save(entity);
        return entity.getId();
    }

    /**
     * 创建规则维度商品.
     *
     * @param calculatorGoods
     * @param calculatorGoodsSourceId
     * @param initialAdditionalAttributesObject
     * @return
     */
    @Validated(CalculatorGoods.Create.class)
    @Transactional
    @BizError("31")
    public Long createCalculatorGoods(CalculatorGoods calculatorGoods, Long calculatorGoodsSourceId, Object initialAdditionalAttributesObject) {
        CalculatorGoodsEntity entity = calculatorGoodsEntityMapper.mapForSave(calculatorGoods);
        entity.setAdditionalAttributes(JsonMapper2.toJson(initialAdditionalAttributesObject));
        CalculatorGoodsSourceEntity calculatorGoodsSourceEntity = calculatorGoodsSourceRepository.get(calculatorGoodsSourceId).orElseThrow(() -> new IllegalArgumentException("no calculator goods source found: " + calculatorGoodsSourceId));
        entity.setGoodsSource(calculatorGoodsSourceEntity);
        calculatorGoodsRepository.save(entity);
        return entity.getId();
    }

    /**
     * 创建规则维度商品规格.
     *
     * @param calculatorGoodsSpecs
     * @param calculatorGoodsId
     * @param initialAdditionalAttributesObject
     * @return
     */
    @Validated(CalculatorGoods.Create.class)
    @Transactional
    @BizError("32")
    public Long createCalculatorGoodsSpecs(CalculatorGoodsSpecs calculatorGoodsSpecs, Long calculatorGoodsId, Object initialAdditionalAttributesObject) {
        CalculatorGoodsSpecsEntity entity = calculatorGoodsSpecsEntityMapper.mapForSave(calculatorGoodsSpecs);
        entity.setAdditionalAttributes(JsonMapper2.toJson(initialAdditionalAttributesObject));
        CalculatorGoodsEntity calculatorGoodsEntity = calculatorGoodsRepository.get(calculatorGoodsId).orElseThrow(() -> new IllegalArgumentException("no calculator goods found: " + calculatorGoodsId));
        entity.setGoods(calculatorGoodsEntity);
        entity.setGoodsSource(calculatorGoodsEntity.getGoodsSource());
        calculatorGoodsSpecsRepository.save(entity);
        return entity.getId();
    }


    /**
     * read condition rules and merge rules to root of rule tree.
     *
     * @param activityId id of activity.
     * @param activity   optional activity. if present it will apply to {@link ConditionRule#activity()}.
     * @return
     */
    @Transactional(readOnly = true)
    public ConditionRule getConditionRule(Long activityId, Activity activity) {
        Map<Long, ConjunctionConditionRule> conjunctionRules = Maps.newHashMap();
        List<AbstractConditionRule> otherRules = Lists.newArrayList();
        List<BaseConditionRuleEntity> conditionEntities = baseConditionRuleRepository.findAllByActivity_Id(activityId);
        if (conditionEntities.size() == 1) {
            return (ConditionRule) mapRule(conditionEntities.get(0), activity);
        }
        conditionEntities.forEach(entity -> {
            AbstractConditionRule rule = (AbstractConditionRule) mapRule(entity, activity);
            Long parentId = entity.getParentCondition();
            rule.setParentId(parentId);
            if (rule instanceof ConjunctionConditionRule) {
                conjunctionRules.put(rule.id(), (ConjunctionConditionRule) rule);
            } else {
                ConjunctionConditionRule conjunctionConditionRule = conjunctionRules.get(parentId);
                if (null != conjunctionConditionRule) {
                    conjunctionConditionRule.addCondition(rule);
                } else {
                    otherRules.add(rule);
                }
            }
        });
        otherRules.forEach((otherRule) -> {
            ConjunctionConditionRule conjunctionConditionRule = conjunctionRules.get(otherRule.getParentId());
            if (null == conjunctionConditionRule) {
                logger.warn("detected and ignored a condition rule[{}]'s parent not found in current activity: {}", otherRule.id(), activityId);
                return;
            }
            conjunctionConditionRule.addCondition(otherRule);
        });
        ConjunctionConditionRule rootConjunctionConditionRule = null;

        for (ConjunctionConditionRule v : conjunctionRules.values()) {
            Long parentId = v.getParentId();
            if (null == parentId) {
                if (null != rootConjunctionConditionRule) {
                    logger.warn("detected multiple root conditions[{},{}] in current activity: {}. use {} as result.", rootConjunctionConditionRule.id(), v.id(), activityId, rootConjunctionConditionRule.id());
                } else {
                    rootConjunctionConditionRule = v;
                }
                continue;
            }
            ConjunctionConditionRule conjunctionConditionRule = conjunctionRules.get(v.getParentId());
            if (null == conjunctionConditionRule) {
                logger.warn("detected and ignored a conjunction condition rule[{}]'s parent not found in current activity: {}", v.id(), activityId);
                continue;
            }
            conjunctionConditionRule.addCondition(v);
        }
        return rootConjunctionConditionRule;
    }

    private AbstractRule mapRule(AbstractRuleEntity entity, Activity activity) {
        Class<?> ruleClass;
        try {
            ruleClass = ClassUtils.forName(entity.getRuleClass(), classLoader);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("no rule class found.");
        }
        AbstractConditionRule conditionRule = JsonMapper2.fromJson(entity.getRulePayload(), ruleClass);
        conditionRule.setId(entity.getId());
        conditionRule.setActivity(activity);
        return conditionRule;
    }


    /**
     * create condition rule internal.
     *
     * @param conditionRule
     * @param entity
     * @param activityId
     * @param parentId
     * @param repository
     * @return
     */
    private Long createConditionRuleInternal(ConditionRule conditionRule, BaseConditionRuleEntity entity, Long activityId, Long parentId, AbstractConditionRuleRepository repository) {
        entity.setActivity(getRequiredActivityEntity(activityId));
        entity.setParentCondition(parentId);
        assertConditionRuleEntity(entity);
        preRuleCreating(conditionRule, entity);
        repository.save(entity);
        return entity.getId();
    }

    /**
     * asserts given activity rule entity is valid.
     *
     * @param conditionEntity
     */
    protected void assertConditionRuleEntity(BaseConditionRuleEntity conditionEntity) {
        Long parentId = conditionEntity.getParentCondition();
        Long activityId = conditionEntity.getActivity().getId();
        if (null == parentId) {
            Assert.isTrue(!baseConditionRuleRepository.existsByActivity_IdAndParentConditionIsNull(activityId), "conditional rules only allow one root in activity tree.");
        } else {
            BaseConditionRuleEntity parentCondition = baseConditionRuleRepository.getById(parentId).orElseThrow(() -> new IllegalArgumentException("no parent condition found: " + parentId));
            Assert.isTrue(Objects.equals(parentCondition.getRuleClass(), ConjunctionConditionRule.class.getName()), "parent condition must be conjunction condition: " + parentId);
            if (ConditionConjunction.getByOrdinal(parentCondition.getConditionConjunction()) == ConditionConjunction.NOT) {
                Assert.isTrue(baseConditionRuleRepository.countByParentCondition(parentId) == 0, "conjunction condition just allowed one child condition with operator 'NOT'");
            }
        }
    }

    private ActivityEntity getRequiredActivityEntity(Long id) {
        return activityRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no activity found: " + id));
    }

    /**
     * pre checking and processing before save.
     *
     * @param rule
     * @param ruleEntity
     */
    private void preRuleCreating(Rule rule, AbstractRuleEntity ruleEntity) {
        ruleEntity.setRuleClass(rule.getClass().getName());
        ruleEntity.setRulePayload(JsonMapper2.toJson(rule));
    }

    @Autowired
    public void setActivityManager(ActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    @Autowired
    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Autowired
    public void setBaseConditionRuleRepository(BaseConditionRuleRepository baseConditionRuleRepository) {
        this.baseConditionRuleRepository = baseConditionRuleRepository;
    }

    @Autowired
    public void setChannelConditionRuleRepository(ChannelConditionRuleRepository channelConditionRuleRepository) {
        this.channelConditionRuleRepository = channelConditionRuleRepository;
    }

    @Autowired
    public void setUserTagConditionRuleRepository(UserTagConditionRuleRepository userTagConditionRuleRepository) {
        this.userTagConditionRuleRepository = userTagConditionRuleRepository;
    }

    @Autowired
    public void setUserLevelConditionRuleRepository(UserLevelConditionRuleRepository userLevelConditionRuleRepository) {
        this.userLevelConditionRuleRepository = userLevelConditionRuleRepository;
    }

    @Autowired
    public void setSellerUserLevelConditionRuleRepository(SellerUserLevelConditionRuleRepository sellerUserLevelConditionRuleRepository) {
        this.sellerUserLevelConditionRuleRepository = sellerUserLevelConditionRuleRepository;
    }

    @Autowired
    public void setBaseCalculatorRuleRepository(BaseCalculatorRuleRepository baseCalculatorRuleRepository) {
        this.baseCalculatorRuleRepository = baseCalculatorRuleRepository;
    }

    @Autowired
    public void setOverflowDiscountRuleRepository(OverflowDiscountRuleRepository overflowDiscountRuleRepository) {
        this.overflowDiscountRuleRepository = overflowDiscountRuleRepository;
    }

    @Autowired
    public void setCalculatorGoodsSourceRepository(CalculatorGoodsSourceRepository calculatorGoodsSourceRepository) {
        this.calculatorGoodsSourceRepository = calculatorGoodsSourceRepository;
    }

    @Autowired
    public void setCalculatorGoodsRepository(CalculatorGoodsRepository calculatorGoodsRepository) {
        this.calculatorGoodsRepository = calculatorGoodsRepository;
    }

    @Autowired
    public void setCalculatorGoodsSpecsRepository(CalculatorGoodsSpecsRepository calculatorGoodsSpecsRepository) {
        this.calculatorGoodsSpecsRepository = calculatorGoodsSpecsRepository;
    }

    @Autowired
    public void setBaseConditionRuleEntityMapper(BaseConditionRuleEntityMapper baseConditionRuleEntityMapper) {
        this.baseConditionRuleEntityMapper = baseConditionRuleEntityMapper;
    }

    @Autowired
    public void setChannelConditionRuleEntityMapper(ChannelConditionRuleEntityMapper channelConditionRuleEntityMapper) {
        this.channelConditionRuleEntityMapper = channelConditionRuleEntityMapper;
    }

    @Autowired
    public void setUserTagConditionRuleEntityMapper(UserTagConditionRuleEntityMapper userTagConditionRuleEntityMapper) {
        this.userTagConditionRuleEntityMapper = userTagConditionRuleEntityMapper;
    }

    @Autowired
    public void setUserLevelConditionRuleEntityMapper(UserLevelConditionRuleEntityMapper userLevelConditionRuleEntityMapper) {
        this.userLevelConditionRuleEntityMapper = userLevelConditionRuleEntityMapper;
    }

    @Autowired
    public void setSellerUserLevelConditionRuleEntityMapper(SellerUserLevelConditionRuleEntityMapper sellerUserLevelConditionRuleEntityMapper) {
        this.sellerUserLevelConditionRuleEntityMapper = sellerUserLevelConditionRuleEntityMapper;
    }

    @Autowired
    public void setBaseCalculatorRuleEntityMapper(BaseCalculatorRuleEntityMapper baseCalculatorRuleEntityMapper) {
        this.baseCalculatorRuleEntityMapper = baseCalculatorRuleEntityMapper;
    }

    @Autowired
    public void setOverflowDiscountRuleEntityMapper(OverflowDiscountRuleEntityMapper overflowDiscountRuleEntityMapper) {
        this.overflowDiscountRuleEntityMapper = overflowDiscountRuleEntityMapper;
    }

    @Autowired
    public void setCalculatorGoodsSourceEntityMapper(CalculatorGoodsSourceEntityMapper calculatorGoodsSourceEntityMapper) {
        this.calculatorGoodsSourceEntityMapper = calculatorGoodsSourceEntityMapper;
    }

    @Autowired
    public void setCalculatorGoodsEntityMapper(CalculatorGoodsEntityMapper calculatorGoodsEntityMapper) {
        this.calculatorGoodsEntityMapper = calculatorGoodsEntityMapper;
    }

    @Autowired
    public void setCalculatorGoodsSpecsEntityMapper(CalculatorGoodsSpecsEntityMapper calculatorGoodsSpecsEntityMapper) {
        this.calculatorGoodsSpecsEntityMapper = calculatorGoodsSpecsEntityMapper;
    }

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
