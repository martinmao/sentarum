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
 * 分销体系层级连线,从一个等级变更到另外一个等级
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface HierarchyLevelConnecting {

    /**
     * id of hierarchy level
     *
     * @return
     */
    Long id();

    /**
     * associated level upgrade/degrade source from.
     *
     * @return
     */
    LevelDefinition changeFrom();

    /**
     * associated distribution hierarchy
     *
     * @return
     */
    DistributionHierarchy distributionHierarchy();


    /**
     * associated level upgrade/degrade target to.
     *
     * @return
     */
    LevelDefinition changeTo();


    /**
     * defined strategy of level changes from {@link #changeFrom()} to {@link #changeTo()}
     *
     * @return
     */
    LevelChangeStrategy levelChangeStrategy();


}
