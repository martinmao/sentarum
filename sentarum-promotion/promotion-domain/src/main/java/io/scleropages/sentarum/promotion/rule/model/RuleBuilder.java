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
package io.scleropages.sentarum.promotion.rule.model;

/**
 * used for promotion rules build.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class RuleBuilder {


    /**
     * create a new rules group
     *
     * @param group
     * @param conjunction
     * @param rules
     */
    public void withRuleGroup(String group, Conjunction conjunction, Rule... rules) {

    }


    /**
     * 规则连接，对于多个规则的匹配结果连接的方式
     */
    enum Conjunction {
        OR, AND
    }

}
