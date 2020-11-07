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

import io.scleropages.sentarum.promotion.activity.model.Activity;

import java.util.List;

/**
 * represent a conceptual item in promotion activity.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface PromotionItem {

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
     * total num of item in promotion.可做促销库存
     *
     * @return
     */
    Integer totalNum();


    /**
     * num of item in promotion per user.可做促销用户限购
     *
     * @return
     */
    Integer userNum();


    /**
     * associated activity.
     *
     * @return
     */
    Activity activity();


    /**
     * conceptual sku list of this item.
     * 该限定并非必须的，只有活动商品粒度细分到具体的sku才需设置.
     *
     * @return
     */
    List<PromotionItemSku> skuList();
}