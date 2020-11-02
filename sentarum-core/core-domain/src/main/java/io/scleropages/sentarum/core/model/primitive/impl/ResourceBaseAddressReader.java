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
package io.scleropages.sentarum.core.model.primitive.impl;

import com.google.common.collect.Lists;
import io.scleropages.sentarum.core.model.primitive.BaseAddress;
import io.scleropages.sentarum.core.model.primitive.BaseAddressReader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static io.scleropages.sentarum.core.model.primitive.BaseAddress.NameSuffix.*;

/**
 * a default implementation. load {@link io.scleropages.sentarum.core.model.primitive.BaseAddress} from specify resource.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class ResourceBaseAddressReader implements BaseAddressReader, ResourceLoaderAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String DEFAULT_RESOURCE_LOCATION = "classpath:/district-full.tsv";

    private ResourceLoader resourceLoader;

    private String location = DEFAULT_RESOURCE_LOCATION;

    public ResourceBaseAddressReader() {
    }

    public ResourceBaseAddressReader(String location) {
        this.location = location;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public List<BaseAddress> read() {
        if (logger.isDebugEnabled()) {
            logger.debug("Loading divisions data files from: " + location);
        }
        Assert.hasText(location, "location must not be empty.");
        Resource resource = resourceLoader.getResource(location);
        List<BaseAddress> addresses = Lists.newArrayList();
        InputStream in;
        try {
            in = resource.getInputStream();
            Assert.notNull(in, "no input stream of: " + location);
        } catch (IOException e) {
            throw new IllegalStateException("failure to read divisions data file. ", e);
        }
        try {
            List<String> strings = IOUtils.readLines(in, "utf-8");
            List<String> noStandards = Lists.newArrayList();
            strings.forEach(s -> {
                BaseAddress baseAddress = createBaseAddress(s);
                if (null == baseAddress)
                    return;
                if (!baseAddress.isStandard()) {
                    noStandards.add(baseAddress.name());
                    return;
                }
                addresses.add(baseAddress);
            });
            logger.info("successfully loaded divisions data. entries size: total({})=valid({})+invalid({}) detail invalid address is: [{}]", strings.size(), addresses.size(), noStandards.size(), StringUtils.collectionToDelimitedString(noStandards, ","));
        } catch (Exception e) {
            throw new IllegalStateException("failure to read divisions data file.", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return addresses;
    }


    /**
     * referenced from: <a href="https://github.com/eduosi/district">https://github.com/eduosi/district</a>
     * <pre>
     * ID
     * 名称
     * 父 ID
     * 拼音首字母
     * 拼音首字母集合（如：齐齐哈尔 - qqhe）
     * 拼音
     * 附加说明（如：广西壮族自治区 - 壮族）可能为空字段
     * 行政级别（如：广西壮族自治区 - 自治区）可能为空字段
     * 行政代码（基本对应身份证号码前6位）
     * 区号
     * 排序
     * </pre>
     *
     * @param row
     * @return
     */
    protected BaseAddress createBaseAddress(String row) {
        if (row.startsWith("#")) {
            return null;
        }

        String[] rowData = StringUtils.delimitedListToStringArray(row, "\t");
        BaseAddressImpl baseAddress = new BaseAddressImpl();
        try {
            baseAddress.id = Integer.valueOf(rowData[0]);
            baseAddress.name = rowData[1];
            baseAddress.parentId = Integer.valueOf(rowData[2]);
            String code = rowData[8];
            if (!StringUtils.hasText(code)) { //非标准地址
                return baseAddress;
            }
            Assert.hasText(code, "code must not be null.");
            baseAddress.code = Integer.valueOf(code);
            if (code.length() != 6) { //非标准地址
                return baseAddress;
            }
            String shortCode;
            if (code.endsWith("0000")) {
                //'0000' 结尾为省，截取前两位数字
                shortCode = code.substring(0, 2);
                baseAddress.nameSuffix = PROVINCE;
            } else if (code.endsWith("00")) {
                //'00' 结尾为市，截取中间两位
                shortCode = code.substring(2, 4);
                baseAddress.nameSuffix = CITY;
            } else {
                shortCode = code.substring(4, 6);
                baseAddress.nameSuffix = AREA;
            }
            baseAddress.shortCode = Integer.valueOf(shortCode);
            baseAddress.telAreaCode = rowData[9];
            baseAddress.markAsStandard();//标记为标准地址

        } catch (Exception e) {
            throw new IllegalStateException("failure to parse base address from row: '" + row + "'. caused: ", e);
        }
        return baseAddress;
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    private class BaseAddressImpl implements BaseAddress {

        private Integer id;
        private Integer parentId;
        private Integer shortCode;
        private Integer code;
        private String name;
        private String telAreaCode;
        private NameSuffix nameSuffix;
        private boolean standard = false;


        @Override
        public Integer id() {
            return id;
        }

        @Override
        public Integer parentId() {
            return parentId;
        }

        @Override
        public Integer shortCode() {
            return shortCode;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public String telAreaCode() {
            return telAreaCode;
        }

        @Override
        public NameSuffix nameSuffix() {
            return nameSuffix;
        }

        @Override
        public boolean isStandard() {
            return standard;
        }

        private void markAsStandard() {
            standard = true;
        }
    }

}
