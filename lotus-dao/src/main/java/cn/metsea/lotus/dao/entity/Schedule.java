package cn.metsea.lotus.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Schedule
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_schedule", autoResultMap = true)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Job Id
     */
    private Integer jobId;

    /**
     * Fire Time
     */
    private Date fireTime;

    /**
     * Create Time
     */
    private Date createTime;

    /**
     * Update Time
     */
    private Date updateTime;

    /**
     * Logical Delete
     */
    private Integer logicalDel;

}
