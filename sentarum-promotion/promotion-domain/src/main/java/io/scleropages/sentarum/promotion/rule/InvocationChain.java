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
 * 规则调用链，通常情况下，一个促销规则计算链包括n个参与规则{@link Condition} 及1个促销规则（{@link PromotionCalculator}）
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface InvocationChain {


    /**
     * 基于当前所处位置，调用下一个规则.
     *
     * @param invocationContext
     */
    void next(InvocationContext invocationContext);
}
