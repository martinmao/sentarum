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

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * referenced from: {@link io.scleropages.sentarum.promotion.activity.model.impl.ActivityModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prom_activity")
@SequenceGenerator(name = "prom_activity_id", sequenceName = "seq_prom_activity", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class ActivityEntity extends IdEntity {

    private String name;
    private String tag;
    private String description;
    private Date startTime;
    private Date endTime;
    private Integer status;

    //transient entity fields.
    private List<AbstractGoodsSourceEntity> goodsSource;


    @Column(name = "name_", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "tag_", nullable = false)
    public String getTag() {
        return tag;
    }

    @Column(name = "desc_", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "start_time", nullable = false)
    public Date getStartTime() {
        return startTime;
    }

    @Column(name = "end_time", nullable = false)
    public Date getEndTime() {
        return endTime;
    }

    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    @Transient
    public List<AbstractGoodsSourceEntity> getGoodsSource() {
        if (null == goodsSource)
            goodsSource = Lists.newArrayList();
        return goodsSource;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setGoodsSource(List<AbstractGoodsSourceEntity> goodsSource) {
        this.goodsSource = goodsSource;
    }


}
