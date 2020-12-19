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

import io.scleropages.sentarum.promotion.rule.PromotionChainStarterRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * simple (sequence computing) implementation of {@link DefaultPromotionChainStarter} runner.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SimplePromotionChainStarterRunner implements PromotionChainStarterRunner<DefaultPromotionChainStarter> {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(DefaultPromotionChainStarter promotionChainStarter) {
        if (logger.isDebugEnabled()) {
            logger.debug("start calculating.....");
        }
        List<ActivityPromotionInvocationChain> headOfChains = promotionChainStarter.headOfChains();
        for (int i = 0; i < headOfChains.size(); i++) {
            ActivityPromotionInvocationChain headChain = headOfChains.get(i);
            if (logger.isDebugEnabled()) {
                logger.debug("HeadChain[{}]->", i);
                logger.debug("  {}", headChain.name());
            }
            headChain.start();
        }
        ActivityPromotionInvocationChain finalChain = promotionChainStarter.finalChain();
        if (logger.isDebugEnabled()) {
            logger.debug("FinalChain->");
            logger.debug("  {}", finalChain.name());
        }
        finalChain.start();
    }
}
