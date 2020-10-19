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
package io.scleropages.sentarum.core.fsm;

import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;

/**
 * evaluator transition of execution
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface TransitionEvaluator {

    /**
     * evaluate given execution context test whether change state to another
     * return true if successful, false otherwise.
     *
     * @param invocationConfig
     * @param executionContext
     * @return
     */
    boolean evaluate(InvocationConfig invocationConfig, StateMachineExecutionContext executionContext);


    /**
     * associated class of invocation config implementation.
     *
     * @return
     */
    Class invocationConfigClass();
}
