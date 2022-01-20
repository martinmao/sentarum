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
package io.scleropages.sentarum.core.mgmt;

import io.scleropages.sentarum.core.tag.model.Tag;
import io.scleropages.sentarum.core.tag.model.TagGroup;
import io.scleropages.sentarum.core.tag.model.impl.TagGroupModel;
import io.scleropages.sentarum.core.tag.model.impl.TagModel;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.Map;

/**
 * manager for tag.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface TagManager {

    /**
     * create new tag group.
     *
     * @param tagGroup model to create
     * @return model id of created.
     */
    Long createTagGroup(@Valid TagGroupModel tagGroup);


    /**
     * save given tag group.
     *
     * @param tagGroup model to save.
     */
    void saveTagGroup(@Valid TagGroupModel tagGroup);


    /**
     * create new tag.
     *
     * @param tag        model to create
     * @param tagGroupId tag group id to bind.
     * @return model id of created.
     */
    Long createTag(@Valid TagModel tag, Long tagGroupId);


    /**
     * save given tag
     *
     * @param tag           model to save
     * @param newTagGroupId optional if new tag group id to bind.
     */
    void saveTag(@Valid TagModel tag, Long newTagGroupId);

    /**
     * delete tag group by given id.
     *
     * @param id model id to delete.
     */
    void deleteTagGroup(Long id);

    /**
     * delete tag by given id.
     *
     * @param id model id to delete.
     */
    void deleteTag(Long id);

    /**
     * find page of tag groups.
     *
     * @param searchFilter filters to search.
     * @param pageable
     * @return page of tag groups
     */
    Page<TagGroup> findTagGroupPage(Map<String, SearchFilter> searchFilter, Pageable pageable);

    /**
     * find page of tags.
     *
     * @param searchFilter filters to search.
     * @param pageable
     * @return
     */
    Page<Tag> findTagPage(Map<String, SearchFilter> searchFilter, Pageable pageable);
}
