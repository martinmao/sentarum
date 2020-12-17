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
package io.scleropages.sentarum.promotion.mgmt.impl;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.promotion.mgmt.CalculatorGoodsManager;
import io.scleropages.sentarum.promotion.rule.invocation.promotion.calculator.CalculatorRuleInitializer;
import io.scleropages.sentarum.promotion.rule.model.CalculatorRule;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorInitializableRuleDetailedConfigure;
import io.scleropages.sentarum.promotion.rule.model.calculator.goods.CalculatorInitializableRule;
import org.aopalliance.intercept.MethodInterceptor;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.core.util.Reflections2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Initializer for {@link CalculatorInitializableRule}.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class CalculatorGoodsSourceRuleInitializer implements CalculatorRuleInitializer, BeanClassLoaderAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private CalculatorGoodsManager calculatorGoodsManager;

    @Override
    public boolean support(CalculatorRule calculatorRule) {
        return calculatorRule instanceof CalculatorInitializableRule;
    }

    @Override
    public CalculatorRule initialize(CalculatorRule calculatorRule) {
        CalculatorInitializableRule initializingRule = (CalculatorInitializableRule) calculatorRule;
        ProxyFactory proxyFactory = new ProxyFactory(initializingRule);
        proxyFactory.setProxyTargetClass(false);
        List<CalculatorInitializableRuleDetailedConfigure> awareList = Lists.newArrayList();
        AtomicBoolean initialized = new AtomicBoolean(false);//mark true if detailedConfigures initialized.
        proxyFactory.addAdvice((MethodInterceptor) invocation -> {
            if (Objects.equals(invocation.getMethod().getName(), "detailedConfigures")) {
                if (initialized.get())
                    return awareList;
                initialized.set(true);
                List<CalculatorGoodsSource> goodsSources = calculatorGoodsManager.readAllCalculatorGoodsSourceByRuleId(initializingRule.id(), initializingRule.goodsSourceType());
                goodsSources.forEach(goodsSource -> {
                    Object className = goodsSource.additionalAttributes().getAttribute(CalculatorGoodsSource.ADDITIONAL_ATTRIBUTE_GOODS_SOURCE_HOLDER_CLASS);
                    Class<?> clazz = Reflections2.getClass(String.valueOf(className), classLoader);
                    Object objectPayload = goodsSource.additionalAttributes().getAttribute(CalculatorGoodsSource.ADDITIONAL_ATTRIBUTE_GOODS_SOURCE_HOLDER_PAYLOAD);
                    Object object = JsonMapper2.fromJson(String.valueOf(objectPayload), clazz);
                    if (object instanceof CalculatorInitializableRuleDetailedConfigure) {
                        CalculatorInitializableRuleDetailedConfigure actualAware = (CalculatorInitializableRuleDetailedConfigure) object;
                        actualAware.setCalculatorGoodsSource(goodsSource);
                        awareList.add(actualAware);
                    } else {
                        logger.warn("detected invalid config for calculate rule: [{}]. #detailedConfigures returned CalculatorGoodsSource: [{}] additional attribute not a instanceof CalculatorGoodsSourceAware", calculatorRule.id(), goodsSource.id());
                    }
                });
                return awareList;
            }
            return invocation.proceed();
        });
        return (CalculatorRule) proxyFactory.getProxy();
    }

    @Autowired
    public void setCalculatorGoodsManager(CalculatorGoodsManager calculatorGoodsManager) {
        this.calculatorGoodsManager = calculatorGoodsManager;
    }

    private ClassLoader classLoader;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
