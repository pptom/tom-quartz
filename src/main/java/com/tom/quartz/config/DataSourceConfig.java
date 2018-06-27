package com.tom.quartz.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Mr Tom
 * @date 2018/6/27
 * @email ptomjie@gmail.com
 * @description
 * @since 2018/6/27
 */
@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource druidDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
