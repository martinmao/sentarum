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
package io.scleropages.sentarum.core.boot.autoconfigure;

import io.scleropages.sentarum.core.model.primitive.BaseAddressReader;
import io.scleropages.sentarum.core.model.primitive.BaseAddresses;
import io.scleropages.sentarum.core.model.primitive.impl.ResourceBaseAddressReader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Auto configure for core configurations.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Configuration
public class CoreApplicationAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {


    @Bean
    public BaseAddressReader baseAddressReader() {
        return new ResourceBaseAddressReader();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        BaseAddresses.initIfNecessary(baseAddressReader());
    }
}
