package com.lyg.blog.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * json响应体去null修饰
 * Created by winggonLee on 2020/2/7
 */
@Configuration
public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return (j) -> {
            j.serializationInclusion(JsonInclude.Include.NON_NULL);
            j.failOnUnknownProperties(false);
//            j.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        };
    }
}
