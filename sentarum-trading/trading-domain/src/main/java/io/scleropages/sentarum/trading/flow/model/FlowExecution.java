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
package io.scleropages.sentarum.trading.flow.model;

import java.util.List;

/**
 * a flow execution of trading.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface FlowExecution {


    /**
     * id of this execution.
     *
     * @return
     */
    Long id();

    /**
     * associated flow of this execution.
     *
     * @return
     */
    TradingFlow tradingFlow();

    /**
     * current node of this execution.
     *
     * @return
     */
    TradingNode currentNode();


    /**
     * list of events was fired in this execution.
     *
     * @return
     */
    List<TradingEvent> events();

    /**
     * terminate this execution.
     *
     * @return
     */
    void terminate();

    /**
     * suspend this execution.
     */
    void suspend();

    /**
     * resume this execution.
     */
    void resume();
}
