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
package io.scleropages.sentarum.promotion.rule.model.condition;

import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.SellerUserLevelCondition;

/**
 * 促销参与用户的店铺会员级别设置
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SellerUserLevelConditionRule extends UserLevelConditionRule {

    /**
     * 商家类型
     *
     * @return
     */
    private Integer sellerType;

    /**
     * 商家唯一标识（商业综合体标识）
     *
     * @return
     */
    private Long sellerUnionId;

    /**
     * 商家唯一标识 (商业综合体内具体销售场所，例如店铺)
     *
     * @return
     */
    private Long sellerId;


    public Integer getSellerType() {
        return sellerType;
    }

    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public void setSellerUnionId(Long sellerUnionId) {
        this.sellerUnionId = sellerUnionId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    protected Integer defaultRuleInvocationImplementation() {
        return SellerUserLevelCondition.ID;
    }
}
