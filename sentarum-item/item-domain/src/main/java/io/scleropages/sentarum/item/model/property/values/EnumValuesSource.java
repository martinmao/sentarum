///**
// * Copyright 2001-2005 The Apache Software Foundation.
// * <p>
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * <p>
// * http://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package io.scleropages.sentarum.item.model.property.values;
//
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import io.scleropages.sentarum.item.model.property.ValuesSource;
//import org.scleropages.core.mapper.JsonMapper2;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 枚举属性值来源，枚举值特点为 数字(id)->文本(tag) 值集.
// *
// * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
// */
//public class EnumValuesSource implements ValuesSource {
//
//
//    private static final String EXAMPLE_TEMPLATE_TEXT = "[\n" +
//            "  {\n" +
//            "    \"id\":1,\n" +
//            "    \"tag\":\"状态1\"\n" +
//            "   },\n" +
//            "   {\n" +
//            "    \"id\":2,\n" +
//            "    \"tag\":\"状态2\"\n" +
//            "   },\n" +
//            "   {\n" +
//            "    \"id\":3,\n" +
//            "    \"tag\":\"状态3\"\n" +
//            "   }\n" +
//            "]";
//
//    @Override
//    public List<Map<String, Object>> readValues(String text) {
//        List<EnumItem> enumItems = JsonMapper2.fromJson(text, List.class, EnumItem.class);
//        List<Map<String, Object>> values = Lists.newArrayList();
//        enumItems.forEach(enumItem -> {
//            Map<String, Object> value = Maps.newHashMap();
//            value.put("id", enumItem.getId());
//            value.put("tag", enumItem.getTag());
//            values.add(value);
//        });
//        return values;
//    }
//
//    @Override
//    public boolean isValid(String text) {
//        return JsonMapper2.fromJson(text, List.class, EnumItem.class) != null;
//    }
//
//    @Override
//    public String exampleTemplateText() {
//        return EXAMPLE_TEMPLATE_TEXT;
//    }
//
//    @Override
//    public Integer id() {
//        return 1;
//    }
//
//
//    public static class EnumItem {
//        private String id;
//        private String tag;
//        private String refId;
//
//
//        public EnumItem() {
//
//        }
//
//        public EnumItem(String id, String tag) {
//            this.id = id;
//            this.tag = tag;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getTag() {
//            return tag;
//        }
//
//        public void setTag(String tag) {
//            this.tag = tag;
//        }
//    }
//
//
//    /**
//     * create text to persists by given {@link EnumItem}'s
//     * @param enumItems
//     * @return
//     */
//    public static String buildEnumItems(EnumItem... enumItems) {
//        return JsonMapper2.toJson(Lists.newArrayList(enumItems));
//    }
//}
