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
package io.scleropages.sentarum.core.model.primitive.impl;

import io.scleropages.sentarum.core.model.primitive.BaseAddress;
import io.scleropages.sentarum.core.model.primitive.BaseAddressReader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * a default implementation. load {@link io.scleropages.sentarum.core.model.primitive.BaseAddress} from specify resource.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Component
public class ResourceBaseAddressReader implements BaseAddressReader, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    private String location;

    private Resource resource;


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    public void setLocation(String location) {
        this.location = location;
        this.resource = resourceLoader.getResource(location);
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public List<BaseAddress> read() {
        return null;
    }
}
