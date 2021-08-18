package com.mservice.transaction;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mservice.transaction.mapper.TransactionFlowMapper;
import com.mservice.transaction.model.TransactionFlow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class PlatformTransactionApplicationTests {

    @Resource
    private TransactionFlowMapper transactionFlowMapper;

    @Test
    public void trans1Test() {

        transactionFlowMapper.deleteAll();

        //验证锁表的情况
        for(int i=0;i<10000;i++) {

            TransactionFlow.builder()
                    .flowNo((long) i)
                    .transactionAmount("101")
                    .transactionTime(LocalDateTime.now())
                    .build().insert();


            int finalI = i;
            Thread t = new Thread(() -> transactionFlowMapper.updateCuz(String.valueOf(finalI)));
            int finalI1 = i;
            Thread t1 = new Thread(() -> transactionFlowMapper.updateCuz1(String.valueOf(finalI1)));
            t.start();
            t1.start();
        }


    }
//
//    @Test
//    public void pageTest() {
//        transactionFlowService.pageTest();
//    }
//
//    @Test
//    public void cuzTest() {
//        transactionFlowService.czEnumTest();
//    }
//
//    @Test
//    public void czSqlTest() {
//        transactionFlowService.czSqlTest();
//    }
//
//    @Test
//    public void mqsendlTest() {
//        httpMqService.sendMessage();
//    }
//
//    @Test
//    public void mqgetlTest() {
//        httpMqService.getMessage();
//    }
//
//    @Test
//    public void mqSendTest() {
//        tcpMqService.sendMsg();
//    }

}
