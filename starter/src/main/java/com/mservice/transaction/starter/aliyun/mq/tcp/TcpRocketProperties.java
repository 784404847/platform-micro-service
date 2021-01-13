package com.mservice.transaction.starter.aliyun.mq.tcp;

import lombok.Data;


/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/31 上午11:48
 */
@Data
public class TcpRocketProperties {
    /**
     * 分组id
     */
    private String groupId;

    /**
     * 接入点
     */
    private String nameSrvAddr;

    /**
     * 用户渠道
     */
    private String onsChannel;

    /**
     * 发送超时时间
     */
    private String sendMsgTimeoutMillis;

    /**
     * 顺序消息消费失败进行重试前的等待时间，单位（毫秒），取值范围: 10毫秒~30,000毫秒
     */
    private String suspendTimeMillis;

    /**
     * 最大重试次数
     */
    private String maxReconsumeTimes;

    /**
     * 订阅饭是钢
     */
    private String messageMode;
}
