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
//import io.scleropages.sentarum.item.model.property.ValuesSource;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 基于sql执行结果集,将结果集作为 {@link ValuesSource}.
// *
// * 废弃：{@link ValuesSource} 定义为统一的结构并存储值关系，其他结构必须统一转换为通用的结构，例如某些信息来源于表，则将表结构转换存储为 {@link io.scleropages.sentarum.item.model.property.ValuesSource.Value}结构
// * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
// */
//@Deprecated
//public class SqlQueryValuesSource implements ValuesSource {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    private String valueField;
//
//
//    public SqlQueryValuesSource(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public List<Map<String, Object>> readValues(String text) {
//        return jdbcTemplate.queryForList(text);
//    }
//
//    @Override
//    public boolean isValid(String text) {
//        if (!StringUtils.startsWithIgnoreCase(text, "select"))
//            return false;
//        return false;
//    }
//
//    @Override
//    public String exampleTemplateText() {
//        return "select 1 as id, 'cafebabe' as getName";
//    }
//
//
//    @Override
//    public Integer id() {
//        return 2;
//    }
//
//    public String getValueField() {
//        return valueField;
//    }
//
//    public void setValueField(String valueField) {
//        this.valueField = valueField;
//    }
//}
