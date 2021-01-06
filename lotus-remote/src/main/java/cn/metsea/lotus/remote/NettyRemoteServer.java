package cn.metsea.lotus.remote;

import cn.metsea.lotus.common.utils.NamedThreadFactory;
import cn.metsea.lotus.remote.codec.MessageDecoder;
import cn.metsea.lotus.remote.codec.MessageEncoder;
import cn.metsea.lotus.remote.config.NettyServerConfig;
import cn.metsea.lotus.remote.handler.NettyServerHandler;
import cn.metsea.lotus.remote.message.MessageType;
import cn.metsea.lotus.remote.processor.NettyRequestProcessor;
import cn.metsea.lotus.remote.utils.NettyUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty Remote Server
 */
@Slf4j
public class NettyRemoteServer {

    private ServerBootstrap server = new ServerBootstrap();

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private NettyServerConfig serverConfig;

    private NettyServerHandler serverHandler = new NettyServerHandler(this);

    @Getter
    private ExecutorService defaultExecutor;

    public NettyRemoteServer(NettyServerConfig nettyServerConfig) {
        // config
        this.serverConfig = nettyServerConfig;

        // init
        this.init();
    }

    public void init() {
        // executor
        int workerThread = this.serverConfig.getWorkerThread();
        this.defaultExecutor = new ScheduledThreadPoolExecutor(workerThread, new NamedThreadFactory("NettyServerDefaultExecutor", workerThread));

        // netty
        if (NettyUtils.useEpoll()) {
            this.bossGroup = new EpollEventLoopGroup(1, new NamedThreadFactory("NettyServerBossThread"));
            this.workerGroup = new EpollEventLoopGroup(workerThread, new NamedThreadFactory("NettyServerWorkerThread", workerThread));
        } else {
            this.bossGroup = new NioEventLoopGroup(1, new NamedThreadFactory("NettyServerBossThread"));
            this.workerGroup = new NioEventLoopGroup(workerThread, new NamedThreadFactory("NettyServerWorkerThread", workerThread));
        }
    }

    public void start() {
        this.server
            .group(this.bossGroup, this.workerGroup)
            .channel(NettyUtils.getServerSocketChannelClass())
            .option(ChannelOption.SO_REUSEADDR, this.serverConfig.isSoReuseAddr())
            .option(ChannelOption.SO_BACKLOG, this.serverConfig.getSoBacklog())
            .childOption(ChannelOption.SO_KEEPALIVE, this.serverConfig.isSoKeepalive())
            .childOption(ChannelOption.TCP_NODELAY, this.serverConfig.isTcpNoDelay())
            .childOption(ChannelOption.SO_RCVBUF, this.serverConfig.getReceiveBufferSize())
            .childOption(ChannelOption.SO_SNDBUF, this.serverConfig.getSendBufferSize())
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    initNettyChannel(ch);
                }
            });

        ChannelFuture future;
        try {
            future = this.server.bind(this.serverConfig.getListenIp(), this.serverConfig.getListenPort()).sync();
        } catch (Exception e) {
            String msg = String.format("NettyRemoteServer bind failed on address %s:%d", this.serverConfig.getListenIp(), this.serverConfig.getListenPort());
            log.error(msg, e);
            throw new RuntimeException(msg);
        }

        if (future.isSuccess()) {
            log.info("NettyRemoteServer bind successful on address {}:{}", this.serverConfig.getListenIp(), this.serverConfig.getListenPort());
        } else {
            throw new RuntimeException(
                String.format("NettyRemoteServer bind successful on address %s:%d", this.serverConfig.getListenIp(), this.serverConfig.getListenPort()),
                future.cause()
            );
        }
    }

    private void initNettyChannel(SocketChannel ch) {
        ch.pipeline()
            .addLast("encoder", new MessageEncoder())
            .addLast("decoder", new MessageDecoder())
            .addLast("idle-state-handler", new IdleStateHandler(0, 0, this.serverConfig.getHeartbeatTime(), TimeUnit.MILLISECONDS))
            .addLast("server-handler", this.serverHandler);
    }

    public void registerProcessor(MessageType messageType, NettyRequestProcessor processor) {
        registerProcessor(messageType, processor, null);
    }

    public void registerProcessor(MessageType messageType, NettyRequestProcessor processor, ExecutorService executor) {
        this.serverHandler.registerProcessor(messageType, processor, executor);
    }

    public void close() {
        try {
            if (this.bossGroup != null) {
                this.bossGroup.shutdownGracefully();
            }
            if (this.workerGroup != null) {
                this.workerGroup.shutdownGracefully();
            }
            this.defaultExecutor.shutdown();
        } catch (Exception e) {
            log.error("NettyRemoteServer close exception", e);
        }
        log.info("NettyRemoteServer close successful");
    }
}
