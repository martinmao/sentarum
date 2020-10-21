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
package io.scleropages.sentarum.core.fsm.model;

/**
 * defined a state transition of fsm (Finite-state machine).
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateTransition {


    /**
     * id of this transition.
     *
     * @return
     */
    Long id();

    /**
     * associated fsm definition of this transition.
     *
     * @return
     */
    StateMachineDefinition stateMachineDefinition();

    /**
     * source state from of this transition.
     *
     * @return
     */
    State from();

    /**
     * target state to of this transition.
     *
     * @return
     */
    State to();

    /**
     * associated event definition. the event will cause the fms state changes during transition.
     *
     * @return
     */
    EventDefinition event();


    /**
     * if entered event fired. the evaluator will check whether the conditions for switching from one state to another.
     * see {@link io.scleropages.sentarum.core.fsm.TransitionEvaluator}
     * @return
     */
    InvocationConfig evaluatorConfig();


    /**
     * action config to be performed during transition.
     * see {@link io.scleropages.sentarum.core.fsm.Action}
     *
     * @return
     */
    InvocationConfig actionConfig();
}
