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
package io.scleropages.sentarum.promotion.rule.model.promotion;

import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.core.model.primitive.Discount.DiscountType;
import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.goods.model.impl.AbstractDetailedGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.AbstractEvaluatorRule;

import java.util.List;

/**
 * 商品折扣规则，可对一组商品来源（来自关联的 {@link Activity#goodsSource()}）设置折扣. 按级别划分为：
 * <pre>
 *  当商品来源为 {@link BrandGoodsSource} 时，可对所有关联品牌商品设置统一折扣({@link #discount}),不支持 {@link DiscountType#OVERRIDE_AMOUNT}.
 *  当商品来源为 {@link CategoryGoodsSource} 时，可对所有关联品类下商品设置统一折扣({@link #discount}),不支持 {@link DiscountType#OVERRIDE_AMOUNT}.
 *  当商品来源为 {@link SellerGoodsSource} 时， 可对所有关联商家下商品设置统一折扣({@link #discount}),不支持 {@link DiscountType#OVERRIDE_AMOUNT}.
 *
 *  多品牌、多品类、多店联合促销场景下均摊规则为：品牌A卖出商品原价总和/(品牌A卖出商品原价总和+品牌B卖出商品原价总和)*总折扣金额=品牌A均摊金额
 *  单品牌，单品类，单店促销场景下：
 *
 *  当商品来源为一个 {@link AbstractDetailedGoodsSource} 时，则可将规则设置到各个商品以及各个规格.
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class GoodsDiscountRule extends AbstractEvaluatorRule {

    /**
     * 统一折扣.
     */
    private Discount discount;

    /**
     * 商品维度折扣
     */
    private List<GoodsDiscount> goodsDiscounts;

    public Discount getDiscount() {
        return discount;
    }

    public List<GoodsDiscount> getGoodsDiscounts() {
        return goodsDiscounts;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setGoodsDiscounts(List<GoodsDiscount> goodsDiscounts) {
        this.goodsDiscounts = goodsDiscounts;
    }

    /**
     * 商品折扣
     */
    public static class GoodsDiscount {

        /**
         * 本地商品id.
         */
        private Long nativeGoodsId;

        /**
         * 商品折扣.
         */
        private Discount discount;

        /**
         * 规格维度折扣.
         */
        private List<GoodsSpecsDiscount> goodsSpecsDiscounts;


        public Long getNativeGoodsId() {
            return nativeGoodsId;
        }

        public Discount getDiscount() {
            return discount;
        }

        public List<GoodsSpecsDiscount> getGoodsSpecsDiscounts() {
            return goodsSpecsDiscounts;
        }

        public void setNativeGoodsId(Long nativeGoodsId) {
            this.nativeGoodsId = nativeGoodsId;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        public void setGoodsSpecsDiscounts(List<GoodsSpecsDiscount> goodsSpecsDiscounts) {
            this.goodsSpecsDiscounts = goodsSpecsDiscounts;
        }
    }

    /**
     * 商品规格折扣
     */
    public static class GoodsSpecsDiscount {

        /**
         * 本地商品规格id.
         */
        private Long nativeGoodsSpecsId;
        /**
         * 规格折扣.
         */
        private Discount discount;

        public Long getNativeGoodsSpecsId() {
            return nativeGoodsSpecsId;
        }

        public Discount getDiscount() {
            return discount;
        }

        public void setNativeGoodsSpecsId(Long nativeGoodsSpecsId) {
            this.nativeGoodsSpecsId = nativeGoodsSpecsId;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }
    }

}
