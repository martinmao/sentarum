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

import io.scleropages.sentarum.core.fsm.model.Event;
import io.scleropages.sentarum.core.fsm.model.InvocationConfig;
import io.scleropages.sentarum.core.fsm.model.State;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;
import io.scleropages.sentarum.core.fsm.model.impl.InvocationConfigModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineExecutionContextModel;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class TestStateAction extends InvocationConfigModel implements Action {
    @Override
    public void execute(InvocationConfig invocationConfig, State from, State to, Event event, StateMachineExecutionContext executionContext) {
        System.out.println("enter/exit state: from: " + toString(from) + " to: " + toString(to) + " on: " + toString(event) + " with: " + toString(executionContext));
    }

    @Override
    public Class invocationConfigClass() {
        return getClass();
    }


    public static String toString(State state) {
        return null != state ? state.name() : null;
    }

    public static  String toString(Event event) {
        return null != event ? event.name() : null;
    }

    public static String toString(StateMachineExecutionContext executionContext) {
        return null != executionContext ? ((StateMachineExecutionContextModel) executionContext).getContextPayload() : null;
    }
}
