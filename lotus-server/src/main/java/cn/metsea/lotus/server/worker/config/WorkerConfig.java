package cn.metsea.lotus.server.worker.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Worker Config
 */
@Getter
@Component
@PropertySource("classpath:worker.properties")
@ConfigurationProperties(prefix = "worker")
public class WorkerConfig {

    @Value("${worker.listen.ip}")
    private String listenIp;

    @Value("${worker.listen.port}")
    private int listenPort;

    @Value("${heartbeat.interval:10}")
    private int heartbeatInterval;

}
