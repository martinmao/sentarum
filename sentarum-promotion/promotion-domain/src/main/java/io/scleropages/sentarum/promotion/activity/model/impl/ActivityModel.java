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
package io.scleropages.sentarum.promotion.activity.model.impl;

import io.scleropages.sentarum.promotion.activity.model.Activity;
import io.scleropages.sentarum.promotion.rule.model.Rule;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ActivityModel implements Activity {

    private Long id;
    private String name;
    private String tag;
    private String description;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private List<Rule> conditionRules;
    private Rule promotionalRule;
    private List<ActivityGoodsSource> goodsSource;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public List<Rule> getConditionRules() {
        return conditionRules;
    }

    public Rule getPromotionalRule() {
        return promotionalRule;
    }

    public List<ActivityGoodsSource> getGoodsSource() {
        return goodsSource;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setConditionRules(List<Rule> conditionRules) {
        this.conditionRules = conditionRules;
    }

    public void setPromotionalRule(Rule promotionalRule) {
        this.promotionalRule = promotionalRule;
    }

    public void setGoodsSource(List<ActivityGoodsSource> goodsSource) {
        this.goodsSource = goodsSource;
    }

    @Override
    public Long id() {
        return getId();
    }

    @Override
    public String name() {
        return getName();
    }

    @Override
    public String tag() {
        return getTag();
    }

    @Override
    public String description() {
        return getDescription();
    }

    @Override
    public Date startTime() {
        return getStartTime();
    }

    @Override
    public Date endTime() {
        return getEndTime();
    }

    @Override
    public Integer status() {
        return getStatus();
    }

    @Override
    public List<Rule> conditionRules() {
        return getConditionRules();
    }

    @Override
    public Rule promotionalRule() {
        return getPromotionalRule();
    }

    @Override
    public List<ActivityGoodsSource> goodsSource() {
        return getGoodsSource();
    }
}
