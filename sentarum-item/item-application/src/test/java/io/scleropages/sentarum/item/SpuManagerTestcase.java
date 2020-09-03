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
import io.scleropages.sentarum.item.category.model.CategoryProperty;
import io.scleropages.sentarum.item.category.model.impl.CategoryPropertyModel;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryModel;
import io.scleropages.sentarum.item.mgmt.CategoryManager;
import io.scleropages.sentarum.item.mgmt.ItemManager;
import io.scleropages.sentarum.item.mgmt.PropertyManager;
import io.scleropages.sentarum.item.mgmt.PropertyValueManager;
import io.scleropages.sentarum.item.mgmt.SpuManager;
import io.scleropages.sentarum.item.model.Item;
import io.scleropages.sentarum.item.model.Spu;
import io.scleropages.sentarum.item.model.impl.ItemModel;
import io.scleropages.sentarum.item.model.impl.SpuModel;
import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValue;
import io.scleropages.sentarum.item.property.model.PropertyValueType;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.constraint.Max;
import io.scleropages.sentarum.item.property.model.constraint.Min;
import io.scleropages.sentarum.item.property.model.impl.GroupedPropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;
import io.scleropages.sentarum.item.property.model.input.InputText;
import io.scleropages.sentarum.item.property.model.input.SingleCheck;
import io.scleropages.sentarum.item.property.model.vs.NativeValuesSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.KEY_PROPERTY;
import static io.scleropages.sentarum.item.category.model.CategoryProperty.CategoryPropertyBizType.SPU_PROPERTY;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class SpuManagerTestcase {

    @Autowired
    protected PropertyValueManager propertyValueManager;
    @Autowired
    protected PropertyManager propertyManager;
    @Autowired
    protected CategoryManager categoryManager;
    @Autowired
    protected SpuManager spuManager;
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    protected ItemManager itemManager;


    @Test
    public void createIphoneX() {
        Map<String, Object> categorySearch = Maps.newHashMap();
        categorySearch.put("name", "digital_phone");

        StandardCategoryModel digitalPhone = (StandardCategoryModel) categoryManager.findStandardCategoryPage(SearchFilter.SearchFilterBuilder.build(categorySearch), Pageable.unpaged()).iterator().next();


        SpuModel iPhoneX = new SpuModel();
        iPhoneX.setName("iphoneX");
        iPhoneX.setTag("苹果 iPhone X 智能手机");
        iPhoneX.setMarketPrice(BigDecimal.valueOf(8888L));
        iPhoneX.setMarketTime(new Date());
        iPhoneX.setStatus(Spu.Status.VALID);


        Map<Long, Object> variables = Maps.newHashMap();

        List<CategoryProperty> categoryProperties = categoryManager.getAllCategoryProperties(digitalPhone.id(), KEY_PROPERTY, SPU_PROPERTY);


        categoryProperties.forEach(cp -> {
            SourceValueModel valueSearch = new SourceValueModel();
            if (cp.propertyMetadata().name().equals("brand")) {
                valueSearch.setValuesSourceId(propertyManager.getValuesSourceByName("brand.values").id());
                valueSearch.setValueTag("Apple");
                variables.put(cp.propertyMetadata().id(), propertyManager.findSourceValuePage(valueSearch, Pageable.unpaged()).iterator().next().value());
            } else if (cp.propertyMetadata().name().equals("series")) {
                valueSearch.setValuesSourceId(propertyManager.getValuesSourceByName("series.values").id());
                valueSearch.setValueTag("iPhone");
                variables.put(cp.propertyMetadata().id(), propertyManager.findSourceValuePage(valueSearch, Pageable.unpaged()).iterator().next().value());
            } else if (cp.propertyMetadata().name().equals("model")) {
                valueSearch.setValuesSourceId(propertyManager.getValuesSourceByName("model.values").id());
                valueSearch.setValueTag("iPhoneX");
                variables.put(cp.propertyMetadata().id(), propertyManager.findSourceValuePage(valueSearch, Pageable.unpaged()).iterator().next().value());
            } else if (cp.propertyMetadata().name().equals("weight")) {
                variables.put(cp.propertyMetadata().id(), "194");
            } else if (cp.propertyMetadata().name().equals("width")) {
                variables.put(cp.propertyMetadata().id(), "75.7");
            } else if (cp.propertyMetadata().name().equals("height")) {
                variables.put(cp.propertyMetadata().id(), "8.3");
            } else if (cp.propertyMetadata().name().equals("length")) {
                variables.put(cp.propertyMetadata().id(), "150.9");
            }
        });
        spuManager.createSpu(iPhoneX, digitalPhone.id(), variables);

        SourceValueModel valueSearch = new SourceValueModel();
        valueSearch.setValuesSourceId(propertyManager.getValuesSourceByName("series.values").id());

        valueSearch.setValueTag("iPhone");

        Map<String, Object> spuSearch = Maps.newHashMap();
        spuSearch.put("LT_marketPrice", 10000);


        Map<String, Object> propertySearch = Maps.newHashMap();
        propertySearch.put("series", propertyManager.findSourceValuePage(valueSearch, Pageable.unpaged()).iterator().next().value());

        valueSearch.setValuesSourceId(propertyManager.getValuesSourceByName("model.values").id());

        valueSearch.setValueTag("iPhoneX");

        propertySearch.put("IN_model", propertyManager.findSourceValuePage(valueSearch, Pageable.unpaged()).iterator().next().value());

        Page<Spu> spuPage = spuManager.findSpuPage(SearchFilter.SearchFilterBuilder.build(spuSearch), SearchFilter.SearchFilterBuilder.build(propertySearch), Pageable.unpaged(), Sort.unsorted());
        System.out.println(JsonMapper2.toJson(spuPage));

        spuPage.forEach(spu -> {
            System.out.println(JsonMapper2.toJson(spuManager.getSpu(spu.id())));
            System.out.println(JsonMapper2.toJson(spuManager.findAllKeyPropertyValues(spu.id())));
            System.out.println(JsonMapper2.toJson(spuManager.findAllSpuPropertyValues(spu.id())));
        });

        Spu next = spuPage.iterator().next();
        SpuModel update = new SpuModel();
        update.setId(next.id());
        update.setMarketPrice(new BigDecimal(9999L));
        Map<Long, Object> keysUpdate = Maps.newHashMap();
        Map<Long, Object> spuUpdate = Maps.newHashMap();

        List<? extends PropertyValue> allKeyPropertyValues = spuManager.findAllKeyPropertyValues(update.id());
        List<? extends PropertyValue> allSpuPropertyValues = spuManager.findAllSpuPropertyValues(update.id());

        allKeyPropertyValues.forEach(o -> {
            if (o.name().equals("model")) {
                keysUpdate.put(o.id(), 3L);
            }
        });
        allSpuPropertyValues.forEach(o -> {
            spuUpdate.put(o.id(), 99.9);
        });

        spuManager.saveSpu(update, keysUpdate, spuUpdate);


        flush();
        propertySearch.remove("IN_model");

        spuPage = spuManager.findSpuPage(SearchFilter.SearchFilterBuilder.build(spuSearch), SearchFilter.SearchFilterBuilder.build(propertySearch), Pageable.unpaged(), Sort.unsorted());
        System.out.println(JsonMapper2.toJson(spuPage));

        spuPage.forEach(spu -> {
            System.out.println(JsonMapper2.toJson(spuManager.getSpu(spu.id())));
            System.out.println(JsonMapper2.toJson(spuManager.findAllKeyPropertyValues(spu.id())));
            System.out.println(JsonMapper2.toJson(spuManager.findAllSpuPropertyValues(spu.id())));
        });

        ItemModel item = new ItemModel();
        item.setItemType(Item.ItemType.ITEM);
        item.setTag("IphoneX国行版");
        item.setDescription("iphoneX安达市多撒大所大所大大");
        item.setNum(998);
        item.setOuterId("x1221212121212121");
        item.setSalesPrice(new BigDecimal("8888"));
        item.setSellerUnionId(101L);
        item.setSellerId(1011L);
        item.setSellerCode("xxx112111");
        item.setSellerType(Item.SellerType.PLATFORM);
        item.setStatus(Item.Status.VALID);


        itemManager.createItem(item, spuPage.iterator().next().id(), null);
        itemManager.createItem(item, spuPage.iterator().next().id(), null);
        itemManager.createItem(item, spuPage.iterator().next().id(), null);
        itemManager.createItem(item, spuPage.iterator().next().id(), null);
        itemManager.createItem(item, spuPage.iterator().next().id(), null);

        Map<String, Object> itemSearch = Maps.newHashMap();
        itemSearch.put("GTE_num",998);
        Page<Item> itemPage = itemManager.findItemPage(SearchFilter.SearchFilterBuilder.build(itemSearch), SearchFilter.SearchFilterBuilder.build(propertySearch), Pageable.unpaged(), Sort.unsorted());
        itemPage.forEach(item1 -> System.out.println(JsonMapper2.toJson(item1)));
    }


    @Before
    public void categorySetup() {
        setupBSM();
        setupBasic();

        StandardCategoryModel shuma = new StandardCategoryModel();
        shuma.setStatus(Category.Status.VALID);
        shuma.setName("digital_products");
        shuma.setTag("数码");
        shuma.setDescription("数码产品");

        categoryManager.createStandardCategory(shuma, null);


        Map<String, Object> categorySearch = Maps.newHashMap();
        categorySearch.put("name", "digital_products");

        shuma = (StandardCategoryModel) categoryManager.findStandardCategoryPage(SearchFilter.SearchFilterBuilder.build(categorySearch), Pageable.unpaged()).iterator().next();

        Map<String, Object> bsmSearch = Maps.newHashMap();
        bsmSearch.put("name", "brand_series_model");

        List<? extends PropertyMetadata> bsm = propertyManager.findAllPropertyMetadataInGroup(propertyManager.findGroupedPropertyMetadataPage(SearchFilter.SearchFilterBuilder.build(bsmSearch), Pageable.unpaged()).iterator().next().id());


        for (int i = 0; i < bsm.size(); i++) {
            PropertyMetadata pm = bsm.get(i);
            CategoryPropertyModel cp = new CategoryPropertyModel();
            cp.setRequired(true);
            cp.setVisible(true);
            cp.setOrder(Float.valueOf(i + ""));
            cp.setReadOnly(false);
            cp.setCategoryPropertyBizType(KEY_PROPERTY);
            categoryManager.createCategoryProperty(cp, shuma.id(), pm.id());
        }

        flush();


        StandardCategoryModel phone = new StandardCategoryModel();
        phone.setStatus(Category.Status.VALID);
        phone.setName("digital_phone");
        phone.setTag("手机");
        phone.setDescription("手机，智能机，手提电话");

        categoryManager.createStandardCategory(phone, shuma.id());

        categorySearch.put("name", "digital_phone");

        phone = (StandardCategoryModel) categoryManager.findStandardCategoryPage(SearchFilter.SearchFilterBuilder.build(categorySearch), Pageable.unpaged()).iterator().next();


        Map<String, Object> basicSearch = Maps.newHashMap();
        basicSearch.put("IN_name", "weight,width,height,length");

        List<PropertyMetadata> basic = propertyManager.findPropertyMetadataPage(
                SearchFilter.SearchFilterBuilder.build(basicSearch), Pageable.unpaged()).getContent();
        for (int i = 0; i < basic.size(); i++) {
            PropertyMetadata pm = basic.get(i);
            CategoryPropertyModel cp = new CategoryPropertyModel();
            cp.setRequired(false);
            cp.setVisible(true);
            cp.setOrder(Float.valueOf(i + ""));
            cp.setReadOnly(false);
            cp.setCategoryPropertyBizType(SPU_PROPERTY);
            categoryManager.createCategoryProperty(cp, phone.id(), pm.id());
//            entityManager.flush();//flush changes
//            entityManager.clear();//clear persistence context
//            categoryManager.createCategoryProperty(cp, phone.id(), pm.id());

        }

        for (int i = 0; i < bsm.size(); i++) {
            PropertyMetadata pm = bsm.get(i);
            CategoryPropertyModel cp = new CategoryPropertyModel();
            cp.setRequired(true);
            cp.setVisible(true);
            cp.setOrder(Float.valueOf(i + ""));
            cp.setReadOnly(false);
            cp.setCategoryPropertyBizType(KEY_PROPERTY);
//            try {
//                categoryManager.createCategoryProperty(cp, phone.id(), pm.id());
//            }catch (Exception e){
//                System.err.println(e.getMessage());
//            }
        }


        flush();
    }


    private void setupBasic() {
        PropertyMetadataModel weight = new PropertyMetadataModel();
        weight.setKeyed(false);
        weight.setBizType(1);
        weight.setName("weight");
        weight.setTag("重量(g)");
        weight.setDescription("重量属性，单位为克");
        weight.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        weight.setValueType(PropertyValueType.DECIMAL);
        weight.setInput(new InputText());
        propertyManager.createPropertyMetadata(weight);

        PropertyMetadataModel width = new PropertyMetadataModel();
        width.setKeyed(false);
        width.setBizType(1);
        width.setName("width");
        width.setTag("宽度(mm)");
        width.setDescription("宽度属性，单位为毫米");
        width.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        width.setValueType(PropertyValueType.DECIMAL);
        width.setInput(new InputText());
        propertyManager.createPropertyMetadata(width);


        PropertyMetadataModel height = new PropertyMetadataModel();
        height.setKeyed(false);
        height.setBizType(1);
        height.setName("height");
        height.setTag("高度(mm)");
        height.setDescription("高度属性，单位为毫米");
        height.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        height.setValueType(PropertyValueType.DECIMAL);
        height.setInput(new InputText());
        propertyManager.createPropertyMetadata(height);

        PropertyMetadataModel length = new PropertyMetadataModel();
        length.setKeyed(false);
        length.setBizType(1);
        length.setName("length");
        length.setTag("长度(mm)");
        length.setDescription("长度属性，单位为毫米");
        length.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        length.setValueType(PropertyValueType.DECIMAL);
        length.setInput(new InputText());
        propertyManager.createPropertyMetadata(length);

        Map<String, Object> propertySearch = Maps.newHashMap();
        propertySearch.put("IN_name", "weight,width,height,length");


        Min min = new Min(0L);
        Max max = new Max(99999L);
        propertyManager.findPropertyMetadataPage(
                SearchFilter.SearchFilterBuilder.build(propertySearch), Pageable.unpaged()).getContent()
                .forEach(propertyMetadata -> {
                    propertyManager.createConstraint(propertyMetadata.id(), min);
                    propertyManager.createConstraint(propertyMetadata.id(), max);
                });
        flush();
    }


    /**
     * 创建品牌，系列，型号属性组
     */
    private void setupBSM() {
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
                propertyManager.createSourceValue(new SourceValueModel(3L, "iPhone11", modelPropertyMeta.valuesSource().id(), seriesValue.id()));

            }
            if (Objects.equals(seriesValue.value(), 3L)) {//InfoSphere
                propertyManager.createSourceValue(new SourceValueModel(4L, "InfoSphere Application Server", modelPropertyMeta.valuesSource().id(), seriesValue.id()));
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

        flush();
    }


    private void flush() {
//        entityManager.flush();
//        entityManager.clear();
    }
}
