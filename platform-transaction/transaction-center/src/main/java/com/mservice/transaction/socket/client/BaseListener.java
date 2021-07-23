package com.mservice.transaction.socket.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import javax.annotation.Nullable;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/7/23 上午10:22
 */
@Slf4j
public class BaseListener extends WebSocketListener {

    private final OkHttpClient httpClient;
    private final String wss;
    private WebSocket webSocket;

    public BaseListener(OkHttpClient httpClient, String wss) {
        this.httpClient = httpClient;
        this.wss = wss;
    }


    public void tryConnect() {
        webSocket = httpClient.newWebSocket(new Request.Builder().url(wss).build(), this);
    }

    @SneakyThrows
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        log.debug("listener received: {}", text);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        log.info("socket open");
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.warn("ws closed. {} {}.", code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        log.warn(" ws err : ", t);
        log.info("try connect");
        this.tryConnect();
    }
    public WebSocket getWebSocket() {
        return webSocket;
    }
}
