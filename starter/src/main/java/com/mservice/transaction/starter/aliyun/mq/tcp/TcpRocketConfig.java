package com.mservice.transaction.starter.aliyun.mq.tcp;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.mservice.transaction.starter.aliyun.AliyunProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/1/5 上午11:11
 */

@EnableConfigurationProperties(AliyunProperties.class)
@Configuration
@ConditionalOnProperty(prefix = "aliyun.tcpRocket.enable", value = "enable", matchIfMissing = true)
public class TcpRocketConfig {

    @Autowired
    private AliyunProperties aliyunProperties;

    private final static String DEFAULT_SEND_TIME_OUT = "1000";
    private final static String DEFAULT_SUSPEND_TIME_OUT = "1000";
    private final static String DEFAULT_MAX_RECONSUME = "10";

    @ConditionalOnBean({AliyunProperties.class})
    @Bean
    public Producer tcpProducer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, aliyunProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, aliyunProperties.getSecret());
        properties.put(PropertyKeyConst.SendMsgTimeoutMillis, StringUtils.isNotBlank(aliyunProperties.getTcpRocket().getSendMsgTimeoutMillis()) ? aliyunProperties.getTcpRocket().getSendMsgTimeoutMillis() : DEFAULT_SEND_TIME_OUT);
        properties.put(PropertyKeyConst.NAMESRV_ADDR, aliyunProperties.getTcpRocket().getNameSrvAddr());

        return ONSFactory.createProducer(properties);
    }

    @ConditionalOnBean({AliyunProperties.class})
    @Bean
    public OrderProducer tcpOrderProducer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID, aliyunProperties.getTcpRocket().getGroupId());
        properties.put(PropertyKeyConst.AccessKey, aliyunProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, aliyunProperties.getSecret());
        properties.put(PropertyKeyConst.SendMsgTimeoutMillis, StringUtils.isNotBlank(aliyunProperties.getTcpRocket().getSendMsgTimeoutMillis()) ? aliyunProperties.getTcpRocket().getSendMsgTimeoutMillis() : DEFAULT_SEND_TIME_OUT);
        properties.put(PropertyKeyConst.NAMESRV_ADDR, aliyunProperties.getTcpRocket().getNameSrvAddr());
        return ONSFactory.createOrderProducer(properties);
    }

    @ConditionalOnBean({AliyunProperties.class})
    @Bean
    public OrderConsumer tcpOrderConsumer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.MaxReconsumeTimes, StringUtils.isNotBlank(aliyunProperties.getTcpRocket().getMaxReconsumeTimes())?aliyunProperties.getTcpRocket().getMaxReconsumeTimes():DEFAULT_MAX_RECONSUME);
        properties.put(PropertyKeyConst.GROUP_ID, aliyunProperties.getTcpRocket().getGroupId());
        properties.put(PropertyKeyConst.AccessKey, aliyunProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, aliyunProperties.getSecret());
        properties.put(PropertyKeyConst.SuspendTimeMillis, StringUtils.isNotBlank(aliyunProperties.getTcpRocket().getSuspendTimeMillis()) ? aliyunProperties.getTcpRocket().getSuspendTimeMillis() : DEFAULT_SUSPEND_TIME_OUT);
        properties.put(PropertyKeyConst.NAMESRV_ADDR, aliyunProperties.getTcpRocket().getNameSrvAddr());
        return ONSFactory.createOrderedConsumer(properties);
    }

    @ConditionalOnBean({AliyunProperties.class})
    @Bean
    public Consumer tcpConsumer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.MaxReconsumeTimes, StringUtils.isNotBlank(aliyunProperties.getTcpRocket().getMaxReconsumeTimes())?aliyunProperties.getTcpRocket().getMaxReconsumeTimes():DEFAULT_MAX_RECONSUME);
        properties.put(PropertyKeyConst.SuspendTimeMillis, StringUtils.isNotBlank(aliyunProperties.getTcpRocket().getSuspendTimeMillis()) ? aliyunProperties.getTcpRocket().getSuspendTimeMillis() : DEFAULT_SUSPEND_TIME_OUT);
        properties.put(PropertyKeyConst.GROUP_ID, aliyunProperties.getTcpRocket().getGroupId());
        properties.put(PropertyKeyConst.AccessKey, aliyunProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, aliyunProperties.getSecret());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, aliyunProperties.getTcpRocket().getNameSrvAddr());
        properties.put(PropertyKeyConst.MessageModel, aliyunProperties.getTcpRocket().getMessageMode());

        return ONSFactory.createConsumer(properties);
    }


}
