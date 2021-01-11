package cn.metsea.lotus.dao.entity;
import cn.metsea.lotus.common.enums.TriggerTypeEnum;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * Trigger
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "lotus_trigger",autoResultMap = true)
public class Trigger implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Job Id
     */
    private Integer jobId;

    /**
     * Type
     * {@link TriggerTypeEnum}
     */
    private TriggerTypeEnum type;

    /**
     * Config
     */
    private String config;

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
