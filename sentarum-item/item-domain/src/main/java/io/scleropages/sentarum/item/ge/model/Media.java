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
package io.scleropages.sentarum.item.ge.model;

import com.google.common.net.MediaType;

import java.util.HashMap;
import java.util.Map;

/**
 * 媒体资源，视频，图片等信息化结构定义
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Media {

    private Long id;
    private ContentType contentType;
    private BizType bizType;
    private Float order;
    private Status status;
    private Map<String, Object> additionalAttributes;

    public Long getId() {
        return id;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public BizType getBizType() {
        return bizType;
    }

    public Float getOrder() {
        return order;
    }

    public Status getStatus() {
        return status;
    }

    public Map<String, Object> getAdditionalAttributes() {
        return additionalAttributes;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setBizType(BizType bizType) {
        this.bizType = bizType;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
        this.additionalAttributes = additionalAttributes;
    }

    public enum BizType {

        MAJOR(1, "主图(主视频)"), HEADER(2, "头图(头视频)");

        private final int ordinal;
        private final String tag;

        BizType(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, BizType> nameMappings = new HashMap<>();
        private static final Map<Integer, BizType> ordinalMappings = new HashMap<>();

        static {
            for (BizType bizType : BizType.values()) {
                nameMappings.put(bizType.name(), bizType);
                ordinalMappings.put(bizType.getOrdinal(), bizType);
            }
        }

        public static BizType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static BizType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

    public enum Status {

        VALID(1, "有效"), INVALID(2, "无效");


        Status(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        private final int ordinal;
        private final String tag;

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, Status> nameMappings = new HashMap<>();
        private static final Map<Integer, Status> ordinalMappings = new HashMap<>();

        static {
            for (Status status : Status.values()) {
                nameMappings.put(status.name(), status);
                ordinalMappings.put(status.getOrdinal(), status);
            }
        }


        public static Status getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static Status getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }


    public enum ContentType {

        JPEG(1, "jpeg图片", MediaType.JPEG),
        PNG(2, "png图片", MediaType.PNG),
        MP4_AUDIO(3, "mp4音频", MediaType.MP4_AUDIO),
        MP4_VIDEO(4, "mp4视频", MediaType.MP4_VIDEO);

        private final int ordinal;
        private final String tag;
        private final MediaType mediaType;

        ContentType(int ordinal, String tag, MediaType mediaType) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.mediaType = mediaType;
        }

        private static final Map<String, ContentType> nameMappings = new HashMap<>();
        private static final Map<Integer, ContentType> ordinalMappings = new HashMap<>();
        private static final Map<String, ContentType> mimeTypeMappings = new HashMap<>();

        static {
            for (ContentType contentType : ContentType.values()) {
                nameMappings.put(contentType.name(), contentType);
                ordinalMappings.put(contentType.getOrdinal(), contentType);
                mimeTypeMappings.put(contentType.getMediaType().toString(), contentType);
            }
        }


        public static ContentType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ContentType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }

        public static ContentType getByMimeType(String mimeType) {
            return mimeTypeMappings.get(mimeType);
        }


        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public MediaType getMediaType() {
            return mediaType;
        }
    }
}
