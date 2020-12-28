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
package io.scleropages.sentarum.member.hierarchy.indicator.model;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.member.hierarchy.model.IndicatorDefinition;
import io.scleropages.sentarum.member.hierarchy.model.IndicatorLine;

import static io.scleropages.sentarum.member.hierarchy.indicator.model.ConsumingIndicator.*;

/**
 * represent indicator of customer historic consumings.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface ConsumingIndicator extends IndicatorDefinition<ConsumingIndicatorLine> {


    /**
     * represent line of customer historic consumings.
     */
    interface ConsumingIndicatorLine extends IndicatorLine {

        /**
         * 历史消费总金额
         *
         * @return
         */
        Amount historicConsumingAmount();

        /**
         * score of total amount.
         *
         * @return
         */
        Integer score();
    }
}
