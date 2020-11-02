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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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

    //6位编码地址表
    private static final Map<String, BaseAddress> CODE_TO_ADDRESS = Maps.newHashMap();
    //区号地址表
    private static final Map<String, BaseAddress> AREA_CODE_TO_ADDRESS = Maps.newHashMap();
    //重复区号地址表,存在多个市使用相同区号的情况
    private static final Map<String, List<BaseAddress>> REDUPLICATE_AREA_CODE_TO_ADDRESS = Maps.newHashMap();


    private static final AtomicBoolean READ_FLAG = new AtomicBoolean(false);


    public static final void initIfNecessary(BaseAddressReader reader) {
        if (READ_FLAG.compareAndSet(false, true)) {
            List<BaseAddress> reads = reader.read();
            reads.forEach(baseAddress -> {
                CODE_TO_ADDRESS.put(baseAddress.code(), baseAddress);
                if (baseAddress.isCity()) {
                    String telAreaCode = baseAddress.telAreaCode();
                    if (!StringUtils.hasText(telAreaCode))
                        return;
                    BaseAddress associated = AREA_CODE_TO_ADDRESS.putIfAbsent(telAreaCode, baseAddress);
                    if (null != associated) {//存在多个市使用相同区号的情况，重复区号的城市将放入 REDUPLICATE_AREA_CODE_TO_ADDRESS
                        List<BaseAddress> reduplicate = REDUPLICATE_AREA_CODE_TO_ADDRESS.computeIfAbsent(telAreaCode, key -> Lists.newArrayList());
                        reduplicate.add(baseAddress);
                    }
                }
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
        assertInitialized();
        return Optional.of(CODE_TO_ADDRESS.get(code)).orElseThrow(() -> new IllegalArgumentException("no such base address found for given code: " + code));
    }

    /**
     * return city base address by area code (区号,三位010 或 四位0571)
     *
     * @param telAreaCode
     * @return
     */
    public static final BaseAddress getBaseAddressByTelArea(String telAreaCode) {
        Assert.hasText(telAreaCode, "tel areaCode must not empty.");
        assertInitialized();
        return Optional.of(AREA_CODE_TO_ADDRESS.get(telAreaCode)).orElseThrow(() -> new IllegalArgumentException("no such base address found for given tel area code: " + telAreaCode));
    }

    public static final List<BaseAddress> getBaseAddressesByTelArea(String telAreaCode) {
        BaseAddress baseAddress = getBaseAddressByTelArea(telAreaCode);
        List<BaseAddress> reduplicateAddresses = REDUPLICATE_AREA_CODE_TO_ADDRESS.get(telAreaCode);
        List<BaseAddress> baseAddresses = Lists.newArrayList();
        if (null != reduplicateAddresses) {
            baseAddresses.addAll(reduplicateAddresses);
        }
        baseAddresses.add(baseAddress);
        return baseAddresses;
    }

    private static void assertInitialized() {
        Assert.isTrue(READ_FLAG.get(), "not initialized. call initIfNecessary first.");
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
