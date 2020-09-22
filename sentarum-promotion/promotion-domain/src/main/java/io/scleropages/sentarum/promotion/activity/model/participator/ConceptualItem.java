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
package io.scleropages.sentarum.promotion.activity.model.participator;

import java.util.List;

/**
 * a concept of item in promotion.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ConceptualItem {

    /**
     * id of item.
     *
     * @return
     */
    Long id();

    /**
     * name of item.
     *
     * @return
     */
    String name();


    /**
     * conceptual sku list of this item.
     * 该限定并非必须的，只有活动商品粒度细分到具体的sku才需设置.
     *
     * @return
     */
    List<ConceptualSku> skuList();
}