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

import io.scleropages.sentarum.core.fsm.StateMachineExecutionListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * represent a fsm (Finite-state machine) execution.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface StateMachineExecution {


    /**
     * id of this execution.
     *
     * @return
     */
    Long id();

    /**
     * id of business
     *
     * @return
     */
    Long bizId();

    /**
     * type of business
     *
     * @return
     */
    Integer bizType();

    /**
     * associated definition of this fsm.
     *
     * @return
     */
    StateMachineDefinition stateMachineDefinition();

    /**
     * current state of this execution.
     *
     * @return
     */
    State currentState();

    /**
     * state of this execution.
     *
     * @return
     */
    ExecutionState executionState();

    /**
     * the execution listener of this execution.
     *
     * @return
     */
    StateMachineExecutionListener executionListener();


    /**
     * context of this execution.
     *
     * @return
     */
    StateMachineExecutionContext executionContext();


    /**
     * enum state of execution.
     */
    enum ExecutionState {

        RUNNING(1, "运行中", "状态机处于运行状态"),
        SUSPEND(2, "暂停", "状态机暂停，可通过resume恢复运行"),
        TERMINATE(3, "终止", "状态机被终止"),
        FINISHED(4, "完成", "状态机执行完成");

        private final int ordinal;
        private final String tag;
        private final String desc;

        ExecutionState(int ordinal, String tag, String desc) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.desc = desc;
        }

        /**
         * return true if current state is running.
         *
         * @return
         */
        public boolean isRunning() {
            return Objects.equals(this, RUNNING);
        }

        /**
         * return true if current state is suspend.
         *
         * @return
         */
        public boolean isSuspend() {
            return Objects.equals(this, SUSPEND);
        }

        /**
         * return true if current state is terminate.
         *
         * @return
         */
        public boolean isTerminate() {
            return Objects.equals(this, TERMINATE);
        }

        /**
         * return true if current state is finished.
         *
         * @return
         */
        public boolean isFinished() {
            return Objects.equals(this, FINISHED);
        }

        /**
         * return true if current state accepting a terminate command.
         *
         * @return
         */
        public boolean acceptingTerminate() {
            if (isTerminate() || isFinished())
                return false;
            return true;
        }

        /**
         * return true if current state accepting a suspend command.
         *
         * @return
         */
        public boolean acceptingSuspend() {
            return isRunning();
        }

        /**
         * return true if current state accepting a resume command.
         *
         * @return
         */
        public boolean acceptingResume() {
            return isSuspend();
        }

        /**
         * return true if current state accepting a event command.
         *
         * @return
         */
        public boolean acceptingEvents() {
            return isRunning();
        }


        public int getOrdinal() {
            return ordinal;
        }

        public String getTag() {
            return tag;
        }

        public String getDesc() {
            return desc;
        }


        private static final Map<String, ExecutionState> nameMappings = new HashMap<>();
        private static final Map<Integer, ExecutionState> ordinalMappings = new HashMap<>();

        static {
            for (ExecutionState executionState : ExecutionState.values()) {
                nameMappings.put(executionState.name(), executionState);
                ordinalMappings.put(executionState.getOrdinal(), executionState);
            }
        }


        public static ExecutionState getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static ExecutionState getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }
    }
}
