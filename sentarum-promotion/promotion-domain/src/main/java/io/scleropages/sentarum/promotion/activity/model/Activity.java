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

import io.scleropages.sentarum.promotion.activity.model.impl.ActivityNativeGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.Rule;

import java.util.Date;
import java.util.List;

/**
 * The root domain class is defined base information about activity in promotion.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Activity {

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
     * promotional goods source of this activity.
     *
     * @return
     */
    List<ActivityNativeGoodsSource> goodsSource();
}
