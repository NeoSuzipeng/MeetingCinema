package com.stylefeng.guns.rest.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Su on 2019/4/30.
 *
 */
@Slf4j
public class PropertiesUtil {
    private static Properties properties;

    static{
        String fileName = "order.properties";
        properties = new Properties();
        try {
            properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("properties 装配失败");
        }
    }

    public static String getProperty(String key){
        String value = properties.getProperty(key.trim()).trim();
        if(StringUtils.isBlank(value))
            return null;
        return value;
    }

    public static String getProperty(String key, String defaultValue){
        String value = properties.getProperty(key.trim()).trim();
        if(StringUtils.isBlank(value))
            return defaultValue;
        return value;
    }
}
