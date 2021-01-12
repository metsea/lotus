package cn.metsea.lotus.dao.entity;

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
 * Trigger Matrix Job Instance
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_trigger_matrix_job_inst", autoResultMap = true)
public class TriggerMatrixJobInst implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Job Id
     */
    private Integer jobId;

    /**
     * Base Date
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date baseDate;

    /**
     * Name
     */
    private String name;

    /**
     * Fire Time
     */
    private String fireTime;

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
