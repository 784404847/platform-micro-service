package com.mservice.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mservice.transaction.model.TransactionFlow;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 交易流水表
 *
 * @author walle
 * @date 2020-08-30
 */
@Repository
@Mapper
public interface TransactionFlowMapper extends BaseMapper<TransactionFlow> {

    List<TransactionFlow> selectByQuery(Map<String, Object> columnMap);
}