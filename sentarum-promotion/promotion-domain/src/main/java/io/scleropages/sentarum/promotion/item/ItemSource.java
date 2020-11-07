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
package io.scleropages.sentarum.promotion.item;

/**
 * represent a item source.
 * 商品来源是一个配置接口，其将商品来源的配置（某一品牌，某一类目，某一三方平台，某些选定商品等）与某一业务进行绑定（例如促销），
 * 具体读取工作由策略接口 {@link ItemSourceReader} 来实现，即reader接口根据来源的配置进行商品的读取.
 * 这在很多场景下可以使用，例如某一业务实体与商品的关系建模往往不是固定的，而是通过配置后形成的。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ItemSource {

    /**
     * id of this item source.
     *
     * @return
     */
    Long id();

    /**
     * reader of this item source.
     *
     * @return
     */
    ItemSourceReader itemSourceReader();
}
