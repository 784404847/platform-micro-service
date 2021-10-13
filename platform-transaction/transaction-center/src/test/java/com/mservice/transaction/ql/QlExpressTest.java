package com.mservice.transaction.ql;

import com.mservice.transaction.util.QlExpressService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wejam
 * @description
 * @date 2021/10/13 下午5:01
 */
@SpringBootTest
@Slf4j
public class QlExpressTest {

    @Autowired
    private QlExpressService qlExpressService;

    @Test
    void futureFunTest(){

        String express = " mid_price('btc_perp') - mid_price('btc/usd') > 50 ? true : false";

        Map<String, Object> context = new HashMap<>();
        try {
            Object execute = qlExpressService.execute(express, context);
            System.out.println(execute);
        } catch (Exception e) {
            log.error("执行 express 失败 {}",express,e);
        }
    }

}
