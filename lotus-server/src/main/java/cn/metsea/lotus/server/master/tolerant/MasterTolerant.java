package cn.metsea.lotus.server.master.tolerant;

import cn.metsea.lotus.common.constants.CommonConstants;
import cn.metsea.lotus.common.constants.LotusConstants;
import cn.metsea.lotus.common.utils.StringUtils;
import cn.metsea.lotus.server.registry.ZookeeperRegistryCenter;
import cn.metsea.lotus.service.zookeeper.opeartor.AbstractZookeeperOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Master Tolerant
 */
@Slf4j
@Component
public class MasterTolerant extends AbstractZookeeperOperator {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    public void start() {
        // preempt lock
        InterProcessMutex mutex = null;
        try {
            // distributed mutex lock : /lotus/lock/active/master
            String masterActiveLockPath = super.getMasterActiveLockPath();
            mutex = new InterProcessMutex(super.getClient(), masterActiveLockPath);
            mutex.acquire();

            // tolerant
            if (super.getMasterNum() == 1) {
                this.handleFailoverWorker(null);
                this.handleFailoverMaster(null);
            }

            // TDO
        } catch (Exception e) {
            log.error("master server tolerant exception", e);
        } finally {
            super.releaseMutex(mutex);
        }

        log.info("master tolerant is successful");
    }


    @Override
    protected void dataChanged(CuratorFramework client, TreeCacheEvent event, String path) {
        if (path.startsWith(this.zookeeperRegistryCenter.getMasterPath() + CommonConstants.SINGLE_SLASH)) {
            // master data changed
            this.handleMasterDataChanged(event, path);
        } else if (path.startsWith(this.zookeeperRegistryCenter.getWorkerPath() + CommonConstants.SINGLE_SLASH)) {
            // worker data changed
            this.handleWorkerDataChanged(event, path);
        }
    }

    private void handleMasterDataChanged(TreeCacheEvent event, String path) {
        switch (event.getType()) {
            case NODE_ADDED:
                this.handleMasterNodeAdded(path);
                break;
            case NODE_REMOVED:
                this.handleMasterNodeRemoved(path);
                break;
            default:
                break;
        }
    }

    private void handleMasterNodeAdded(String path) {
        log.info("master node added to path : {}", path);
        try {
            this.handleDeadMaster(path, Type.NODE_ADDED);
        } catch (Exception e) {
            log.error("handler master NODE_ADDED exception", e);
        }
    }

    private void handleMasterNodeRemoved(String path) {
        InterProcessMutex mutex = null;
        try {
            // lock
            String failoverMasterPath = this.zookeeperRegistryCenter.getFailoverMasterPath();
            mutex = new InterProcessMutex(super.getClient(), failoverMasterPath);
            mutex.acquire();

            // handle dead
            this.handleDeadMaster(path, Type.NODE_REMOVED);

            // failover
            String masterAddress = this.getAddressByEventDataPath(path);
            this.handleFailoverMaster(masterAddress);
        } catch (Exception e) {
            log.error("master server failover exception", e);
        } finally {
            super.releaseMutex(mutex);
        }
    }

    private void handleDeadMaster(String path, Type eventType) throws Exception {
        String address = this.getAddressByEventDataPath(path);
        String deadMasterPath = this.zookeeperRegistryCenter.getDeadPath()
            + CommonConstants.SINGLE_SLASH + LotusConstants.MASTER + CommonConstants.SINGLE_SLASH + address;
        switch (eventType) {
            case NODE_ADDED:
                if (super.isExistedPath(deadMasterPath)) {
                    log.info("master node : {} was recovered, and delete from dead path : {}", address, deadMasterPath);
                    super.deletePath(deadMasterPath, true);
                }
                break;
            case NODE_REMOVED:
                log.info("master node : {} was removed, and add to dead path : {}", address, deadMasterPath);
                super.upsertPersist(deadMasterPath, "", true);
                break;
            default:
                break;
        }
    }

    private void handleFailoverMaster(String masterAddress) {
        log.info("start failover master ...");
        // TDO
        log.info("failover master is successful");
    }

    private void handleWorkerDataChanged(TreeCacheEvent event, String path) {
        switch (event.getType()) {
            case NODE_ADDED:
                this.handleWorkerNodeAdded(path);
                break;
            case NODE_REMOVED:
                this.handleWorkerNodeRemoved(path);
                break;
            default:
                break;
        }
    }

    private void handleWorkerNodeAdded(String path) {
        log.info("worker node added to path : {}", path);
        try {
            this.handleDeadWorker(path, Type.NODE_ADDED);
        } catch (Exception e) {
            log.error("handler worker NODE_ADDED exception", e);
        }
    }

    private void handleDeadWorker(String path, Type eventType) throws Exception {
        String address = this.getAddressByEventDataPath(path);
        String deadWorkerPath = this.zookeeperRegistryCenter.getDeadPath()
            + CommonConstants.SINGLE_SLASH + LotusConstants.WORKER + CommonConstants.SINGLE_SLASH + address;
        switch (eventType) {
            case NODE_ADDED:
                if (super.isExistedPath(deadWorkerPath)) {
                    log.info("worker node : {} was recovered, and delete from dead path : {}", address, deadWorkerPath);
                    super.deletePath(deadWorkerPath, true);
                }
                break;
            case NODE_REMOVED:
                log.info("worker node : {} was removed, and add to dead path : {}", address, deadWorkerPath);
                super.upsertPersist(deadWorkerPath, "", true);
                break;
            default:
                break;
        }
    }

    private void handleWorkerNodeRemoved(String path) {
        InterProcessMutex mutex = null;
        try {
            // lock
            String failoverMasterPath = this.zookeeperRegistryCenter.getFailoverWorkerPath();
            mutex = new InterProcessMutex(super.getClient(), failoverMasterPath);
            mutex.acquire();

            // handle dead
            this.handleDeadWorker(path, Type.NODE_REMOVED);

            // failover
            String workerAddress = this.getAddressByEventDataPath(path);
            this.handleFailoverWorker(workerAddress);
        } catch (Exception e) {
            log.error("handle worker NODE_REMOVED exception", e);
        }
    }

    private void handleFailoverWorker(String workerAddress) {
        log.info("start failover worker  ...");
        // TDO
        log.info("failover worker is successful");
    }

    protected String getAddressByEventDataPath(String path) {
        if (StringUtils.isEmpty(path)) {
            log.error("empty path!");
            return "";
        }
        String[] pathArray = path.split(CommonConstants.SINGLE_SLASH);
        if (pathArray.length < 1) {
            log.error("parse address error: {}", path);
            return "";
        }
        return pathArray[pathArray.length - 1];
    }

}
