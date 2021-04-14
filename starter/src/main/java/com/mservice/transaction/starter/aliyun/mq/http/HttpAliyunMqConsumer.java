package com.mservice.transaction.starter.aliyun.mq.http;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQConsumer;
import com.aliyun.mq.http.model.Message;
import com.google.common.collect.Lists;
import com.mservice.transaction.starter.aliyun.AliyunProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/16 上午11:04
 */
//@Component
public class HttpAliyunMqConsumer {

    @Autowired
    private MQClient mqClient;
    @Autowired
    private AliyunProperties aliyunProperties;

    /**
     * 获取消息，需要自己去
     *
     * @param instanceId
     * @param topic
     * @param groupId
     * @param messageTag
     * @return
     */
    public List<Message> getMessage(String instanceId, String topic, String groupId, String messageTag, Function function) {

        MQConsumer consumer;
        if (StringUtils.isNotBlank(instanceId)) {
            consumer = mqClient.getConsumer(instanceId, topic, groupId, messageTag);
        } else {
            consumer = mqClient.getConsumer(aliyunProperties.getHttpRocket().getInstanceId(), topic, groupId, messageTag);
        }

        List<Message> messages = consumer.consumeMessage(1, 1);

        /*处理消息*/
        function.apply(messages);

        List<String> handles = Lists.newArrayList();
        for (Message message : messages) {
            handles.add(message.getReceiptHandle());
        }

        consumer.ackMessage(handles);
        return messages;
    }
}

//    public void test() {
//        getMessage("1", "t", "s", "2", o -> {
//            System.out.println("12");
//            return null;
//        });
//    }
//    }
