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
package io.scleropages.sentarum.promotion;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品促销折扣计算请求
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PromotionCalculateRequest {

    /**
     * 买家id
     */
    private Long buyerId;

    private ChannelConditionRule.ChannelType channelType;

    private Integer channelId;

    /**
     * 计算商品列表
     */
    private List<CalculatingGoodsSpecs> calculatingGoodsSpecs;


    @NotNull
    public Long getBuyerId() {
        return buyerId;
    }

    @NotNull
    public ChannelConditionRule.ChannelType getChannelType() {
        return channelType;
    }

    @NotNull
    public Integer getChannelId() {
        return channelId;
    }

    @NotEmpty
    public List<CalculatingGoodsSpecs> getCalculatingGoodsSpecs() {
        if (null == calculatingGoodsSpecs)
            calculatingGoodsSpecs = Lists.newArrayList();
        return calculatingGoodsSpecs;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setChannelType(ChannelConditionRule.ChannelType channelType) {
        this.channelType = channelType;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public void setCalculatingGoodsSpecs(List<CalculatingGoodsSpecs> calculatingGoodsSpecs) {
        this.calculatingGoodsSpecs = calculatingGoodsSpecs;
    }

    public static class CalculatingGoodsSpecs {

        /**
         * 商品规格id.
         */
        private Long goodsSpecsId;
        /**
         * 购买数量.
         */
        private Integer num;

        public CalculatingGoodsSpecs() {

        }

        public CalculatingGoodsSpecs(Long goodsSpecsId, Integer num) {
            this.goodsSpecsId = goodsSpecsId;
            this.num = num;
        }

        @NotNull
        public Long getGoodsSpecsId() {
            return goodsSpecsId;
        }

        @NotNull
        public Integer getNum() {
            return num;
        }

        public void setGoodsSpecsId(Long goodsSpecsId) {
            this.goodsSpecsId = goodsSpecsId;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

}
