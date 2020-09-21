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
package io.scleropages.sentarum.core.model.primitive;

import static io.scleropages.sentarum.core.model.primitive.BaseAddress.NameSuffix.*;

/**
 * domain primitive of base address (province,city,area).
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface BaseAddress {

    /**
     * 唯一标识
     *
     * @return
     */
    Long id();

    /**
     * 上级标识
     *
     * @return
     */
    Long parentId();

    /**
     * 短编码
     *
     * @return
     */
    String shortCode();

    /**
     * 编码
     *
     * @return
     */
    String code();

    /**
     * 显示名
     *
     * @return
     */
    String name();

    /**
     * 区号
     *
     * @return
     */
    String areaCode();

    /**
     * 显示名后缀，例如：省，市，区...
     *
     * @return
     */
    NameSuffix nameSuffix();


    /**
     * return true if {@link #nameSuffix()}==PROVINCE
     *
     * @return
     */
    default boolean isProvince() {
        return nameSuffix() == PROVINCE;
    }

    /**
     * return true if {@link #nameSuffix()}==CITY
     *
     * @return
     */
    default boolean isCity() {
        return nameSuffix() == CITY;
    }

    /**
     * return true if {@link #nameSuffix()}==AREA
     *
     * @return
     */
    default boolean isArea() {
        return nameSuffix() == AREA;
    }


    enum NameSuffix {

        PROVINCE, CITY, AREA
    }
}
