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

import com.google.common.collect.Maps;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import static statemachine.SpringStateMachineTests.Events;
import static statemachine.SpringStateMachineTests.States;


/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Configuration
//@SpringBootApplication
@EnableStateMachine
@Component
public class SpringStateMachineTests extends EnumStateMachineConfigurerAdapter<States, Events> implements StateMachinePersist<States, Events, Long>, CommandMarker, ApplicationContextAware {


    private static final Map<Long, States> memoryStatesRepository = Maps.newHashMap();


    @Override
    public void write(StateMachineContext<States, Events> context, Long contextObj) throws Exception {
        memoryStatesRepository.put(contextObj, context.getState());
    }

    @Override
    public StateMachineContext<States, Events> read(Long contextObj) throws Exception {
        return null;
    }


    public enum States {
        S1, S2, S3, SF
    }

    public enum Events {
        E1, E2, E3
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(listener()).machineId("sampleMachine");
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states.withStates()
                .initial(States.S1)
                .end(States.SF)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions.withExternal()
                .source(States.S1).target(States.S2).event(Events.E1)
                .and()
                .withExternal()
                .source(States.S2).target(States.S3).event(Events.E2)
                .and()
                .withExternal()
                .source(States.S3).target(States.SF).event(Events.E3);
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
                System.out.println("State change to " + to.getId());
            }
        };
    }

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @CliCommand(value = "sm event", help = "Sends an event to a state machine")
    public String event(@CliOption(key = {"", "event"}, mandatory = true, help = "The event") final String event) {
        StateMachine<States, Events> sm = applicationContext.getBean(StateMachine.class);
        sm.sendEvent(Events.valueOf(event));
        return "Event " + event + " send";
    }


    public static void main(String[] args) throws IOException {
//        ConfigurableApplicationContext applicationContext = SpringApplication.run(StateMachineTests.class, args);
//        StateMachine<States, Events> sm = applicationContext.getBean(StateMachine.class);
//        sm.sendEvent(Events.E1);
//        sm.sendEvent(Events.E2);
        Bootstrap.main(args);
    }

}
