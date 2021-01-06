package cn.metsea.lotus.remote.config;

import cn.metsea.lotus.common.utils.OsUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Netty Client Config
 */
@Getter
@Setter
public class NettyClientConfig {

    private int soBacklog = 1024;

    private boolean tcpNoDelay = true;

    private boolean soReuseAddr = true;

    private boolean soKeepalive = true;

    private int sendBufferSize = 65535;

    private int receiveBufferSize = 65535;

    private int heartbeatTime = 1000 * 60;

    private int connectTimeoutMillis = 3000;

    private int workerThread = OsUtils.getAvailableProcessors();

}
