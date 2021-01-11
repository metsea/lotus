package cn.metsea.lotus.dao.entity;
import cn.metsea.lotus.common.enums.TransactionStatusEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Transaction
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_transaction",autoResultMap = true)
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
     * config
     */
    private String config;

    /**
     * Status
     * {@link TransactionStatusEnum}
     */
    private TransactionStatusEnum status;

    /**
     * Start Time
     */
    private Date startTime;

    /**
     * End Time
     */
    private Date endTime;

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
    @TableLogic
    private Integer logicalDel;

}
