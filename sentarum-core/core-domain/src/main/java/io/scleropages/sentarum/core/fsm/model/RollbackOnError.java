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

import io.scleropages.sentarum.core.fsm.Action;

/**
 * an identified interface. if error was occurred on {@link Action}. determined whether transition succeed.<br>
 * you can in your owned implementation of {@link Action} or {@link InvocationConfig} class declaring add this interface.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface RollbackOnError {

    /**
     * return true the current transaction will roll back. default return false.
     *
     * @return
     */
    default boolean rollback() {
        return false;
    }
}
