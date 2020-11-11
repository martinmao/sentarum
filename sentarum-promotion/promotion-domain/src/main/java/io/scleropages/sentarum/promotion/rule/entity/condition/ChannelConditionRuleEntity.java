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
package io.scleropages.sentarum.promotion.rule.entity.condition;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.promotion.rule.model.condition.ChannelConditionRule}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
//@DiscriminatorValue("1")
@Table(name = "prom_condition_channel")
@SequenceGenerator(name = "prom_condition_channel_id", sequenceName = "seq_prom_condition_channel", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class ChannelConditionRuleEntity extends BaseConditionRuleEntity {


    private Integer channelId;

    private String channelName;

    private String outerId;

    private Long sellerUnionId;

    private Long sellerId;


    @Column(name = "channel_id", nullable = false)
    public Integer getChannelId() {
        return channelId;
    }

    @Column(name = "channel_name", nullable = false)
    public String getChannelName() {
        return channelName;
    }

    @Column(name = "outer_id", nullable = false)
    public String getOuterId() {
        return outerId;
    }

    @Column(name = "seller_union_id")
    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    @Column(name = "seller_id")
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
}

