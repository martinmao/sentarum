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
package statemachine;

import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.Condition;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.StateMachineConfiguration;
import org.squirrelframework.foundation.fsm.StateMachineStatus;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SimpleStateMachineTests {

    static class SampleStateMachine extends AbstractUntypedStateMachine {

        protected void enteredS1(Object from, Object to, Object event, Object context) {
            System.out.println("enteredS1:   " + event + "(" + from + "->" + to + ")" + context);
        }

        protected void exitedS1(Object from, Object to, Object event, Object context) {
            System.out.println("exitedS1:  " + event + "(" + from + "->" + to + ")" + context);
        }


        protected void s2ToS3(Object from, Object to, Object event, Object context) {
            System.out.println("s2ToS3");
        }

        //follow methods always call and not required to registered.
        protected void beforeActionInvoked(Object from, Object to, Object event, Object context) {
        }

        protected void afterTransitionCausedException(Exception e, Object from, Object to, Object event, Object context) {
        }

        protected void beforeTransitionBegin(Object from, Object event, Object context) {
        }

        protected void afterTransitionCompleted(Object from, Object to, Object event, Object context) {
        }

        protected void afterTransitionEnd(Object from, Object to, Object event, Object context) {
        }

        protected void afterTransitionDeclined(Object from, Object event, Object context) {
            throw new IllegalArgumentException("不支持的 event");
        }

        @Override
        protected void afterTransitionCausedException(Object fromState, Object toState, Object event, Object context) {
            //默认情况下：状态机异常将会终止状态机执行，此处进行重置
            setStatus(StateMachineStatus.IDLE);
            //super.afterTransitionCausedException(fromState, toState, event, context);
        }
    }


    public static void main(String[] args) {

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(SampleStateMachine.class);

        builder.externalTransition().from("S1").to("S2").on("E1").perform(new Action<UntypedStateMachine, Object, Object, Object>() {
            @Override
            public void execute(Object from, Object to, Object event, Object context, UntypedStateMachine stateMachine) {
                System.out.println(event + "(" + from + "->" + to + ")" + context);
            }

            @Override
            public String name() {
                return "this must not null.";
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
                System.out.println("condition fired: " + context);
                return true;
            }

            @Override
            public String name() {
                return null;
            }
        }).callMethod("s2ToS3");

        builder.onEntry("S1").callMethod("enteredS1");
        builder.onExit("S1").callMethod("exitedS1");


        //序列状态，串行，自动只会进入第一个子状态
        builder.defineSequentialStatesOn("S3", "S31", "S32", "S33");
        builder.externalTransition().from("S3").to("S4").on("E3");
        builder.externalTransition().from("S31").to("S32").on("E3_1");
        builder.externalTransition().from("S32").to("S33").on("E3_2");
        builder.externalTransition().from("S33").to("S5").on("E3_3");

        //并发状态，自动进入全部子状态
        builder.defineParallelStatesOn("S5", "S51", "S52");
        builder.defineSequentialStatesOn("S51", "S511", "S512");
        builder.defineSequentialStatesOn("S52", "S521", "S522");
        builder.externalTransition().from("S511").to("S512").on("E5_1");
        builder.externalTransition().from("S521").to("S522").on("E5_2");
        builder.defineFinalState("S512");
        builder.defineFinalState("S522");


        builder.defineFinishEvent("S5_FINISHED");

        builder.externalTransition().from("S5").to("S6").on("S5_FINISHED");

        builder.defineFinalState("S6");

        // after 50ms delay fire event "END" every 100ms from S6 with null context
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        SquirrelSingletonProvider.getInstance().register(ScheduledExecutorService.class, scheduler);
//        builder.defineTimedState("S5", 50, 100, "END", null);
//        builder.externalTransition().from("S5").to("S_END").on("END");


        UntypedStateMachine fsm = builder.newStateMachine("S1", StateMachineConfiguration.create().enableDebugMode(true));

        //fsm.addTransitionXXXXXListener(); for each transition event listener.
        //fsm.addXXXXListener(); for other else.
        //fsm.addDeclarativeListener(); 支持以注解方式申明，参考StateMachineLogger
        fsm.addStateMachineListener(event -> {
            System.out.println("fsm listener event: " + event.getClass().getSimpleName());
        });


        fsm.start("fsm context");
        fsm.fire("E1", "fsm context1");
        System.out.println("Current state is " + fsm.getCurrentState());
        fsm.fire("E2", "fsm context2");
        System.out.println("Current state is " + fsm.getCurrentState());

        System.out.println("########################");
        fsm.fire("E3_1");
        System.out.println("Current state is " + fsm.getCurrentState());
        fsm.fire("E3_2");
        System.out.println("Current state is " + fsm.getCurrentState());
        fsm.fire("E3_3");
        System.out.println("Current state is " + fsm.getCurrentState());

        System.out.println(fsm.getSubStatesOn("S5"));

        fsm.fire("E5_1");

        System.out.println(fsm.getSubStatesOn("S5"));

        fsm.fire("E5_2");

        System.out.println(fsm.getSubStatesOn("S5"));

        System.out.println("Current state is " + fsm.getCurrentState());

        System.out.println(fsm.canAccept("xxxxx"));
        System.out.println(fsm.isTerminated());

        fsm = builder.newStateMachine("S5");
        fsm.fire("E5_1");
        System.out.println(fsm.isTerminated());
        fsm.fire("E5_2");
        System.out.println(fsm.isTerminated());
//      System.out.println(fsm.exportXMLDefinition(true));

    }
}
