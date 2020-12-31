package com.mservice.transaction;

import com.mservice.transaction.serivce.HttpMqService;
import com.mservice.transaction.serivce.MqService;
import com.mservice.transaction.serivce.TransactionFlowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableConfigurationProperties
class PlatformTransactionApplicationTests {

    @Autowired
    private TransactionFlowService transactionFlowService;
    @Autowired
    private HttpMqService httpMqService;
    @Test
    public void trans1Test() {
        transactionFlowService.selectTest();
    }

    @Test
    public void pageTest() {
        transactionFlowService.pageTest();
    }

    @Test
    public void cuzTest() {
        transactionFlowService.czEnumTest();
    }

    @Test
    public void czSqlTest() {
        transactionFlowService.czSqlTest();
    }

    @Test
    public void mqsendlTest() {
        httpMqService.sendMessage();
    }

    @Test
    public void mqgetlTest() {
        httpMqService.getMessage();
    }
}
