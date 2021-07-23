package com.mservice.transaction.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author wejam
 * @date 2020/10/6 9:47 下午
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({HttpClientProperties.class})
@ConditionalOnProperty(prefix = "my.http", value = "enabled", matchIfMissing = true)
public class HttpClientAutoConfiguration {

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Bean
    @ConditionalOnMissingBean(OkHttpClient.class)
    public OkHttpClient okHttpClient() {
        HttpClientProperties.ProxyProperties proxy = httpClientProperties.getProxy();
        return new OkHttpClient.Builder()
                /*暂时不需要使用证书验证*/
                //.sslSocketFactory(sslSocketFactory(), x509TrustManager())
                .retryOnConnectionFailure(httpClientProperties.isRetry())
                .connectTimeout(httpClientProperties.getConnectTimeout())
                .readTimeout(httpClientProperties.getReadTimeout())
                .writeTimeout(httpClientProperties.getWriteTimeout())
                .connectionPool(new ConnectionPool(
                        httpClientProperties.getPool().getMaxIdleConnections(),
                        httpClientProperties.getPool().getKeepAliveDuration().getSeconds(), TimeUnit.SECONDS)
                )
                .proxy(proxy == null || !proxy.isEnabled() ? null :
                        new Proxy(proxy.getType(), new InetSocketAddress(proxy.getHost(), proxy.getPort())))
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    @ConditionalOnBean(OkHttpClient.class)
    public RestTemplate restTemplate(OkHttpClient client) {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory(client));
    }

    private X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private SSLSocketFactory sslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

}
