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

import io.scleropages.sentarum.core.tag.entity.TagEntity;
import io.scleropages.sentarum.core.tag.entity.TagGroupEntity;
import io.scleropages.sentarum.core.tag.entity.mapper.TagEntityMapper;
import io.scleropages.sentarum.core.tag.entity.mapper.TagGroupEntityMapper;
import io.scleropages.sentarum.core.tag.model.Tag;
import io.scleropages.sentarum.core.tag.model.TagGroup;
import io.scleropages.sentarum.core.tag.model.impl.TagGroupModel;
import io.scleropages.sentarum.core.tag.model.impl.TagModel;
import io.scleropages.sentarum.core.tag.repo.TagGroupRepository;
import io.scleropages.sentarum.core.tag.repo.TagRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
public class TagManagerImpl implements GenericManager<TagModel, Long, TagEntityMapper>, TagManager {


    private TagGroupRepository tagGroupRepository;
    private TagRepository tagRepository;

    @Override
    @Validated({TagGroupModel.CreateModel.class})
    @Transactional
    public Long createTagGroup(@Valid TagGroupModel tagGroup) {
        TagGroupEntity tagGroupEntity = getModelMapper(TagGroupEntityMapper.class).mapForSave(tagGroup);
        tagGroupRepository.save(tagGroupEntity);
        return tagGroupEntity.getId();
    }

    @Override
    @Validated({TagGroupModel.UpdateModel.class})
    @Transactional
    public void saveTagGroup(@Valid TagGroupModel tagGroup) {
        TagGroupEntity tagGroupEntity = tagGroupRepository.get(tagGroup.getId()).orElseThrow(() -> new IllegalArgumentException("no tag group found: " + tagGroup.getId()));
        getModelMapper(TagGroupEntityMapper.class).mapForUpdate(tagGroup, tagGroupEntity);
        tagGroupRepository.save(tagGroupEntity);
    }

    @Override
    @Validated({TagGroupModel.CreateModel.class})
    @Transactional
    public Long createTag(@Valid TagModel tag, Long tagGroupId) {
        Assert.notNull(tagGroupId, "tagGroupId must not be null.");
        TagEntity tagEntity = getModelMapper().mapForSave(tag);
        TagGroupEntity tagGroupEntity = tagGroupRepository.get(tagGroupId).orElseThrow(() -> new IllegalArgumentException("no tag group found: " + tagGroupId));
        tagEntity.setTagGroup(tagGroupEntity);
        tagRepository.save(tagEntity);
        return tagEntity.getId();
    }

    @Override
    @Validated({TagGroupModel.UpdateModel.class})
    @Transactional
    public void saveTag(@Valid TagModel tag, Long newTagGroupId) {
        TagEntity tagEntity = tagRepository.get(tag.getId()).orElseThrow(() -> new IllegalArgumentException("no tag found: " + tag.getId()));
        getModelMapper().mapForUpdate(tag, tagEntity);
        if (null != newTagGroupId) {
            TagGroupEntity tagGroupEntity = tagGroupRepository.get(newTagGroupId).orElseThrow(() -> new IllegalArgumentException("no tag group found: " + newTagGroupId));
            tagEntity.setTagGroup(tagGroupEntity);
        }
        tagRepository.save(tagEntity);
    }

    @Override
    @Transactional
    public void deleteTagGroup(Long id) {
        Assert.notNull(id, "id must not be null.");
        tagGroupRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        Assert.notNull(id, "id must not be null.");
        tagRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagGroup> findTagGroupPage(Map<String, SearchFilter> searchFilter, Pageable pageable) {
        return tagGroupRepository.findPage(searchFilter, pageable).map(tagGroupEntity -> getModelMapper(TagGroupEntityMapper.class).mapForRead(tagGroupEntity));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Tag> findTagPage(Map<String, SearchFilter> searchFilter, Pageable pageable) {
        return tagRepository.findPage(searchFilter, pageable).map(tagEntity -> getModelMapper().mapForRead(tagEntity));
    }

    @Autowired
    public void setTagGroupRepository(TagGroupRepository tagGroupRepository) {
        this.tagGroupRepository = tagGroupRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
}
