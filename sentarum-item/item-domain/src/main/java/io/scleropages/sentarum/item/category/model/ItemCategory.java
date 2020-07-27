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
package io.scleropages.sentarum.item.category.model;

import io.scleropages.sentarum.item.model.Item;

/**
 * 与商家相关的商品的类目，例如：
 * <pre>
 *     商家自定义的商品类目,例如店铺商品类目
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ItemCategory extends Category {

    /**
     * 商家类型
     *
     * @return
     */
    Item.SellerType sellerType();

    /**
     * 商家唯一标识（商业综合体标识）
     *
     * @return
     */
    Long sellerUnionId();

    /**
     * 商家唯一标识 (商业综合体内具体销售场所，例如店铺)
     *
     * @return
     */
    Long sellerId();

}
