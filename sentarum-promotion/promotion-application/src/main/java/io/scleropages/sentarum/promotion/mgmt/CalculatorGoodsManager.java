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
import io.scleropages.sentarum.promotion.goods.repo.AdditionalAttributesInitializer;
import io.scleropages.sentarum.promotion.goods.repo.DetailedGoodsSourceReaderInitializer;
import io.scleropages.sentarum.promotion.rule.calculator.goods.repo.CalculatorGoodsRepository;
import io.scleropages.sentarum.promotion.rule.calculator.goods.repo.CalculatorGoodsSourceRepository;
import io.scleropages.sentarum.promotion.rule.calculator.goods.repo.CalculatorGoodsSpecsRepository;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.CalculatorGoodsEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.CalculatorGoodsSourceEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.CalculatorGoodsSpecsEntity;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.mapper.CalculatorGoodsEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.mapper.CalculatorGoodsSourceEntityMapper;
import io.scleropages.sentarum.promotion.rule.entity.calculator.goods.mapper.CalculatorGoodsSpecsEntityMapper;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoods;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSourceModel;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSpecs;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("23")
public class CalculatorGoodsManager implements InitializingBean {

    /**
     * managers
     */
    private ActivityManager activityManager;

    /**
     * calculator goods repositories.
     */
    private CalculatorGoodsSourceRepository calculatorGoodsSourceRepository;
    private CalculatorGoodsRepository calculatorGoodsRepository;
    private CalculatorGoodsSpecsRepository calculatorGoodsSpecsRepository;


    /**
     * promotion goods mappers.
     */
    private CalculatorGoodsSourceEntityMapper calculatorGoodsSourceEntityMapper;
    private CalculatorGoodsEntityMapper calculatorGoodsEntityMapper;
    private CalculatorGoodsSpecsEntityMapper calculatorGoodsSpecsEntityMapper;


    /**
     * other components.
     */
    private AdditionalAttributesInitializer additionalAttributesInitializer;
    private DetailedGoodsSourceReaderInitializer detailedGoodsSourceReaderInitializer;

    /**
     * 创建规则维度商品来源.
     *
     * @param calculatorGoodsSource
     * @param initialAdditionalAttributesObject
     * @return
     */
    @Validated(CalculatorGoodsSourceModel.Create.class)
    @Transactional
    @BizError("10")
    public Long createCalculatorGoodsSource(@Valid CalculatorGoodsSourceModel calculatorGoodsSource, Object initialAdditionalAttributesObject) {
        CalculatorGoodsSourceEntity entity = calculatorGoodsSourceEntityMapper.mapForSave(calculatorGoodsSource);
        Map<String, Object> attributePayload = Maps.newHashMap();
        attributePayload.put(CalculatorGoodsSource.ADDITIONAL_ATTRIBUTE_GOODS_SOURCE_HOLDER_CLASS, initialAdditionalAttributesObject.getClass().getName());
        attributePayload.put(CalculatorGoodsSource.ADDITIONAL_ATTRIBUTE_GOODS_SOURCE_HOLDER_PAYLOAD, JsonMapper2.toJson(initialAdditionalAttributesObject));
        entity.setAttributePayLoad(JsonMapper2.toJson(attributePayload));
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
    @BizError("11")
    public Long createCalculatorGoods(@Valid CalculatorGoods calculatorGoods, Long calculatorGoodsSourceId, Object initialAdditionalAttributesObject) {
        CalculatorGoodsEntity entity = calculatorGoodsEntityMapper.mapForSave(calculatorGoods);
        entity.setAdditionalAttributes(null != initialAdditionalAttributesObject ? JsonMapper2.toJson(initialAdditionalAttributesObject) : "{}");
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
    @BizError("12")
    public Long createCalculatorGoodsSpecs(@Valid CalculatorGoodsSpecs calculatorGoodsSpecs, Long calculatorGoodsId, Object initialAdditionalAttributesObject) {
        CalculatorGoodsSpecsEntity entity = calculatorGoodsSpecsEntityMapper.mapForSave(calculatorGoodsSpecs);
        entity.setAdditionalAttributes(JsonMapper2.toJson(initialAdditionalAttributesObject));
        CalculatorGoodsEntity calculatorGoodsEntity = calculatorGoodsRepository.get(calculatorGoodsId).orElseThrow(() -> new IllegalArgumentException("no calculator goods found: " + calculatorGoodsId));
        entity.setGoods(calculatorGoodsEntity);
        entity.setGoodsSource(calculatorGoodsEntity.getGoodsSource());
        calculatorGoodsSpecsRepository.save(entity);
        return entity.getId();
    }

    /**
     * return goods source by id.
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("12")
    public Optional<CalculatorGoodsSource> getGoodsSource(Long id) {
        Assert.notNull(id, "id must not be null.");
        Optional<CalculatorGoodsSourceEntity> optional = calculatorGoodsSourceRepository.get(id);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        return Optional.of(calculatorGoodsSourceEntityMapper.mapForRead(optional.get()));
    }

    /**
     * return all {@link CalculatorGoodsSource}
     *
     * @return
     */
    @Transactional(readOnly = true)
    @BizError("13")
    public List<CalculatorGoodsSource> readAllCalculatorGoodsSourceByRuleId(Long bizId, Integer goodsSourceType) {
        Assert.notNull(bizId, "bizId must not be null.");
        Assert.notNull(goodsSourceType, "goodsSourceType must not be null.");
        List<CalculatorGoodsSource> goodsSources = Lists.newArrayList();
        calculatorGoodsSourceRepository.consumeEntitiesByRecord(
                calculatorGoodsSourceRepository.readAllByBizTypeAndBizId(
                        CalculatorGoodsSource.BIZ_TYPE_OF_CALCULATOR
                        , bizId
                        , goodsSourceType)
                , entity -> {
                    CalculatorGoodsSource calculatorGoodsSource = (CalculatorGoodsSource) activityManager.initializeGoodsSource(calculatorGoodsSourceEntityMapper.mapForRead(entity), entity, calculatorGoodsSourceRepository, detailedGoodsSourceReaderInitializer);
                    goodsSources.add(calculatorGoodsSource);
                });
        return goodsSources;
    }

    @Autowired
    public void setActivityManager(ActivityManager activityManager) {
        this.activityManager = activityManager;
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

    @Autowired
    public void setAdditionalAttributesInitializer(AdditionalAttributesInitializer additionalAttributesInitializer) {
        this.additionalAttributesInitializer = additionalAttributesInitializer;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(calculatorGoodsRepository, "calculatorGoodsRepository must not be null");
        Assert.notNull(calculatorGoodsSpecsRepository, "calculatorGoodsSpecsRepository must not be null");
        Assert.notNull(calculatorGoodsEntityMapper, "calculatorGoodsEntityMapper must not be null");
        Assert.notNull(calculatorGoodsSpecsEntityMapper, "calculatorGoodsSpecsEntityMapper must not be null");
        Assert.notNull(additionalAttributesInitializer, "additionalAttributesInitializer must not be null");

        detailedGoodsSourceReaderInitializer = new DetailedGoodsSourceReaderInitializer(calculatorGoodsRepository, calculatorGoodsSpecsRepository, calculatorGoodsEntityMapper, calculatorGoodsSpecsEntityMapper, additionalAttributesInitializer);
    }
}
