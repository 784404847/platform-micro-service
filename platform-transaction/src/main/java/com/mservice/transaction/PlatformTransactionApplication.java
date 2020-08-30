package com.mservice.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 交易中心
 * @author wuwenjun
 */
@SpringBootApplication(scanBasePackages = {"com.mservice.transaction",
        "com.mservice.common"})
@MapperScan(basePackages = {"com.mservice.transaction.mapper"})
@EnableDiscoveryClient
@EnableConfigurationProperties
public class PlatformTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformTransactionApplication.class, args);
    }

}
