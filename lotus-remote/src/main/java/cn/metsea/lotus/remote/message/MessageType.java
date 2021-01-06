package cn.metsea.lotus.remote.message;

import lombok.Getter;

/**
 * Message Type
 */
@Getter
public enum MessageType {

    /**
     * job execute request
     */
    JOB_EXECUTE_REQUEST(10),

    /**
     * job execute response
     */
    JOB_EXECUTE_RESPONSE(11),

    /**
     * job stop request
     */
    JOB_STOP_REQUEST(20),

    /**
     * job stop response
     */
    JOB_STOP_RESPONSE(21),

    /**
     * heartbeat
     */
    HEARTBEAT(9999),

    /**
     * ping
     */
    PING(9998),

    /**
     * pong
     */
    PONG(9997);

    private int code;

    MessageType(int code) {
        this.code = code;
    }

    public static MessageType getByCode(int code) {
        for (MessageType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

}
