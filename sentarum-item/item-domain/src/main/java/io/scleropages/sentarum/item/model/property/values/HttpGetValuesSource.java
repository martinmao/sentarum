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
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
// */
//@Deprecated
//public class HttpGetValuesSource implements ValuesSource {
//
//
//    private final RestTemplate restTemplate;
//
//
//    public HttpGetValuesSource(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Override
//    public List<Map<String, Object>> readValues(String text) {
//        List forObject = restTemplate.getForObject(text, List.class);
//        return null;
//    }
//
//    @Override
//    public boolean isValid(String text) {
//        return false;
//    }
//
//    @Override
//    public String exampleTemplateText() {
//        return null;
//    }
//
//    @Override
//    public Integer id() {
//        return 3;
//    }
//}
