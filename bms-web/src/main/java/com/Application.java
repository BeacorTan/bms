package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 *
 */
// 扫描servlet容器
//@ServletComponentScan
@ComponentScan(value = "com.**.*")
@MapperScan("com.**.mapper")
// （exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class}）禁用应用启动自动创建mongodb数据库连接
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}");
        SpringApplication.run(Application.class, args);
    }
}
