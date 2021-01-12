package cn.metsea.lotus.dao.entity;

import cn.metsea.lotus.common.enums.JobStatusEnum;
import cn.metsea.lotus.common.enums.JobTypeEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Job
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_job", autoResultMap = true)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Name
     */
    private String name;

    /**
     * Description
     */
    private String description;

    /**
     * Type {@link JobTypeEnum}
     */
    private JobTypeEnum type;

    /**
     * Online or Offline Status {@link JobStatusEnum}
     */
    private JobStatusEnum status;

    /**
     * Config
     */
    private String config;

    /**
     * Last Instance
     */
    private Long lastInstance;

    /**
     * Create Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * Update Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * Logical Delete
     */
    @TableLogic
    private Integer logicalDel;

}
