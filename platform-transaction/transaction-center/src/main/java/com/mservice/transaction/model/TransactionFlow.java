package com.mservice.transaction.model;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.mservice.transaction.enums.TxStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.EnumTypeHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易流水表
 *
 * @author walle
 * @date 2020-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "transaction_flow",autoResultMap = true)
public class TransactionFlow extends Model {

    @Schema(title = "id", name = "id")
    @TableId
    private Long id;

    @Schema(title = "交易流水", name = "flowNo")
    private Long flowNo;

    @Schema(title = "交易金额", name = "transactionAmount")
    @TableField(updateStrategy = FieldStrategy.DEFAULT)
    private String transactionAmount;

    @Schema(title = "交易时间", name = "transactionTime")
    private LocalDateTime transactionTime;

    @Schema(title = "状态", name = "txStatusEnum")
    @TableField(typeHandler = EnumTypeHandler.class)
    private TxStatusEnum flowStatus;

    @Schema(title = "", name = "createTime")
    private LocalDateTime createTime;

    @Schema(title = "", name = "updateTime")
    private LocalDateTime updateTime;
}