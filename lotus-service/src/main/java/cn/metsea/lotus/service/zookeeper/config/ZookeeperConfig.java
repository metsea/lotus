package cn.metsea.lotus.service.zookeeper.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Zookeeper Config
 */
@Getter
@Component
@PropertySource("classpath:zookeeper.properties")
@ConfigurationProperties(prefix = "zookeeper")
public class ZookeeperConfig {

    @Value("${zookeeper.lotus.root:/lotus}")
    private String lotusRoot;

    @Value("${zookeeper.quorum}")
    private String quorum;

    @Value("${zookeeper.retry.base.sleep.ms:100}")
    private int baseSleepTimeMs;

    @Value("${zookeeper.retry.max.sleep.ms:30000}")
    private int maxSleepMs;

    @Value("${zookeeper.retry.max.retries:10}")
    private int maxRetries;

}
