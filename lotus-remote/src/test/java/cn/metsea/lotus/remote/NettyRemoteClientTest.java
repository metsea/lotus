package cn.metsea.lotus.remote;

import cn.metsea.lotus.remote.config.NettyClientConfig;
import cn.metsea.lotus.remote.config.NettyServerConfig;
import cn.metsea.lotus.remote.message.MessageType;
import cn.metsea.lotus.remote.message.heartbeat.Ping;
import cn.metsea.lotus.remote.message.heartbeat.Pong;
import cn.metsea.lotus.remote.model.Host;
import org.junit.Test;

public class NettyRemoteClientTest {

    @Test
    public void testPingPong() throws InterruptedException {
        // server
        NettyServerConfig serverConfig = new NettyServerConfig();
        NettyRemoteServer server = new NettyRemoteServer(serverConfig);
        server.registerProcessor(MessageType.PING, (channel, message) -> {
            System.out.println("server process message : " + message);
            channel.writeAndFlush(new Pong().convert());
        });
        server.start();

        // client
        NettyRemoteClient client = new NettyRemoteClient(new NettyClientConfig());
        client.registerProcessor(MessageType.PONG, (channel, message) -> {
            System.out.println("client process message : " + message);
        });
        client.send(new Host(serverConfig.getListenIp(), serverConfig.getListenPort()), new Ping().convert());

        // sleep for processors
        Thread.sleep(1000 * 3);

        // close
        server.close();
        client.close();
    }

}
