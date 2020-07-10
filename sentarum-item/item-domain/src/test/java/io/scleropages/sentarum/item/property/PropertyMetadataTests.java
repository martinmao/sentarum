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
package io.scleropages.sentarum.item.property;


import com.google.common.collect.Lists;
import io.scleropages.sentarum.item.property.model.PropertyInputValidators;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValueType;
import io.scleropages.sentarum.item.property.model.constraint.ConstraintDepends;
import io.scleropages.sentarum.item.property.model.constraint.Max;
import io.scleropages.sentarum.item.property.model.constraint.MaxLength;
import io.scleropages.sentarum.item.property.model.constraint.Min;
import io.scleropages.sentarum.item.property.model.constraint.MinLength;
import io.scleropages.sentarum.item.property.model.constraint.NotNull;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataBean;
import io.scleropages.sentarum.item.property.model.impl.SourceValueBean;
import io.scleropages.sentarum.item.property.model.input.InputText;
import junit.framework.TestCase;
import org.junit.Test;
import org.scleropages.core.mapper.JsonMapper2;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */

public class PropertyMetadataTests extends TestCase {


    @Test
    public void testFlatPropertyMetadata() {

        //create metadata
        PropertyMetadataBean address = new PropertyMetadataBean();
        address.setId(1l);
        address.setName("address");
        address.setTag("地址");
        address.setDescription("详细地址信息");
        address.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        address.setValueType(PropertyValueType.TEXT);
        address.setConstraints(Lists.newArrayList(new MinLength(11), new MaxLength(32), new NotNull()));
        address.setInput(new InputText());
        System.out.println(JsonMapper2.toJson(address));

        assertSame(NotNull.class, PropertyInputValidators.validate(address).getClass());

        InputText addressInput = (InputText) address.getInput();
        addressInput.setValue("印度尼西亚-加里曼丹");

        assertSame(MinLength.class, PropertyInputValidators.validate(address).getClass());

        addressInput.setValue("印度尼西亚-加里曼丹-卡普瓦斯河上游-圣塔伦大湖-龙哥龙鱼繁殖场");

        assertNull(PropertyInputValidators.validate(address));

        addressInput.setValue("印度尼西亚-加里曼丹-卡普瓦斯河上游-圣塔伦大湖-龙哥龙鱼繁殖场1");

        assertNotNull(PropertyInputValidators.validate(address));


        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//


        PropertyMetadataBean price = new PropertyMetadataBean();
        price.setId(2l);
        price.setName("price");
        price.setTag("价格");
        price.setDescription("商品价格");
        price.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        price.setValueType(PropertyValueType.DECIMAL);

        NotNull priceNotNull = new NotNull();//设置依赖规则，address如果不为空，则price也不为空.
        ConstraintDepends constraintDepends = new ConstraintDepends();
        priceNotNull.setConstraintDepends(constraintDepends);
        constraintDepends.setConjunction(ConstraintDepends.Conjunction.AND);
        constraintDepends.addConstraintDepend(new ConstraintDepends.ConstraintDepend(address.getName(), ConstraintDepends.Operator.NOT_NULL));


        price.setConstraints(Lists.newArrayList(priceNotNull, new Min(0L), new Max(9999L)));
        price.setInput(new InputText());
        System.out.println(JsonMapper2.toJson(price));
        InputText priceInput = (InputText) price.getInput();
        priceInput.setValue(null);

        assertSame(NotNull.class, PropertyInputValidators.validate(price).getClass());

        addressInput.setValue(null);

        assertNull(PropertyInputValidators.validate(address, price));

        addressInput.setValue("印度尼西亚-加里曼丹-卡普瓦斯河上游-圣塔伦大湖-龙哥龙鱼繁殖场");

        assertSame(NotNull.class, PropertyInputValidators.validate(address, price).getClass());

        priceInput.setValue("-0.01");
        assertSame(Min.class, PropertyInputValidators.validate(address, price).getClass());

        priceInput.setValue("-0.00");
        assertNull(PropertyInputValidators.validate(address, price));

        priceInput.setValue("9999.01");
        assertSame(Max.class, PropertyInputValidators.validate(address, price).getClass());

        priceInput.setValue("9999.00");
        assertNull(PropertyInputValidators.validate(address, price));
    }


    public void testHierarchyPropertyMetadata() {

        PropertyMetadataBean brand = new PropertyMetadataBean();
        brand.setId(1L);
        brand.setName("brand");
        brand.setTag("品牌");
        brand.setDescription("品牌作为品类的一个关键属性");
        brand.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_ROOT_PROPERTY);
        brand.setValueType(PropertyValueType.LONG);
        brand.setValuesSource(() -> Lists.newArrayList(new SourceValueBean(1L, 1L, "Apple"), new SourceValueBean(2L, 2L, "IBM")));


        PropertyMetadataBean series = new PropertyMetadataBean();
        series.setId(2L);
        series.setName("series");
        series.setTag("系列");
        series.setDescription("品牌系列作为品类的一个关键属性，属于品牌，二级属性");
        series.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_NODE_PROPERTY);
        series.setValueType(PropertyValueType.PROPERTY_REF);
        series.setRefId(1L);
        series.setValuesSource(() -> Lists.newArrayList(new SourceValueBean(3L, 1L, "Mac", 1L), new SourceValueBean(4L, 1L, "iPhone", 1L), new SourceValueBean(5L, 2L, "InfoSphere", 2L)));


        PropertyMetadataBean model = new PropertyMetadataBean();
        model.setId(3L);
        model.setName("madia");
        model.setTag("型号");
        model.setDescription("型号作为品类的一个关键属性，隶属系列，三级属性");
        model.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_LEAF_PROPERTY);
        model.setValueType(PropertyValueType.PROPERTY_REF);
        model.setRefId(2L);
        model.setValuesSource(() -> Lists.newArrayList(new SourceValueBean(6L, 1L, "MacBookPro-13-inch", 3L), new SourceValueBean(7L, 1L, "iPhoneX", 4L), new SourceValueBean(8L, 2L, "InfoSphere Application Server", 5L)));
    }

}
