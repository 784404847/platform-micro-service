package com.mservice.transaction.mongo;

import com.mservice.transaction.dao.TransFlowMGDao;
import com.mservice.transaction.enums.TxStatusEnum;
import com.mservice.transaction.model.TransFlowMg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/8/30 下午4:36
 */
@SpringBootTest
public class MonGoTest {

    @Autowired
    private TransFlowMGDao transFlowMGDao;

    @Test
    void insetTest(){

        TransFlowMg t = new TransFlowMg();
        t.setId(1L);
        t.setFlowNo(9001L);
        t.setFlowStatus(TxStatusEnum.INIT);
        t.setTransactionTime(LocalDateTime.now());
        t.setTransactionAmount(BigDecimal.ZERO.toPlainString());
        t.setCreateTime(LocalDateTime.now());
        t.setUpdateTime(LocalDateTime.now());
        transFlowMGDao.saveTransFlow(t);
    }


}
