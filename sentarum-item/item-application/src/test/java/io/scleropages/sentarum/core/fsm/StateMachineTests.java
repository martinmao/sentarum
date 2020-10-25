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

import io.scleropages.sentarum.core.fsm.mgmt.StateMachineManager;
import io.scleropages.sentarum.core.fsm.model.impl.EventDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateMachineDefinitionModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateModel;
import io.scleropages.sentarum.core.fsm.model.impl.StateTransitionModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class StateMachineTests {

    @Autowired
    private StateMachineManager stateMachineManager;

    @Test
    public void test() {
        StateModel locked = new StateModel();
        locked.setValue(1001);
        locked.setName("LOCKED");
        locked.setTag("锁定");
        locked.setDesc("门禁系统处于锁定状态");
        stateMachineManager.createState(locked);

        StateModel unlocked = new StateModel();
        unlocked.setValue(1002);
        unlocked.setName("UNLOCKED");
        unlocked.setTag("解锁");
        unlocked.setDesc("门禁系统处于解锁状态");
        stateMachineManager.createState(unlocked);

        EventDefinitionModel card = new EventDefinitionModel();
        card.setName("CARD");
        card.setTag("刷门卡");
        card.setDesc("刷门卡事件行为");
        stateMachineManager.createEventDefinition(card);

        EventDefinitionModel push = new EventDefinitionModel();
        card.setName("PUSH");
        card.setTag("推门");
        card.setDesc("推门进入事件行为");
        stateMachineManager.createEventDefinition(push);

        StateMachineDefinitionModel cardPush = new StateMachineDefinitionModel();
        cardPush.setName("CARD_PUSH");
        cardPush.setTag("门禁状态机");
        cardPush.setDesc("门禁状态机");
        cardPush.setAutoStartup(true);
        stateMachineManager.createStateMachineDefinition(cardPush, stateMachineManager.getStateByName("LOCKED").id());

        StateTransitionModel transition = new StateTransitionModel();

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
    }
}
