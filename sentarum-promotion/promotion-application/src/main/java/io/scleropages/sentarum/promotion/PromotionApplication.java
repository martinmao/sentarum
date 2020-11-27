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
package io.scleropages.sentarum.promotion;

import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.ItemApi;
import org.apache.dubbo.config.annotation.Reference;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * application layer for promotion.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@BizError("90")
public class PromotionApplication implements InitializingBean {

    @Reference
    private ItemApi itemApi;

    public void computePromotion() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(JsonMapper2.toJson(itemApi.findSkuPage(Maps.newHashMap(), Pageable.unpaged())));
    }
}
