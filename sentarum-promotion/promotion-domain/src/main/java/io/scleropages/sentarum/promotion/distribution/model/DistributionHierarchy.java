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

import java.util.List;

/**
 * 分销体系
 * <p>
 * 分销体系源于一种营销策略，随着社会发展及市场趋于饱和，用户难以触达、流量贵、资金流等问题凸显.
 * 而平台将广告、渠道营销等投入直接让利给终端用户，通过一系列的激励策略使其通过推荐、引导其圈子内用户进行裂变、购买等行为获得收益的营销手段.
 * 受限于法律相关规定分销级别一般在三级以内
 * <p>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface DistributionHierarchy {


    /**
     * 唯一标识
     *
     * @return
     */
    Long id();


    /**
     * distribution levels definition of this hierarchy.
     *
     * @return
     */
    List<HierarchyLevelConnecting> hierarchyLevels();

}
