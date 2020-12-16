package cn.metsea.lotus.service.zookeeper.opeartor;

import cn.metsea.lotus.service.zookeeper.client.ZookeeperClient;
import cn.metsea.lotus.service.zookeeper.config.ZookeeperConfig;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.DeleteBuilder;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NoNodeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Zookeeper Operator
 */
@Slf4j
@Component
public class ZookeeperOperator implements InitializingBean {

    @Autowired
    private ZookeeperClient zookeeperClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.initListener();
    }

    /**
     * init listener for sub class
     */
    protected void initListener() {
        // NOP
    }

    public void addListener(ConnectionStateListener listener) {
        this.zookeeperClient.addListener(listener);
    }

    public ZookeeperConfig getZookeeperConfig() {
        return this.zookeeperClient.getZookeeperConfig();
    }

    public void upsertPersist(final String path, final String value, boolean creatingParentsIfNeeded) {
        try {
            if (!isExistedPath(path)) {
                this.createPath(path, value, CreateMode.PERSISTENT, creatingParentsIfNeeded);
            } else {
                this.updatePath(path, value);
            }
        } catch (Exception e) {
            log.error("upsert persist path : {} , value : {}", path, value, e);
        }
    }

    public void upsertEphemeral(final String path, final String value, boolean creatingParentsIfNeeded) {
        try {
            if (isExistedPath(path)) {
                try {
                    this.deletePath(path, true);
                } catch (NoNodeException e) {
                    // NOP
                }
            }
            this.createPath(path, value, CreateMode.EPHEMERAL, creatingParentsIfNeeded);
        } catch (final Exception e) {
            log.error("upsertEphemeral path : {} , value : {}", path, value, e);
        }
    }

    public boolean isExistedPath(final String path) {
        try {
            return this.zookeeperClient.getClient().checkExists().forPath(path) != null;
        } catch (Exception e) {
            log.error("isExistedPath path : {}", path, e);
        }
        return false;
    }

    public void createPath(String path, String value, CreateMode createMode, boolean creatingParentsIfNeeded) throws Exception {
        CreateBuilder builder = this.zookeeperClient.getClient().create();
        if (creatingParentsIfNeeded) {
            builder.creatingParentsIfNeeded().withMode(createMode).forPath(path, value.getBytes(StandardCharsets.UTF_8));
        } else {
            builder.withMode(createMode).forPath(path, value.getBytes(StandardCharsets.UTF_8));
        }
    }

    public void updatePath(final String path, final String value) throws Exception {
        this.zookeeperClient.getClient().setData().forPath(path, value.getBytes(StandardCharsets.UTF_8));
    }

    public void deletePath(String path, boolean deletingChildrenIfNeeded) throws Exception {
        DeleteBuilder deleteBuilder = this.zookeeperClient.getClient().delete();
        if (deletingChildrenIfNeeded) {
            deleteBuilder.deletingChildrenIfNeeded().forPath(path);
        } else {
            deleteBuilder.forPath(path);
        }
    }

    public CuratorFramework getClient() {
        return this.zookeeperClient.getClient();
    }

}
