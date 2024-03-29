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
package io.scleropages.sentarum.member.core.model;

import io.scleropages.sentarum.core.model.primitive.Address;
import io.scleropages.sentarum.core.model.primitive.Tel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * represent a conceptual customer.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Customer {

    /**
     * id of this customer.
     *
     * @return
     */
    Long id();

    /**
     * name of this customer.
     *
     * @return
     */
    String name();

    /**
     * nickname of this customer.
     *
     * @return
     */
    String nickname();

    /**
     * gender of this customer.
     *
     * @return
     */
    Gender gender();

    /**
     * avatar of this customer.
     *
     * @return
     */
    String avatar();

    /**
     * tel of this customer.
     *
     * @return
     */
    Tel tel();

    /**
     * id of we chat.
     *
     * @return
     */
    String wx();

    /**
     * addresses of this customer.
     *
     * @return
     */
    List<Address> addresses();


    enum Gender {

        UNKNOWN(0, "未知"),
        MALE(1, "男性"),
        FEMALE(2, "女性");

        private final int ordinal;
        private final String tag;

        Gender(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }


        private static final Map<String, Gender> nameMappings = new HashMap<>();
        private static final Map<Integer, Gender> ordinalMappings = new HashMap<>();

        static {
            for (Gender gender : Gender.values()) {
                nameMappings.put(gender.name(), gender);
                ordinalMappings.put(gender.getOrdinal(), gender);
            }
        }


        public static Gender getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static Gender getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}
