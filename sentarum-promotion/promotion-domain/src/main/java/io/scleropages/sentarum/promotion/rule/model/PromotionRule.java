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
package io.scleropages.sentarum.promotion.rule.model;

import io.scleropages.sentarum.promotion.activity.model.Activity;

/**
 * Base class of promotion activity rule.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PromotionRule {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 关联的活动
     *
     * @return
     */
    Activity activity();


    /**
     * return true if promotion request is matches and handled by current rule.
     *
     * @return
     */
    boolean evaluate();
}
