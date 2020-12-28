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
package io.scleropages.sentarum.member.hierarchy.model;

import io.scleropages.sentarum.core.model.primitive.Percentage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 价值指标定义，会员的等级成长值由一组价值指标统筹计算得出，而价值指标作为一种计算规则定义了在满足其条件基础上计算得出的成长值。该值根据指标类型 {@link IndicatorType} 的不同可增可减.
 * <pre>
 *     会员价值计算公式为:
 *     指标A计算结果*指标A权重%+指标B计算结果*指标B权重%+指标N计算结果*指标N权重%，其中指标A权重+指标B权重+指标N权重=100%.
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface IndicatorDefinition<L extends IndicatorLine> {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 名称
     *
     * @return
     */
    String name();

    /**
     * 说明
     *
     * @return
     */
    String description();

    /**
     * return true if this indicator enabled.
     *
     * @return
     */
    Boolean enabled();

    /**
     * 该指标所占权重，权重越高该价值指标占比越重.权重值取值1-99。
     *
     * @return
     */
    Percentage weight();


    /**
     * lines of {@link IndicatorLine}.
     *
     * @return
     */
    List<L> lines();


    /**
     * 价值指标类型.
     */
    enum IndicatorType {

        /**
         * RFM指标，最近消费频次及累计交易额
         */
        RFM(1, "Recency Frequency Monetary最近消费频次及累计交易额指标", "交易历史指标，在时间维度上消费行为是动态，即可正可负的会员成长值指标."),
        /**
         * 消费记录指标
         */
        CONSUMING(2, "消费激励指标", "正向增长的成长值指标，只要消费就增加的单向指标."),
        /**
         * 行为活跃度指标
         */
        BEHAVIOUR(3, "行为活跃度指标", "点赞、评论、签到、转发、恶意评论、刷单等行为定义指标，可正可负的会员成长值指标."),
        /**
         * 基础信息完整度指标
         */
        BASE(4, "基础信息完整度指标", "根据用户基本信息的完整度而设立的成长指标，如电话、地址、门店大小、床位等.");

        private final int ordinal;
        private final String tag;
        private final String desc;

        IndicatorType(int ordinal, String tag, String desc) {
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


        private static final Map<String, IndicatorType> nameMappings = new HashMap<>();
        private static final Map<Integer, IndicatorType> ordinalMappings = new HashMap<>();

        static {
            for (IndicatorType measureType : IndicatorType.values()) {
                nameMappings.put(measureType.name(), measureType);
                ordinalMappings.put(measureType.getOrdinal(), measureType);
            }
        }


        public static IndicatorType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static IndicatorType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }


}
