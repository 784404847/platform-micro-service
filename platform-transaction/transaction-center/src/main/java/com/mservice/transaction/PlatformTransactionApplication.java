package com.mservice.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 交易中心
 *
 * @author wuwenjun
 */
@MapperScan(basePackages = {"com.mservice.transaction.mapper"})
@EnableConfigurationProperties
@SpringCloudApplication
public class PlatformTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformTransactionApplication.class, args);
    }

}
