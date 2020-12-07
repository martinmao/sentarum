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
package io.scleropages.sentarum.promotion.rule.invocation.calculator.promotion;

import java.util.HashMap;
import java.util.Map;

/**
 * 促销计算级别，同级互斥，上级可可叠加下级,当前根据促销规则如下:
 * <p>
 * 对活动设置计算级别可以对不同活动的叠加关系进行定义.
 * 活动叠加存在的先后次序，同样可以按照级别进行定义，后面的促销结果计算基于前面的促销结果
 * <pre>
 *     规则说明：
 *
 *     同级别的活动互斥，即同级别的活动不能同时参与优惠
 *
 *     活动能否叠加由前面的活动决定，在设置活动时除了设置当前的活动级别，也要设置后续可叠加的活动级别。
 *     例如iphonex秒杀价8888（级别1），在设置该活动同时设置可叠加的后续级别（2-5之间，例如当前选择2，3）即说明可叠加满减，优惠券
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public enum CalculateLevel {


    GOODS_DISCOUNT(10, "商品折扣", "场景：单品折扣、一口价、秒杀、拼团、预购、会员价."),
    RANGE_PROMOTION(20, "范围促销", "场景：指定商品分类（店铺、类目、品牌）满减、满送、全场折扣等."),
    COUPON(30, "优惠券", "场景：满减券，抵扣券等"),
    DISCOUNT_BY_DISCOUNT(40, "折扣基础上再打折", "场景：符合特定条件下可在折扣基础上再打折.如会员全场8.5折"),
    FREE_FREIGHT(50, "包邮", "场景：单品包邮、满99包邮、大促全场包邮"),
    POINTS_DEDUCTION(60, "积分抵扣", "场景：100积分抵1元");

    private final int ordinal;
    /**
     * 显示名.
     */
    private final String tag;
    /**
     * 描述
     */
    private final String desc;

    CalculateLevel(int ordinal, String tag, String desc) {
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


    private static final Map<String, CalculateLevel> nameMappings = new HashMap<>();
    private static final Map<Integer, CalculateLevel> ordinalMappings = new HashMap<>();

    static {
        for (CalculateLevel level : CalculateLevel.values()) {
            nameMappings.put(level.name(), level);
            ordinalMappings.put(level.getOrdinal(), level);
        }
    }


    public static CalculateLevel getByName(String name) {
        return (name != null ? nameMappings.get(name) : null);
    }

    public static CalculateLevel getByOrdinal(int ordinal) {
        return ordinalMappings.get(ordinal);
    }
}
