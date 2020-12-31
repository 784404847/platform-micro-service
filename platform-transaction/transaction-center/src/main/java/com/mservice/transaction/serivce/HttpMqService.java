package com.mservice.transaction.serivce;

import com.alibaba.fastjson.JSON;
import com.aliyun.mq.http.model.Message;
import com.mservice.transaction.starter.aliyun.mq.AliyunMqConsumer;
import com.mservice.transaction.starter.aliyun.mq.AliyunMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/16 下午5:05
 */
@Service
public class HttpMqService {

    @Autowired
    private AliyunMqProducer aliyunMqProducer;
    @Autowired
    private AliyunMqConsumer aliyunMqConsumer;

    public void sendMessage(){
        aliyunMqProducer.send("topic1",null,null,"123123");
    }

    public void getMessage(){
        aliyunMqConsumer.getMessage(null, "topic1", "GID_topic1", null, new Function() {
            @Override
            public Object apply(Object o) {
                System.out.println("接受到消息："+JSON.toJSONString(o));
                List<Message> messageList = (List<Message>) o;
                for (Message message : messageList){
                    System.out.println(message.getMessageBodyString());
                }
                return null;
            }
        });
    }

}
