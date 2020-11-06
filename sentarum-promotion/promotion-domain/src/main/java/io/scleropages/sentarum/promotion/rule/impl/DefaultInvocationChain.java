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

import io.scleropages.sentarum.promotion.rule.InvocationChain;
import io.scleropages.sentarum.promotion.rule.InvocationContext;
import io.scleropages.sentarum.promotion.rule.RuleInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * default implementation of {@link InvocationChain}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class DefaultInvocationChain implements InvocationChain {


    protected static final Logger logger = LoggerFactory.getLogger(DefaultInvocationChain.class);

    //sequential invocations of this chain.
    private final List<RuleInvocation> invocations;
    //next chain if current sequential invocations finished.
    private final InvocationChain nextInvocationChain;
    //position of current invocation in this chain.
    private int currentPosition = 0;

    public DefaultInvocationChain(List<RuleInvocation> invocations, InvocationChain nextInvocationChain) {
        this.invocations = null != invocations ? invocations : Collections.emptyList();
        this.nextInvocationChain = nextInvocationChain;
    }

    @Override
    public void next(InvocationContext invocationContext) {
        if (null != nextInvocationChain && currentPosition == invocations.size()) {
            if (logger.isDebugEnabled()) {
                logger.debug("forwarding to next invocation chain: {}", nextInvocationChain);
            }
            nextInvocationChain.next(invocationContext);
        } else {
            final RuleInvocation next = invocations.get(currentPosition++);
            if (logger.isDebugEnabled()) {
                logger.debug("invoking rule invocation [{}]: {}", next.getClass().getSimpleName(), next.description());
            }
            next.execute(null, invocationContext, this);
        }
    }
}
