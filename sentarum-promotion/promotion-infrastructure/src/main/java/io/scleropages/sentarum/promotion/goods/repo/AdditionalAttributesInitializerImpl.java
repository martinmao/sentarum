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
package io.scleropages.sentarum.promotion.goods.repo;

import com.google.common.collect.Maps;
import io.scleropages.sentarum.promotion.goods.AdditionalAttributes;
import io.scleropages.sentarum.promotion.goods.AdditionalAttributesProvider;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class AdditionalAttributesInitializerImpl implements AdditionalAttributesInitializer, InitializingBean {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @Override
    public AdditionalAttributesProvider initializeAdditionalAttributes(AdditionalAttributesProvider provider, Object entity, AdditionalAttributesSavingCallback savingCallback, boolean forceProxy, Advice... beforeAdvices) {
        if (AopUtils.isAopProxy(provider))
            throw new IllegalStateException(provider + " already proxied.");
        ProxyFactory proxyFactory = new ProxyFactory(provider);
        proxyFactory.setProxyTargetClass(forceProxy);
        Map initialMap = savingCallback.additionalAttributesMap(entity);
        if (logger.isDebugEnabled()) {
            logger.debug("creating proxy for: {} with initial data {} ", provider.getClass().getSimpleName(), initialMap);
        }
        AdditionalAttributesImpl additionalAttributes = new AdditionalAttributesImpl(savingCallback, initialMap, provider);

        if (ArrayUtils.isNotEmpty(beforeAdvices)) {
            for (Advice beforeAdvice : beforeAdvices) {
                proxyFactory.addAdvice(beforeAdvice);
                if (logger.isDebugEnabled()) {
                    logger.debug("adding advice: {} to: {}", beforeAdvice.getClass().getSimpleName(), provider.getClass().getSimpleName());
                }
            }
        }

        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            if (Objects.equals(invocation.getMethod().getName(), "additionalAttributes")) {
                return additionalAttributes;
            }
            return invocation.proceed();
        });
        return (AdditionalAttributesProvider) proxyFactory.getProxy();
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(transactionManager, "no transaction manager found.");
        this.transactionTemplate = new TransactionTemplate(transactionManager, new DefaultTransactionDefinition());
    }

    private class AdditionalAttributesImpl implements AdditionalAttributes {

        private final AdditionalAttributesSavingCallback savingCallback;
        private final Map<String, Object> attributes;
        private final AdditionalAttributesProvider provider;

        public AdditionalAttributesImpl(AdditionalAttributesSavingCallback savingCallback, Map<String, Object> initialMap, AdditionalAttributesProvider provider) {
            this.savingCallback = savingCallback;
            this.attributes = null != initialMap ? initialMap : Maps.newHashMap();
            this.provider = provider;
        }

        @Override
        public AdditionalAttributes setAttribute(String name, Object value, boolean force) {
            Assert.hasText(name, "name must not be empty text.");
            Assert.notNull(value, "value must not be null.");
            if (force)
                attributes.put(name, value);
            else
                attributes.putIfAbsent(name, value);
            return this;
        }

        @Override
        public AdditionalAttributes removeAttribute(String name) {
            Assert.hasText(name, "name must not be empty text.");
            attributes.remove(name);
            return this;
        }

        @Override
        public Object getAttribute(String name) {
            Assert.hasText(name, "name must not be empty text.");
            return attributes.get(name);
        }

        @Override
        public Map<String, Object> getAttributes() {
            return Collections.unmodifiableMap(attributes);
        }

        @Override
        public boolean hasAttribute(String name) {
            Assert.hasText(name, "name must not be empty text.");
            return attributes.containsKey(name);
        }

        @Override
        public void save() {
            transactionTemplate.execute(status -> {
                savingCallback.save(provider, attributes);
                return null;
            });
        }
    }
}
