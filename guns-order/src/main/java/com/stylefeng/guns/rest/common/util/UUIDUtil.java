package com.stylefeng.guns.rest.common.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.downgoon.snowflake.Snowflake;

/**
 * Created by Su on 2019/4/30.
 * 订单号生成策略
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "uuid")
public class UUIDUtil {

    private static int datacenter = Integer.parseInt(PropertiesUtil.getProperty("uuid.datacenter"));

    private static int workerId = Integer.parseInt(PropertiesUtil.getProperty("uuid.workerId"));

    private static final Snowflake snowflake = new Snowflake(datacenter, workerId);

    public static long getUuid(){
        return snowflake.nextId();
    }

//    public static void main(String[] args) {
//        System.out.println(UUIDUtil.getUuid());
//    }

}
