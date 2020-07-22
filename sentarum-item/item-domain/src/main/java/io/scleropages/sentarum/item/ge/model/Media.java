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

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Media {

    private Long id;
    private ContentType contentType;
    private Float order;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Float getOrder() {
        return order;
    }

    public void setOrder(Float order) {
        this.order = order;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    enum ContentType {

        HEADER_IMAGE(1, "头图"), IMAGE(2, "图片"), HEADER_VIDEO(3, "头视频"), VIDEO(4, "视频");

        private final int ordinal;
        private final String tag;

        ContentType(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, ContentType> nameMappings = new HashMap<>();
        private static final Map<Integer, ContentType> ordinalMappings = new HashMap<>();

        static {
            for (ContentType contentType : ContentType.values()) {
                nameMappings.put(contentType.name(), contentType);
                ordinalMappings.put(contentType.getOrdinal(), contentType);
            }
        }

        public static ContentType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ContentType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

    enum Status {

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


}
