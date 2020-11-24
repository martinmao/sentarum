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
package io.scleropages.sentarum.promotion.rule.model.evaluator;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.core.model.primitive.Discount.DiscountType;
import io.scleropages.sentarum.promotion.rule.model.evaluator.OverflowDiscountRule.OverflowDiscountType;
import io.scleropages.sentarum.promotion.rule.model.evaluator.goods.EvaluatorGoodsSource;

/**
 * 满减促销规则 {@link OverflowDiscountRule} 明细.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OverflowDiscount {

    /**
     * 满减折扣，{@link OverflowDiscountType#FIXED_FEE_OVERFLOW} || {@link OverflowDiscountType#STEPPED_FEE_OVERFLOW} 时有效.<br>
     * 折扣类型仅支持 {@link DiscountType#DISCOUNT_WITHOUT_ORIGINAL_PRICE} || {@link DiscountType#DECREASE_WITHOUT_ORIGINAL_PRICE}
     */
    private Discount overflowDiscount;
    /**
     * 满件数 {@link OverflowDiscountType#FIXED_GOODS_NUM_OVERFLOW} || {@link OverflowDiscountType#STEPPED_GOODS_NUM_OVERFLOW} 时有效.
     */
    private Integer overflowNum;
    /**
     * 满金额 {@link OverflowDiscountType#FIXED_FEE_OVERFLOW} || {@link OverflowDiscountType#STEPPED_FEE_OVERFLOW} 时有效.
     */
    private Amount overflowFee;
    /**
     * 赠品来源.
     */
    private EvaluatorGoodsSource giftSource;
    /**
     * 满赠商品时有效：用户可选赠品总数，-1为全选
     */
    private Integer userSelectNum;

    public Discount getOverflowDiscount() {
        return overflowDiscount;
    }

    public Integer getOverflowNum() {
        return overflowNum;
    }

    public Amount getOverflowFee() {
        return overflowFee;
    }

    public EvaluatorGoodsSource getGiftSource() {
        return giftSource;
    }

    public Integer getUserSelectNum() {
        return userSelectNum;
    }

    public void setOverflowDiscount(Discount overflowDiscount) {
        this.overflowDiscount = overflowDiscount;
    }

    public void setOverflowNum(Integer overflowNum) {
        this.overflowNum = overflowNum;
    }

    public void setOverflowFee(Amount overflowFee) {
        this.overflowFee = overflowFee;
    }

    public void setGiftSource(EvaluatorGoodsSource giftSource) {
        this.giftSource = giftSource;
    }

    public void setUserSelectNum(Integer userSelectNum) {
        this.userSelectNum = userSelectNum;
    }
}
