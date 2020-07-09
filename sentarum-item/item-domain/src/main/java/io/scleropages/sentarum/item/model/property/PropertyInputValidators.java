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
package io.scleropages.sentarum.item.model.property;

import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.model.property.constraint.ConstraintDepends;
import org.springframework.core.OrderComparator;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 工具类，用于检查 {@link Input}是否符合属性元数据中定义的一组的约束({@link Constraint}).
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class PropertyInputValidators {

    /**
     * validate given property metadata ({@link Input} already binding to metadata).
     *
     * @param propertyMetadata
     * @return If the check passes, it returns null. otherwise it returns the constraint that failed
     */
    public static Constraint validate(PropertyMetadata propertyMetadata) {

        Assert.notNull(propertyMetadata, "propertyMetadata must not be null.");

        if (propertyMetadata instanceof GroupedPropertyMetadata) {
            GroupedPropertyMetadata groupedPropertyMetadata = (GroupedPropertyMetadata) propertyMetadata;
            for (GroupedPropertyMetadata.OrderedPropertyMetadata metadata : groupedPropertyMetadata.orderedPropertiesMetadata()) {
                Constraint validate = validate(metadata);
                if (null != validate)
                    return validate;
            }
        }
        return validateInternal(propertyMetadata, propertyMetadata.input());
    }


    /**
     * validate given property metadata and input.
     *
     * @param propertyMetadata
     * @param input
     * @return If the check passes, it returns null. otherwise it returns the constraint that failed
     */
    public static Constraint validate(PropertyMetadata propertyMetadata, Input input) {
        Assert.notNull(propertyMetadata, "propertyMetadata must not be null.");
        if (propertyMetadata instanceof GroupedPropertyMetadata)
            throw new IllegalArgumentException("not support grouped property metadata. use validate(PropertyMetadata propertyMetadata) instead.");
        return validateInternal(propertyMetadata, input);
    }


    /**
     * validate given properties metadata. also support constraint depends check.{@link Constraint#getConstraintDepends()}
     *
     * @param propertiesMetadata
     * @return If the check passes, it returns null. otherwise it returns the constraint that failed
     */
    public static Constraint validate(PropertyMetadata... propertiesMetadata) {
        Assert.notNull(propertiesMetadata, "propertiesMetadata must not empty.");
        if (propertiesMetadata.length == 1) {
            return validate(propertiesMetadata[0]);
        }
        Map<String, PropertyMetadata> namedPropertiesMetadata = Maps.newHashMap();
        for (PropertyMetadata property : propertiesMetadata) {
            namedPropertiesMetadata.put(property.name(), property);
        }

        for (PropertyMetadata property : propertiesMetadata) {
            List<Constraint> constraints = property.constraints();
            for (Constraint constraint : constraints) {
                ConstraintDepends constraintDepends = constraint.getConstraintDepends();
                if (null != constraintDepends && !constraintDepends.isEmpty()) {
                    if (constraintDepends.matches(name -> namedPropertiesMetadata.get(name))) {
                        Constraint validate = validate(property);
                        if (null != validate)
                            return validate;
                    }
                }
            }
        }
        return null;
    }


    /**
     * assert given property metadata is valid (contains an input).
     *
     * @param propertyMetadata
     * @throws IllegalArgumentException when check is failed.
     */
    public static void assertInputValid(PropertyMetadata propertyMetadata) {
        Constraint validate = validate(propertyMetadata);
        Assert.isNull(validate, () -> propertyMetadata.name() + "(" + propertyMetadata.tag() + ")" + validate.getMessage());
    }

    private static Constraint validateInternal(PropertyMetadata propertyMetadata, Input input) {
        Assert.notNull(input, "input must not be null.");
        List<Constraint> constraints = propertyMetadata.constraints();
        if (null == constraints || constraints.size() == 0) {
            return null;
        }
        OrderComparator.sort(constraints);
        for (Constraint constraint : constraints) {
            if (!constraint.validate(propertyMetadata, input))
                return constraint;
        }
        return null;
    }
}
