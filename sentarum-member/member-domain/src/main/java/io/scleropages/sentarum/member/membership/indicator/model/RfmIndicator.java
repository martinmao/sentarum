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
package io.scleropages.sentarum.member.membership.indicator.model;

import io.scleropages.sentarum.core.model.primitive.Amount;
import io.scleropages.sentarum.member.membership.model.IndicatorDefinition;
import io.scleropages.sentarum.member.membership.model.IndicatorLine;

import static io.scleropages.sentarum.member.membership.indicator.model.RfmIndicator.*;

/**
 * represent indicator of rfm (Recency Frequency Monetary).
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface RfmIndicator extends IndicatorDefinition<RfmIndicatorLine> {


    /**
     * represent a rfm indicator line.
     */
    interface RfmIndicatorLine extends IndicatorLine {


        /**
         * R，最近的一次消费（天数）
         *
         * @return
         */
        Integer recency();

        /**
         * score of recency.
         *
         * @return
         */
        Integer recencyScore();


        /**
         * F，消费频次（次数）
         *
         * @return
         */
        Integer frequency();

        /**
         * score of frequency.
         *
         * @return
         */
        Integer frequencyScore();


        /**
         * M，总消费（金额）
         *
         * @return
         */
        Amount monetary();

        /**
         * score of monetary.
         *
         * @return
         */
        Integer monetaryScore();
    }
}
