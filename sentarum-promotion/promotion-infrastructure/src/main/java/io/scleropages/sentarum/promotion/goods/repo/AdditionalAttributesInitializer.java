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
package io.scleropages.sentarum.promotion.goods.repo;

import io.scleropages.sentarum.promotion.goods.AdditionalAttributes;
import io.scleropages.sentarum.promotion.goods.AdditionalAttributesProvider;
import org.aopalliance.aop.Advice;

import java.util.Map;

/**
 * initializer of {@link AdditionalAttributesProvider}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface AdditionalAttributesInitializer {


    /**
     * initialize {@link AdditionalAttributesProvider#additionalAttributes()} from given provider and returned proxy object.
     *
     * @param provider       provider of additional attributes.
     * @param entity         entity for initial attributes reading.
     * @param savingCallback callback for attributes saving.
     * @param forceProxy     true if use cglib
     * @param readOnly       true if want to create a read only attributes.
     * @param beforeAdvices  advices for method invocation.
     * @return
     */
    AdditionalAttributesProvider initializeAdditionalAttributes(AdditionalAttributesProvider provider, Object entity, AdditionalAttributesSavingCallback savingCallback, boolean forceProxy, boolean readOnly, Advice... beforeAdvices);


    /**
     * call back interface for {@link AdditionalAttributes} saving.
     */
    interface AdditionalAttributesSavingCallback<M extends AdditionalAttributesProvider, T> {

        /**
         * save additional attributes map.
         *
         * @param provider
         * @param additionalAttributesMap
         */
        void save(M provider, Map<String, Object> additionalAttributesMap);

        /**
         * read additional attributes map from entity.
         *
         * @param entity
         * @return
         */
        Map<String, Object> additionalAttributesMap(T entity);
    }
}
