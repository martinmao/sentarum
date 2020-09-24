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

/**
 * defined a trade node transition.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface NodeTransition {


    /**
     * id of this transition.
     *
     * @return
     */
    Long id();

    /**
     * associated trading flow
     *
     * @return
     */
    TradingFlow tradingFlow();

    /**
     * source of node.
     *
     * @return
     */
    TradingNode source();

    /**
     * target of node.
     *
     * @return
     */
    TradingNode target();

    /**
     * associated incoming event. the event will cause the flowing from {@link #source()} to {@link #target()}
     *
     * @return
     */
    EventDefinition incomingEvent();

    /**
     * if incoming event fired. the associated outgoing event will throw out.
     *
     * @return
     */
    EventDefinition outgoingEvent();
}
