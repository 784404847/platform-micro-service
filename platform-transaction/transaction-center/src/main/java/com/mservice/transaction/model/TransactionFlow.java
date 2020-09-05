package com.mservice.transaction.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易流水表
 *
 * @author walle
 * @date 2020-08-30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "交易流水表",name = "TransactionFlow",description = "数据库表：transaction_flow")
@Table(name = "transaction_flow")
public class TransactionFlow {

    @Schema(title = "id", name = "id")
    @Id
    private Long id;

    @Schema(title = "交易流水", name = "flowNo")
    private Long flowNo;

    @Schema(title = "交易金额", name = "transactionAmount")
    private BigDecimal transactionAmount;

    @Schema(title = "交易时间", name = "transactionTime")
    private LocalDateTime transactionTime;

    @Schema(title = "", name = "createTime")
    private LocalDateTime createTime;

    @Schema(title = "", name = "updateTime")
    private LocalDateTime updateTime;
}