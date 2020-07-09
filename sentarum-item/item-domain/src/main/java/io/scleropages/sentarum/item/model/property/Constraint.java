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
package io.scleropages.sentarum.item.model.property;

import io.scleropages.sentarum.item.model.property.constraint.ConstraintDepends;
import io.scleropages.sentarum.item.model.property.constraint.NotEmpty;
import io.scleropages.sentarum.item.model.property.constraint.NotNull;
import io.scleropages.sentarum.item.model.property.input.MultiInput;
import io.scleropages.sentarum.item.model.property.input.SingleInput;
import org.springframework.core.Ordered;

/**
 * 描述属性约束
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class Constraint implements Ordered {

    private String message;

    private ConstraintDepends constraintDepends;

    public ConstraintDepends getConstraintDepends() {
        return constraintDepends;
    }

    public void setConstraintDepends(ConstraintDepends constraintDepends) {
        this.constraintDepends = constraintDepends;
    }

    public boolean validate(PropertyMetadata propertyMetadata) {
        return validate(propertyMetadata, propertyMetadata.input());
    }

    public boolean validate(PropertyMetadata propertyMetadata, Input input) {
        if (input instanceof SingleInput)
            return validateInternal(propertyMetadata, (SingleInput) input);
        if (propertyMetadata.input() instanceof MultiInput)
            return validateInternal(propertyMetadata, (MultiInput) input);
        throw new IllegalArgumentException("unsupported input type: " + input);
    }

    /**
     * {@link NotNull} {@link NotEmpty} 等应该具有最高的优先级
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    abstract protected boolean validateInternal(PropertyMetadata propertyMetadata, SingleInput input);

    abstract protected boolean validateInternal(PropertyMetadata propertyMetadata, MultiInput input);

    abstract protected String internalMessage();


    public String getMessage() {
        return null != message ? message : internalMessage();
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
