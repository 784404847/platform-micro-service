package com.mservice.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 交易中心
 *
 * @author wuwenjun
 */
@EnableConfigurationProperties
@SpringCloudApplication
public class PlatformTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformTransactionApplication.class, args);
    }

}
