package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TransactionStatusEnum {
    RUNNING(10, "running"),
    SUCCEED(20, "succeed"),
    FAILED(30, "failed");

    @EnumValue
    private Integer code;
    @JsonValue
    private String desc;

    @JsonCreator
    TransactionStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}