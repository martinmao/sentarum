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

import io.scleropages.sentarum.item.model.property.input.BasicInput;
import io.scleropages.sentarum.item.model.property.input.ComplexInput;
import org.apache.commons.collections.ComparatorUtils;

import java.util.List;

/**
 * 属性组定义，描述属性组或属性组元数据
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface GroupedPropertyMetadata extends PropertyMetadata {

    @Override
    default Boolean keyed() {//组合属性不可能是键类型的（能被检索的），组合属性无法直接存储
        return false;
    }

    @Override
    default PropertyStructureType structureType() {//组合属性不支持层级关系
        return PropertyStructureType.FLAT_PROPERTY;
    }

    @Override
    default PropertyValueType valueType() {//组合属性没有值
        throw new IllegalStateException("unsupported operation.");
    }

    @Override
    default Input input() {
        ComplexInput complexInput = new ComplexInput();
        orderedPropertiesMetadata().forEach(orderedPropertyMetadata -> {
            complexInput.addBasicInput((BasicInput) orderedPropertyMetadata.input());
        });
        return complexInput;
    }

    /**
     * 返回已排序的属性列表
     *
     * @return
     */
    List<OrderedPropertyMetadata> orderedPropertiesMetadata();


    /**
     * 含顺序的 {@link PropertyMetadata}
     */
    class OrderedPropertyMetadata implements PropertyMetadata, Comparable<OrderedPropertyMetadata> {

        private float order;

        private PropertyMetadata propertyMetadata;


        public float getOrder() {
            return order;
        }

        public void setOrder(float order) {
            this.order = order;
        }

        public PropertyMetadata getPropertyMetadata() {
            return propertyMetadata;
        }

        public void setPropertyMetadata(PropertyMetadata propertyMetadata) {
            this.propertyMetadata = propertyMetadata;
        }

        @Override
        public int compareTo(OrderedPropertyMetadata o) {
            return ComparatorUtils.naturalComparator().compare(getOrder(), o.getOrder());
        }

        @Override
        public Long id() {
            return propertyMetadata.id();
        }

        @Override
        public String name() {
            return propertyMetadata.name();
        }

        @Override
        public String tag() {
            return propertyMetadata.tag();
        }

        @Override
        public String description() {
            return propertyMetadata.description();
        }

        @Override
        public Boolean keyed() {
            return propertyMetadata.keyed();
        }

        @Override
        public Integer bizType() {
            return propertyMetadata.bizType();
        }

        @Override
        public PropertyValueType valueType() {
            return propertyMetadata.valueType();
        }

        @Override
        public PropertyStructureType structureType() {
            return propertyMetadata.structureType();
        }

        @Override
        public Input input() {
            return propertyMetadata.input();
        }

        @Override
        public ValuesSource valuesSource() {
            return propertyMetadata.valuesSource();
        }

        @Override
        public List<Constraint> constraints() {
            return propertyMetadata.constraints();
        }
    }
}
