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
package io.scleropages.sentarum.promotion.activity.repo;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.jooq.tables.PromActivity;
import io.scleropages.sentarum.jooq.tables.records.PromActivityRecord;
import io.scleropages.sentarum.promotion.activity.entity.ActivityEntity;
import org.jooq.Field;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.metamodel.Attribute;
import java.util.List;

import static io.scleropages.sentarum.jooq.Tables.*;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ActivityRepository extends GenericRepository<ActivityEntity, Long>, JooqRepository<PromActivity, PromActivityRecord, ActivityEntity> {

    default List<ActivityEntity> findAllFromGoodsSourceByBrandId(Long brandId,Integer status) {
        PromActivity promActivity = dslTable();

        List<ActivityEntity> entities = Lists.newArrayList();
        dslContext().select(promActivity.fields()).from(promActivity)
                .join(PROM_ACT_BRAND_GOODS_SOURCE).on(promActivity.ID.eq(PROM_ACT_BRAND_GOODS_SOURCE.ACTIVITY_ID))
                .where(PROM_ACT_BRAND_GOODS_SOURCE.BRAND_ID.eq(brandId))
                .and(promActivity.STATUS.eq(status)).fetch().forEach(record -> {
            ActivityEntity entity = new ActivityEntity();
            dslRecordInto(record, entity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {

                }
            });
            entities.add(entity);
        });
        return entities;
    }

    /**
     * return true if any activity has brand goods source with given status.
     * 是否存在给定状态下的品牌活动.
     *
     * @return
     */
    @Cacheable
    default Boolean existsBrandGoodsSourceActivityWithStatus(Integer status) {
        PromActivity promActivity = dslTable();
        return null != dslContext().select(promActivity.ID)
                .from(promActivity)
                .join(PROM_ACT_BRAND_GOODS_SOURCE).on(promActivity.ID.eq(PROM_ACT_BRAND_GOODS_SOURCE.ACTIVITY_ID))
                .where(promActivity.STATUS.eq(status)).limit(1).fetchAny();
    }

    /**
     * return true if any activity has category goods source with given status.
     *
     * @param status
     * @return
     */
    @Cacheable
    default Boolean existsCategoryGoodsSourceActivityWitStatus(Integer status) {
        PromActivity promActivity = dslTable();
        return null != dslContext().select(promActivity.ID)
                .from(promActivity)
                .join(PROM_ACT_CATEGORY_GOODS_SOURCE).on(promActivity.ID.eq(PROM_ACT_CATEGORY_GOODS_SOURCE.ACTIVITY_ID))
                .where(promActivity.STATUS.eq(status)).limit(1).fetchAny();
    }

    /**
     * return true if any activity has seller goods source with given status.
     *
     * @param status
     * @return
     */
    @Cacheable
    default Boolean existsSellerGoodsSourceActivityWitStatus(Integer status) {
        PromActivity promActivity = dslTable();
        return null != dslContext().select(promActivity.ID)
                .from(promActivity)
                .join(PROM_ACT_SELLER_GOODS_SOURCE).on(promActivity.ID.eq(PROM_ACT_SELLER_GOODS_SOURCE.ACTIVITY_ID))
                .where(promActivity.STATUS.eq(status)).limit(1).fetchAny();
    }


}
