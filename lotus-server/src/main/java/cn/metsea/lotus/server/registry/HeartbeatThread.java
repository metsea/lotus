package cn.metsea.lotus.server.registry;

import cn.metsea.lotus.common.utils.DateUtils;
import cn.metsea.lotus.common.utils.JsonUtils;
import cn.metsea.lotus.common.utils.OsUtils;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Heartbeat Thread
 */
@AllArgsConstructor
@Slf4j
public class HeartbeatThread extends Thread {

    private String heartbeatPath;
    private String startTime;
    private ZookeeperRegistryCenter zookeeperRegistryCenter;

    @Override
    public void run() {
        // metrics
        Map<String, Object> metrics = new LinkedHashMap<>();
        metrics.put("startTime", this.startTime);
        metrics.put("updateTime", DateUtils.dateToString(new Date()));
        metrics.put("totalMemorySize", OsUtils.totalMemorySize());
        metrics.put("availablePhysicalMemorySize", OsUtils.availablePhysicalMemorySize());
        metrics.put("loadAverage", OsUtils.loadAverage());
        metrics.put("cpuUsage", OsUtils.cpuUsage());
        metrics.put("processId", OsUtils.getProcessId());

        // update metrics to zookeeper
        try {
            this.zookeeperRegistryCenter.getZookeeperCachedOperator().updatePath(this.heartbeatPath, JsonUtils.toJsonString(metrics));
        } catch (Exception e) {
            log.error("heartbeat update metrics to zookeeper is failed", e);
        }
    }
}
