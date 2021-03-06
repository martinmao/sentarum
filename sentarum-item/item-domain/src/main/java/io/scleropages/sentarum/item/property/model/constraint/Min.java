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
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数值最小值约束
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Min extends Constraint {

    private Long min;

    public Min() {
    }

    public Min(Long min) {
        this.min = min;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, SingleInput input) {
        String value = input.getValue();
        if (null == value)
            return true;
        int compare = Min.compare(value, min);
        if (compare == Integer.MIN_VALUE)
            return false;
        return compare >= 0;
    }


    /**
     * 将文本转换为数字进行比较，如果转换文本失败，返回 {@link Integer#MIN_VALUE}
     *
     * @param value
     * @param compareTo
     * @return
     */
    protected static int compare(String value, Long compareTo) {
        int compare = Integer.MIN_VALUE;
        if (NumberUtils.isDigits(value)) {//完全由数字构成（小数，数字符号都不会匹配）全部当做long处理
            compare = NumberUtils.createLong(value).compareTo(Long.valueOf(compareTo));
        } else if (NumberUtils.isCreatable(value)) {//可以转化为数字全部当做big decimal处理
            try {
                compare = NumberUtils.createBigDecimal(value).compareTo(BigDecimal.valueOf(compareTo));
            } catch (NumberFormatException ex) {//8进制，16进制文本需要处理.
                compare = NumberUtils.createLong(value).compareTo(Long.valueOf(compareTo));
            }
        }
        return compare;
    }

    @Override
    protected boolean validateInternal(PropertyMetadata propertyMetadata, MultiInput input) {
        List<String> values = input.values();
        if (values.size() == 0)
            return true;
        int matchCount = 0;
        for (String value : values) {
            int compare = Min.compare(value, min);
            if (compare >= 0 && compare != Integer.MIN_VALUE) {
                matchCount++;
            }
        }
        if (matchCount == values.size())
            return true;
        return false;
    }

    @Override
    protected String internalMessage() {
        return "超过最小值限定: " + min;
    }

    @Override
    public void assertValid() {
        Assert.notNull(min, "min is required for: " + getName());
    }
}
