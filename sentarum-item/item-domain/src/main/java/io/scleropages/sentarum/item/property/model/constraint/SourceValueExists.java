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
import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.input.MultiInput;
import io.scleropages.sentarum.item.property.model.input.SingleInput;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class SourceValueExists extends Constraint {

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, SingleInput input) {
        ValuesSource valuesSource = propertyMetadata.valuesSource();
        if (valuesSource == null)
            return true;
        try {
            return null != valuesSource.readValue(valuesSource.id(), Long.valueOf(input.getValue()));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, MultiInput input) {
        ValuesSource valuesSource = propertyMetadata.valuesSource();
        if (valuesSource == null)
            return true;
        try {
            for (String value : input.values()) {
                if (null == valuesSource.readValue(valuesSource.id(), Long.valueOf(value))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected String internalMessage() {
        return "no match value found from values source.";
    }

    @Override
    public void assertValid() {

    }
}
