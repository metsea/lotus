package cn.metsea.lotus.dao.entity;
import cn.metsea.lotus.common.enums.JobStatusEnum;
import cn.metsea.lotus.common.enums.JobTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Job
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_job",autoResultMap = true)
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
     * Type
     * {@link JobTypeEnum}
     */
    private JobTypeEnum type;

    /**
     * Online or Offline Status
     * {@link JobStatusEnum}
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
