package com.mservice.transaction.starter.aliyun.mq.tcp;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.order.OrderProducer;
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
//@Component
public class TcpAliyunMqProducer {

    @Autowired
    private Producer normalProducer;
    @Autowired
    private OrderProducer orderProducer;
    /**
     *  普通同步消息
     * @param dto
     */
    public void sendSycNormal(ProductMessageDto dto) {

        log.info("send message key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage());

        try {
            normalProducer.start();
            Message msg = new Message(dto.getTopic(),dto.getTag(),StringUtils.isNotBlank(dto.getKey())?dto.getKey():"", dto.getMessage().getBytes());
            SendResult sendResult = normalProducer.send(msg);
            log.info("send syc message success messageId {}",sendResult.getMessageId());
        }catch (Exception e){
            log.error("send syc message fail key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage(),e);
        }finally {
            normalProducer.shutdown();
        }
    }

    /**
     *  异步普通消息
     * @param dto
     */
    public void sendAsyNormal(ProductMessageDto dto) {

        log.info("send asy message key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage());

        try {
            normalProducer.start();
            Message msg = new Message(dto.getTopic(),dto.getTag(),StringUtils.isNotBlank(dto.getKey())?dto.getKey():"", dto.getMessage().getBytes());
            normalProducer.sendAsync(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("send asy message success messageId {}",sendResult.getMessageId());
                }

                @Override
                public void onException(OnExceptionContext onExceptionContext) {
                    log.error("send asy message fail key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage(),onExceptionContext.getException());
                }
            });

        }catch (Exception e){
            log.error("send asy message fail key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage(),e);
        }
    }

    /**
     *  单向普通消息
     * @param dto
     */
    public void sendOneWayNormal(ProductMessageDto dto) {

        log.info("send oneway message key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage());

        try {
            normalProducer.start();
            Message msg = new Message(dto.getTopic(),dto.getTag(),StringUtils.isNotBlank(dto.getKey())?dto.getKey():"", dto.getMessage().getBytes());
            normalProducer.sendOneway(msg);
        }catch (Exception e){
            log.error("send message fail key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage(),e);
        }
    }

    public void sendOrder(ProductMessageDto dto) {

        log.info("send order message key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage());

        try {
            orderProducer.start();
            Message msg = new Message(dto.getTopic(),dto.getTag(),StringUtils.isNotBlank(dto.getKey())?dto.getKey():"", dto.getMessage().getBytes());
            orderProducer.send(msg,"A001");
        }catch (Exception e){
            log.error("send order fail key {} topic {} tag {} message {}",dto.getKey(), dto.getTopic(), dto.getTag(), dto.getMessage(),e);
        }
    }





}
