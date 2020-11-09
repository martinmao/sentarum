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
package io.scleropages.sentarum.promotion.activity.entity;

import io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity;
import io.scleropages.sentarum.promotion.goods.entity.GoodsEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prom_act_goods")
@SequenceGenerator(name = "prom_act_goods_id", sequenceName = "seq_prom_act_goods", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class ActivityGoodsEntity extends GoodsEntity {


    private Integer totalNum;
    private Integer userNum;
    private ActivityEntity activity;


    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = ActivityGoodsSourceEntity.class)
    @JoinColumn(name = "goods_source_id", nullable = false)
    public AbstractGoodsSourceEntity getGoodsSource() {
        return super.getGoodsSource();
    }

    @Column(name = "total_num", nullable = false)
    public Integer getTotalNum() {
        return totalNum;
    }

    @Column(name = "user_num", nullable = false)
    public Integer getUserNum() {
        return userNum;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    public ActivityEntity getActivity() {
        return activity;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
    }
}
