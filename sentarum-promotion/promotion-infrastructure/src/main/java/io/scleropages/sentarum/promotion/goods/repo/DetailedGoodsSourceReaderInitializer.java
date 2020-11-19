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
package io.scleropages.sentarum.promotion.goods.repo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.scleropages.sentarum.promotion.goods.DetailedGoodsSourceReader;
import io.scleropages.sentarum.promotion.goods.DetailedGoodsSourceReader.GoodsHolder;
import io.scleropages.sentarum.promotion.goods.entity.GoodsEntity;
import io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity;
import io.scleropages.sentarum.promotion.goods.entity.mapper.GoodsEntityMapper;
import io.scleropages.sentarum.promotion.goods.entity.mapper.GoodsSpecsEntityMapper;
import io.scleropages.sentarum.promotion.goods.model.DetailedGoodsSource;
import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.goods.model.GoodsSpecs;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * advice of {@link DetailedGoodsSourceReader}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class DetailedGoodsSourceReaderInitializer implements MethodInterceptor {


    private final GoodsRepository goodsRepository;
    private final GoodsSpecsRepository goodsSpecsRepository;
    private final GoodsEntityMapper goodsEntityMapper;
    private final GoodsSpecsEntityMapper goodsSpecsEntityMapper;
    private final AdditionalAttributesInitializer additionalAttributesInitializer;

    public DetailedGoodsSourceReaderInitializer(GoodsRepository goodsRepository, GoodsSpecsRepository goodsSpecsRepository, GoodsEntityMapper goodsEntityMapper, GoodsSpecsEntityMapper goodsSpecsEntityMapper, AdditionalAttributesInitializer additionalAttributesInitializer) {
        this.goodsRepository = goodsRepository;
        this.goodsSpecsRepository = goodsSpecsRepository;
        this.goodsEntityMapper = goodsEntityMapper;
        this.goodsSpecsEntityMapper = goodsSpecsEntityMapper;
        this.additionalAttributesInitializer = additionalAttributesInitializer;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        if (Objects.equals(invocation.getMethod().getReturnType(), DetailedGoodsSourceReader.class)) {

            Object target = invocation.getThis();

            if (target instanceof DetailedGoodsSource) {
                DetailedGoodsSource detailedGoodsSource = (DetailedGoodsSource) target;
                Assert.notNull(detailedGoodsSource.id(), "no id found by given detailed goods source.");
                return (DetailedGoodsSourceReader) () -> {
                    Map<Long, GoodsHolder> goodsMap = Maps.newHashMap();
                    goodsRepository.findAllByGoodsSource_Id(detailedGoodsSource.id()).forEach(entity -> {
                        GoodsEntity goodsEntity = (GoodsEntity) entity;
                        Goods goodsModel = (Goods) goodsEntityMapper.mapForRead(goodsEntity);
                        final Long goodsId = goodsModel.id();
                        goodsModel = (Goods) additionalAttributesInitializer.initializeAdditionalAttributes(goodsModel, goodsEntity, goodsRepository, false, (MethodInterceptor) methodInvocation -> {
                            if (Objects.equals(methodInvocation.getMethod().getName(), "getSpecs")) {
                                List<GoodsSpecs> goodsSpecs = Lists.newArrayList();
                                List<? extends GoodsSpecsEntity> goodsSpecsEntities = goodsRepository.findAllGoodsSpecsByGoodsId(goodsSpecsRepository, goodsId);
                                goodsSpecsEntities.forEach(goodsSpecsEntity -> {
                                    GoodsSpecs goodsSpecsModel = (GoodsSpecs) goodsSpecsEntityMapper.mapForRead(goodsSpecsEntity);
                                    goodsSpecs.add((GoodsSpecs) additionalAttributesInitializer.initializeAdditionalAttributes(goodsSpecsModel, goodsSpecsEntity, goodsSpecsRepository, false));
                                });
                                return goodsSpecs;
                            }
                            return methodInvocation.proceed();
                        });
                        goodsMap.put(goodsId, new GoodsHolderImpl(goodsModel));
                    });
                    return id -> goodsMap.get(id);
                };
            }
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    private class GoodsHolderImpl implements GoodsHolder {

        private Goods goods;
        private Map<Long, GoodsSpecs> goodsSpecsMap;

        private GoodsHolderImpl(Goods goods) {
            this.goods = goods;
        }

        @Override
        public Goods get() {
            return goods;
        }

        @Override
        public GoodsSpecs goodsSpecs(Long id) {
            if (goodsSpecsMap == null) {
                goodsSpecsMap = Maps.newHashMap();
                goods.specs().forEach(goodsSpecs -> {
                    goodsSpecsMap.put(goodsSpecs.id(), goodsSpecs);
                });
            }
            return goodsSpecsMap.get(id);
        }

        @Override
        public Boolean emptySpecs(){
            return CollectionUtils.isEmpty(goods.specs());
        }
    }

}
