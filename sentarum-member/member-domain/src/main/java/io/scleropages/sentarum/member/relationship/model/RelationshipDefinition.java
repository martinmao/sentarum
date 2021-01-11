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
package io.scleropages.sentarum.member.relationship.model;

/**
 * this class defined relationships of nodes ({@link Node}).
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface RelationshipDefinition<S extends Class<? extends Node>, T extends Class<? extends Node>> {

    /**
     * id of this relationship definition.
     *
     * @return
     */
    Long id();

    /**
     * name of this relationship definition.
     *
     * @return
     */
    String name();

    /**
     * description of this relationship definition.
     *
     * @return
     */
    String description();


    /**
     * direction of this relationship(relationships always have a direction).
     *
     * @return
     */
    Direction direction();


    /**
     * enumerate relationship direction.
     */
    enum Direction {
        /**
         * incoming relationship.
         */
        IN_COMING,
        /**
         * outgoing relationship
         */
        OUT_GOING,
    }
}
