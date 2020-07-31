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
import io.scleropages.sentarum.item.category.model.Category;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryModel;
import io.scleropages.sentarum.item.mgmt.CategoryManager;
import io.scleropages.sentarum.item.mgmt.PropertyManager;
import io.scleropages.sentarum.item.mgmt.PropertyValueManager;
import io.scleropages.sentarum.item.mgmt.SpuManager;
import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValueType;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.impl.GroupedPropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;
import io.scleropages.sentarum.item.property.model.input.SingleCheck;
import io.scleropages.sentarum.item.property.model.vs.NativeValuesSource;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class SpuManagerTestcase {

    @Autowired
    protected PropertyValueManager propertyValueManager;
    @Autowired
    protected PropertyManager propertyManager;
    @Autowired
    protected CategoryManager categoryManager;
    @Autowired
    protected SpuManager spuManager;


    @Before
    public void categorySetup() {
        setupBSM();

        StandardCategoryModel shuma = new StandardCategoryModel();
        shuma.setStatus(Category.Status.VALID);
        shuma.setName("digital_products");
        shuma.setTag("数码");
        shuma.setDescription("数码产品");

        categoryManager.createStandardCategory(shuma, null);


    }


    /**
     * 创建品牌，系列，型号属性组
     */
    protected void setupBSM() {
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

        propertyManager.createValuesSource(new NativeValuesSource("brand.values", "品牌", "品牌列表"));

        Map<String, Object> valuesSourceSearch = Maps.newHashMap();
        valuesSourceSearch.put("name", "brand.values");
        ValuesSource brandValuesSource = propertyManager.findValuesSourcePage(SearchFilter.SearchFilterBuilder.build(valuesSourceSearch), Pageable.unpaged()).iterator().next();

        propertyManager.bindValuesSourceToPropertyMetadata(brandValuesSource.id(), brandPropertyMeta.id());

        brandPropertyMeta = propertyManager.getPropertyMetadata(brandPropertyMeta.id());

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

        propertyManager.createValuesSource(new NativeValuesSource("series.values", "系列", "系列列表"));

        valuesSourceSearch.put("name", "series.values");
        ValuesSource seriesValuesSource = propertyManager.findValuesSourcePage(SearchFilter.SearchFilterBuilder.build(valuesSourceSearch), Pageable.unpaged()).iterator().next();

        propertyManager.bindValuesSourceToPropertyMetadata(seriesValuesSource.id(), seriesPropertyMeta.id());

        seriesPropertyMeta = propertyManager.getPropertyMetadata(seriesPropertyMeta.id());

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

        propertyManager.createValuesSource(new NativeValuesSource("model.values", "型号", "型号列表"));


        valuesSourceSearch.put("name", "model.values");
        ValuesSource modelValuesSource = propertyManager.findValuesSourcePage(SearchFilter.SearchFilterBuilder.build(valuesSourceSearch), Pageable.unpaged()).iterator().next();

        propertyManager.bindValuesSourceToPropertyMetadata(modelValuesSource.id(), modelPropertyMeta.id());

        modelPropertyMeta = propertyManager.getPropertyMetadata(modelPropertyMeta.id());

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
    }

}
