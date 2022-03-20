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
package io.scleropages.sentarum.boot.bc.alipay.autoconfigure;

import io.scleropages.sentarum.bc.alipay.AlipayClientTemplate;
import io.scleropages.sentarum.bc.alipay.AlipayMessages;
import io.scleropages.sentarum.bc.alipay.AlipayRepository;
import io.scleropages.sentarum.bc.alipay.AlipaySdkClient;
import io.scleropages.sentarum.bc.alipay.SecurityConfigSource;
import io.scleropages.sentarum.bc.alipay.mvc.AlipayMessageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Configuration
@ConditionalOnClass(AlipayRepository.class)
@ConditionalOnProperty(prefix = "alipay", name = "enabled", havingValue = "true", matchIfMissing = true)
public class AlipayInfrastructureAutoConfiguration {

    @Configuration
    public static class MVCConfiguration implements WebMvcConfigurer {


        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
            resolvers.add(new AlipayMessageResolver());
        }
    }

    @Configuration
    public static class AlipayConfiguration {

        @Value("#{ @environment['alipay.appId'] ?: '2016091800542362' }")
        protected String appId;

        @Value("#{ @environment['alipay.useSandbox'] ?: true }")
        protected boolean useSandbox;

        @Value("#{ @environment['alipay.alipayPublicKey'] ?: 'classpath:alipay/signature/alipay/public.x509' }")
        protected Resource alipayPublicKey;

        @Value("#{ @environment['alipay.localPrivateKey'] ?: 'classpath:alipay/signature/local/private.pkcs8' }")
        protected Resource localPrivateKey;


        @Bean
        @ConditionalOnMissingBean
        public AlipayClientTemplate alipayClientTemplate(@Autowired(required = false) SecurityConfigSource securityConfigSource) {
            AlipayClientTemplate alipayClientTemplate = new AlipayClientTemplate();
            alipayClientTemplate.setAppId(appId);
            alipayClientTemplate.setUseSandbox(useSandbox);
            alipayClientTemplate.setAlipayPublicKey(alipayPublicKey);
            alipayClientTemplate.setLocalPrivateKey(localPrivateKey);
            alipayClientTemplate.setSecurityConfigSource(securityConfigSource);
            AlipayMessages.setClientTemplate(alipayClientTemplate);
            return alipayClientTemplate;
        }

        @Bean
        @ConditionalOnMissingBean
        public SecurityConfigSource securityConfigSource() {
            return null;
        }

        @Bean
        @ConditionalOnMissingBean
        AlipayRepository alipayRepository() {
            return new AlipaySdkClient();
        }
    }

}
