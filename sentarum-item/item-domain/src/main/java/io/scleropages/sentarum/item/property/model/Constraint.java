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
package io.scleropages.sentarum.item.property.model;

import io.scleropages.sentarum.item.property.model.constraint.ConstraintDepends;
import io.scleropages.sentarum.item.property.model.constraint.NotEmpty;
import io.scleropages.sentarum.item.property.model.constraint.NotNull;
import io.scleropages.sentarum.item.property.model.input.MultiInput;
import io.scleropages.sentarum.item.property.model.input.SingleInput;
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

    /**
     * return true if the check passes.
     *
     * @param propertyMetadata
     * @return
     */
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


    /**
     * 返回约束名称，实际返回的是约束的类名(不含包名).在进行持久化时，不会存储包信息
     * 这要求实现类必须确保统一实现在 {@link io.scleropages.sentarum.item.property.model.constraint}包下.
     *
     * @return
     */
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
