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
 * referenced from: {@link io.scleropages.sentarum.promotion.rule.model.condition.UserLevelConditionRule}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
//@DiscriminatorValue("3")
@Table(name = "prom_cond_user_lv")
@SequenceGenerator(name = "prom_cond_user_lv_id", sequenceName = "seq_prom_cond_user_lv", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class UserLevelConditionRuleEntity extends BaseConditionRuleEntity {

    private Integer levelId;

    private String levelName;

    @Column(name = "level_id", nullable = false)
    public Integer getLevelId() {
        return levelId;
    }

    @Column(name = "level_name", nullable = false)
    public String getLevelName() {
        return levelName;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
