package cn.metsea.lotus.remote.message;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.Setter;

/**
 * Message
 */
@Getter
@Setter
public class Message implements Serializable {

    private static final AtomicLong REQUEST_ID_GENERATOR = new AtomicLong(1);

    /**
     * message type
     */
    private MessageType type;

    /**
     * request id
     */
    private long rid;

    /**
     * content
     */
    private byte[] content;

    public Message() {
        this.rid = REQUEST_ID_GENERATOR.getAndIncrement();
    }

    public Message(long rid) {
        this.rid = rid;
    }

    @Override
    public String toString() {
        return "Message{" + "type=" + this.type + ", rid=" + this.rid + ", contentLength=" + (this.content == null ? 0 : this.content.length) + '}';
    }

}
