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

import com.google.common.collect.Maps;
import org.scleropages.crud.FrameworkContext;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * utility class for {@link BaseAddress}
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class BaseAddresses {

    private static final Map<String, BaseAddress> CODE_TO_ADDRESS = Maps.newHashMap();

    private static final Map<String, BaseAddress> AREA_CODE_TO_ADDRESS = Maps.newHashMap();

    private static final AtomicBoolean INIT_FLAG = new AtomicBoolean(false);


    public static final void initIfNecessary() {
        if (INIT_FLAG.compareAndSet(false, true)) {
            BaseAddressReader reader = FrameworkContext.getBean(BaseAddressReader.class);
            List<BaseAddress> reads = reader.read();
            reads.forEach(baseAddress -> {
                CODE_TO_ADDRESS.put(baseAddress.code(), baseAddress);
                if (baseAddress.isCity())
                    AREA_CODE_TO_ADDRESS.put(baseAddress.areaCode(), baseAddress);
            });
        }
    }

    /**
     * return base address by given code (6位完整编码)
     *
     * @param code
     * @return
     * @throws IllegalArgumentException if no base address found.
     */
    public static final BaseAddress getBaseAddress(String code) {
        Assert.hasText(code, "code must not empty.");
        Assert.isTrue(INIT_FLAG.get(), "call initIfNecessary first.");
        return Optional.of(CODE_TO_ADDRESS.get(code)).orElseThrow(() -> new IllegalArgumentException("no such base address found for given code: " + code));
    }

    /**
     * return city base address by area code (区号,三位010 或 四位0571)
     *
     * @param areaCode
     * @return
     */
    public static final BaseAddress getBaseAddressByArea(String areaCode) {
        Assert.hasText(areaCode, "areaCode must not empty.");
        Assert.isTrue(INIT_FLAG.get(), "call initIfNecessary first.");
        return Optional.of(AREA_CODE_TO_ADDRESS.get(areaCode)).orElseThrow(() -> new IllegalArgumentException("no such base address found for given area code: " + areaCode));
    }

    /**
     * return true if given area code is valid.
     *
     * @return
     */
    public static final boolean isAreaCode(String areaCode) {
        return AREA_CODE_TO_ADDRESS.containsKey(areaCode);
    }

}
