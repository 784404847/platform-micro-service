package com.mservice.transaction.starter.aliyun.mq;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQProducer;
import com.aliyun.mq.http.model.TopicMessage;
import com.mservice.transaction.starter.aliyun.AliyunProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;


/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/15 下午5:36
 */
@Slf4j
@Component
public class AliyunMqProducer {

    @Autowired
    private MQClient mqClient;
    @Autowired
    private AliyunProperties aliyunProperties;

    public void send(String topic,String instanceId,String tag,String content){

        log.info("send message topic {} instanceId {} tag {} content {}",topic,instanceId,tag,content);
        // 获取Topic的生产者
        MQProducer producer;
        if (StringUtils.isNotBlank(instanceId)) {
            producer = mqClient.getProducer(instanceId, topic);
        } else {
            producer = mqClient.getProducer(aliyunProperties.getRock().getInstanceId(),topic);
        }

        TopicMessage msg = new TopicMessage(content.getBytes(),tag);
        TopicMessage topicMessage = producer.publishMessage(msg);
        log.info("send message success messageId {} body {}",topicMessage.getMessageId(),topicMessage.getMessageBodyString());

    }

}
