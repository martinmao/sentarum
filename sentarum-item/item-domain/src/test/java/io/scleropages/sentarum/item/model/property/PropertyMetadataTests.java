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


import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.model.property.constraint.MaxLength;
import io.scleropages.sentarum.item.model.property.constraint.NotNull;
import io.scleropages.sentarum.item.model.property.impl.PropertyMetadataBean;
import io.scleropages.sentarum.item.model.property.input.InputText;
import org.junit.Test;
import org.scleropages.core.mapper.JsonMapper2;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */

public class PropertyMetadataTests {


    @Test
    public void testFlatPropertyMetadata() {

        PropertyMetadataBean address = new PropertyMetadataBean();
        address.setId(1l);
        address.setName("address");
        address.setTag("地址");
        address.setDescription("详细地址信息");
        address.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        address.setValueType(PropertyValueType.TEXT);
        address.setConstraints(Lists.newArrayList(new NotNull(), new MaxLength(20)));
        address.setInput(new InputText());



        System.out.println(JsonMapper2.toJson(address));
    }


    public void testHierarchyPropertyMetadata() {

        PropertyMetadataBean brandName = new PropertyMetadataBean();
        brandName.setId(1L);
        brandName.setName("brandName");
        brandName.setTag("品牌名称");
        brandName.setDescription("品牌名称作为品类的一个关键属性");
        brandName.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_NODE_PROPERTY);
        brandName.setValueType(PropertyValueType.PROPERTY_REF);

        PropertyMetadataBean seriesName = new PropertyMetadataBean();
        seriesName.setId(2L);
        seriesName.setName("seriesName");
        seriesName.setTag("系列名称");
        seriesName.setDescription("品牌系列作为品类的一个关键属性，属于品牌，二级属性");
        seriesName.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_NODE_PROPERTY);
        brandName.setValueType(PropertyValueType.PROPERTY_REF);


        PropertyMetadataBean modelName = new PropertyMetadataBean();
        modelName.setName("modelName");
        brandName.setTag("型号名称");
        brandName.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_LEAF_PROPERTY);

    }

}
