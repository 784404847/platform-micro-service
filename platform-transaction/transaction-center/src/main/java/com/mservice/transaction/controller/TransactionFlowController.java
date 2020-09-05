package com.mservice.transaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/8/30 9:34 下午
 */
@RestController
@RequestMapping("/transactionFlow")
@Tag(name = "TransactionFlowController")
public class TransactionFlowController {

    @Operation(summary = "发起交易开始")
    @PostMapping(value = "/create")
    public String transactionCreate(@Parameter(name = "用户id") Long userId,
                                    @Parameter(name = "订单号") Long orderNo,
                                    @Parameter(name = "交易金额") BigDecimal amount) {

        return "";
    }

}
