package com.mservice.transaction.starter.aliyun.mq.tcp;

import lombok.Data;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/1/5 下午2:21
 */
@Data
public class ProductMessageDto {

    private String topic;

    private String tag;

    private String message;

    private String key;

}
