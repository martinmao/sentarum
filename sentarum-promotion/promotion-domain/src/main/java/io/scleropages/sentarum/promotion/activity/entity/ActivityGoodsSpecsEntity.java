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

import io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * referenced from: {@link io.scleropages.sentarum.promotion.activity.model.impl.ActivityGoodsSpecsModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ActivityGoodsSpecsEntity extends GoodsSpecsEntity {

    private Integer totalNum;
    private Integer userNum;
    private ActivityEntity activity;


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
