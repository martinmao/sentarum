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
package io.scleropages.sentarum.promotion.rule.entity.promotion;

import io.scleropages.sentarum.core.entity.embeddable.EmbeddableDiscount;
import io.scleropages.sentarum.promotion.rule.entity.AbstractRuleEntity;
import io.scleropages.sentarum.promotion.rule.model.promotion.GoodsDiscountRule;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * referenced from {@link GoodsDiscountRule}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prom_eval_goods_discount")
@SequenceGenerator(name = "prom_eval_goods_discount_id", sequenceName = "seq_prom_eval_goods_discount", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class GoodsDiscountRuleEntity extends AbstractRuleEntity {

    private EmbeddableDiscount discount;

    @Embedded
    public EmbeddableDiscount getDiscount() {
        return discount;
    }

    public void setDiscount(EmbeddableDiscount discount) {
        this.discount = discount;
    }
}
