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
import io.scleropages.sentarum.promotion.activity.repo.ActivityRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.BaseConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.ChannelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.SellerUserLevelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.UserLevelConditionRuleRepository;
import io.scleropages.sentarum.promotion.rule.condition.repo.UserTagConditionRuleRepository;
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
import io.scleropages.sentarum.promotion.rule.model.ConditionRule;
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
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 活动规则管理器.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("10")
public class ActivityRuleManager implements BeanClassLoaderAware {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

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
     * condition rule mappers.
     */
    private BaseConditionRuleEntityMapper baseConditionRuleEntityMapper;
    private ChannelConditionRuleEntityMapper channelConditionRuleEntityMapper;
    private UserTagConditionRuleEntityMapper userTagConditionRuleEntityMapper;
    private UserLevelConditionRuleEntityMapper userLevelConditionRuleEntityMapper;
    private SellerUserLevelConditionRuleEntityMapper sellerUserLevelConditionRuleEntityMapper;

    @Transactional
    public Long createConjunctionConditionRule(ConjunctionConditionRule conditionRule, Long activityId, Long parentConditionId) {
        BaseConditionRuleEntity entity = baseConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, baseConditionRuleRepository);
    }

    @Transactional
    public Long createChannelConditionRule(ChannelConditionRule conditionRule, Long activityId, Long parentConditionId) {
        ChannelConditionRuleEntity entity = channelConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, channelConditionRuleRepository);
    }

    @Transactional
    public Long createUserTagConditionRule(UserTagConditionRule conditionRule, Long activityId, Long parentConditionId) {
        UserTagConditionRuleEntity entity = userTagConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, userTagConditionRuleRepository);
    }

    @Transactional
    public Long createUserLevelConditionRule(UserLevelConditionRule conditionRule, Long activityId, Long parentConditionId) {
        UserLevelConditionRuleEntity entity = userLevelConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, userLevelConditionRuleRepository);
    }

    @Transactional
    public Long createSellerUserLevelConditionRule(SellerUserLevelConditionRule conditionRule, Long activityId, Long parentConditionId) {
        SellerUserLevelConditionRuleEntity entity = sellerUserLevelConditionRuleEntityMapper.mapForSave(conditionRule);
        return createConditionRuleInternal(conditionRule, entity, activityId, parentConditionId, sellerUserLevelConditionRuleRepository);
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
            return mapConditionRule(conditionEntities.get(0), activity);
        }
        conditionEntities.forEach(entity -> {
            AbstractConditionRule rule = mapConditionRule(entity, activity);
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

    private AbstractConditionRule mapConditionRule(BaseConditionRuleEntity entity, Activity activity) {
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
        preConditionRuleCreating(conditionRule, entity);
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
            BaseConditionRuleEntity parentCondition = baseConditionRuleRepository.get(parentId).orElseThrow(() -> new IllegalArgumentException("no parent condition found: " + parentId));
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
     * @param conditionRule
     * @param baseConditionRuleEntity
     */
    private void preConditionRuleCreating(ConditionRule conditionRule, BaseConditionRuleEntity baseConditionRuleEntity) {
        baseConditionRuleEntity.setRuleClass(conditionRule.getClass().getName());
        baseConditionRuleEntity.setRulePayload(JsonMapper2.toJson(conditionRule));
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

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
