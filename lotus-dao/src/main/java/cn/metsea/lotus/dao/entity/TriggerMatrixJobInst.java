package cn.metsea.lotus.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Trigger Matrix Job Instance
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_trigger_matrix_job_inst",autoResultMap = true)
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
