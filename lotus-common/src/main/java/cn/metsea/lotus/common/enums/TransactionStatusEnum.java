package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TransactionStatusEnum {
    RUNNING(10, "Running"),
    SUCCESS(20, "Success"),
    FAILED(30, "Failed");

    @EnumValue
    private Integer code;
    @JsonValue
    private String label;

    TransactionStatusEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }
}
