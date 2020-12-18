package cn.metsea.lotus.server.master.registry;

import cn.metsea.lotus.common.constants.CommonConstants;
import cn.metsea.lotus.common.utils.DateUtils;
import cn.metsea.lotus.common.utils.NamedThreadFactory;
import cn.metsea.lotus.server.master.config.MasterConfig;
import cn.metsea.lotus.server.registry.HeartbeatThread;
import cn.metsea.lotus.server.registry.ZookeeperRegistryCenter;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.state.ConnectionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Master Registry
 */
@Slf4j
@Component
public class MasterRegistry {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    private MasterConfig masterConfig;

    private String startTime;

    private ScheduledExecutorService heartbeatExecutor;

    @PostConstruct
    public void init() {
        this.startTime = DateUtils.dateToString(new Date());
        this.heartbeatExecutor = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("Heartbeat", 1));
    }

    public void registry() {
        // master registry
        String address = buildMasterAddress();
        String masterRegistryPath = buildMasterRegistryPath();
        this.zookeeperRegistryCenter.getZookeeperCachedOperator().upsertEphemeral(masterRegistryPath, "", true);
        this.zookeeperRegistryCenter.getZookeeperCachedOperator().addListener((client, newState) -> {
            if (newState == ConnectionState.LOST) {
                log.error("master : {} connection LOST from zookeeper", address);
            } else if (newState == ConnectionState.RECONNECTED) {
                log.info("master : {} connection RECONNECTED to zookeeper", address);
            } else if (newState == ConnectionState.SUSPENDED) {
                log.warn("master : {} connection SUSPENDED from zookeeper", address);
            }
        });

        // master heartbeat
        int heartbeatInterval = this.masterConfig.getHeartbeatInterval();
        HeartbeatThread heartbeatThread = new HeartbeatThread(masterRegistryPath, this.startTime, this.zookeeperRegistryCenter);
        this.heartbeatExecutor.scheduleAtFixedRate(heartbeatThread, 0, heartbeatInterval, TimeUnit.SECONDS);

        // master log
        log.info("master server : {} registered to zookeeper path {} is successful with heartbeat interval : {}s", address, masterRegistryPath, heartbeatInterval);
    }

    private String buildMasterAddress() {
        return this.masterConfig.getListenIp() + CommonConstants.COLON + this.masterConfig.getListenPort();
    }

    private String buildMasterRegistryPath() {
        return this.zookeeperRegistryCenter.getMasterPath() + CommonConstants.SINGLE_SLASH + this.buildMasterAddress();
    }

}
