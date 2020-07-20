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
package io.scleropages.sentarum.item;

import com.google.common.collect.Maps;
import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValueType;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.constraint.ConstraintDepends;
import io.scleropages.sentarum.item.property.model.constraint.Max;
import io.scleropages.sentarum.item.property.model.constraint.MaxLength;
import io.scleropages.sentarum.item.property.model.constraint.Min;
import io.scleropages.sentarum.item.property.model.constraint.MinLength;
import io.scleropages.sentarum.item.property.model.constraint.NotNull;
import io.scleropages.sentarum.item.property.model.impl.GroupedPropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;
import io.scleropages.sentarum.item.property.model.input.InputText;
import io.scleropages.sentarum.item.property.model.input.SingleCheck;
import io.scleropages.sentarum.item.property.model.vs.NativeValuesSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class PropertyManagerTestcase {

    @Autowired
    private PropertyManager propertyManager;

    @Test
    public void testFlatPropertyCreation() {
        PropertyMetadataModel address = new PropertyMetadataModel();
        address.setName("address");
        address.setTag("地址");
        address.setDescription("详细地址信息");
        address.setKeyed(false);
        address.setBizType(1);
        address.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        address.setValueType(PropertyValueType.TEXT);
        address.setInput(new InputText());
        propertyManager.createPropertyMetadata(address);

        Map<String, Object> search = Maps.newHashMap();
        search.put("name", "address");
        Page<PropertyMetadata> propertyMetadataPage = propertyManager.findPropertyMetadataPage(SearchFilter.SearchFilterBuilder.build(search), Pageable.unpaged());
        PropertyMetadata next = propertyMetadataPage.toList().iterator().next();
        System.out.println(JsonMapper2.toJson(next));
        MinLength minLength = new MinLength(11);
        MaxLength maxLength = new MaxLength(32);
        NotNull notNull = new NotNull();

        propertyManager.createConstraint(next.id(), minLength);
        propertyManager.createConstraint(next.id(), maxLength);
        propertyManager.createConstraint(next.id(), notNull);

        PropertyMetadata propertyMetadata = propertyManager.getPropertyMetadata(next.id());
        System.out.println(JsonMapper2.toJson(propertyMetadata));

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

        PropertyMetadataModel price = new PropertyMetadataModel();
        price.setName("price");
        price.setKeyed(false);
        price.setBizType(1);
        price.setTag("价格");
        price.setDescription("商品价格");
        price.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        price.setValueType(PropertyValueType.DECIMAL);
        price.setInput(new InputText());
        propertyManager.createPropertyMetadata(price);

        search.put("name", "price");
        propertyMetadataPage = propertyManager.findPropertyMetadataPage(SearchFilter.SearchFilterBuilder.build(search), Pageable.unpaged());
        next = propertyMetadataPage.toList().iterator().next();
        System.out.println(JsonMapper2.toJson(next));

        NotNull priceNotNull = new NotNull();//设置依赖规则，address如果不为空，则price也不为空.
        ConstraintDepends constraintDepends = new ConstraintDepends();
        priceNotNull.setConstraintDepends(constraintDepends);
        constraintDepends.setConjunction(ConstraintDepends.Conjunction.AND);
        constraintDepends.addConstraintDepend(new ConstraintDepends.ConstraintDepend(address.getName(), ConstraintDepends.Operator.NOT_NULL));


        Min min = new Min(0L);
        Max max = new Max(9999L);

        propertyManager.createConstraint(next.id(), priceNotNull);
        propertyManager.createConstraint(next.id(), min);
        propertyManager.createConstraint(next.id(), max);

        propertyMetadata = propertyManager.getPropertyMetadata(next.id());
        System.out.println(JsonMapper2.toJson(propertyMetadata));
    }


    @Test
    public void testHierarchyCreation() {
        PropertyMetadataModel brand = new PropertyMetadataModel();
        brand.setKeyed(false);
        brand.setBizType(1);
        brand.setName("brand");
        brand.setTag("品牌");
        brand.setDescription("品牌作为品类的一个关键属性");
        brand.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_ROOT_PROPERTY);
        brand.setValueType(PropertyValueType.LONG);
        brand.setInput(new SingleCheck());

        propertyManager.createPropertyMetadata(brand);

        Map<String, Object> propertyMetaSearch = Maps.newHashMap();
        propertyMetaSearch.put("name", "brand");

        PropertyMetadata brandPropertyMeta = propertyManager.findPropertyMetadataPage(SearchFilter.SearchFilterBuilder.build(propertyMetaSearch), Pageable.unpaged()).toList().iterator().next();

        Assert.assertNotNull(brandPropertyMeta);

        propertyManager.createValuesSource(new NativeValuesSource("brand.values", "品牌", "品牌列表"));

        Map<String, Object> valuesSourceSearch = Maps.newHashMap();
        valuesSourceSearch.put("name", "brand.values");
        ValuesSource brandValuesSource = propertyManager.findValuesSourcePage(SearchFilter.SearchFilterBuilder.build(valuesSourceSearch), Pageable.unpaged()).iterator().next();

        propertyManager.bindValuesSourceToPropertyMetadata(brandValuesSource.id(),brandPropertyMeta.id());

        brandPropertyMeta = propertyManager.getPropertyMetadata(brandPropertyMeta.id());
        System.out.println(JsonMapper2.toJson(brandPropertyMeta));

        propertyManager.createSourceValue(new SourceValueModel(1L, "Apple", brandValuesSource.id()));
        propertyManager.createSourceValue(new SourceValueModel(2L, "IBM", brandValuesSource.id()));

        SourceValueModel valuesSearch = new SourceValueModel();
        valuesSearch.setValuesSourceId(brandPropertyMeta.valuesSource().id());

        List<? extends ValuesSource.SourceValue> brandValues = propertyManager.findSourceValuePage(valuesSearch, Pageable.unpaged()).toList();


        PropertyMetadataModel series = new PropertyMetadataModel();
        series.setKeyed(false);
        series.setBizType(1);
        series.setName("series");
        series.setTag("系列");
        series.setDescription("品牌系列作为品类的一个关键属性，属于品牌，二级属性");
        series.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_NODE_PROPERTY);
        series.setValueType(PropertyValueType.PROPERTY_REF);
        series.setRefId(brandPropertyMeta.id());
        series.setInput(new SingleCheck());

        propertyManager.createPropertyMetadata(series);

        propertyMetaSearch.put("name", "series");

        PropertyMetadata seriesPropertyMeta = propertyManager.findPropertyMetadataPage(SearchFilter.SearchFilterBuilder.build(propertyMetaSearch), Pageable.unpaged()).toList().iterator().next();

        Assert.assertNotNull(seriesPropertyMeta);

        propertyManager.createValuesSource(new NativeValuesSource("series.values", "系列", "系列列表"));

        valuesSourceSearch.put("name", "series.values");
        ValuesSource seriesValuesSource = propertyManager.findValuesSourcePage(SearchFilter.SearchFilterBuilder.build(valuesSourceSearch), Pageable.unpaged()).iterator().next();

        propertyManager.bindValuesSourceToPropertyMetadata(seriesValuesSource.id(),seriesPropertyMeta.id());

        seriesPropertyMeta = propertyManager.getPropertyMetadata(seriesPropertyMeta.id());

        System.out.println(JsonMapper2.toJson(seriesPropertyMeta));


        for (ValuesSource.SourceValue brandValue : brandValues) {
            if (Objects.equals(brandValue.value(), 1L)) {//apple
                propertyManager.createSourceValue(new SourceValueModel(1L, "Mac", seriesPropertyMeta.valuesSource().id(), brandValue.id()));
                propertyManager.createSourceValue(new SourceValueModel(2L, "iPhone", seriesPropertyMeta.valuesSource().id(), brandValue.id()));
            } else if (Objects.equals(brandValue.value(), 2L)) {// ibm
                propertyManager.createSourceValue(new SourceValueModel(3L, "InfoSphere", seriesPropertyMeta.valuesSource().id(), brandValue.id()));
            }
        }

        valuesSearch.setValuesSourceId(seriesPropertyMeta.valuesSource().id());

        List<? extends ValuesSource.SourceValue> seriesValues = propertyManager.findSourceValuePage(valuesSearch, Pageable.unpaged()).toList();


        PropertyMetadataModel model = new PropertyMetadataModel();
        model.setKeyed(false);
        model.setBizType(1);
        model.setName("model");
        model.setTag("型号");
        model.setDescription("型号作为品类的一个关键属性，隶属系列，三级属性");
        model.setStructureType(PropertyMetadata.PropertyStructureType.HIERARCHY_LEAF_PROPERTY);
        model.setValueType(PropertyValueType.PROPERTY_REF);
        model.setRefId(seriesPropertyMeta.id());
        model.setInput(new SingleCheck());

        propertyManager.createPropertyMetadata(model);

        propertyMetaSearch.put("name", "model");

        PropertyMetadata modelPropertyMeta = propertyManager.findPropertyMetadataPage(SearchFilter.SearchFilterBuilder.build(propertyMetaSearch), Pageable.unpaged()).toList().iterator().next();

        Assert.assertNotNull(modelPropertyMeta);
        propertyManager.createValuesSource(new NativeValuesSource("model.values", "型号", "型号列表"));


        valuesSourceSearch.put("name", "model.values");
        ValuesSource modelValuesSource = propertyManager.findValuesSourcePage(SearchFilter.SearchFilterBuilder.build(valuesSourceSearch), Pageable.unpaged()).iterator().next();

        propertyManager.bindValuesSourceToPropertyMetadata(modelValuesSource.id(),modelPropertyMeta.id());

        modelPropertyMeta = propertyManager.getPropertyMetadata(modelPropertyMeta.id());

        System.out.println(JsonMapper2.toJson(modelPropertyMeta));


        for (ValuesSource.SourceValue seriesValue : seriesValues) {
            if (Objects.equals(seriesValue.value(), 1L)) {//Mac
                propertyManager.createSourceValue(new SourceValueModel(1L, "MacBookPro-13-inch", modelPropertyMeta.valuesSource().id(), seriesValue.id()));
            }
            if (Objects.equals(seriesValue.value(), 2L)) {//iPhone
                propertyManager.createSourceValue(new SourceValueModel(2L, "iPhoneX", modelPropertyMeta.valuesSource().id(), seriesValue.id()));
            }
            if (Objects.equals(seriesValue.value(), 3L)) {//InfoSphere
                propertyManager.createSourceValue(new SourceValueModel(3L, "InfoSphere Application Server", modelPropertyMeta.valuesSource().id(), seriesValue.id()));
            }
        }

        GroupedPropertyMetadataModel groupedPropertyMetadataModel = new GroupedPropertyMetadataModel();

        groupedPropertyMetadataModel.setName("brand_series_model");
        groupedPropertyMetadataModel.setTag("品牌系列型号");
        groupedPropertyMetadataModel.setDescription("品牌系列型号描述");

        propertyManager.createGroupedPropertyMetadata(groupedPropertyMetadataModel);

        Map<String, Object> groupSearch = Maps.newHashMap();
        groupSearch.put("name", "brand_series_model");
        GroupedPropertyMetadata groupedPropertyMetadata = propertyManager.findGroupedPropertyMetadataPage(SearchFilter.SearchFilterBuilder.build(groupSearch), Pageable.unpaged()).iterator().next();

        propertyManager.addPropertyMetadataToGroup(groupedPropertyMetadata.id(), brandPropertyMeta.id(), 0.1f);
        propertyManager.addPropertyMetadataToGroup(groupedPropertyMetadata.id(), modelPropertyMeta.id(), 0.3f);
        propertyManager.addPropertyMetadataToGroup(groupedPropertyMetadata.id(), seriesPropertyMeta.id(), 0.2f);

        propertyManager.findAllPropertyMetadataInGroup(groupedPropertyMetadata.id()).forEach(o -> System.out.println(JsonMapper2.toJson(o)));
    }

}
