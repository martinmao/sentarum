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
import org.springframework.util.StringUtils;

/**
 * 非空约束.适用于字符串，数组，集合不允许为空
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class NotEmpty extends Constraint {
    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, SingleInput input) {
        return StringUtils.hasText(input.getValue());
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, MultiInput input) {
        return input.values().size() > 1;
    }

    @Override
    protected String internalMessage() {
        return "内容不允许为空白";
    }


    @Override
    public int getOrder() {
        return Integer.MIN_VALUE - 1;
    }

    @Override
    public void assertValid() {
    }
}
