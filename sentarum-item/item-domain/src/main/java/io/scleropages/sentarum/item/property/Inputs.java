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
package io.scleropages.sentarum.item.property;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.property.model.Input;
import io.scleropages.sentarum.item.property.model.input.MultiInput;
import io.scleropages.sentarum.item.property.model.input.SingleInput;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Utility class provider some common operations for {@link io.scleropages.sentarum.item.property.model.Input}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class Inputs {


    public static final void addValues(Input input, Object... values) {
        Assert.notNull(input, "input must not be null.");
        if (null == values || values.length == 0)
            return;
        if (input instanceof SingleInput) {
            if (values.length > 1)
                throw new IllegalArgumentException("to much values for single input type: " + input.getType());
            ((SingleInput) input).setValue(String.valueOf(values[0]));
        } else if (input instanceof MultiInput) {
            for (Object value : values) {
                ((MultiInput) input).addValue(String.valueOf(value));
            }
        }
    }

    public static final List<Object> readValues(Input input) {
        Assert.notNull(input, "input must not be null.");
        if (input instanceof SingleInput) {
            return Lists.newArrayList(((SingleInput) input).getValue());
        }
        if (input instanceof MultiInput) {
            return Lists.newArrayList(((MultiInput) input).values());
        }
        throw new IllegalArgumentException("unsupported input: " + input);
    }
}
