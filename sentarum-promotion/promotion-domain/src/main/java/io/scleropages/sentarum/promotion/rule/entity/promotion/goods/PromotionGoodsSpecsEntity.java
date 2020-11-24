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
package io.scleropages.sentarum.promotion.rule.entity.promotion.goods;

import io.scleropages.sentarum.promotion.goods.entity.AbstractGoodsSourceEntity;
import io.scleropages.sentarum.promotion.goods.entity.GoodsEntity;
import io.scleropages.sentarum.promotion.goods.entity.GoodsSpecsEntity;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "prom_goods_specs")
@SequenceGenerator(name = "prom_goods_specs_id", sequenceName = "seq_prom_goods_specs", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class PromotionGoodsSpecsEntity extends GoodsSpecsEntity {


    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PromotionGoodsEntity.class)
    @JoinColumn(name = COLUMN_GOODS_ID, nullable = false)
    public GoodsEntity getGoods() {
        return super.getGoods();
    }

    @Override
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = PromotionGoodsSourceEntity.class)
    @JoinColumn(name = GoodsEntity.COLUMN_GOODS_SOURCE_ID, nullable = false)
    public AbstractGoodsSourceEntity getGoodsSource() {
        return super.getGoodsSource();
    }

}
