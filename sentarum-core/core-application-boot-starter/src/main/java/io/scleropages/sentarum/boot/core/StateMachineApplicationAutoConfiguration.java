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
package io.scleropages.sentarum.boot.core;

import io.scleropages.sentarum.core.fsm.entity.StateMachineDefinitionEntity;
import io.scleropages.sentarum.core.fsm.entity.mapper.StateMachineDefinitionEntityMapper;
import io.scleropages.sentarum.core.fsm.mgmt.StateMachineManager;
import io.scleropages.sentarum.core.fsm.provider.StateMachineFactory;
import io.scleropages.sentarum.core.fsm.repo.StateMachineDefinitionRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Auto configure for state machine configurations.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Configuration
@ConditionalOnClass(StateMachineManager.class)
@ConditionalOnMissingBean(StateMachineApplicationAutoConfiguration.class)
@ConditionalOnProperty(prefix = "statemachine", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
public class StateMachineApplicationAutoConfiguration {


    @EntityScan(basePackageClasses = {StateMachineDefinitionEntity.class})
    @EnableJpaRepositories(basePackageClasses = {StateMachineDefinitionRepository.class})
    @ComponentScan(basePackageClasses = {StateMachineManager.class, StateMachineDefinitionEntityMapper.class, StateMachineFactory.class})
    public static class Configuration {

    }

}
