package com.mservice.transaction.starter.aliyun.mq.http;

import com.aliyun.mq.http.MQClient;
import com.mservice.transaction.starter.aliyun.AliyunProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/15 下午3:52
 */
//@EnableConfigurationProperties(AliyunProperties.class)
//@Configuration
//@ConditionalOnProperty(prefix = "aliyun.http.rocket.enable", value = "enable", matchIfMissing = true)
public class HttpRocketConfig {

    @Autowired
    private AliyunProperties aliyunProperties;

    @Bean(destroyMethod = "close")
    public MQClient mqClient() {
        return new MQClient(
                // 设置HTTP接入域名（此处以公共云生产环境为例）
                aliyunProperties.getHttpRocket().getEndpoint(),
                // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
                aliyunProperties.getAccessKey(),
                // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
                aliyunProperties.getSecret()
        );
    }

    @Bean
    @ConditionalOnBean({MQClient.class, AliyunProperties.class})
    public HttpAliyunMqConsumer aliyunMqConsumer() {
        return new HttpAliyunMqConsumer();
    }

    @Bean
    @ConditionalOnBean({MQClient.class, AliyunProperties.class})
    public HttpAliyunMqProducer aliyunMqProducer() {
        return new HttpAliyunMqProducer();
    }
}
