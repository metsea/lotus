package cn.metsea.lotus.server.worker.registry;

import cn.metsea.lotus.common.utils.DateUtils;
import cn.metsea.lotus.common.utils.NamedThreadFactory;
import cn.metsea.lotus.server.registry.HeartbeatThread;
import cn.metsea.lotus.server.registry.ZookeeperRegistryCenter;
import cn.metsea.lotus.server.worker.config.WorkerConfig;
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
 * Worker Registry
 */
@Component
@Slf4j
public class WorkerRegistry {

    @Autowired
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Autowired
    private WorkerConfig workerConfig;

    private String startTime;

    private ScheduledExecutorService heartbeatExecutor;

    private String workerIp;

    /**
     * worker port
     */
    private int workerPort;

    @PostConstruct
    public void init() {
        this.startTime = DateUtils.dateToString(new Date());
        this.heartbeatExecutor = new ScheduledThreadPoolExecutor(1,
            new NamedThreadFactory("WorkerHeartbeat", 1));
        workerIp = this.workerConfig.getListenIp();
        workerPort = this.workerConfig.getListenPort();
    }

    public void registry() {
        // worker registry
        String address = workerIp + ":" + workerIp;
        String workerRegistryPath = buildWorkerRegistryPath();
        this.zookeeperRegistryCenter.getZookeeperCachedOperator().upsertEphemeral(workerRegistryPath, "", true);
        this.zookeeperRegistryCenter.getZookeeperCachedOperator()
            .addListener((client, newState) -> {
                if (newState == ConnectionState.LOST) {
                    log.error("worker : {} connection LOST from zookeeper", address);
                } else if (newState == ConnectionState.RECONNECTED) {
                    log.info("worker : {} connection RECONNECTED to zookeeper", address);
                } else if (newState == ConnectionState.SUSPENDED) {
                    log.warn("worker : {} connection SUSPENDED from zookeeper", address);
                }
            });

        // worker heartbeat
        int heartbeatInterval = this.workerConfig.getHeartbeatInterval();
        HeartbeatThread heartbeatThread = new HeartbeatThread(workerRegistryPath, this.startTime, this.zookeeperRegistryCenter);
        this.heartbeatExecutor.scheduleAtFixedRate(heartbeatThread, 0, heartbeatInterval, TimeUnit.SECONDS);

        // worker log
        log.info("worker server : {} registered to zookeeper path {} is successful with heartbeat interval : {}s", address, workerRegistryPath, heartbeatInterval);
    }

    private String buildWorkerRegistryPath() {
        return this.zookeeperRegistryCenter.getWorkerPath() + "/" + workerIp + ":" + workerPort;
    }
}
