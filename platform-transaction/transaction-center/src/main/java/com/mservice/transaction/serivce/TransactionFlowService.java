package com.mservice.transaction.serivce;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.mservice.transaction.mapper.TransactionFlowMapper;
import com.mservice.transaction.model.TransactionFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/2 下午3:01
 */
@Service
@Slf4j
public class TransactionFlowService {

    @Autowired
    private TransactionFlowMapper transactionFlowMapper;

    public void selectTest() {
//        LambdaQueryWrapper<TransactionFlow> lambda1 = new QueryWrapper<TransactionFlow>().lambda();
        LambdaQueryChainWrapper<TransactionFlow> lambda2 = ChainWrappers.lambdaQueryChain(transactionFlowMapper);
//        LambdaQueryWrapper<Object> objectLambdaQueryWrapper = Wrappers.lambdaQuery();
//        lambda2.list();

        TransactionFlow.builder()
                .id(1L)
                .flowNo(124L)
                .transactionAmount("")
                .transactionTime(LocalDateTime.now())
                .build().insertOrUpdate();

        transactionFlowMapper.updateById(TransactionFlow.builder()
                .id(1L)
                .flowNo(124L)
                .transactionAmount("")
                .transactionTime(LocalDateTime.now())
                .build());


    }

    public void pageTest() {

        Page<TransactionFlow> page = ChainWrappers
                .lambdaQueryChain(transactionFlowMapper)
                .page(new Page<>(0, 10));

        log.info("返回分页内容：{}", JSON.toJSONString(page));
    }


    public void czEnumTest() {

        List<TransactionFlow> transactionFlows = transactionFlowMapper.selectById(1L);
        log.info("返回分页内容：{}", JSON.toJSONString(transactionFlows));
    }
}
