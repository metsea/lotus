package cn.metsea.lotus.server.master.remote;

import cn.metsea.lotus.remote.NettyRemoteServer;
import cn.metsea.lotus.remote.config.NettyServerConfig;
import cn.metsea.lotus.remote.message.MessageType;
import cn.metsea.lotus.server.master.config.MasterConfig;
import cn.metsea.lotus.server.master.processor.JobExecuteResponseProcessor;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Master Remote
 */
@Component
public class MasterRemote {

    @Autowired
    private MasterConfig masterConfig;

    private NettyServerConfig serverConfig;

    private NettyRemoteServer nettyRemoteServer;

    @PostConstruct
    private void init() {
        // server config
        this.serverConfig = new NettyServerConfig();
        this.serverConfig.setListenIp(this.masterConfig.getListenIp());
        this.serverConfig.setListenPort(this.masterConfig.getListenPort());

        // netty server
        this.nettyRemoteServer = new NettyRemoteServer(this.serverConfig);

        // register processor
        registerProcessor();
    }

    private void registerProcessor() {
        this.nettyRemoteServer.registerProcessor(MessageType.JOB_EXECUTE_RESPONSE, new JobExecuteResponseProcessor());
    }

    public void start() {
        this.nettyRemoteServer.start();
    }
}
