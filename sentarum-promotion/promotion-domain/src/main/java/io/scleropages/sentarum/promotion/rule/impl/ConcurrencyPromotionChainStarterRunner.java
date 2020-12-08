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

import io.scleropages.sentarum.promotion.rule.InvocationChainStarterRunner;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * using jdk concurrency(J.U.C) to implementation concurrency computing in single local vm.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ConcurrencyPromotionChainStarterRunner implements InvocationChainStarterRunner<PromotionChainStarter> {

    private ExecutorService executorService;

    @Override
    public void run(PromotionChainStarter invocationChainStarter) {
        List<PromotionChainStarterFactory.HeadOfChain> headOfGoodsChain = invocationChainStarter.getHeadOfGoodsChain();
        CountDownLatch latch = new CountDownLatch(headOfGoodsChain.size());
        for (int i = 0; i < headOfGoodsChain.size(); i++) {
            int finalI = i;
            executorService.submit(() -> {
                headOfGoodsChain.get(finalI).startInternal();
                latch.countDown();
            });
        }
        try {
            latch.await(1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException("timeout waiting for computing results");
        }

    }
}
