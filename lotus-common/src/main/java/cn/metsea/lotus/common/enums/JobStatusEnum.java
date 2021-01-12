package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JobStatusEnum {
    ONLINE(1, "Online"),
    OFFLINE(2, "Offline");

    @EnumValue
    private Integer code;
    @JsonValue
    private String label;

    JobStatusEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }
}
