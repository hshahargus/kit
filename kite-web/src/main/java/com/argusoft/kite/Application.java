package com.argusoft.kite;

/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.argusoft.kite.ticker.dto.Tiker;
import com.argusoft.kite.util.ConstantUtil;
import com.zerodhatech.kiteconnect.KiteConnect;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.argusoft.kite")
//@EnableJpaRepositories(basePackages = "com.argusoft.hkg.dao")
@EntityScan(basePackages = "com.argusoft.kite")
@PropertySource({"classpath:server.properties",
    "classpath:jdbc.properties"})
@EnableScheduling
public class Application extends SpringBootServletInitializer {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DataSource dataSource;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ResourceBundle serverPropertiesBundle = ResourceBundle.getBundle("server");
        ConstantUtil.SERVER_COMPANY_ID = Long.parseLong(serverPropertiesBundle.getString("server.company.id"));

    }

    @Bean
    @Order(1)
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    @Bean
    @Order(2)
    public KiteConnect kiteSdk() {
        return new KiteConnect("3579298mc3xenf4c");
    }

    @Bean
    @Order(3)
    public Tiker kiteTicker() {
        return new Tiker();
    }

    @Bean
    @Order(2)
    public HibernateTransactionManager transactionManager() throws IOException {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());

        return txManager;
    }

//    @Bean
//    @Order(3)
//    public CustomInterceptor customInterceptor() throws IOException {
//
//        CustomInterceptor customInterceptor = new CustomInterceptor();
//        transactionManager().setEntityInterceptor(customInterceptor);
//        return customInterceptor;
//    }
    @Bean
    public FilterRegistrationBean accessControlFilterRegistrationBean() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader(ConstantUtil.COUNT);
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

//    @Bean
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }
}
