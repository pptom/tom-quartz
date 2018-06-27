package com.tom.quartz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author: Mr Tom
 * @date: 2018/6/26
 * @since: 2018/6/26
 * @email: ptomjie@gmail.com
 * @description: 解决Long型传到前端会丢失精度的问题。
 */
public class MyObjectMapper extends ObjectMapper {

    public MyObjectMapper() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        registerModule(simpleModule);
    }

}