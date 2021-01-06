package cn.metsea.lotus.remote.model;

import cn.metsea.lotus.common.constants.CommonConstants;
import lombok.Data;

/**
 * Host
 */
@Data
public class Host {

    private String ip;

    private int port;

    private String address;

    public Host(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.address = ip + CommonConstants.COLON + port;
    }

}
