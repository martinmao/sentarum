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
 * referenced from: {@link io.scleropages.sentarum.promotion.rule.model.condition.SellerUserLevelConditionRule}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
//@DiscriminatorValue("4")
@Table(name = "prom_condition_seller_user_lv")
@SequenceGenerator(name = "prom_condition_seller_user_lv_id", sequenceName = "seq_prom_condition_seller_user_lv", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class SellerUserLevelConditionRuleEntity extends BaseConditionRuleEntity {


    private Integer levelId;

    private String levelName;

    private Integer sellerType;

    private Long sellerUnionId;

    private Long sellerId;

    @Column(name = "level_id", nullable = false)
    public Integer getLevelId() {
        return levelId;
    }

    @Column(name = "level_name", nullable = false)
    public String getLevelName() {
        return levelName;
    }

    @Column(name = "seller_type", nullable = false)
    public Integer getSellerType() {
        return sellerType;
    }

    @Column(name = "seller_union_id", nullable = false)
    public Long getSellerUnionId() {
        return sellerUnionId;
    }

    @Column(name = "seller_id", nullable = false)
    public Long getSellerId() {
        return sellerId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setSellerType(Integer sellerType) {
        this.sellerType = sellerType;
    }

    public void setSellerUnionId(Long sellerUnionId) {
        this.sellerUnionId = sellerUnionId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
