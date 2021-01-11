package cn.metsea.lotus.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JobTypeEnum {
    SHELL(10, "Shell"),
    HIVE(20, "Hive"),
    SPARK(30, "Spark"),
    FLINK(40, "Flink"),
    SQOOP(50, "Sqoop"),
    JAVA_JAR(60, "Java / Jar");

    @EnumValue
    private Integer code;
    @JsonValue
    private String label;

    @JsonCreator
    JobTypeEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }
}
