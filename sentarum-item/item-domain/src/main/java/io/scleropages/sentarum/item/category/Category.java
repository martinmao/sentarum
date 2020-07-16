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
 * 描述品类（类目）业务模型.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Category {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 类目名称
     *
     * @return
     */
    String name();

    /**
     * 类目显示名
     *
     * @return
     */
    String tag();

    /**
     * 类目描述
     *
     * @return
     */
    String description();


    /**
     * 上级类目
     *
     * @return
     */
    Long parentId();

    /**
     * 关联的一组类目属性.
     */
    List<CategoryProperty> categoryProperties();

    /**
     * 关联的一组子类目
     *
     * @return
     */
    List<Category> childCategories();
}
