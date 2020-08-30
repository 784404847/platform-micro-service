package com.mservice.transaction.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.*;

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
@ApiModel(value = "交易流水表", description = "数据库表：transaction_flow")
@Table(name = "transaction_flow")
public class TransactionFlow {

    @ApiModelProperty(value = "", name = "id")
    @Id
    private Long id;

    @ApiModelProperty(value = "交易流水", name = "flowNo")
    private Long flowNo;

    @ApiModelProperty(value = "交易金额", name = "transactionAmount")
    private BigDecimal transactionAmount;

    @ApiModelProperty(value = "交易时间", name = "transactionTime")
    private LocalDateTime transactionTime;

    @ApiModelProperty(value = "", name = "createTime")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "", name = "updateTime")
    private LocalDateTime updateTime;
}