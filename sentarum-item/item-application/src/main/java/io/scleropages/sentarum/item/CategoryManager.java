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
package io.scleropages.sentarum.item;

import io.scleropages.sentarum.item.category.model.impl.StandardCategoryModel;
import io.scleropages.sentarum.item.category.repo.CategoryPropertyRepository;
import io.scleropages.sentarum.item.category.repo.MarketingCategoryRepository;
import io.scleropages.sentarum.item.category.repo.StandardCategoryLinkRepository;
import io.scleropages.sentarum.item.category.repo.StandardCategoryRepository;
import org.scleropages.crud.exception.BizError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 类目通用（原子）功能管理器.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("20")
public class CategoryManager {

    private CategoryPropertyRepository categoryPropertyRepository;
    private StandardCategoryRepository standardCategoryRepository;
    private MarketingCategoryRepository marketingCategoryRepository;
    private StandardCategoryLinkRepository standardCategoryLinkRepository;


    @Validated({StandardCategoryModel.Create.class})
    @Transactional
    @BizError("01")
    public void createStandardCategory(StandardCategoryModel model) {
        
    }


}
