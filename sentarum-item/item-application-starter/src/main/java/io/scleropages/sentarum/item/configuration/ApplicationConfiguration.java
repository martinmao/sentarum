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
package io.scleropages.sentarum.item.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.scleropages.crud.configure.CrudFeaturesImporter;
import org.scleropages.crud.dao.jdbc.FrameworkRoutingDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Configuration
@Import({CrudFeaturesImporter.class})
public class ApplicationConfiguration {

    @Configuration
    @ConditionalOnProperty(name = "datasource-multiple.enabled")
    public static class DataSourcesConfiguration {

        @Primary
        @ConfigurationProperties(prefix = "spring.datasource")
        @Bean
        public DataSourceProperties defaultDataSourceProperties() {
            return new DataSourceProperties();
        }

        @ConfigurationProperties(prefix = "second.datasource")
        @Bean
        public DataSourceProperties secondDataSourceProperties() {
            return new DataSourceProperties();
        }

        @ConfigurationProperties(prefix = "third.datasource")
        @Bean
        public DataSourceProperties thirdDataSourceProperties() {
            return new DataSourceProperties();
        }

        @Primary
        @Bean
        public FrameworkRoutingDataSource dataSource() {
            FrameworkRoutingDataSource dataSource = new FrameworkRoutingDataSource();
            dataSource.setDefaultDataSourceBeanId("defaultDataSource");
            return dataSource;
        }

        protected static <T> T createDataSource(DataSourceProperties properties,
                                                Class<? extends DataSource> type) {
            return (T) properties.initializeDataSourceBuilder().type(type).build();
        }

        @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.zaxxer.hikari.HikariDataSource")
        public static class HikariDataSourceConfiguration {

            @ConfigurationProperties(prefix = "spring.datasource.hikari")
            @Bean
            public HikariDataSource defaultDataSource(@Qualifier("defaultDataSourceProperties") DataSourceProperties properties) {
                HikariDataSource dataSource = createDataSource(properties,
                        HikariDataSource.class);
                if (StringUtils.hasText(properties.getName())) {
                    dataSource.setPoolName(properties.getName());
                }
                return dataSource;
            }

            @ConfigurationProperties(prefix = "spring.datasource.hikari")
            @Bean
            public HikariDataSource secondDataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties properties) {
                HikariDataSource dataSource = createDataSource(properties,
                        HikariDataSource.class);
                if (StringUtils.hasText(properties.getName())) {
                    dataSource.setPoolName(properties.getName());
                }
                return dataSource;
            }


            @ConfigurationProperties(prefix = "spring.datasource.hikari")
            @Bean
            public HikariDataSource thirdDataSource(@Qualifier("thirdDataSourceProperties") DataSourceProperties properties) {
                HikariDataSource dataSource = createDataSource(properties,
                        HikariDataSource.class);
                if (StringUtils.hasText(properties.getName())) {
                    dataSource.setPoolName(properties.getName());
                }
                return dataSource;
            }

        }

        @ConditionalOnProperty(name = "spring.datasource.type", havingValue = "com.alibaba.druid.pool.DruidDataSource")
        public static class DruidDataSourceConfiguration {

            @ConfigurationProperties(prefix = "spring.datasource.druid")
            @Bean
            public DruidDataSource defaultDataSource(@Qualifier("defaultDataSourceProperties") DataSourceProperties properties) {
                DruidDataSource dataSource = createDataSource(properties,
                        DruidDataSource.class);
                if (StringUtils.hasText(properties.getName())) {
                    dataSource.setName(properties.getName());
                }
                return dataSource;
            }

            @ConfigurationProperties(prefix = "spring.datasource.druid")
            @Bean
            public DruidDataSource secondDataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties properties) {
                DruidDataSource dataSource = createDataSource(properties,
                        DruidDataSource.class);
                if (StringUtils.hasText(properties.getName())) {
                    dataSource.setName(properties.getName());
                }
                return dataSource;
            }


            @ConfigurationProperties(prefix = "spring.datasource.druid")
            @Bean
            public DruidDataSource thirdDataSource(@Qualifier("thirdDataSourceProperties") DataSourceProperties properties) {
                DruidDataSource dataSource = createDataSource(properties,
                        DruidDataSource.class);
                if (StringUtils.hasText(properties.getName())) {
                    dataSource.setName(properties.getName());
                }
                return dataSource;
            }

        }

    }
}
