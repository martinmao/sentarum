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
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class TestAction extends InvocationConfigModel implements Action {

    private String a1;
    private Integer a2;


    @Override
    public void execute(InvocationConfig invocationConfig, State from, State to, Event event, StateMachineExecutionContext executionContext) {

        System.out.println(invocationConfig.toString());

        System.out.println("from: " + TestStateAction.toString(from) + " to: " + TestStateAction.toString(to) + " on: " + TestStateAction.toString(event) + " with: " + TestStateAction.toString(executionContext));
//
//        throw new IllegalStateException("xxxxx");
    }

    @Override
    public Class invocationConfigClass() {
        return getClass();
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public Integer getA2() {
        return a2;
    }

    public void setA2(Integer a2) {
        this.a2 = a2;
    }

    @Override
    public String toString() {
        return "TestAction{" +
                "a1='" + a1 + '\'' +
                ", a2=" + a2 +
                '}';
    }
}
