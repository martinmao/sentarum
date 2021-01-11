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

import java.util.List;

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
     * tel of this customer.
     *
     * @return
     */
    Tel tel();

    /**
     * addresses of this customer.
     *
     * @return
     */
    List<Address> addresses();
}
