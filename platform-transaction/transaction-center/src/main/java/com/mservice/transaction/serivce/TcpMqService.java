package com.mservice.transaction.serivce;

import com.mservice.transaction.starter.aliyun.mq.tcp.ProductMessageDto;
import com.mservice.transaction.starter.aliyun.mq.tcp.TcpAliyunMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/1/5 下午3:12
 */
@Service
public class TcpMqService {

    @Autowired
    private TcpAliyunMqProducer tcpAliyunMqProducer;


    public void sendMsg(){

        ProductMessageDto dto = new ProductMessageDto();
        dto.setKey("1");
        dto.setMessage("123");
        dto.setTag("123");
        dto.setTopic("123");
        tcpAliyunMqProducer.sendSycNormal(dto);
    }

}
