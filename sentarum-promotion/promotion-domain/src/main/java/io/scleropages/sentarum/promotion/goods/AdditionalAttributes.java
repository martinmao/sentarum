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
package io.scleropages.sentarum.promotion.goods;

import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.goods.model.GoodsSource;
import io.scleropages.sentarum.promotion.goods.model.GoodsSpecs;

import java.util.Map;

/**
 * additional attributes of {@link GoodsSource} , {@link Goods} , {@link GoodsSpecs}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface AdditionalAttributes {

    /**
     * set a attribute to this attributes.
     *
     * @param name  name of this attribute
     * @param value value of this attribute
     * @param force true if replaces when exists. or false do nothing.
     */
    AdditionalAttributes setAttribute(String name, Object value, boolean force);

    /**
     * remove attribute from this attributes.
     *
     * @param name name of this attribute
     */
    AdditionalAttributes removeAttribute(String name);

    /**
     * get attribute from this attributes.
     *
     * @param name
     * @return
     */
    Object getAttribute(String name);

    /**
     * get all attributes from this attributes.
     *
     * @return
     */
    Map<String, Object> getAttributes();

    /**
     * return true if given attribute exists.
     *
     * @param name
     * @return
     */
    boolean hasAttribute(String name);

    /**
     * save changes of this attributes.
     */
    default void save() {
        throw new IllegalStateException("not implementation or not initialized.");
    }
}
