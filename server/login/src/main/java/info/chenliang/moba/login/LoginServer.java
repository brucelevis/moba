package info.chenliang.moba.login;

import info.chenliang.moba.db.DBServer;
import info.chenliang.moba.executor.TaskExecutor;
import info.chenliang.moba.login.handler.LoginChannelInitializer;
import info.chenliang.moba.login.player.PlayerManager;
import info.chenliang.moba.match.MatchServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by chenliang on 16/5/10.
 */
public class LoginServer {
    private DBServer dbServer;
    private MatchServer matchServer;

    private void initDbServer() throws Exception {
        dbServer = new DBServer();
        dbServer.start();
    }

    private void initMatchServer() throws Exception {
        matchServer = new MatchServer();
        matchServer.start();
    }

    private void startLoginServer(int port) throws Exception{
        TaskExecutor.getInstance().init();
        PlayerManager.getInstance().init();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new LoginChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 256)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public void start(int port) throws Exception{
        initDbServer();
        initMatchServer();
        startLoginServer(port);
    }
}
