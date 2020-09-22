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
package io.scleropages.sentarum.promotion.activity.model.participator;

import io.scleropages.sentarum.promotion.activity.model.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * implementation this for item source from associated domain object.
 *
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ItemSource {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * read a page of items.
     *
     * @param pageable
     * @return
     */
    Page<? extends ItemSnapshot> readItems(Pageable pageable);

    /**
     * read a item by id.
     *
     * @param id
     * @return
     */
    ItemSnapshot readItem(Long id);

    /**
     * associated activity.
     *
     * @return
     */
    Activity activity();
}
