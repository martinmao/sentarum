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
package io.scleropages.sentarum.promotion.rule.entity.calculator;

import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from: {@link io.scleropages.sentarum.promotion.rule.model.calculator.OverflowDiscountRule}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prom_calc_overflow_discount")
@SequenceGenerator(name = "prom_calc_overflow_discount_id", sequenceName = "seq_prom_calc_overflow_discount", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OverflowDiscountRuleEntity extends BaseCalculatorRuleEntity {

    private Integer overflowDiscountType;

    private Integer overflowCycleLimit;


    @Column(name = "overflow_discount_type", nullable = false)
    public Integer getOverflowDiscountType() {
        return overflowDiscountType;
    }

    @Column(name = "overflow_cycle_limit", nullable = false)
    public Integer getOverflowCycleLimit() {
        return overflowCycleLimit;
    }

    public void setOverflowDiscountType(Integer overflowDiscountType) {
        this.overflowDiscountType = overflowDiscountType;
    }

    public void setOverflowCycleLimit(Integer overflowCycleLimit) {
        this.overflowCycleLimit = overflowCycleLimit;
    }
}
