package com.mservice.transaction.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.Proxy;
import java.time.Duration;

/**
 * @author wejam
 * @date 2020/10/6 10:15 下午
 */
@ConfigurationProperties(prefix = "my.http")
@Data
public class HttpClientProperties {

    private boolean enabled;
    private boolean retry;
    private Duration connectTimeout = Duration.ofSeconds(5);
    private Duration readTimeout = Duration.ofSeconds(10);
    private Duration writeTimeout = Duration.ofSeconds(10);
    private HttpClientPoolProperties pool;
    private ProxyProperties proxy;

    @Data
    public static class HttpClientPoolProperties {
        private int maxIdleConnections = 5;
        private Duration keepAliveDuration = Duration.ofSeconds(300);
    }

    @Data
    public static class ProxyProperties {
        private boolean enabled;
        private Proxy.Type type;
        private String host;
        private int port;
    }

}
