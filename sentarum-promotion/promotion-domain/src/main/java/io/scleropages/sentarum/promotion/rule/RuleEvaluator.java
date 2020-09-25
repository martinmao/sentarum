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
package io.scleropages.sentarum.promotion.rule;

/**
 * 促销规则计算器.每个计算规则都需要实现该接口，完成计算后将结果写入{@link EvaluationContext}.最终由一系列连续计算规则构成 {@link RuleEvaluatorChain} 进行顺序计算
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface RuleEvaluator {


    /**
     * 计算规则所处级别,同级互斥，低级可与高级叠加
     *
     * @return
     */
    EvaluationLevel evaluationLevel();

    /**
     * @param evaluateContext
     * @param chain
     */
    void evaluate(EvaluationContext evaluateContext, RuleEvaluatorChain chain);

    /**
     * 计算规则描述
     */
    String desc();
}