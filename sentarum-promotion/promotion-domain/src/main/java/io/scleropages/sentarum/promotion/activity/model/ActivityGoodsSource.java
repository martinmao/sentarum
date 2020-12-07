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
package io.scleropages.sentarum.promotion.activity.model;

import io.scleropages.sentarum.promotion.goods.model.GoodsSource;

/**
 * represent a activity goods source.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ActivityGoodsSource extends GoodsSource {


    int BIZ_TYPE_OF_ACTIVITY = 1;


    // goods source type definitions.
    /**
     * 全部
     */
    int CLASSIFIED_GOODS_SOURCE_TYPE_ALL = 0;
    /**
     * 品牌分类
     */
    int CLASSIFIED_GOODS_SOURCE_TYPE_BRAND = 1;
    /**
     * 品类分类
     */
    int CLASSIFIED_GOODS_SOURCE_TYPE_CATEGORY = 2;
    /**
     * 商家分类
     */
    int CLASSIFIED_GOODS_SOURCE_TYPE_SELLER = 3;

    /**
     * 商品明细
     */
    int DETAILED_GOODS_SOURCE_TYPE = 4;

    /**
     * associated activity of this goods source.
     *
     * @return
     */
    Activity activity();
}
