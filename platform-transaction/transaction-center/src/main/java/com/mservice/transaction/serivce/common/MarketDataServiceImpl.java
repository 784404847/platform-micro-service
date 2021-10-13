package com.mservice.transaction.serivce.common;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wejam
 * @description
 * @date 2021/10/13 下午5:16
 */
@Service
public class MarketDataServiceImpl {

    public BigDecimal midPrice(String symbol){
        if(symbol.toUpperCase().contains("/USD")){
            return new BigDecimal("99.999");
        }
        return new BigDecimal("100.001");
    }

}
