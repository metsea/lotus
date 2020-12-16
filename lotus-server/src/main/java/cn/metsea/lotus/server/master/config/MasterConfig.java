package cn.metsea.lotus.server.master.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Master Config
 */
@Getter
@Component
@PropertySource("classpath:master.properties")
@ConfigurationProperties(prefix = "master")
public class MasterConfig {

    @Value("${master.listen.ip}")
    private String listenIp;

    @Value("${master.listen.port}")
    private int listenPort;

    @Value("${heartbeat.interval:10}")
    private int heartbeatInterval;

}
