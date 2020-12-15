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
package io.scleropages.sentarum.promotion.rule.model.condition;

import io.scleropages.sentarum.promotion.rule.invocation.promotion.condition.ChannelCondition;
import io.scleropages.sentarum.promotion.rule.model.AbstractConditionRule;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 促销渠道规则设置.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ChannelConditionRule extends AbstractConditionRule {


    /**
     * 渠道标识
     */
    private Integer channelId;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 外部渠道标识.
     */
    private String outerId;

    /**
     * 商家唯一标识（商业综合体标识），渠道类型为 {@link ChannelType#SELLER_SHOP} 时有效.
     *
     * @return
     */
    private Long sellerUnionId;

    /**
     * 商家唯一标识 (商业综合体内具体销售场所，例如店铺)，渠道类型为 {@link ChannelType#SELLER_SHOP} 时有效.
     *
     * @return
     */
    private Long sellerId;

    @NotNull(groups = Create.class)
    public Integer getChannelId() {
        return channelId;
    }

    @NotEmpty(groups = Create.class)
    public String getChannelName() {
        return channelName;
    }

    @NotEmpty(groups = Create.class)
    public String getOuterId() {
        return outerId;
    }

    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    public void setSellerUnionId(Long sellerUnionId) {
        this.sellerUnionId = sellerUnionId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    protected Integer defaultRuleInvocationImplementation() {
        return ChannelCondition.ID;
    }

    public enum ChannelType {

        ALL(0, "全平台", "全平台参与活动"),
        APP_MALL(1, "APP商城", "该活动仅支持app商城"),
        WEB_MALL(2, "web商城", "该活动仅支持web商城"),
        WE_CHAT_MALL(3, "微信商城", "该活动仅支持微信商城"),
        SELLER_SHOP(4, "商家店铺", "平台商家自建活动");

        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;
        /**
         * 描述
         */
        private final String desc;

        ChannelType(int ordinal, String tag, String desc) {
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


        private static final Map<String, ChannelType> nameMappings = new HashMap<>();
        private static final Map<Integer, ChannelType> ordinalMappings = new HashMap<>();

        static {
            for (ChannelType channelType : ChannelType.values()) {
                nameMappings.put(channelType.name(), channelType);
                ordinalMappings.put(channelType.getOrdinal(), channelType);
            }
        }

        public static ChannelType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ChannelType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }

}
