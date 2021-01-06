package cn.metsea.lotus.remote.config;

import cn.metsea.lotus.common.utils.OsUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Netty Server Config
 */
@Getter
@Setter
public class NettyServerConfig {

    private String listenIp = "127.0.0.1";

    private int listenPort = 8006;

    private int soBacklog = 1024;

    private boolean tcpNoDelay = true;

    private boolean soReuseAddr = true;

    private boolean soKeepalive = true;

    private int sendBufferSize = 65535;

    private int receiveBufferSize = 65535;

    private int heartbeatTime = 1000 * 60 * 3;

    private int workerThread = OsUtils.getAvailableProcessors();

}
