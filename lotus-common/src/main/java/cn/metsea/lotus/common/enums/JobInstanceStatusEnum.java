package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JobInstanceStatusEnum {
    READY(10, "Ready", false),
    RUNNING(20, "Running", false),
    FAILED(30, "Failed", true),
    SUCCEED(40, "Succeed", true),
    STOPPING(50, "Stopping", false),
    STOPPED(60, "Stopped", true),
    TOLERANCE(70, "Tolerance", false),
    UNKNOWN(-1, "Unknown", false);

    @EnumValue
    private Integer code;
    @JsonValue
    private String label;

    private boolean isFinal;

    JobInstanceStatusEnum(Integer code, String label, boolean isFinal) {
        this.code = code;
        this.label = label;
        this.isFinal = isFinal;
    }
}
