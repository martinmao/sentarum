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

import com.google.common.collect.Maps;
import io.scleropages.sentarum.core.fsm.mgmt.StateMachineManager;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecution;
import io.scleropages.sentarum.core.fsm.model.StateMachineExecutionContext;
import io.scleropages.sentarum.core.fsm.model.impl.EventDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateTransitionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class StateMachineTests {

    @Autowired
    private StateMachineManager stateMachineManager;

    @Test
    public void _1_test() {
        TestStateAction stateChangeActionConfig = new TestStateAction();
        stateChangeActionConfig.setInvocationImplementation("testStateAction");

        TestAction transitionActionConfig = new TestAction();
        transitionActionConfig.setA1("a1");
        transitionActionConfig.setA2(2);
        transitionActionConfig.setInvocationImplementation("testAction");

        TestTransitionEvaluator transitionEvaluatorConfig = new TestTransitionEvaluator();
        transitionEvaluatorConfig.setE1("e1");
        transitionEvaluatorConfig.setE2(2);
        transitionEvaluatorConfig.setInvocationImplementation("testTransitionEvaluator");


        StateModel locked = new StateModel();
        locked.setValue(1001);
        locked.setName("LOCKED");
        locked.setTag("锁定");
        locked.setDesc("门禁系统处于锁定状态");
        locked.setEnteredActionConfig(stateChangeActionConfig);
        stateMachineManager.createState(locked);

        StateModel unlocked = new StateModel();
        unlocked.setValue(1002);
        unlocked.setName("UNLOCKED");
        unlocked.setTag("解锁");
        unlocked.setDesc("门禁系统处于解锁状态");
        unlocked.setExitActionConfig(stateChangeActionConfig);
        stateMachineManager.createState(unlocked);

        EventDefinitionModel card = new EventDefinitionModel();
        card.setName("CARD");
        card.setTag("刷门卡");
        card.setDesc("刷门卡事件行为");
        stateMachineManager.createEventDefinition(card);

        EventDefinitionModel push = new EventDefinitionModel();
        push.setName("PUSH");
        push.setTag("推门");
        push.setDesc("推门进入事件行为");
        stateMachineManager.createEventDefinition(push);

        StateMachineDefinitionModel cardPush = new StateMachineDefinitionModel();
        cardPush.setName("CARD_PUSH");
        cardPush.setTag("门禁状态机");
        cardPush.setDesc("门禁状态机");
        cardPush.setAutoStartup(true);
        stateMachineManager.createStateMachineDefinition(cardPush, stateMachineManager.getStateByName("LOCKED").id());

        StateTransitionModel transition = new StateTransitionModel();
        transition.setActionConfig(transitionActionConfig);
        transition.setEvaluatorConfig(transitionEvaluatorConfig);
        //        CARD
        //LOCKED------->UNLOCKED
        stateMachineManager.createStateTransition(transition,
                stateMachineManager.getStateMachineDefinitionByName("CARD_PUSH").id(),
                stateMachineManager.getStateByName("LOCKED").id(),
                stateMachineManager.getStateByName("UNLOCKED").id(),
                stateMachineManager.getEventDefinitionByName("CARD").id()
        );
        //        PUSH
        //UNLOCKED------->LOCKED
        stateMachineManager.createStateTransition(transition,
                stateMachineManager.getStateMachineDefinitionByName("CARD_PUSH").id(),
                stateMachineManager.getStateByName("UNLOCKED").id(),
                stateMachineManager.getStateByName("LOCKED").id(),
                stateMachineManager.getEventDefinitionByName("PUSH").id()
        );

        System.out.println("##################################################");

        Map<String, Object> cardEventBody = Maps.newHashMap();
        cardEventBody.put("x", 1);
        cardEventBody.put("y", "xxxxxxxxxxxx");
        cardEventBody.put("z", new Date());
        Map<String, Object> contextAttributes = Maps.newHashMap();
        contextAttributes.put("1", "1");
        StateMachine stateMachine = stateMachineManager.createStateMachine(stateMachineManager.getStateMachineDefinitionByName("CARD_PUSH").id(), 1, 1l, contextAttributes);
        contextAttributes.remove("1");
        contextAttributes.put("2", "2");
        stateMachine.sendEvent(stateMachineManager.createEvent("PUSH", null), contextAttributes);
        contextAttributes.clear();
        contextAttributes.put("3", "3");
        stateMachine.sendEvent(stateMachineManager.createEvent("CARD", cardEventBody), contextAttributes);
    }

    @Test
    public void _2_test() {
        StateMachine stateMachine = stateMachineManager.getStateMachine(1, 1l);

        StateMachineExecution stateMachineExecution = stateMachine.stateMachineExecution();

        System.out.println(stateMachineExecution.currentState().name());

        StateMachineExecutionContext executionContext = stateMachineExecution.executionContext();

        executionContext.setAttribute("test_2_1", "test_2_1");
        executionContext.removeAttribute("3");
//        executionContext.save();

        Map<String, Object> contextAttributes = Maps.newHashMap();
        contextAttributes.put("test_2_2", "test_2_2");

        Map<String, Object> eventBody = Maps.newHashMap();
        eventBody.put("test_2_x", 1);
        eventBody.put("test_2_x", "zzzzzzzzz");
        eventBody.put("test_2_x", new Date());

        stateMachine.sendEvent(stateMachineManager.createEvent("CARD", eventBody), contextAttributes);
        contextAttributes.remove("test_2_2");
        contextAttributes.put("test_2_3", "test_2_3");
        stateMachine.sendEvent(stateMachineManager.createEvent("PUSH", eventBody), contextAttributes);
    }
}
