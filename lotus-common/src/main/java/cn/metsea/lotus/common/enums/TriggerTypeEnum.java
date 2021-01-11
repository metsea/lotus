package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TriggerTypeEnum {
    SCHEDULE(10, "schedule"),
    LINEAR_DEPENDENT(20, "linearDependent"),
    MATRIX_DEPENDENT(30, "matrixDependent");
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
