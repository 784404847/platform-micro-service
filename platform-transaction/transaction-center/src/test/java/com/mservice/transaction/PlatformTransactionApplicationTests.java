package com.mservice.transaction;

import com.mservice.transaction.serivce.TransactionFlowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlatformTransactionApplicationTests {

    @Autowired
    private TransactionFlowService transactionFlowService;

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
}
