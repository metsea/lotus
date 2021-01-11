package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JobStatusEnum {
    ONLINE(1, "online"),
    OFFLINE(2, "offline");

    @EnumValue
    private Integer code;
    @JsonValue
    private String desc;

    JobStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
