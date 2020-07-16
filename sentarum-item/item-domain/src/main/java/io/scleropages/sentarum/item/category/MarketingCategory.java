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
package io.scleropages.sentarum.item.category;

import java.util.List;

/**
 * 营销类目（前台类目），与营销（销售渠道，季节，促销）相关.
 * 类目结构经常根据运营需求进行灵活调整，其会映射到多个管理类目 {@link StandardCategory}，通过管理类目间接关联商品（产品）
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface MarketingCategory extends Category {

    /**
     * 营销类目直接关联的管理类目（硬链接).
     *
     * @return
     */
    List<StandardCategory> directCategories();


    /**
     * 营销类目基于条件链接到管理类目 (软连接).
     *
     * @return
     */
    List<CategoryLink> categoryLinks();

}
