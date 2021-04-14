package com.mservice.transaction.starter.aliyun.mq.tcp;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.order.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/15 下午5:36
 */
@Slf4j
//@Component
public class TcpAliyunMqConsumer {

    @Autowired
    private OrderConsumer tcpOrderConsumer;
    @Autowired
    private Consumer consumer;


    /**
     * 订阅顺序消息
     */
    public void orderConsumerListener(){
        tcpOrderConsumer.subscribe("t1", "*", (message, consumeOrderContext) -> {
            log.info("get message key {} body {} ",message.getKey(),message.getBody());
            return OrderAction.Success;
        });
        tcpOrderConsumer.start();
    }

    /**
     * 订阅普通消息
     */
    public void normalConsumerListener(){

        consumer.subscribe("t2", "TagA||TagB", (message, consumeOrderContext) -> {
            log.info("get message key {} body {} ",message.getKey(),message.getBody());
            return Action.CommitMessage;
        });

        consumer.subscribe("t3", "*", (message, consumeOrderContext) -> {
            log.info("get message key {} body {} ",message.getKey(),message.getBody());
            return Action.CommitMessage;
        });

        tcpOrderConsumer.start();
    }




}
