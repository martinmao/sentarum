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

import io.scleropages.sentarum.item.category.model.impl.CategoryPropertyModel;
import io.scleropages.sentarum.item.mgmt.CategoryManager;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 类目应用层，提供特定场景下的功能实现。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@BizError("21")
public class CategoryApplication {

    private CategoryManager categoryManager;

    @Transactional
    @BizError("01")
    public void createCategoryProperties(List<CategoryPropertyModel> models, Long stdCategoryId, Long propertyMetaId) {
        Assert.notEmpty(models, "saving category properties must not empty.");
        for (CategoryPropertyModel model : models) {
            categoryManager.createCategoryProperty(model, stdCategoryId, propertyMetaId);
        }
    }

    @Autowired
    public void setCategoryManager(CategoryManager categoryManager) {
        this.categoryManager = categoryManager;
    }
}
