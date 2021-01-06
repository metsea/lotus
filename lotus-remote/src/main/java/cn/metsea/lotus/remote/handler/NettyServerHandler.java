package cn.metsea.lotus.remote.handler;

import cn.metsea.lotus.remote.NettyRemoteServer;
import cn.metsea.lotus.remote.message.Message;
import cn.metsea.lotus.remote.message.MessageType;
import cn.metsea.lotus.remote.processor.NettyRequestProcessor;
import cn.metsea.lotus.remote.utils.NettyUtils;
import cn.metsea.lotus.remote.utils.Pair;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty Server Handler
 */
@Slf4j
@Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private final NettyRemoteServer nettyRemoteServer;

    private ConcurrentHashMap<MessageType, Pair<NettyRequestProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();

    public NettyServerHandler(NettyRemoteServer nettyRemoteServer) {
        this.nettyRemoteServer = nettyRemoteServer;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        processReceived(ctx.channel(), (Message) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("exceptionCaught", cause);
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            ctx.channel().close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    public void registerProcessor(MessageType messageType, NettyRequestProcessor processor, ExecutorService executor) {
        ExecutorService executorRef = executor;
        if (executorRef == null) {
            executorRef = this.nettyRemoteServer.getDefaultExecutor();
        }
        this.processors.putIfAbsent(messageType, new Pair<>(processor, executorRef));
    }

    private void processReceived(Channel channel, Message msg) {
        processByMessageType(channel, msg);
    }

    private void processByMessageType(Channel channel, Message message) {
        MessageType messageType = message.getType();
        Pair<NettyRequestProcessor, ExecutorService> pair = this.processors.get(messageType);
        if (pair != null) {
            try {
                pair.getRight().submit(() -> {
                    try {
                        pair.getLeft().process(channel, message);
                    } catch (Exception e) {
                        log.error("process received msg {} exception", message, e);
                    }
                });
            } catch (RejectedExecutionException e) {
                log.error("thread pool is full, discard msg {} from {}", message, NettyUtils.getRemoteAddress(channel));
            }
        } else {
            log.error("messageType {} is not support", messageType);
        }
    }

}
