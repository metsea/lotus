package cn.metsea.lotus.remote.codec;

import cn.metsea.lotus.remote.message.Message;
import cn.metsea.lotus.remote.message.MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.util.List;

/**
 * Message Decoder
 */
public class MessageDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // type
        int type = in.readInt();
        // rid
        long rid = in.readLong();
        // length
        int length = in.readInt();
        // content
        byte[] content = new byte[length];
        in.readBytes(content);

        // packet
        Message packet = new Message(rid);
        packet.setType(MessageType.getByCode(type));
        packet.setContent(content);

        // add
        out.add(packet);
    }

}
