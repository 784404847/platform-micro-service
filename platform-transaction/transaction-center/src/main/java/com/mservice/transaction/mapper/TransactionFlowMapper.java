package com.mservice.transaction.mapper;

import com.mservice.transaction.model.TransactionFlow;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 交易流水表
 *
 * @author walle
 * @date 2020-08-30
 */
@Repository
public interface TransactionFlowMapper extends Mapper<TransactionFlow> {
}