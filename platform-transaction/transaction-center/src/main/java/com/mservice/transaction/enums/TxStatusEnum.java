package com.mservice.transaction.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: wejam
 * @Description
 * @Date: 2020/12/9 上午11:56
 */
@Getter
@AllArgsConstructor
public enum TxStatusEnum {

    /*code 描述*/
    INIT(1, "初始化"),
    ING(2, "处理中"),
    END(3, "结束"),
    ;

    @EnumValue
    private Integer code;

    private String desc;
}
