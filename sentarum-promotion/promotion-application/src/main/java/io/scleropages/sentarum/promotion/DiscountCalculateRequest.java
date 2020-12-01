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

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 商品促销折扣计算请求
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class DiscountCalculateRequest {

    /**
     * 买家id
     */
    private Long buyerId;

    /**
     * 计算商品列表
     */
    private List<CalculatingGoods> calculatingGoods;


    @NotNull
    public Long getBuyerId() {
        return buyerId;
    }

    @NotEmpty
    public List<CalculatingGoods> getCalculatingGoods() {
        return calculatingGoods;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setCalculatingGoods(List<CalculatingGoods> calculatingGoods) {
        this.calculatingGoods = calculatingGoods;
    }

    public static class CalculatingGoods {

        /**
         * 商品id.
         */
        private Long goodsId;
        /**
         * 商品规格id.
         */
        private Long goodsSpecsId;
        /**
         * 购买数量.
         */
        private Integer num;


        @NotNull
        public Long getGoodsId() {
            return goodsId;
        }

        public Long getGoodsSpecsId() {
            return goodsSpecsId;
        }

        public Integer getNum() {
            return num;
        }

        public void setGoodsId(Long goodsId) {
            this.goodsId = goodsId;
        }

        public void setGoodsSpecsId(Long goodsSpecsId) {
            this.goodsSpecsId = goodsSpecsId;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

}
