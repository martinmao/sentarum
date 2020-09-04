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

package io.scleropages.sentarum.item.core.model;


import java.math.BigDecimal;
import java.util.Map;

/**
 * SKU逻辑组 {@link CombineSku}的 条目，每个条目确定目标sku、顺序、数量等销售信息.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CombineSkuEntry {


    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 目标 sku
     *
     * @return
     */
    Sku sku();

    /**
     * 目标 sku 顺序
     *
     * @return
     */
    Float order();

    /**
     * 目标 sku 数量
     *
     * @return
     */
    Integer num();

    /**
     * 目标 sku 价格
     *
     * @return
     */
    BigDecimal salesPrice();

    /**
     * 可选的其他属性
     *
     * @return
     */
    Map<String, Object> additionalAttributes();
}