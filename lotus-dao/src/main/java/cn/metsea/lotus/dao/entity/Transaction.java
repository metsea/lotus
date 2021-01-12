package cn.metsea.lotus.dao.entity;

import cn.metsea.lotus.common.enums.TransactionStatusEnum;
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
 * Transaction
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_transaction", autoResultMap = true)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * All Job DAG
     */
    private String dag;

    /**
     * Config
     */
    private String config;

    /**
     * Status {@link TransactionStatusEnum}
     */
    private TransactionStatusEnum status;

    /**
     * Start Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    /**
     * End Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

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
