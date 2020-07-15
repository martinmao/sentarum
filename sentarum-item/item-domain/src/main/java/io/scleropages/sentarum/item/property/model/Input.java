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
package io.scleropages.sentarum.item.property.model;

import io.scleropages.sentarum.item.property.model.input.ComplexInput;
import io.scleropages.sentarum.item.property.model.input.InputText;
import io.scleropages.sentarum.item.property.model.input.MultiCheck;
import io.scleropages.sentarum.item.property.model.input.MultiComplexInput;
import io.scleropages.sentarum.item.property.model.input.MultiInputText;
import io.scleropages.sentarum.item.property.model.input.SingleCheck;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 与输入相关，定义了一组输入类型名称.具体的输入类型需实现该接口.input不能独立存在，其必须绑定到特定的属性定义上 {@link PropertyMetadata}.
 * 属性定义作为一个工厂接口，结合属性定义上下文创建合适的input 实例.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface Input {


    enum InputType {
        /**
         * 单值文本输入型.
         */
        TEXT(1, "单值文本输入型", InputText.class),
        /**
         * 多值文本输入型.
         */
        MULTI_TEXT(2, "多值文本输入型", MultiInputText.class),
        /**
         * 单选（单值).
         */
        SINGLE_CHECK(3, "单选(单值)", SingleCheck.class),
        /**
         * 多选（多值).
         */
        MULTI_CHECK(4, "多选(多值)", MultiCheck.class),
        /**
         * 复合输入类型, 适用于 {@link GroupedPropertyMetadata}. 其中，元素类型只能是以上定义的基本输入类型（如: text,multiText,singleCheck,multiCheck.）
         * 举例：country，province，city三个 singleCheck合并定义为一个名为address的complex类型.
         */
        COMPLEX(5, "复合输入类型", ComplexInput.class),
        /**
         * 多复合输入类型, 适用于 {@link GroupedPropertyMetadata}.其中.元素类型只能是 complex（复合类型）.
         */
        MULTI_COMPLEX(6, "多复合输入类型", MultiComplexInput.class);

        /**
         * 显示指定 ordinal,避免定义顺序被意外变更.
         */
        private final int ordinal;
        /**
         * 显示名.
         */
        private final String tag;

        /**
         * input 类
         */
        private final Class inputClass;

        InputType(int ordinal, String tag, Class inputClass) {
            this.ordinal = ordinal;
            this.tag = tag;
            this.inputClass = inputClass;
        }

        private static final Map<String, InputType> nameMappings = new HashMap<>(12);
        private static final Map<Integer, InputType> ordinalMappings = new HashMap<>(12);


        static {
            for (InputType inputType : InputType.values()) {
                nameMappings.put(inputType.name(), inputType);
                ordinalMappings.put(inputType.getOrdinal(), inputType);
            }
        }

        public static InputType getByName(String name) {
            return (name != null ? nameMappings.get(name) : null);
        }

        public static InputType getByOrdinal(int ordinal) {
            return ordinalMappings.get(ordinal);
        }


        /**
         * return ordinal of this enum.
         *
         * @return
         */
        public int getOrdinal() {
            return ordinal;
        }

        /**
         * return display tag of this enum.
         *
         * @return
         */
        public String getTag() {
            return tag;
        }

        /**
         * return input class.
         *
         * @return
         */
        public Class getInputClass() {
            return inputClass;
        }


        /**
         * return true if given input is check input(single or multi)
         *
         * @param input
         * @return
         */
        public static boolean isCheckInput(Input input) {
            return input.getType() == SINGLE_CHECK || input.getType() == MULTI_CHECK;
        }

        /**
         * return true if given ordinal is check input(single or multi)
         *
         * @param ordinal
         * @return
         */
        public static boolean isCheckInput(Integer ordinal) {
            InputType inputType = getByOrdinal(ordinal);
            Assert.notNull(inputType, "no inputType found by given ordinal: " + ordinal);
            return inputType == SINGLE_CHECK || inputType == MULTI_CHECK;
        }
    }

    /**
     * 输入类型名称，实现类参考接口的名称进行返回.
     *
     * @return
     */
    InputType getType();
}
