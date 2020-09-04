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
package io.scleropages.sentarum.promotion.activity.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * this class is defined base information about activity of promotion.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Activity {

    /**
     * 活动唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 活动名称
     *
     * @return
     */
    String name();

    /**
     * 活动描述
     *
     * @return
     */
    String description();

    /**
     * 开始时间
     *
     * @return
     */
    Date startTime();

    /**
     * 结束时间
     *
     * @return
     */
    Date endTime();

    /**
     * 活动渠道
     *
     * @return
     */
    Channel channel();


    /**
     * 渠道
     */
    enum Channel {

        ALL(1, "全平台", ""), APP_MALL(2, "APP商城", ""), PC_MALL(3, "PC商城", ""), WE_CHAT_MALL(4, "微信商城", "");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        Channel(int ordinal, String tag, String desc) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public String getDesc() {
            return desc;
        }


        private static final Map<String, Channel> nameMappings = new HashMap<>();
        private static final Map<Integer, Channel> ordinalMappings = new HashMap<>();

        static {
            for (Channel channel : Channel.values()) {
                nameMappings.put(channel.name(), channel);
                ordinalMappings.put(channel.getOrdinal(), channel);
            }
        }

        public static Channel getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static Channel getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

}
