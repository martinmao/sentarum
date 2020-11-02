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
package io.scleropages.sentarum.trading.order.entity;

import io.scleropages.sentarum.core.entity.embeddable.EmbeddableAmount;
import io.scleropages.sentarum.core.model.primitive.Address;
import io.scleropages.sentarum.core.model.primitive.Geo;
import io.scleropages.sentarum.core.model.primitive.Tel;
import io.scleropages.sentarum.trading.order.model.Order;
import org.scleropages.crud.dao.orm.jpa.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * referenced from: {@link io.scleropages.sentarum.trading.order.model.impl.OrderConsigneeModel}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Entity
@Table(name = "td_order_consignee")
@SequenceGenerator(name = "td_order_consignee_id", sequenceName = "seq_td_order_consignee", allocationSize = IdEntity.SEQ_DEFAULT_ALLOCATION_SIZE, initialValue = IdEntity.SEQ_DEFAULT_INITIAL_VALUE)
public class OrderConsigneeEntity extends IdEntity {


    private Date startTime;
    private Date endTime;
    private Date deliveredTime;
    private EmbeddableAmount deliveryAmount;
    private String name;
    private String tel;
    private Address address;
    private String detailAddress;
    private String postalCode;
    private Geo geo;
    private Order order;
}
