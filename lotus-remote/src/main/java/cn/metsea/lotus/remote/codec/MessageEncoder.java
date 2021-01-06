package cn.metsea.lotus.remote.codec;

import cn.metsea.lotus.remote.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Message Encoder
 */
@Sharable
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        if (msg == null) {
            throw new RuntimeException("encode msg is null");
        }

        // type
        out.writeInt(msg.getType().getCode());
        // rid
        out.writeLong(msg.getRid());
        // length
        out.writeInt(msg.getContent().length);
        // content
        out.writeBytes(msg.getContent());
    }

}
