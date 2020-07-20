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
 * 该类目设计是出于商品（产品）层次化（物理分类）管理的需求定义的, 属于管理类目范畴.其经常不易被变动。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StandardCategory extends Category {

    /**
     * 关联的一组类目属性.
     */
    List<? extends CategoryProperty> categoryProperties();
}