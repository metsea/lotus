package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TriggerTypeEnum {
    CRON(10, "Cron"),
    LINEAR(20, "Linear"),
    MATRIX(30, "Matrix");
    @EnumValue
    private Integer code;
    @JsonValue
    private String label;

    @JsonCreator
    TriggerTypeEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }
}
