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
package io.scleropages.sentarum.promotion.rule.invocation.condition.repo;

import io.scleropages.sentarum.jooq.tables.PromCondUserLv;
import io.scleropages.sentarum.jooq.tables.records.PromCondUserLvRecord;
import io.scleropages.sentarum.promotion.rule.entity.condition.UserLevelConditionRuleEntity;
import io.scleropages.sentarum.promotion.rule.repo.AbstractConditionRuleRepository;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface UserLevelConditionRuleRepository extends AbstractConditionRuleRepository<UserLevelConditionRuleEntity, PromCondUserLv, PromCondUserLvRecord> {
}
