package cn.metsea.lotus.service.zookeeper.opeartor;

import cn.metsea.lotus.common.constants.LotusConstants;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.stereotype.Component;

/**
 * Abstract Zookeeper Operator
 */
@Slf4j
@Component
public class AbstractZookeeperOperator extends ZookeeperCachedOperator {

    public void releaseMutex(InterProcessMutex mutex) {
        if (mutex != null) {
            try {
                mutex.release();
            } catch (Exception e) {
                log.error("release mutex lock failed", e);
            }
        }
    }

    public String getMasterActiveLockPath() {
        return super.getZookeeperConfig().getLotusRoot() + LotusConstants.ZOOKEEPER_LOTUS_LOCK_ACTIVE_MASTER;
    }

    protected int getMasterNum() {
        List<String> childrenList = new ArrayList<>();
        try {
            String masterZNodeParentPath = this.getMasterZNodeParentPath();
            if (super.isExistedPath(masterZNodeParentPath)) {
                childrenList = super.getChildrenList(masterZNodeParentPath);
            }
        } catch (Exception e) {
            log.error("getMasterActiveNum exception", e);
        }
        return childrenList.size();
    }

    public String getMasterZNodeParentPath() {
        return this.getZookeeperConfig().getLotusRoot() + LotusConstants.ZOOKEEPER_LOTUS_NODES_MASTER;
    }

}
