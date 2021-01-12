package cn.metsea.lotus.dao.entity;

import cn.metsea.lotus.common.enums.JobInstanceStatusEnum;
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
 * Instance
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_instance", autoResultMap = true)
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Job Id
     */
    private Integer jobId;

    /**
     * Transaction Id
     */
    private Long transactionId;

    /**
     * Fire Time
     */
    private Date fireTime;

    /**
     * Status {@link JobInstanceStatusEnum}
     */
    private JobInstanceStatusEnum status;

    /**
     * Work Host
     */
    private String workHost;

    /**
     * Application Id
     */
    private String appId;

    /**
     * Config
     */
    private String config;

    /**
     * Log File
     */
    private String logFile;

    /**
     * Script File
     */
    private String scriptFile;

    /**
     * Application Ui
     */
    private String ui;

    /**
     * Start Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * Stop Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stopTime;

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
