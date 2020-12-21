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
import io.scleropages.sentarum.promotion.rule.calculator.repo.BaseCalculatorRuleRepository;
import io.scleropages.sentarum.promotion.rule.entity.AbstractRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.BaseCalculatorRuleEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.mapper.BaseCalculatorRuleEntityMapper;
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
import io.scleropages.sentarum.promotion.rule.invocation.condition.repo.BaseConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.invocation.condition.repo.ChannelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.invocation.condition.repo.SellerUserLevelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.invocation.condition.repo.UserLevelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.invocation.condition.repo.UserTagConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.calculator.CalculatorRuleInitializer;
import io.scleropages.sentarum.promotion.rule.model.AbstractConditionRule;
import io.scleropages.sentarum.promotion.rule.model.AbstractRule;
import io.scleropages.sentarum.promotion.rule.model.BaseCalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
import io.scleropages.sentarum.promotion.rule.model.Rule;
import io.scleropages.sentarum.promotion.rule.model.calculator.GoodsDiscountRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSourceModel;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorInitializableRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorInitializableRuleDetailedConfigure;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.ConjunctionConditionRule.ConditionConjunction;
import io.scleropages.sentarum.promotion.rule.model.condition.SellerUserLevelConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.UserLevelConditionRule;
import io.scleropages.sentarum.promotion.rule.model.condition.UserTagConditionRule;
import io.scleropages.sentarum.promotion.rule.repo.AbstractConditionRuleRepository;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.core.util.Reflections2;
import org.scleropages.crud.exception.BizError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.scleropages.sentarum.promotion.rule.model.CalculatorRule.ATTRIBUTE_CALCULATE_LEVEL;

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
    private CalculatorGoodsManager calculatorGoodsManager;

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

    /**
     * other components
     */
    private List<CalculatorRuleInitializer> calculatorRuleInitializers = Collections.emptyList();


    /**
     * 创建逻辑运算条件规则
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
        ActivityEntity activityEntity = getRequiredActivityEntity(activityId);
        activityManager.initializeActivity(activityEntity, null).additionalAttributes().setAttribute(ATTRIBUTE_CALCULATE_LEVEL, goodsDiscountRule.calculateLevel(), true).save();
        entity.setActivity(activityEntity);
        preRuleCreating(goodsDiscountRule, entity);
        baseCalculatorRuleRepository.save(entity);
        return entity.getId();
    }

    /**
     * 创建计算规则.
     *
     * @param calculatorRule
     * @param activityId
     * @return
     */
    @Validated(BaseCalculatorRule.Create.class)
    @Transactional
    @BizError("16")
    public Long createCalculatingRule(@Valid BaseCalculatorRule calculatorRule, Long activityId) {
        Assert.notNull(activityId, "activityId must not be null.");
        BaseCalculatorRuleEntity entity = baseCalculatorRuleEntityMapper.mapForSave(calculatorRule);
        ActivityEntity activityEntity = getRequiredActivityEntity(activityId);
        entity.setActivity(activityEntity);
        activityManager.initializeActivity(activityEntity, null).additionalAttributes().setAttribute(ATTRIBUTE_CALCULATE_LEVEL, calculatorRule.calculateLevel(), true).save();
        preRuleCreating(calculatorRule, entity);
        baseCalculatorRuleRepository.save(entity);
        return entity.getId();
    }


    /**
     * 创建计算规则详情
     *
     * @param configure
     * @param calculatorRuleId
     * @return
     */
    @Transactional
    @BizError("17")
    public Long createCalculatingRuleDetailedConfig(CalculatorInitializableRuleDetailedConfigure configure, Long calculatorRuleId) {
        Assert.notNull(configure, "configure must not be null.");
        Assert.notNull(calculatorRuleId, "calculatorRuleId must not be null.");
        BaseCalculatorRuleEntity baseCalculatorRuleEntity = baseCalculatorRuleRepository.get(calculatorRuleId).orElseThrow(() -> new IllegalArgumentException("no calculator rule found: " + calculatorRuleId));
        AbstractRule rule = mapRule(baseCalculatorRuleEntity, null);
        Assert.isInstanceOf(CalculatorInitializableRule.class, rule, "not an instanceof CalculatorInitializableRule by given rule id:" + calculatorRuleId);
        CalculatorInitializableRule initializableRule = (CalculatorInitializableRule) rule;
        configure.assertConfigure(initializableRule);
        CalculatorGoodsSourceModel calculatorGoodsSource = new CalculatorGoodsSourceModel();
        calculatorGoodsSource.setBizType(CalculatorGoodsSource.BIZ_TYPE_OF_CALCULATOR);
        calculatorGoodsSource.setBizId(calculatorRuleId);
        calculatorGoodsSource.setComment(configure.comment());
        calculatorGoodsSource.setGoodsSourceType(initializableRule.goodsSourceType());
        return calculatorGoodsManager.createCalculatorGoodsSource(calculatorGoodsSource, configure);
    }


    /**
     * read condition rules and merge rules to root of rule tree.
     *
     * @param activityId id of activity.
     * @param activity   optional activity. if present it will apply to {@link ConditionRule#activity()}.
     * @return root node of merged rule tree.
     */
    @Transactional(readOnly = true)
    @BizError("33")
    public ConditionRule getConditionRule(Long activityId, Activity activity) {
        Assert.notNull(activityId, "activityId must not be null.");
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


    /**
     * find all {@link CalculatorRule}s by given activity id.
     *
     * @param activityId id of activity.
     * @param activity   (optional) if present,it will apply to {@link CalculatorRule#activity()}
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("34")
    public List<CalculatorRule> findAllCalculatorRulesByActivityId(Long activityId, Activity activity) {
        List<BaseCalculatorRuleEntity> entities = baseCalculatorRuleRepository.readAllByActivity_Id(activityId);
        List<CalculatorRule> calculatorRules = Lists.newArrayList();
        for (BaseCalculatorRuleEntity entity : entities) {
            CalculatorRule calculatorRule = (CalculatorRule) mapRule(entity, activity);
            for (CalculatorRuleInitializer initializer : calculatorRuleInitializers) {
                if (initializer.support(calculatorRule)) {
                    calculatorRule = initializer.initialize(calculatorRule);
                }
            }
            calculatorRules.add(calculatorRule);
        }
        return calculatorRules;
    }


    /**
     * map rule entity as rule model and applying to given activity(optional)
     *
     * @param entity
     * @param activity
     * @return
     */
    private AbstractRule mapRule(AbstractRuleEntity entity, Activity activity) {
        Class<?> ruleClass = Reflections2.getClass(entity.getRuleClass(), classLoader);
        AbstractRule abstractRule = JsonMapper2.fromJson(entity.getRulePayload(), ruleClass);
        abstractRule.setId(entity.getId());
        abstractRule.setActivity(activity);
        return abstractRule;
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
    public void setCalculatorGoodsManager(CalculatorGoodsManager calculatorGoodsManager) {
        this.calculatorGoodsManager = calculatorGoodsManager;
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
    public void setCalculatorRuleInitializers(List<CalculatorRuleInitializer> calculatorRuleInitializers) {
        this.calculatorRuleInitializers = calculatorRuleInitializers;
    }

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

}
