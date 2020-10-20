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
package io.scleropages.sentarum.core.fsm.repo;

import io.scleropages.sentarum.core.fsm.entity.InvocationConfigEntity;
import io.scleropages.sentarum.jooq.tables.FsmInvocationConf;
import io.scleropages.sentarum.jooq.tables.records.FsmInvocationConfRecord;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface InvocationConfigRepository extends GenericRepository<InvocationConfigEntity, Long>, JooqRepository<FsmInvocationConf, FsmInvocationConfRecord, InvocationConfigEntity> {
}
