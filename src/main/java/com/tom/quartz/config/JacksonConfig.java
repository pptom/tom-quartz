package com.tom.quartz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Mr Tom
 * @date: 2018/6/26
 * @since: 2018/6/26
 * @email: ptomjie@gmail.com
 * @description: 解决Long型传到前端会丢失精度的问题。
 */
@Configuration
public class JacksonConfig extends ObjectMapper {

    @Bean
    public ObjectMapper objectMapper() {
        return new MyObjectMapper();
    }
}