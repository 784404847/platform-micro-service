package com.mservice.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 交易中心
 *
 * @author wuwenjun
 */
@EnableConfigurationProperties
@SpringCloudApplication
@EnableDiscoveryClient
public class PlatformTransactionApplication {
    //-javaagent:/Users/wuwenjun/Documents/skywalking/agent/skywalking-agent.jar -Dskywalking.agent.service_name=platform-transaction -Dskywalking.collector.backend_service=127.0.0.1:11800
    public static void main(String[] args) {
        SpringApplication.run(PlatformTransactionApplication.class, args);
    }

}
