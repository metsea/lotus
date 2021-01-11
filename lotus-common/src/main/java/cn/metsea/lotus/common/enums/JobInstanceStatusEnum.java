package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JobInstanceStatusEnum {
    WAITING_START(10, "waitingStart", false),
    STARTING(20, "starting", false),
    RUNNING(30, "running", false),
    START_FAILED(40, "startFailed", true),
    RUN_FAILED(50, "runFailed", true),
    STOPPED(60, "stopped", true),
    SUCCESS(70, "success", true),
    UNKNOWN(-1, "unknown", false);

    @EnumValue
    private Integer code;
    @JsonValue
    private String desc;

    private boolean FinalState;

    JobInstanceStatusEnum(Integer code, String desc, boolean finalState) {
        this.code = code;
        this.desc = desc;
        FinalState = finalState;
    }
}
