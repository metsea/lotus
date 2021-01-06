package cn.metsea.lotus.remote.processor;

import cn.metsea.lotus.remote.message.Message;
import io.netty.channel.Channel;

/**
 * Netty Request Processor
 */
public interface NettyRequestProcessor {

    /**
     * process
     *
     * @param channel
     * @param message
     */
    void process(final Channel channel, final Message message);

}
