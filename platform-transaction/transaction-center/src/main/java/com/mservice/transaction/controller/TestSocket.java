package com.mservice.transaction.controller;

import com.beust.jcommander.internal.Lists;
import com.mservice.common.util.concurrent.ThreadPoolUtil;
import com.mservice.transaction.socket.client.BaseListener;
import io.swagger.v3.oas.annotations.Operation;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/7/23 上午10:32
 */
@Controller
@RestController
@RequestMapping("/socket")
public class TestSocket {

    private static final List<BaseListener> listenerList = Lists.newArrayList();
    private static final String WS_EXC = "ws://127.0.0.1:8093/trading/ws";

    private ThreadPoolExecutor executor = ThreadPoolUtil.getCommonThreadPool();

    @Resource
    private OkHttpClient okHttpClient;

    @Operation(summary = "创建连接")
    @GetMapping(value = "/connect")
    public void connect() {
        for (int i = 0; i < 40; i++) {
            executor.execute(() -> {
                BaseListener listener = new BaseListener(okHttpClient, WS_EXC);
                listener.tryConnect();
                listenerList.add(listener);
            });
        }
    }

    @Operation(summary = "发送信息")
    @PostMapping(value = "/msg")
    public void send(@RequestParam String message) {
        for(String s:message.split("\n")){
            for (BaseListener listener : listenerList) {
                WebSocket webSocket = listener.getWebSocket();
                webSocket.send(s);
            }
        }
    }

}
