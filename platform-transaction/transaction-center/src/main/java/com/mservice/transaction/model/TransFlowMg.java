package com.mservice.transaction.model;

import com.mservice.transaction.enums.TxStatusEnum;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @Author: wejam
 * @Description
 * @Date: 2021/8/30 下午4:17
 */
@Document(collection = "trans_flow")
@Data
public class TransFlowMg {


    @Id
    private Long id;

    @Indexed(unique = true)
    private Long flowNo;

    private String transactionAmount;

    private LocalDateTime transactionTime;

    private TxStatusEnum flowStatus;

    @Transient
    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
