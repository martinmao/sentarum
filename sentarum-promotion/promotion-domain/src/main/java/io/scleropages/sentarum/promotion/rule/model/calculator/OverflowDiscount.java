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
package io.scleropages.sentarum.promotion.rule.model.calculator;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.core.model.primitive.Discount;
import io.scleropages.sentarum.core.model.primitive.Discount.DiscountType;
import io.scleropages.sentarum.promotion.goods.model.DetailedGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule.OverflowDiscountType;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorInitializableRuleDetailedConfigure;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 满减促销规则 {@link OverflowDiscountRule} 明细：
 * <pre>
 *      条件包含两类：
 *          满金额 {@link #overflowFee}：匹配 {@link OverflowDiscountType#FIXED_FEE_OVERFLOW} || {@link OverflowDiscountType#STEPPED_FEE_OVERFLOW}
 *          满件数 {@link #overflowNum}：匹配 {@link OverflowDiscountType#FIXED_GOODS_NUM_OVERFLOW} || {@link OverflowDiscountType#STEPPED_GOODS_NUM_OVERFLOW}
 *      结果包含两类：
 *          折扣 {@link #overflowDiscount}：优先，无类型区分，该值为空时代表发赠品.
 *          发赠品 {@link #userGiftNum}：{@link #overflowDiscount} 为空时有效.
 * </pre>
 * <p>
 * NOTE:结构存储上没有定义专用表结构，将其信息作为扩展属性落到 {@link DetailedGoodsSource} 上.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class OverflowDiscount implements CalculatorInitializableRuleDetailedConfigure<OverflowDiscountRule> {

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
     * 满赠商品时有效(overflowDiscount 为空时)：用户可选赠品总数，-1为全选
     */
    private Integer userGiftNum;


    /**
     * 检查规则（当前设置是否可落到给定规则上）
     *
     * @param overflowDiscountRule
     */
    @Override
    public void assertConfigure(OverflowDiscountRule overflowDiscountRule) {

        Assert.notNull(overflowDiscountRule, "overflowDiscountRule must not be null.");
        OverflowDiscountType overflowDiscountType = overflowDiscountRule.getOverflowDiscountType();
        Assert.notNull(overflowDiscountType, "overflowDiscountType must not be null.");

        switch (overflowDiscountType) {
            case FIXED_FEE_OVERFLOW:
                assertFeeOverflowDiscountType(overflowDiscountType);
                break;
            case STEPPED_FEE_OVERFLOW:
                assertFeeOverflowDiscountType(overflowDiscountType);
                break;
            case FIXED_GOODS_NUM_OVERFLOW:
                assertNumOverflowDiscountType(overflowDiscountType);
                break;
            case STEPPED_GOODS_NUM_OVERFLOW:
                assertNumOverflowDiscountType(overflowDiscountType);
                break;
        }
    }

    private void assertNumOverflowDiscountType(OverflowDiscountType overflowDiscountType) {
        Assert.notNull(overflowNum, "overflow num is required for: " + overflowDiscountType);
        Assert.isTrue(overflowNum > 0, "userSelectNum must be greater than 0.");
    }

    private void assertFeeOverflowDiscountType(OverflowDiscountType overflowDiscountType) {
        Assert.notNull(overflowFee, "overflow fee is required for: " + overflowDiscountType);
        if (null != overflowDiscount) {
            Assert.isTrue(
                    Objects.equals(overflowDiscount.getDiscountType(), DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE)
                            || Objects.equals(overflowDiscount.getDiscountType(), DiscountType.DISCOUNT_WITHOUT_ORIGINAL_PRICE),
                    "discount type must be DECREASE_WITHOUT_ORIGINAL_PRICE or DISCOUNT_WITHOUT_ORIGINAL_PRICE for: " + overflowDiscountType);
        } else {
            Assert.notNull(userGiftNum, "overflowDiscount or userGiftNum must specified at least one.");
            Assert.isTrue(userGiftNum == -1 || userGiftNum > 0, "userGiftNum must be '-1' or greater than 0.");
        }
    }


    public Discount getOverflowDiscount() {
        return overflowDiscount;
    }

    public Integer getOverflowNum() {
        return overflowNum;
    }

    public Amount getOverflowFee() {
        return overflowFee;
    }

    public Integer getUserGiftNum() {
        return userGiftNum;
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

    public void setUserGiftNum(Integer userGiftNum) {
        this.userGiftNum = userGiftNum;
    }

    private CalculatorGoodsSource calculatorGoodsSource;

    @Override
    public void setCalculatorGoodsSource(CalculatorGoodsSource calculatorGoodsSource) {
        this.calculatorGoodsSource = calculatorGoodsSource;
    }

    @Override
    public String comment() {
        return "满减促销明细规则";
    }

    public CalculatorGoodsSource getCalculatorGoodsSource() {
        return calculatorGoodsSource;
    }
}
