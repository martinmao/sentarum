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

import java.util.HashMap;
import java.util.Map;

/**
 * 活动级别，对活动设置活动级别可以对不同活动的叠加关系进行定义.
 * 活动叠加存在的先后次序，同样可以按照活动级别进行定义，后面的促销结果计算基于前面的促销结果
 * <pre>
 *     规则说明：
 *
 *     同级别的活动互斥，即同级别的活动不能同时参与优惠
 *
 *     活动能否叠加由前面的活动决定，在设置活动时除了设置当前的活动级别，也要设置后续可叠加的活动级别。
 *     例如iphonex秒杀价8888（级别1），在设置该活动同时设置可叠加的后续级别（2-5之间，例如当前选择2，3）即说明可叠加满减，优惠券
 *
 *
 *
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public enum ActivityLevel {


    FIXED_PRICE(1, "固定金额", "直降价、秒杀、拼团、预购、一口价等."),
    RANGE_PROMOTION(2, "范围促销", "指定商品（类目、品牌）满减、满送等."),
    COUPON(3, "优惠券", "满减券，抵扣券等"),
    DISCOUNT_IN_DISCOUNT(4, "折上折", "会员折上折，在实付金额基础上再打折."),
    FREE_FREIGHT(5, "包邮", "单品包邮、满99包邮、大促全场包邮"),
    POINTS_DEDUCTION(6, "积分抵扣", "100积分抵1元");
    
    private final int ordinal;
    /**
     * 显示名.
     */
    private final String tag;
    /**
     * 描述
     */
    private final String desc;

    ActivityLevel(int ordinal, String tag, String desc) {
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


    private static final Map<String, ActivityLevel> nameMappings = new HashMap<>();
    private static final Map<Integer, ActivityLevel> ordinalMappings = new HashMap<>();

    static {
        for (ActivityLevel level : ActivityLevel.values()) {
            nameMappings.put(level.name(), level);
            ordinalMappings.put(level.getOrdinal(), level);
        }
    }


    public static ActivityLevel getByName(String name) {
        return (name != null ? nameMappings.get(name) : null);
    }

    public static ActivityLevel getByOrdinal(int ordinal) {
        return ordinalMappings.get(ordinal);
    }

}
