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

/**
 * 约束规则非常复杂，难以通过默认的约束描述，需要将这些提示显示给前端用户人工确保约束成立，或一些警告信息.例如改价这种风险性较高的操作.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Tip extends Constraint {

    private String tips;

    private String url;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, SingleInput input) {
        return false;
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, MultiInput input) {
        return false;
    }

    @Override
    protected String internalMessage() {
        return "系统提示";
    }

    @Override
    public void assertValid() {
        Assert.hasText(tips, "tips is required for: " + getName());
    }
}
