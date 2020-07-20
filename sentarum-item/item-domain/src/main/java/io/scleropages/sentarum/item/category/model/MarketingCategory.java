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

import java.util.List;

/**
 * 营销类目（前台类目），与营销（销售渠道，季节，促销）相关.
 * 类目结构经常根据运营需求进行灵活调整，其会映射到多个管理类目 {@link StandardCategory}，通过管理类目间接关联商品（产品）.
 * 目前提供两种映射方式：
 * <pre>
 *  直接关联，通过关联关系将管理类目挂靠到营销类目 {@link io.scleropages.sentarum.item.category.model.StandardCategoryLink.LinkType#DIRECT}
 *  软关联，通过对 {@link StandardCategory} 设置检索条件，根据检索条件运行时动态确认关联关系。{@link io.scleropages.sentarum.item.category.model.StandardCategoryLink.LinkType#SOFT}
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface MarketingCategory extends Category {


    /**
     * 关联的一组管理类目链接.
     * @return
     */
    List<StandardCategoryLink> standardCategoryLinks();
}
