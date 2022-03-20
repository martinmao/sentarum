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
package io.scleropages.sentarum.boot.bc.alipay.tests;

import io.scleropages.sentarum.bc.alipay.mvc.AlipayMessageResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Configuration
public class AlipayTestConfiguration {

    @Configuration
    public static class MVCConfiguration implements WebMvcConfigurer {


        /**
         * 如果你没有前置的反向代理(nginx)解决跨域请求，使用spring的方案（增加response header）处理
         *
         * @param registry
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("**/webjars/**");
            registry.addMapping("**/rest/**");
        }
    }
}
