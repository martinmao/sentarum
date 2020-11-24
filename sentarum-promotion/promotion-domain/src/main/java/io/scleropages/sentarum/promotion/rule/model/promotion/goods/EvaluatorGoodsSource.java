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
package io.scleropages.sentarum.promotion.rule.model.promotion.goods;

import io.scleropages.sentarum.promotion.goods.model.impl.AbstractDetailedGoodsSource;
import io.scleropages.sentarum.promotion.rule.model.EvaluatorRule;

import javax.validation.constraints.Null;

/**
 * promotional goods source for evaluator rule references.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class EvaluatorGoodsSource extends AbstractDetailedGoodsSource {


    public static final int BIZ_TYPE_OF_EVALUATOR = 1;


    public static final int DETAILED_GOODS_SOURCE_TYPE = 4;//商品明细


    private EvaluatorRule evaluatorRule;

    @Null
    public EvaluatorRule getEvaluatorRule() {
        return evaluatorRule;
    }

    public void setEvaluatorRule(EvaluatorRule evaluatorRule) {
        this.evaluatorRule = evaluatorRule;
    }


    @Null
    public Integer getBizType() {
        return BIZ_TYPE_OF_EVALUATOR;
    }
}
