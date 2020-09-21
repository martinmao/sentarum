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
package io.scleropages.sentarum.promotion.rule.model.impl;

import io.scleropages.sentarum.promotion.rule.model.AbstractRule;

import java.util.List;

/**
 * 赠品促销规则，购买商品后可获得任意个数的赠品
 * <pre>
 *  majorNum：设置需要购买多少件主商品才可获得赠送.
 *  giftNum：设置赠品赠送的数量，全部赠送或该值指定数量的赠品.例如：-1=全部赠送，1=任选一件，2=任选两件
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class GiftRule extends AbstractRule {

    private List<Long> giftIds;

    private Integer majorNum;

    private Integer giftNum;

    /**
     * return list of gift ids.
     *
     * @return
     */
    public List<Long> getGiftIds() {
        return giftIds;
    }

    /**
     * set list of gift ids.
     *
     * @param giftIds
     */
    public void setGiftIds(List<Long> giftIds) {
        this.giftIds = giftIds;
    }

    /**
     * return num of major item count will fire this rule.
     *
     * @return
     */
    public Integer getMajorNum() {
        return majorNum;
    }

    /**
     * set num of major item count will fire this rule.
     *
     * @param majorNum
     */
    public void setMajorNum(Integer majorNum) {
        this.majorNum = majorNum;
    }

    /**
     * return num of gift for sent.
     *
     * @return
     */
    public Integer getGiftNum() {
        return giftNum;
    }

    /**
     * set num of gift for sent.
     *
     * @param giftNum
     */
    public void setGiftNum(Integer giftNum) {
        this.giftNum = giftNum;
    }
}
