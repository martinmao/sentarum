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

import io.scleropages.sentarum.promotion.rule.EvaluationContext;
import io.scleropages.sentarum.promotion.rule.PromotionEvaluator;
import io.scleropages.sentarum.promotion.rule.RuleEvaluatorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * a simple implementations of rule evaluator chain.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SimpleRuleEvaluatorChain implements RuleEvaluatorChain {


    protected static final Logger logger = LoggerFactory.getLogger(RuleEvaluatorChain.class);

    private final List<PromotionEvaluator> evaluators;

    private final RuleEvaluatorChain nextEvaluatorChain;

    private final String name;

    private int currentPosition = 0;

    public SimpleRuleEvaluatorChain(String name, List<PromotionEvaluator> evaluators, RuleEvaluatorChain nextEvaluatorChain) {
        this.name = name;
        if (null == evaluators)
            evaluators = Collections.emptyList();
        this.evaluators = evaluators;
        this.nextEvaluatorChain = nextEvaluatorChain;
    }

    @Override
    public void evaluate(EvaluationContext evaluateContext) {
        if (null != nextEvaluatorChain && currentPosition == evaluators.size()) {
            if (forwarding(evaluateContext, nextEvaluatorChain)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("forwarding to next rule evaluator chain: {}", nextEvaluatorChain.name());
                }
                nextEvaluatorChain.evaluate(evaluateContext);
            }
        } else {
            final PromotionEvaluator next = evaluators.get(currentPosition++);
            if (logger.isDebugEnabled()) {
                logger.debug("invoking rule evaluator [{}]: {}", next.getClass().getSimpleName(), next.desc());
            }
            next.evaluate(evaluateContext, this);
        }
    }

    @Override
    public String name() {
        return name;
    }


    /**
     * return true will forwarding to next evaluator chain.
     *
     * @param evaluateContext
     * @param nextEvaluatorChai
     * @return
     */
    protected boolean forwarding(EvaluationContext evaluateContext, RuleEvaluatorChain nextEvaluatorChai) {
        return true;
    }
}
