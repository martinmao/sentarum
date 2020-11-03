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
package io.scleropages.sentarum.core.entity.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * referenced from: {@link io.scleropages.sentarum.core.model.primitive.BaseAddress}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Embeddable
public class EmbeddableBaseAddress {

    private String code;
    private String name;

    @Column(name = "district_code", nullable = false, length = 16)
    public String getCode() {
        return code;
    }

    @Column(name = "district_", nullable = false, length = 16)
    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}
