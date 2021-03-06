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
package io.scleropages.sentarum.member.membership.model;

/**
 * represent edge from source hierarchy level to target.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface HierarchyLevelEdge {

    /**
     * id of this edge.
     *
     * @return
     */
    Long id();

    /**
     * edge source hierarchy level from.
     *
     * @return
     */
    HierarchyLevel from();

    /**
     * edge target hierarchy level to.
     *
     * @return
     */
    HierarchyLevel to();

    /**
     * score of this edge.
     *
     * @return
     */
    Integer score();

    /**
     * associated membership hierarchy of this edge.
     *
     * @return
     */
    MembershipHierarchy membershipHierarchy();
}
