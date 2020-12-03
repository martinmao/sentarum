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
package io.scleropages.sentarum.item.boot.autoconfigure;

import io.scleropages.sentarum.item.ItemApi;
import io.scleropages.sentarum.item.rpc.client.dubbo.ItemClientFactoryBean;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor;
import org.apache.dubbo.spring.boot.autoconfigure.DubboAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * auto configure for item clients.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Configuration
public class ItemClientsAutoConfiguration {


    @ConditionalOnClass(ReferenceAnnotationBeanPostProcessor.class)
    @ConditionalOnProperty(prefix = "rpc.client.dubbo.item", name = "enabled", havingValue = "true", matchIfMissing = true)
    @AutoConfigureAfter(DubboAutoConfiguration.class)
    @EnableDiscoveryClient
    public static class ItemDubboClientConfiguration {

        private ItemApi itemApi;

        @Bean
        public ItemClientFactoryBean itemClientFactoryBean() {
            return new ItemClientFactoryBean(itemApi);
        }

        @Reference(check = false)
        public void setItemApi(ItemApi itemApi) {
            this.itemApi = itemApi;
        }
    }
}
