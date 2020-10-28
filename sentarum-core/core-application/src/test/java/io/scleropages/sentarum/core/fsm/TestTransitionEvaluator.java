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
import io.scleropages.sentarum.core.fsm.model.impl.InvocationConfigModel;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class TestTransitionEvaluator extends InvocationConfigModel implements TransitionEvaluator {


    private String e1;
    private Integer e2;

    @Override
    public boolean evaluate(InvocationConfig invocationConfig, StateMachineExecutionContext executionContext) {
        System.out.println(invocationConfig.toString());
        System.out.println(TestStateAction.toString(executionContext));
        return true;
    }

    @Override
    public Class invocationConfigClass() {
        return getClass();
    }

    public String getE1() {
        return e1;
    }

    public void setE1(String e1) {
        this.e1 = e1;
    }

    public Integer getE2() {
        return e2;
    }

    public void setE2(Integer e2) {
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return "TestTransitionEvaluator{" +
                "e1='" + e1 + '\'' +
                ", e2=" + e2 +
                '}';
    }
}
