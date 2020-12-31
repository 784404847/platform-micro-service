package com.mservice.transaction.starter.aliyun;

import com.mservice.transaction.starter.aliyun.mq.RocketProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/15 下午3:54
 */
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunProperties {
    private String accessKey;
    private String secret;
    private RocketProperties rock;
}
