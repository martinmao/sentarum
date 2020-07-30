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
package io.scleropages.sentarum.item.mgmt;

import io.scleropages.sentarum.item.ge.entity.MediaEntity;
import io.scleropages.sentarum.item.ge.entity.mapper.MediaEntityMapper;
import io.scleropages.sentarum.item.ge.model.Media;
import io.scleropages.sentarum.item.ge.repo.MediaRepository;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 媒体资源管理器（图片，视频。。。）
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("40")
public class MediaManager implements GenericManager<Media, Long, MediaEntityMapper> {

    private MediaRepository mediaRepository;

    /**
     * 创建一个媒体资源
     *
     * @param media
     */
    @Validated({Media.Create.class})
    @Transactional
    @BizError("10")
    public void createMedia(@Valid Media media) {
        mediaRepository.save(getModelMapper().mapForSave(media));
    }

    /**
     * 更新保存媒体资源
     *
     * @param media
     */
    @Validated({Media.Update.class})
    @Transactional
    @BizError("15")
    public void saveMedia(@Valid Media media) {
        MediaEntity mediaEntity = mediaRepository.get(media.getId()).orElseThrow(() -> new IllegalArgumentException("no media found: " + media.getId()));
        getModelMapper().mapForUpdate(media, mediaEntity);
    }

    @Autowired
    public void setMediaRepository(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }
}
