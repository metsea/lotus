package cn.metsea.lotus.remote.message.heartbeat;

import cn.metsea.lotus.remote.message.Message;
import cn.metsea.lotus.remote.message.MessageConverter;
import cn.metsea.lotus.remote.message.MessageType;

/**
 * Pong
 */
public class Pong implements MessageConverter {

    public static final String PONG = "pong";

    @Override
    public Message convert() {
        Message message = new Message();
        message.setType(MessageType.PONG);
        message.setContent(PONG.getBytes());
        return message;
    }

}
