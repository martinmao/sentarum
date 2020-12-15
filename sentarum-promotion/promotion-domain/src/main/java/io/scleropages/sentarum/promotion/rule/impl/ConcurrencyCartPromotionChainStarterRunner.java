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
package io.scleropages.sentarum.promotion.rule.impl;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.scleropages.sentarum.promotion.rule.PromotionChainStarterRunner;
import org.apache.commons.lang3.StringUtils;
import org.scleropages.core.concurrent.ExecutorServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * using jdk concurrency(J.U.C) to implementation concurrency calculating in single local vm.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ConcurrencyCartPromotionChainStarterRunner implements PromotionChainStarterRunner<CartPromotionChainStarter>, InitializingBean, DisposableBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("#{ @environment['promotion.calculating.threads.core'] ?: 5 }")
    private int coreCalculatingThreadsSize;
    @Value("#{ @environment['promotion.calculating.threads.maximum'] ?: 20 }")
    private int maximumCalculatingThreadsSize;
    @Value("#{ @environment['promotion.calculating.threads.alive_seconds'] ?: 300 }")
    private long calculatingThreadsKeepAliveSeconds;
    @Value("#{ @environment['promotion.calculating.threads.flow_limit'] ?: 100 }")
    private int flowLimit;
    @Value("#{ @environment['promotion.calculating.max_goods'] ?: 3 }")
    private int maxGoods;

    private ExecutorService executorService;

    @Override
    public void run(CartPromotionChainStarter invocationChainStarter) {
        List<ActivityPromotionInvocationChain> activityPromotionInvocationChains = invocationChainStarter.headOfChains();
        if (activityPromotionInvocationChains.size() == 3) {
            activityPromotionInvocationChains.forEach(activityPromotionInvocationChain -> activityPromotionInvocationChain.start());
            invocationChainStarter.finalChain().start();
            return;
        }


        List<ActivityPromotionInvocationChain> headOfChains = invocationChainStarter.headOfChains();

        CountDownLatch latch = new CountDownLatch(headOfChains.size());
        List<Future> futures = calculatingInternal(latch, headOfChains);
        try {
            if (latch.await(1000, TimeUnit.MILLISECONDS)) {
                for (Future future : futures) {
                    future.get();
                }
            } else {
                futures.forEach(future -> future.cancel(true));
                throw new IllegalStateException("timeout waiting goods-calculating task....");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("promotion calculating interrupted by other process.");
        } catch (ExecutionException e) {
            throw new IllegalStateException(e);
        }
        ActivityPromotionInvocationChain finalChain = invocationChainStarter.finalChain();
        finalChain.start();
    }

    public List<Future> calculatingInternal(CountDownLatch latch, List<ActivityPromotionInvocationChain> headOfChains) {
        List<Future> futures = Lists.newArrayList();
        for (int i = 0; i < headOfChains.size(); i++) {
            int finalI = i;
            futures.add(executorService.submit(() -> {
                try {
                    headOfChains.get(finalI).start();
                } catch (Exception e) {
                    logger.warn("detected an error was occurred when calculating.", e);
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException(e);
                } finally {
                    latch.countDown();
                }
            }));
        }
        return futures;
    }

    protected void initExecutorService() {
        executorService = new ThreadPoolExecutor(coreCalculatingThreadsSize, maximumCalculatingThreadsSize,
                calculatingThreadsKeepAliveSeconds, TimeUnit.SECONDS, flowLimit <= 0 ? new LinkedBlockingQueue<>() : new LinkedBlockingQueue<>(flowLimit),
                new ThreadFactoryBuilder().setNameFormat("promotion-calculating-%d").build(), (r, executor) -> {
            throw new RejectedExecutionException(
                    "overflow of request pending queues. reject new task. thread-pool: ["
                            + StringUtils.substringAfter(executor.toString(), "["));
        });
        logger.info("promotion calculating thread-pool[{}->{}/{}s] Initialized. [{}", coreCalculatingThreadsSize,
                maximumCalculatingThreadsSize, calculatingThreadsKeepAliveSeconds,
                StringUtils.substringAfter(executorService.toString(), "["));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initExecutorService();
    }

    @Override
    public void destroy() throws Exception {
        ExecutorServices.gracefulShutdown(executorService, logger, "promotion calculating threads-pool", 5);
    }
}
