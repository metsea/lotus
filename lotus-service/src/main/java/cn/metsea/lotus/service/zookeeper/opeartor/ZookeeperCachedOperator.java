package cn.metsea.lotus.service.zookeeper.opeartor;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.springframework.stereotype.Component;

/**
 * Zookeeper Cached Operator
 */
@Slf4j
@Component
public class ZookeeperCachedOperator extends ZookeeperOperator {

    private TreeCache treeCache;

    @Override
    protected void initListener() {
        String nodesPath = super.getZookeeperConfig().getLotusRoot() + "/nodes";
        this.treeCache = new TreeCache(super.getClient(), nodesPath);
        log.info("init listener for zookeeper path: {}", nodesPath);
        try {
            this.treeCache.start();
        } catch (Exception e) {
            log.error("init listener for zookeeper path: {} failed.", nodesPath);
            throw new RuntimeException(e);
        }

        this.treeCache.getListenable().addListener((client, event) -> {
            String path = null == event.getData() ? "" : event.getData().getPath();
            if (path.isEmpty()) {
                return;
            }
            dataChanged(client, event, path);
        });

    }

    protected void dataChanged(CuratorFramework client, TreeCacheEvent event, String path) {
        // NOP
    }

}
