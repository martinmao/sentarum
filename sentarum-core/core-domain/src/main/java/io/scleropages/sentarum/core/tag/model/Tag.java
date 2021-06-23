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
package io.scleropages.sentarum.core.tag.model;

/**
 * represent a tag definition.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Tag {

    /**
     * id of this tag.
     *
     * @return
     */
    Long id();

    /**
     * name of this tag.
     *
     * @return
     */
    String name();

    /**
     * return true if this tag enabled.
     *
     * @return
     */
    Boolean enabled();

    /**
     * group of this tag.
     *
     * @return
     */
    TagGroup tagGroup();


    /**
     * business type of this tag.
     *
     * @return
     */
    Integer bizType();

    /**
     * sequence number(marked column index tag1,tag2...) of this tag.
     *
     * @return
     */
    Integer seq();

    /**
     * order of this tag in tag group.
     *
     * @return
     */
    Float order();
}
