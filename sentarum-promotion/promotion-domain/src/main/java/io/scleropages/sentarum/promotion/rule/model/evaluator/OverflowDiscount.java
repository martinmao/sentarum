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
import org.springframework.util.Assert;

import java.util.Objects;

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
     * 满赠商品时有效：用户可选赠品总数，-1为全选
     */
    private Integer userSelectNum;


    public void assertValid(OverflowDiscountRule overflowDiscountRule) {

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
                    Objects.equals(overflowDiscount, DiscountType.DECREASE_WITHOUT_ORIGINAL_PRICE)
                            || Objects.equals(overflowDiscount, DiscountType.DISCOUNT_WITHOUT_ORIGINAL_PRICE),
                    "discount type must be DECREASE_WITHOUT_ORIGINAL_PRICE or DISCOUNT_WITHOUT_ORIGINAL_PRICE for: " + overflowDiscountType);
        } else {
            Assert.notNull(userSelectNum, "overflowDiscount or userSelectNum must specified at least one.");
            Assert.isTrue(userSelectNum == -1 || userSelectNum > 0, "userSelectNum must be '-1' or greater than 0.");
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

    public void setUserSelectNum(Integer userSelectNum) {
        this.userSelectNum = userSelectNum;
    }
}
