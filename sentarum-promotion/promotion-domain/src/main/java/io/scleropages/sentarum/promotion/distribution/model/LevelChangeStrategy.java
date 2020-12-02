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
package io.scleropages.sentarum.promotion.distribution.model;

/**
 * 分销级别变更策略
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface LevelChangeStrategy {

    /**
     * change result of this strategy.
     *
     * @return
     */
    ChangeResult changeResult();


    /**
     * implementation this strategy method to calculate given distributor level meet the change conditions.
     *
     * @param levelConnecting
     * @param distributor
     * @return return true if given distributor the level changed.
     */
    Boolean calculate(HierarchyLevelConnecting levelConnecting, Distributor distributor);


    /**
     * change result definition
     */
    enum ChangeResult {
        Upgrade, Degrade
    }

}
