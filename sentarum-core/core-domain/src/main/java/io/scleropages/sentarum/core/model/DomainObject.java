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
package io.scleropages.sentarum.core.model;

import java.util.List;

/**
 * represent a domain object.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface DomainObject {

    /**
     * identity of this domain object.
     *
     * @return
     */
    Long id();

    /**
     * type id of this domain object.
     *
     * @return
     */
    Integer typeId();

    /**
     * name of this domain object.
     *
     * @return
     */
    String name();

    /**
     * the tags marked of this domain object.
     *
     * @return
     */
    List<Tag> tags();

    /**
     * add tag.
     *
     * @param tag
     */
    void addTag(Tag tag);

    /**
     * remove tag.
     *
     * @param tag
     */
    void removeTag(Tag tag);
}
