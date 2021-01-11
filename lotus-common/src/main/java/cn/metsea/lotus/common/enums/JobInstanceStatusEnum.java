package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JobInstanceStatusEnum {
    READY(10, "ready", false),
    RUNNING(20, "running", false),
    FAILED(30, "failed", true),
    SUCCESS(40, "success", true),
    STOPPING(50, "stopping", false),
    STOPPED(60, "stopped", true),
    TOLERANCE(70, "tolerance", false),
    UNKNOWN(-1, "unknown", false);

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
