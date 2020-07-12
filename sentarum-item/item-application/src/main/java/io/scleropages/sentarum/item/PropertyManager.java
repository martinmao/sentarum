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
package io.scleropages.sentarum.item;

import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import io.scleropages.sentarum.item.property.repo.ConstraintRepository;
import io.scleropages.sentarum.item.property.repo.PropertyMetadataRepository;
import io.scleropages.sentarum.item.property.repo.SourceValueRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class PropertyManager {


    private PropertyMetadataRepository propertyMetadataRepository;
    private SourceValueRepository sourceValueRepository;
    private ConstraintRepository constraintRepository;


    public void createPropertyMetadata(PropertyMetadataModel propertyMetadataModel) {

    }


    @Autowired
    public void setPropertyMetadataRepository(PropertyMetadataRepository propertyMetadataRepository) {
        this.propertyMetadataRepository = propertyMetadataRepository;
    }

    @Autowired
    public void setSourceValueRepository(SourceValueRepository sourceValueRepository) {
        this.sourceValueRepository = sourceValueRepository;
    }

    @Autowired
    public void setConstraintRepository(ConstraintRepository constraintRepository) {
        this.constraintRepository = constraintRepository;
    }
}
