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

import java.util.List;

/**
 * SKU逻辑组，基于售卖需求，将一组sku打包为一个sku
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CombineSku extends Sku {

    /**
     * 包含的一组 sku 条目.
     *
     * @return
     */
    List<CombineSkuEntry> entries();
}
