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
package springsource.statemachine;

import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.Condition;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SimpleStateMachineTests {

    static class SampleStateMachine extends AbstractUntypedStateMachine {

        protected void s1ToS2(String from, String to, String event, Object context) {
            System.out.println("s1ToS2");
        }

        protected void s2ToS3(Object from, Object to, Object event, Object context) {
            System.out.println("s2ToS3");
        }
    }


    public static void main(String[] args) {

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(SampleStateMachine.class);
        builder.externalTransition().from("S1").to("S2").on("E1").perform(new Action<UntypedStateMachine, Object, Object, Object>() {
            @Override
            public void execute(Object from, Object to, Object event, Object context, UntypedStateMachine stateMachine) {
                System.out.println(event + ": " + from + "->" + to + " {" + context + "}");
            }

            @Override
            public String name() {
                return null;
            }

            @Override
            public int weight() {
                return 0;
            }

            @Override
            public boolean isAsync() {
                return false;
            }

            @Override
            public long timeout() {
                return 0;
            }
        });
        builder.externalTransition().from("S2").to("S3").on("E2").when(new Condition<Object>() {
            @Override
            public boolean isSatisfied(Object context) {
                System.out.println("condition fired: "+context);
                return true;
            }

            @Override
            public String name() {
                return null;
            }
        }).callMethod("s2ToS3");

        UntypedStateMachine fsm = builder.newStateMachine("S1");
        fsm.fire("E1", "E1 fsm context");
        System.out.println("Current state is " + fsm.getCurrentState());
        fsm.fire("E2", "E2 fsm context");
        System.out.println("Current state is " + fsm.getCurrentState());
    }
}
