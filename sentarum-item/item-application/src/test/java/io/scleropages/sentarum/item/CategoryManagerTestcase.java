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
import io.scleropages.sentarum.item.category.model.StandardCategoryLink;
import io.scleropages.sentarum.item.category.model.impl.CategoryPropertyModel;
import io.scleropages.sentarum.item.category.model.impl.MarketingCategoryModel;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryLinkModel;
import io.scleropages.sentarum.item.category.model.impl.StandardCategoryModel;
import io.scleropages.sentarum.item.mgmt.CategoryManager;
import io.scleropages.sentarum.item.mgmt.PropertyManager;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValueType;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;
import io.scleropages.sentarum.item.property.model.input.MultiCheck;
import io.scleropages.sentarum.item.property.model.vs.NativeValuesSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Transactional
public class CategoryManagerTestcase {

    @Autowired
    private CategoryManager categoryManager;
    @Autowired
    private PropertyManager propertyManager;

    private static final Map<String, Object> EXAMPLE_ATTRIBUTES;

    static {

        EXAMPLE_ATTRIBUTES = Maps.newHashMap();
        EXAMPLE_ATTRIBUTES.put("key1", 10);
        EXAMPLE_ATTRIBUTES.put("key2", new Date());
        EXAMPLE_ATTRIBUTES.put("key3", 33.22);
        EXAMPLE_ATTRIBUTES.put("key4", "text");
    }


    @Test
    public void testCategoryCreation() {
        StandardCategoryModel standardCategory = new StandardCategoryModel();
        standardCategory.setName("test_std_cl");
        standardCategory.setTag("测试后台类目");
        standardCategory.setDescription("测试后台类目");
        standardCategory.setStatus(Category.Status.VALID);
        standardCategory.setAdditionalAttributes(EXAMPLE_ATTRIBUTES);
        categoryManager.createStandardCategory(standardCategory, null);

        Map<String, Object> stdCategorySearch = Maps.newHashMap();
        stdCategorySearch.put("name", "test_std_cl");

        standardCategory = (StandardCategoryModel) categoryManager.findStandardCategoryPage(SearchFilter.SearchFilterBuilder.build(stdCategorySearch), Pageable.unpaged()).iterator().next();


        NativeValuesSource testStdPropertyValuesSource = new NativeValuesSource("testValuesSource", "测试属性值", "测试属性值");

        propertyManager.createValuesSource(testStdPropertyValuesSource);

        testStdPropertyValuesSource = (NativeValuesSource) propertyManager.getValuesSourceByName("testValuesSource");

        propertyManager.createSourceValue(new SourceValueModel(1L, "值1", testStdPropertyValuesSource.id()));
        propertyManager.createSourceValue(new SourceValueModel(2L, "值2", testStdPropertyValuesSource.id()));
        propertyManager.createSourceValue(new SourceValueModel(3L, "值3", testStdPropertyValuesSource.id()));


        PropertyMetadataModel testStdPropertyMeta = new PropertyMetadataModel();
        testStdPropertyMeta.setInput(new MultiCheck());
        testStdPropertyMeta.setName("test_std_cl_prop_1");
        testStdPropertyMeta.setTag("测试属性1");
        testStdPropertyMeta.setDescription("测试属性1");
        testStdPropertyMeta.setKeyed(true);
        testStdPropertyMeta.setBizType(1);
        testStdPropertyMeta.setStructureType(PropertyMetadata.PropertyStructureType.FLAT_PROPERTY);
        testStdPropertyMeta.setValueType(PropertyValueType.INTEGER);

        propertyManager.createPropertyMetadata(testStdPropertyMeta);

        testStdPropertyMeta = (PropertyMetadataModel) propertyManager.getPropertyMetadataByName("test_std_cl_prop_1");

        propertyManager.bindValuesSourceToPropertyMetadata(testStdPropertyValuesSource.id(), testStdPropertyMeta.id());

        CategoryPropertyModel categoryProperty = new CategoryPropertyModel();
        categoryProperty.setAdditionalAttributes(EXAMPLE_ATTRIBUTES);
        categoryProperty.setCategoryPropertyBizType(CategoryProperty.CategoryPropertyBizType.KEY_PROPERTY);
        categoryProperty.setOrder(0.01F);
        categoryProperty.setReadOnly(false);
        categoryProperty.setRequired(true);
        categoryProperty.setVisible(true);
        categoryProperty.setDefaultValues(new CategoryProperty.DefaultValues(new Object[]{1, 2, 3}));
        categoryManager.createCategoryProperty(categoryProperty, standardCategory.id(), testStdPropertyMeta.id());

        MarketingCategoryModel marketingCategory = new MarketingCategoryModel();

        marketingCategory.setName("test_mkt_cl");
        marketingCategory.setTag("测试前台类目");
        marketingCategory.setDescription("测试前台类目");
        marketingCategory.setStatus(Category.Status.VALID);
        marketingCategory.setAdditionalAttributes(EXAMPLE_ATTRIBUTES);
        categoryManager.createMarketingCategory(marketingCategory, null);

        stdCategorySearch.put("name", "test_mkt_cl");
        marketingCategory = (MarketingCategoryModel) categoryManager.findMarketingCategoryPage(SearchFilter.SearchFilterBuilder.build(stdCategorySearch), Pageable.unpaged()).iterator().next();

        StandardCategoryLinkModel link = new StandardCategoryLinkModel();
        link.setLinkStatus(StandardCategoryLink.LinkStatus.VALID);
        link.setLinkType(StandardCategoryLink.LinkType.DIRECT);

        categoryManager.createStandardCategoryLink(link, marketingCategory.id(), standardCategory.id());

    }
}
