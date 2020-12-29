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
package io.scleropages.sentarum.member.card;

import io.scleropages.sentarum.member.customer.model.Customer;
import io.scleropages.sentarum.member.hierarchy.model.MemberHierarchy;
import io.scleropages.sentarum.member.hierarchy.model.HierarchyLevel;

/**
 * represent card of member(levels in member hierarchy).
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface MemberCard {

    /**
     * id of this card.
     *
     * @return
     */
    Long id();

    /**
     * card no of this card.
     *
     * @return
     */
    String no();

    /**
     * owner of this card.
     *
     * @return
     */
    Customer customer();

    /**
     * associated customer hierarchy.
     *
     * @return
     */
    MemberHierarchy customerHierarchy();

    /**
     * level of customer hierarchy.
     *
     * @return
     */
    HierarchyLevel level();

    /**
     * type of this card.
     *
     * @return
     */
    CardType cardType();


    /**
     * enumerates of card type.
     */
    enum CardType {
        /**
         * 实体卡.
         */
        PHYSICAL_CARD,
        /**
         * 虚拟卡（电子卡）.
         */
        VIRTUAL_CARD
    }
}
