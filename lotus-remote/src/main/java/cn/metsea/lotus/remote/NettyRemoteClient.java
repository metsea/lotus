package cn.metsea.lotus.remote;

import cn.metsea.lotus.common.utils.NamedThreadFactory;
import cn.metsea.lotus.remote.codec.MessageDecoder;
import cn.metsea.lotus.remote.codec.MessageEncoder;
import cn.metsea.lotus.remote.config.NettyClientConfig;
import cn.metsea.lotus.remote.handler.NettyClientHandler;
import cn.metsea.lotus.remote.message.Message;
import cn.metsea.lotus.remote.message.MessageType;
import cn.metsea.lotus.remote.model.Host;
import cn.metsea.lotus.remote.processor.NettyRequestProcessor;
import cn.metsea.lotus.remote.utils.NettyUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty Remote Client
 */
@Slf4j
public class NettyRemoteClient {

    private NettyClientConfig clientConfig;

    private final Bootstrap client = new Bootstrap();

    private ConcurrentHashMap<Host, Channel> channels = new ConcurrentHashMap<>(256);

    private EventLoopGroup workerGroup;

    private ExecutorService callbackExecutor;

    private NettyClientHandler clientHandler;

    @Getter
    private ExecutorService defaultExecutor;

    public NettyRemoteClient(NettyClientConfig clientConfig) {
        // config
        this.clientConfig = clientConfig;

        // init
        this.init();
    }

    public void init() {
        // executor
        int workerThread = this.clientConfig.getWorkerThread();
        this.callbackExecutor = new ScheduledThreadPoolExecutor(workerThread, new NamedThreadFactory("NettyClientCallbackExecutor", workerThread));
        this.defaultExecutor = new ScheduledThreadPoolExecutor(workerThread, new NamedThreadFactory("NettyClientDefaultExecutor", workerThread));

        // netty
        if (NettyUtils.useEpoll()) {
            this.workerGroup = new EpollEventLoopGroup(workerThread, new NamedThreadFactory("NettyClientWorkerThread", workerThread));
        } else {
            this.workerGroup = new NioEventLoopGroup(workerThread, new NamedThreadFactory("NettyClientWorkerThread", workerThread));
        }

        // handler
        this.clientHandler = new NettyClientHandler(this, this.callbackExecutor);

        // client
        this.client
            .group(this.workerGroup)
            .channel(NettyUtils.getSocketChannelClass())
            .option(ChannelOption.SO_KEEPALIVE, this.clientConfig.isSoKeepalive())
            .option(ChannelOption.TCP_NODELAY, this.clientConfig.isTcpNoDelay())
            .option(ChannelOption.SO_SNDBUF, this.clientConfig.getSendBufferSize())
            .option(ChannelOption.SO_RCVBUF, this.clientConfig.getReceiveBufferSize())
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, this.clientConfig.getConnectTimeoutMillis())
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    initNettyChannel(ch);
                }
            });
    }

    private void initNettyChannel(SocketChannel ch) {
        ch.pipeline()
            .addLast("encoder", new MessageEncoder())
            .addLast("decoder", new MessageDecoder())
            .addLast("idle-state-handler", new IdleStateHandler(this.clientConfig.getHeartbeatTime(), 0, 0, TimeUnit.MILLISECONDS))
            .addLast("client-handler", this.clientHandler);
    }

    public void registerProcessor(MessageType messageType, NettyRequestProcessor processor) {
        registerProcessor(messageType, processor, null);
    }

    public void registerProcessor(MessageType messageType, NettyRequestProcessor processor, ExecutorService executor) {
        this.clientHandler.registerProcessor(messageType, processor, executor);
    }

    public void send(Host host, Message message) {
        Channel channel = getChannel(host);
        if (channel == null) {
            throw new RuntimeException(String.format("connect to address : %s failed", host.getAddress()));
        }

        try {
            ChannelFuture future = channel.writeAndFlush(message).await();
            if (future.isSuccess()) {
                log.debug("send message : {} to address {} successful", message, host.getAddress());
            } else {
                String msg = String.format("send message : %s to address %s failed", message, host.getAddress());
                log.error(msg, future.cause());
                throw new RuntimeException(msg);
            }
        } catch (InterruptedException e) {
            String msg = String.format("send message : %s to address %s exception", message, host.getAddress());
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    public Channel getChannel(Host host) {
        Channel channel = this.channels.get(host);
        if (channel != null && channel.isActive()) {
            return channel;
        }
        return createChannel(host, true);
    }

    public Channel createChannel(Host host, boolean isSync) {
        ChannelFuture future;
        try {
            synchronized (this.client) {
                future = this.client.connect(new InetSocketAddress(host.getIp(), host.getPort()));
            }
            if (isSync) {
                future.sync();
            }
            if (future.isSuccess()) {
                Channel channel = future.channel();
                this.channels.put(host, channel);
                return channel;
            }
        } catch (Exception ex) {
            log.warn(String.format("connect to %s error", host), ex);
        }
        return null;
    }

    public void closeChannels() {
        for (Channel channel : this.channels.values()) {
            channel.close();
        }
        this.channels.clear();
    }

    public void closeChannel(Host host) {
        Channel channel = this.channels.remove(host);
        if (channel != null) {
            channel.close();
        }
    }

    public void close() {
        try {
            closeChannels();
        } catch (Exception e) {
            log.error("NettyRemoteClient close exception", e);
        }
        log.info("NettyRemoteClient close successful");
    }

}
