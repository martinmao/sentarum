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

import io.scleropages.sentarum.core.model.primitive.Amount;

/**
 * 赠品,统一描述为单规格商品，如存在多规格，做不同 gift 处理.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Gift {

    /**
     * 本地赠品id
     */
    private Long nativeId;
    /**
     * 单用户赠送数量.
     */
    private Integer userNum;
    /**
     * 可赠总数.
     */
    private Integer num;
    /**
     * 补差价，例如：满100元+5元送牙刷
     */
    private Amount adjustFee;
    /**
     * 赠品单价
     */
    private Amount price;


    public Long getNativeId() {
        return nativeId;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public Integer getNum() {
        return num;
    }

    public Amount getAdjustFee() {
        return adjustFee;
    }

    public Amount getPrice() {
        return price;
    }

    public void setNativeId(Long nativeId) {
        this.nativeId = nativeId;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setAdjustFee(Amount adjustFee) {
        this.adjustFee = adjustFee;
    }

    public void setPrice(Amount price) {
        this.price = price;
    }
}
