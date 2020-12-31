package com.mservice.transaction.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.mservice.transaction.model.TransactionFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 交易流水表
 *
 * @author walle
 * @date 2020-08-30
 */
@Mapper
@Repository
public interface TransactionFlowMapper extends BaseMapper<TransactionFlow> {

    List<TransactionFlow> selectById(@Param("id") Long id);

    @Select(value = "select * from transaction_flow ${ew.customSqlSegment}")
    TransactionFlow selectQueryWrapper(@Param(Constants.WRAPPER) Wrapper queryWrapper);
}