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
 * Trigger Matrix
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_trigger_matrix", autoResultMap = true)
public class TriggerMatrix implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Trigger Id
     */
    private Integer triggerId;

    /**
     * Rule
     */
    private String rule;

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
