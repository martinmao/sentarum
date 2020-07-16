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
package io.scleropages.sentarum.item.property.model.constraint;

import io.scleropages.sentarum.item.property.model.Constraint;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.input.MultiInput;
import io.scleropages.sentarum.item.property.model.input.SingleInput;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 数值最大值约束.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Max extends Constraint {

    private Long max;

    public Max() {
    }

    public Max(Long max) {
        this.max = max;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, SingleInput input) {
        String value = input.getValue();
        if (null == value)
            return true;
        int compare = Min.compare(value, max);
        if (compare == Integer.MIN_VALUE)
            return false;
        return compare <= 0;
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, MultiInput input) {
        List<String> values = input.values();
        if (values.size() == 0)
            return true;
        int matchCount = 0;
        for (String value : values) {
            int compare = Min.compare(value, max);
            if (compare <= 0 && compare != Integer.MIN_VALUE) {
                matchCount++;
            }
        }
        if (matchCount == values.size())
            return true;
        return false;
    }

    @Override
    protected String internalMessage() {
        return "超过最大值限定: " + max;
    }

    @Override
    public void assertValid() {
        Assert.notNull(max, "max is required for: " + getName());
    }
}
