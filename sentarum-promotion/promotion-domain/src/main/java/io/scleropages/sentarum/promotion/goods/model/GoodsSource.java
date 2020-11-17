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
package io.scleropages.sentarum.promotion.goods.model;

import io.scleropages.sentarum.promotion.goods.AdditionalAttributes;

/**
 * represent a goods source.
 * 商品来源是一个关于商品层级的模型定义，其将业务实体与商品几种可能的关联维度进行抽象并划分两个维度.
 * <pre>
 *  {@link ClassifiedGoodsSource}：单层模型，例如按品牌、品类、卖家等这类商品归属统一抽象. 并不具有商品明细的关联关系.
 *  {@link DetailedGoodsSource}：两层模型，其会关联到具体的商品明细.
 *  高低维度并非具有两层上下级关系，而仅是一个标识，实际与目标实体建模关联二选一，要么关联到高维度，要么关联到明细这一低维度.
 *
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface GoodsSource {

    /**
     * id of this goods source.
     *
     * @return
     */
    Long id();

    /**
     * type of this goods source.
     *
     * @return
     */
    Integer goodsSourceType();

    /**
     * 对商品来源的说明.
     *
     * @return
     */
    String comment();

    /**
     * 与此商品来源关联的业务类型.
     *
     * @return
     */
    Integer bizType();

    /**
     * 与此商品来源关联的业务模型标识.
     *
     * @return
     */
    Long bizId();

    /**
     * additional attributes of this goods source.
     *
     * @return
     */
    default AdditionalAttributes additionalAttributes() {
        throw new IllegalStateException("not initialized.");
    }
}
