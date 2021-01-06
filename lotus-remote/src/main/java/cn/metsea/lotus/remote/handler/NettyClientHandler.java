package cn.metsea.lotus.remote.handler;

import cn.metsea.lotus.remote.NettyRemoteClient;
import cn.metsea.lotus.remote.message.Message;
import cn.metsea.lotus.remote.message.MessageType;
import cn.metsea.lotus.remote.processor.NettyRequestProcessor;
import cn.metsea.lotus.remote.utils.NettyUtils;
import cn.metsea.lotus.remote.utils.Pair;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty Client Handler
 */
@Slf4j
@Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final byte[] HEARTBEAT_DATA = "heartbeat".getBytes(CharsetUtil.UTF_8);

    private final NettyRemoteClient nettyRemoteClient;

    private ExecutorService callbackExecutor;

    private ConcurrentHashMap<MessageType, Pair<NettyRequestProcessor, ExecutorService>> processors = new ConcurrentHashMap<>();

    public NettyClientHandler(NettyRemoteClient nettyRemoteClient, ExecutorService callbackExecutor) {
        this.nettyRemoteClient = nettyRemoteClient;
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        this.nettyRemoteClient.closeChannel(NettyUtils.toAddress(ctx.channel()));
        ctx.channel().close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        processReceived(ctx.channel(), (Message) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("exceptionCaught", cause);
        this.nettyRemoteClient.closeChannel(NettyUtils.toAddress(ctx.channel()));
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // heartbeat
            Message heartbeat = new Message();
            heartbeat.setType(MessageType.HEARTBEAT);
            heartbeat.setContent(HEARTBEAT_DATA);

            // write
            ctx.writeAndFlush(heartbeat).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    public void registerProcessor(MessageType messageType, NettyRequestProcessor processor, ExecutorService executor) {
        ExecutorService executorRef = executor;
        if (executorRef == null) {
            executorRef = this.nettyRemoteClient.getDefaultExecutor();
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
                log.error("thread pool is full, discard message {} from {}", message, NettyUtils.getRemoteAddress(channel));
            }
        } else {
            log.error("messageType {} is not support", messageType);
        }
    }

}
