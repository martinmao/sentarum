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
package io.scleropages.sentarum.promotion.activity.model;

import io.scleropages.sentarum.promotion.goods.AdditionalAttributesProvider;
import io.scleropages.sentarum.promotion.rule.model.Rule;

import java.util.Date;
import java.util.List;

/**
 * The root domain class defined base information about activity in promotion.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Activity extends AdditionalAttributesProvider {

    /**
     * unique id of this activity
     *
     * @return
     */
    Long id();

    /**
     * name of this activity
     *
     * @return
     */
    String name();

    /**
     * tag of this activity.
     *
     * @return
     */
    String tag();

    /**
     * description of this activity.
     *
     * @return
     */
    String description();

    /**
     * start time of this activity.
     *
     * @return
     */
    Date startTime();

    /**
     * end time of this activity.
     *
     * @return
     */
    Date endTime();

    /**
     * status of this activity.
     * <pre>
     *     example:
     *                                        |---------------------|
     *                                        |                     |
     *                                        V                     |
     *     SAVED->SUBMIT->APPROVING->READY->RUNNING->FINISHED       |
     *                        |               |                     |
     *                        |->REJECT       |->SUSPEND->RUNNING---|
     *                                        |
     *                                        |->TERMINATE
     * </pre>
     *
     * @return
     */
    Integer status();

    /**
     * participation condition rules of this activity.
     *
     * @return
     */
    List<Rule> conditionRules();


    /**
     * promotional rule of this activity.
     *
     * @return
     */
    Rule promotionalRule();


    /**
     * a group of promotional goods source of this activity.
     * 活动可关联多个商品来源，例如多品牌、多品类、多店、单店促销场景.
     * 但不允许跨维度组合。即一组 {@link ActivityClassifiedGoodsSource} 类型必须相同.
     * 如要设置到商品，sku维度，则需关联（仅一个） {@link ActivityDetailedGoodsSource}.
     *
     * @return
     */
    List<ActivityGoodsSource> goodsSource();


    /**
     * order of this activity.
     *
     * @return
     */
    default Float order(){
        //default to lowest precedence value.
        return Float.MAX_VALUE;
    }
}
