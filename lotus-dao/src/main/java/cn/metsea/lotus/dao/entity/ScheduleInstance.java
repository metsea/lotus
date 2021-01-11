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
 * Schedule Instance
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_schedule_instance",autoResultMap = true)
public class ScheduleInstance implements Serializable {

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
    @TableLogic
    private Integer logicalDel;

}
