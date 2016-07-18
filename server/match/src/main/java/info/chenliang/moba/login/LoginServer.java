package info.chenliang.moba.login;

import info.chenliang.moba.login.handler.LoginServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by chenliang on 16/5/11.
 */
public class LoginServer {
    private int port;
    private int id;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public LoginServer(int port, int id) {
        this.port = port;
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    public int getId() {
        return id;
    }

    public void init() throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new LoginServerChannelInitializer())
            .option(ChannelOption.SO_BACKLOG, 256)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
    }
}
