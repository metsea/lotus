package cn.metsea.lotus.remote.message.heartbeat;

import cn.metsea.lotus.remote.message.Message;
import cn.metsea.lotus.remote.message.MessageConverter;
import cn.metsea.lotus.remote.message.MessageType;

/**
 * Ping
 */
public class Ping implements MessageConverter {

    public static final String PING = "ping";

    @Override
    public Message convert() {
        Message message = new Message();
        message.setType(MessageType.PING);
        message.setContent(PING.getBytes());
        return message;
    }

}
