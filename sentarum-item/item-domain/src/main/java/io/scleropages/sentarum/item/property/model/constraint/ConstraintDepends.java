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

import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.property.model.Input;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;

import io.scleropages.sentarum.item.property.model.input.MultiInput;
import io.scleropages.sentarum.item.property.model.input.SingleInput;
import org.apache.commons.collections.ComparatorUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 约束依赖关系集.描述约束的依赖.
 * 例如：商品价格在商品上架时必须提供.其进行规则检查的前提是商品状态=1
 *
 * <pre>
 * {
 *   "propertyName": "price",
 *   "constraints": [
 *     {
 *       "constraint": "required",
 *       "depends": [
 *         {
 *           "conjunction": "AND",
 *           "constraintDepends": [
 *             {
 *               "propertyName": "status",
 *               "value": "1",
 *               "operator": "EQ"
 *             }
 *           ]
 *         }
 *       ]
 *     }
 *   ]
 * }
 *
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ConstraintDepends {


    public static final ConstraintDepends EMPTY_CONSTRAINT_DEPENDS = new ConstraintDepends();

    private Conjunction conjunction;

    private List<ConstraintDepend> constraintDepends;

    public Conjunction getConjunction() {
        return conjunction;
    }

    public void setConjunction(Conjunction conjunction) {
        this.conjunction = conjunction;
    }

    public List<ConstraintDepend> getConstraintDepends() {
        return constraintDepends;
    }

    public void setConstraintDepends(List<ConstraintDepend> constraintDepends) {
        this.constraintDepends = constraintDepends;
    }

    public void addConstraintDepend(ConstraintDepend constraintDepend) {
        if (null == getConstraintDepends()) {
            setConstraintDepends(Lists.newArrayList());
        }
        constraintDepends.add(constraintDepend);
    }


    /**
     * return true if given {@link ConstraintDepends} is empty.
     *
     * @param constraintDepends
     * @return
     */
    public static boolean isEmpty(ConstraintDepends constraintDepends) {
        if (null == constraintDepends)
            return true;
        if (Objects.equals(constraintDepends, EMPTY_CONSTRAINT_DEPENDS))
            return true;
        if (null == constraintDepends.getConstraintDepends())
            return true;
        return false;
    }

    /**
     * return true if current is empty.
     *
     * @return
     */
    public boolean isEmpty() {
        return ConstraintDepends.isEmpty(this);
    }


    public interface ConstraintDependsContext {

        PropertyMetadata getPropertyMetadata(String name);
    }


    /**
     * return current depends check result.
     *
     * @param dependsContext
     * @return
     */
    public boolean matches(ConstraintDependsContext dependsContext) {
        if (isEmpty())
            return true;
        final Comparator comparator = getComparator();
        int andMatchCount = 0;
        for (ConstraintDepend constraintDepend : constraintDepends) {
            String propertyName = constraintDepend.getPropertyName();
            String compareTo = constraintDepend.getValue();
            PropertyMetadata propertyMetadata = dependsContext.getPropertyMetadata(propertyName);
            Input input = propertyMetadata.input();
            boolean match = false;
            if (input instanceof SingleInput) {
                String value = ((SingleInput) input).getValue();
                switch (constraintDepend.operator) {
                    case NULL:
                        match = null == value;
                        break;
                    case NOT_NULL:
                        match = null != value;
                        break;
                    case EQ:
                        match = Objects.equals(value, compareTo);
                        break;
                    case NEQ:
                        match = !Objects.equals(value, compareTo);
                        break;
                    case GT:
                        match = comparator.compare(value, compareTo) > 0;
                        break;
                    case GTE:
                        match = comparator.compare(value, compareTo) >= 0;
                        break;
                    case LT:
                        match = comparator.compare(value, compareTo) < 0;
                        break;
                    case LTE:
                        match = comparator.compare(value, compareTo) <= 0;
                        break;
                }
            } else if (input instanceof MultiInput) {
                List<String> values = ((MultiInput) input).values();
                if (constraintDepend.operator == Operator.IN)
                    match = values.contains(compareTo);
                else if (constraintDepend.operator == Operator.NOT_IN)
                    match = !values.contains(compareTo);
            }
            if (match) {
                andMatchCount++;
                if (conjunction == Conjunction.OR)
                    return true;
            }
        }
        return andMatchCount == constraintDepends.size();
    }

    protected Comparator getComparator() {
        return ComparatorUtils.naturalComparator();
    }

    /**
     * 一组依赖的组合关系: OR，AND
     */
    public enum Conjunction {
        OR, AND
    }

    /**
     * 依赖操作符
     * <pre>
     * = 目标属性的值相等
     * != 目标属性值不相等
     * > 目标属性值大于
     * < 目标属性值小于
     * >= 目标属性值大于等于
     * <= 目标属性值小于等于
     * NULL 目标属性值为空
     * NOT_NULL 目标属性值不为空
     * IN 目标属性值列表包含
     * NOT_IN 目标属性值列表不包含
     * </pre>
     */
    public enum Operator {
        EQ, NEQ, GT, LT, GTE, LTE, NULL, NOT_NULL, //适用于单值 SingleInput
        IN, NOT_IN //适用于多值 MultiInput
    }


    /**
     * 约束依赖关系条目.
     */
    public static class ConstraintDepend {

        private String propertyName;
        private String value;
        private Operator operator;


        public ConstraintDepend(String propertyName, String value, Operator operator) {
            this.propertyName = propertyName;
            this.value = value;
            this.operator = operator;
        }

        public ConstraintDepend(String propertyName, Operator operator) {
            this.propertyName = propertyName;
            this.operator = operator;
        }

        public ConstraintDepend() {
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Operator getOperator() {
            return operator;
        }

        public void setOperator(Operator operator) {
            this.operator = operator;
        }
    }
}
