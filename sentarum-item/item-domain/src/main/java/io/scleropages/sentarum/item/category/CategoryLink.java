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

import org.scleropages.crud.dao.orm.SearchFilter;

import java.util.List;

/**
 * 类目连接，描述多种类目体系的连接描述. 例如某种检索条件连接。一种更加灵活的挂靠方式.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CategoryLink {

    /**
     * 返回一组 {@link SearchFilter} 作为检索条件，将检索到的类目关联到目标类目（软连接）.
     *
     * @return
     */
    List<SearchFilter> conditions();


    /**
     * @return
     */
    Category source();
}
