package cn.metsea.lotus.remote.utils;

import cn.metsea.lotus.common.utils.OsUtils;
import cn.metsea.lotus.remote.model.Host;
import io.netty.channel.Channel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;

/**
 * Netty Utils
 */
public class NettyUtils {

    private static final String LINUX = "linux";

    public static boolean useEpoll() {
        String osName = OsUtils.getOsName();
        if (!osName.toLowerCase().contains(LINUX)) {
            return false;
        }
        if (!Epoll.isAvailable()) {
            return false;
        }
        String nettyEpollEnable = getNettyEpollEnable();
        return Boolean.parseBoolean(nettyEpollEnable);
    }

    public static Class<? extends ServerSocketChannel> getServerSocketChannelClass() {
        if (useEpoll()) {
            return EpollServerSocketChannel.class;
        }
        return NioServerSocketChannel.class;
    }

    public static Class<? extends SocketChannel> getSocketChannelClass() {
        if (useEpoll()) {
            return EpollSocketChannel.class;
        }
        return NioSocketChannel.class;
    }

    public static String getNettyEpollEnable() {
        return System.getProperty("netty.epoll.enable", "true");
    }

    public static String getLocalAddress(Channel channel) {
        return ((InetSocketAddress) channel.localAddress()).getAddress().getHostAddress();
    }

    public static String getRemoteAddress(Channel channel) {
        return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
    }

    public static Host toAddress(Channel channel) {
        InetSocketAddress socketAddress = ((InetSocketAddress) channel.remoteAddress());
        return new Host(socketAddress.getAddress().getHostAddress(), socketAddress.getPort());
    }

}
