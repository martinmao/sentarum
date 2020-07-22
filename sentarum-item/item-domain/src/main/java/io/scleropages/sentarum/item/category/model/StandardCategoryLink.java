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

import org.scleropages.crud.dao.orm.SearchFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理类目链接，提供关系链接供其他渠道类目引用，例如 {@link MarketingCategory}使用。
 * 实际使用中需要限制连接的条数，建议不要超过3条。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StandardCategoryLink {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 连接类型
     *
     * @return
     */
    LinkType linkType();

    /**
     * 连接状态
     *
     * @return
     */
    LinkStatus status();

    /**
     * 关联的营销类目.属于 source 一方
     *
     * @return
     */
    MarketingCategory marketingCategory();


    /**
     * 关联的管理类目，{@link #linkType()}=={@link LinkType#DIRECT}时有效. 属于target一方.
     *
     * @return
     */
    StandardCategory standardCategory();


    /**
     * 检索条件，{@link #linkType()}=={@link LinkType#SOFT}时有效.检索条件对应的目标模型为SPU.
     *
     * @return
     */
    SearchFilter searchFilter();


    enum LinkStatus {
        VALID(1, "有效"), INVALID(2, "无效");

        private final int ordinal;
        private final String tag;

        LinkStatus(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        private static final Map<String, LinkStatus> nameMappings = new HashMap<>();
        private static final Map<Integer, LinkStatus> ordinalMappings = new HashMap<>();

        static {
            for (LinkStatus linkStatus : LinkStatus.values()) {
                nameMappings.put(linkStatus.name(), linkStatus);
                ordinalMappings.put(linkStatus.getOrdinal(), linkStatus);
            }
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public static LinkStatus getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static LinkStatus getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

    enum LinkType {
        /**
         * 直接连接，通过关联关系确定连接
         */
        DIRECT(1, "直接关联"),
        /**
         * 软连接，基于条件确定连接
         */
        SOFT(2, "软连接");

        private final int ordinal;
        private final String tag;

        LinkType(int ordinal, String tag) {
            this.ordinal = ordinal;
            this.tag = tag;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        private static final Map<String, LinkType> nameMappings = new HashMap<>();
        private static final Map<Integer, LinkType> ordinalMappings = new HashMap<>();

        static {
            for (LinkType linkType : LinkType.values()) {
                nameMappings.put(linkType.name(), linkType);
                ordinalMappings.put(linkType.getOrdinal(), linkType);
            }
        }

        public static LinkType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static LinkType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

}
