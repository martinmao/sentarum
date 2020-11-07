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

import io.scleropages.sentarum.promotion.rule.model.Rule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The root domain class is defined base information about activity of promotion.
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
     * 活动标题
     *
     * @return
     */
    String tag();

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
     * 活动状态
     * <pre>
     *     SAVED->SUBMIT->APPROVING->READY->RUNNING->FINISHED
     *                        |               |
     *                        |->REJECT       |->SUSPEND->RUNNING
     *                                        |
     *                                        |->TERMINATE
     * </pre>
     *
     * @return
     */
    Integer status();

    /**
     * 参与商品范围
     *
     * @return
     */
    ParticipateItemRange participateItemRange();

    /**
     * 参与用户类型
     *
     * @return
     */
    ParticipateUserType participateUserType();


    /**
     * 匹配的会员级别
     *
     * @return
     */
    String memberLevel();

    /**
     * 用户标签，被标记标签的用户才能参与活动. {@link #participateUserType()}=={@link ParticipateUserType#TAG_USER}时才有效
     *
     * @return
     */
    String userTag();


    /**
     * 条件规则
     *
     * @return
     */
    List<Rule> conditions();


    /**
     * 促销规则
     *
     * @return
     */
    List<Rule> promotions();


    /**
     * 促销主商品来源
     *
     * @return
     */
    List<ActivityItemSource> itemSources();


    enum ParticipateItemRange {

        ALL(0, "全部参与", "平台所有商品都参与活动"),
        ITEM(1, "指定商品", "指定商品参与活动"),
        BRAND(2, "指定品牌", "指定品牌参与活动"),
        CATEGORY(3, "指定营销类目", "指定营销类目参与的活动"),
        SELLER(4, "指定商家", "指定商家参与活动");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        ParticipateItemRange(int ordinal, String tag, String desc) {
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


        private static final Map<String, ParticipateItemRange> nameMappings = new HashMap<>();
        private static final Map<Integer, ParticipateItemRange> ordinalMappings = new HashMap<>();

        static {
            for (ParticipateItemRange participateRange : ParticipateItemRange.values()) {
                nameMappings.put(participateRange.name(), participateRange);
                ordinalMappings.put(participateRange.getOrdinal(), participateRange);
            }
        }


        public static ParticipateItemRange getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ParticipateItemRange getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

    enum ParticipateUserType {

        ALL(0, "全体用户", "平台所有用户"),
        LEVEL_MEMBER(1, "等级会员", "指定的会员等级"),
        TAG_USER(2, "标签用户", "被标记特定标签的用户"),
        LEVEL_MEMBER_SELLER_SHOP(3, "店铺会员级别", "店铺sass会员等级");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        ParticipateUserType(int ordinal, String tag, String desc) {
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


        private static final Map<String, ParticipateUserType> nameMappings = new HashMap<>();
        private static final Map<Integer, ParticipateUserType> ordinalMappings = new HashMap<>();

        static {
            for (ParticipateUserType participateUserType : ParticipateUserType.values()) {
                nameMappings.put(participateUserType.name(), participateUserType);
                ordinalMappings.put(participateUserType.getOrdinal(), participateUserType);
            }
        }


        public static ParticipateUserType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ParticipateUserType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

}
