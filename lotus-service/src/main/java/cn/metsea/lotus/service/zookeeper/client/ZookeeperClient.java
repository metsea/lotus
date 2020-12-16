package cn.metsea.lotus.service.zookeeper.client;

import cn.metsea.lotus.service.zookeeper.config.DefaultEnsembleProvider;
import cn.metsea.lotus.service.zookeeper.config.ZookeeperConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Zookeeper Curator Client
 */
@Slf4j
@Component
public class ZookeeperClient implements InitializingBean {

    @Getter
    @Autowired
    private ZookeeperConfig zookeeperConfig;

    @Getter
    private CuratorFramework client;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.client = buildClient();
        initListener();
    }

    private CuratorFramework buildClient() {
        log.info("zookeeper curator client init. the zookeeper server quorum is: {}", this.zookeeperConfig.getQuorum());
        CuratorFrameworkFactory.Builder clientBuilder = CuratorFrameworkFactory.builder()
            .ensembleProvider(new DefaultEnsembleProvider(this.zookeeperConfig.getQuorum()))
            .retryPolicy(new ExponentialBackoffRetry(this.zookeeperConfig.getBaseSleepTimeMs(), this.zookeeperConfig.getMaxRetries(), this.zookeeperConfig.getMaxSleepMs()));
        this.client = clientBuilder.build();
        this.client.start();
        try {
            this.client.blockUntilConnected();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this.client;
    }

    private void initListener() {
        this.addListener((client1, newState) -> {
            if (newState == ConnectionState.CONNECTED) {
                log.info("client connection CONNECTED to zookeeper");
            } else if (newState == ConnectionState.LOST) {
                log.error("client connection LOST from zookeeper");
            } else if (newState == ConnectionState.RECONNECTED) {
                log.info("client connection RECONNECTED to zookeeper");
            } else if (newState == ConnectionState.SUSPENDED) {
                log.warn("client connection SUSPENDED from zookeeper");
            }
        });
    }

    public void addListener(ConnectionStateListener listener) {
        this.client.getConnectionStateListenable().addListener(listener);
    }

}
